package com.study.comparator.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.study.comparator.annotation.DisplayName;
import com.study.comparator.entity.CampoAlterado;

public class DtoComparer {

    private static final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    /**
     * Compara um objeto DTO atual com uma versão anterior armazenada como JSON.
     * 
     * @param novoDto objeto DTO atual
     * @param jsonAntigo JSON representando a versão anterior do DTO
     * @param camposMonitorados lista de caminhos de campos a serem especialmente monitorados
     * @return lista de campos alterados com suas informações
     */
    public static List<CampoAlterado> comparar(Object novoDto, String jsonAntigo, List<String> camposMonitorados) {
        List<CampoAlterado> resultado = new ArrayList<>();
        try {
            Object antigoDto = objectMapper.readValue(jsonAntigo, novoDto.getClass());
            compararTodosRecursivamente("", novoDto, antigoDto, resultado, camposMonitorados);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    /**
     * Método recursivo para comparar todos os campos de dois objetos.
     * 
     * @param caminho caminho atual na árvore de objetos
     * @param novo objeto novo
     * @param antigo objeto antigo
     * @param resultado lista para armazenar campos alterados
     * @param camposMonitorados lista de caminhos de campos monitorados
     * @throws Exception se ocorrer erro na reflexão
     */
    private static void compararTodosRecursivamente(String caminho, Object novo, Object antigo,
                                                    List<CampoAlterado> resultado, List<String> camposMonitorados) throws Exception {
        if (novo == null && antigo == null) return;

        if (isWrapperOrString((novo != null) ? novo.getClass() : (antigo != null ? antigo.getClass() : null))) {
            if ((novo != null && !novo.equals(antigo)) || (novo == null && antigo != null)) {
                boolean monitorado = isMonitorado(caminho, camposMonitorados);
                resultado.add(new CampoAlterado(caminho, antigo, novo, monitorado, null));
            }
            return;
        }

        Class<?> clazz = (novo != null) ? novo.getClass() : antigo.getClass();
        for (Field campo : clazz.getDeclaredFields()) {
            campo.setAccessible(true);
            Object valNovo = (novo != null) ? campo.get(novo) : null;
            Object valAntigo = (antigo != null) ? campo.get(antigo) : null;

            boolean isLista = campo.getType() == List.class;
            String nomeCampo = campo.getName();

            String caminhoAtual = caminho.isEmpty() ? nomeCampo : caminho + "." + nomeCampo;
            String displayName = getDisplayName(campo);

            if (isLista) {
                @SuppressWarnings("unchecked")
                List<?> listaNova = (List<?>) (valNovo != null ? valNovo : List.of());
                @SuppressWarnings("unchecked")
                List<?> listaAntiga = (List<?>) (valAntigo != null ? valAntigo : List.of());
                int max = Math.max(listaNova.size(), listaAntiga.size());

                for (int i = 0; i < max; i++) {
                    Object itemNovo = i < listaNova.size() ? listaNova.get(i) : null;
                    Object itemAntigo = i < listaAntiga.size() ? listaAntiga.get(i) : null;
                    
                    // Para itens em listas, adicionamos o índice ao caminho
                    String caminhoItem = caminhoAtual + "[" + i + "]";
                    compararTodosRecursivamente(caminhoItem, itemNovo, itemAntigo, resultado, camposMonitorados);
                }
            } else {
                compararTodosRecursivamente(caminhoAtual, valNovo, valAntigo, resultado, camposMonitorados);
            }

            // Propagar os nomes de exibição para os subcampos
            for (CampoAlterado alt : resultado) {
                if (alt.getPath().startsWith(caminhoAtual) && alt.getDisplayName() == null && displayName != null) {
                    alt.setDisplayName(displayName);
                }
            }
        }
    }

    /**
     * Verifica se uma classe é um tipo primitivo, wrapper, ou outro tipo simples.
     * 
     * @param clazz classe a ser verificada
     * @return true se for um tipo simples, false caso contrário
     */
    private static boolean isWrapperOrString(Class<?> clazz) {
        if (clazz == null) return false;
        
        return clazz.isPrimitive() ||
                clazz.isEnum() ||
                clazz == String.class ||
                clazz == Integer.class ||
                clazz == Long.class ||
                clazz == Double.class ||
                clazz == Boolean.class ||
                clazz == BigDecimal.class ||
                clazz == LocalDate.class ||
                clazz == LocalDateTime.class;
    }

    /**
     * Verifica se um caminho está na lista de campos monitorados.
     * 
     * @param caminho caminho do campo
     * @param monitorados lista de caminhos monitorados
     * @return true se o campo estiver monitorado, false caso contrário
     */
    private static boolean isMonitorado(String caminho, List<String> monitorados) {
        if (monitorados == null || monitorados.isEmpty()) {
            return false;
        }
        
        // Verificação direta
        if (monitorados.contains(caminho)) {
            return true;
        }
        
        // Verificar padrões com coringas
        for (String padraoMonitorado : monitorados) {
            // Se o padrão termina com .* (como em cliente.enderecoList.*)
            if (padraoMonitorado.endsWith(".*")) {
                String prefixo = padraoMonitorado.substring(0, padraoMonitorado.length() - 2);
                if (caminho.startsWith(prefixo + ".")) {
                    return true;
                }
            }
            
            // Se o padrão contém um nome de lista sem índice específico
            if (caminho.matches(padraoMonitorado + "\\[\\d+\\].*")) {
                return true;
            }
        }
        
        return false;
    }

    private static String getDisplayName(Field field) {
        DisplayName annotation = field.getAnnotation(DisplayName.class);
        return annotation != null ? annotation.value() : null;
    }
}