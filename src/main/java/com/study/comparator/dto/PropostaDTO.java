package com.study.comparator.dto;

import com.study.comparator.annotation.DisplayName;

public class PropostaDTO {
	
    private ClienteDTO cliente;
    
    @DisplayName("Loja")
    private Integer loja;

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public Integer getLoja() {
        return loja;
    }

    public void setLoja(Integer loja) {
        this.loja = loja;
    }
}