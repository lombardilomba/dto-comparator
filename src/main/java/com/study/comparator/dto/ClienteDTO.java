package com.study.comparator.dto;

import java.util.List;

import com.study.comparator.annotation.DtoObserver;
import com.study.comparator.annotation.DisplayName;

@DtoObserver
public class ClienteDTO {
    @DisplayName("Nome do Cliente")
    private String nome;
    
    private DocumentoDTO documento;
    
    private List<EnderecoDTO> enderecoList;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public DocumentoDTO getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoDTO documento) {
        this.documento = documento;
    }

    public List<EnderecoDTO> getEnderecoList() {
        return enderecoList;
    }

    public void setEnderecoList(List<EnderecoDTO> enderecoList) {
        this.enderecoList = enderecoList;
    }
}