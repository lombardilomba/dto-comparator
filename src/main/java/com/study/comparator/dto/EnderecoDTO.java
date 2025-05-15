package com.study.comparator.dto;

import com.study.comparator.annotation.DtoObserver;
import com.study.comparator.annotation.DisplayName;

@DtoObserver
public class EnderecoDTO {
    @DisplayName("Rua")
    private String rua;
    
    @DisplayName("Cidade")
    private String cidade;

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
}