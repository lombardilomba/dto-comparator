package com.study.comparator;

import java.util.List;

import com.study.comparator.dto.ClienteDTO;
import com.study.comparator.dto.DocumentoDTO;
import com.study.comparator.dto.EnderecoDTO;
import com.study.comparator.dto.PropostaDTO;
import com.study.comparator.entity.CampoAlterado;
import com.study.comparator.enums.TipoDoc;
import com.study.comparator.utils.DtoComparer;

public class Application {

    public static void main(String[] args) {
    	PropostaDTO novoDto = new PropostaDTO();
        novoDto.setCliente(new ClienteDTO());
        novoDto.getCliente().setNome("Novo Nome");
        novoDto.getCliente().setDocumento(new DocumentoDTO());
        novoDto.getCliente().getDocumento().setNumero("123");
        novoDto.getCliente().getDocumento().setData("2024-01-01");
        novoDto.getCliente().getDocumento().setTipo(TipoDoc.TIPO2);
        novoDto.setLoja(1);

        EnderecoDTO end1 = new EnderecoDTO();
        end1.setRua("Rua A2");
        end1.setCidade("Lisboa");

        EnderecoDTO end2 = new EnderecoDTO();
        end2.setRua("Rua B");
        end2.setCidade("Braga");


        novoDto.getCliente().setEnderecoList(List.of(end1, end2));

        String armazenado = """
            {
              "cliente": {
                "nome": "Nome da Pessoa",
                "documento": {
                  "numero": "123",
                  "data": "2024-01-01",
                  "tipo": "TIPO1"
                },
                "enderecoList": [
                  {
                    "rua": "Rua A",
                    "cidade": "Lisboa"
                  },
                  {
                    "rua": "Rua B",
                    "cidade": "Braga"
                  }
                ]
              }
            }
            """;

        List<String> camposMonitorados = List.of(
                "cliente.nome",
                "cliente.enderecoList.cidade",
                "loja"
        );

        List<CampoAlterado> mudancas = DtoComparer.comparar(novoDto, armazenado, camposMonitorados);

        mudancas.forEach(System.out::println);
    }
}
