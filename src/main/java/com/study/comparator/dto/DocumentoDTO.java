package com.study.comparator.dto;

import com.study.comparator.annotation.DtoObserver;
import com.study.comparator.annotation.DisplayName;
import com.study.comparator.enums.TipoDoc;

@DtoObserver
public class DocumentoDTO {
    @DisplayName("Numero documento")
    private String numero;
    
    @DisplayName("Data documento")
    private String data;
    
    @DisplayName("Tipo Documento")
    private TipoDoc tipo;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public TipoDoc getTipo() {
        return tipo;
    }

    public void setTipo(TipoDoc tipo) {
        this.tipo = tipo;
    }
}