package com.study.comparator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "campos_monitorados")
public class CampoAlterado {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campo_monitorado_seq")
    @SequenceGenerator(name = "campo_monitorado_seq", sequenceName = "seq_campo_monitorado", allocationSize = 1)
    private Long id;
    
    @Column(name = "campo", nullable = false)
    private String path;
    
    @Column(name = "campo_monitorado", nullable = false)
    private boolean campoMonitorado;
    
    @Column(name = "display_name")
    private String displayName;
    
    @Transient
    private Object valorAntes;
    
    @Transient
    private Object valorDepois;


    public CampoAlterado(String path, Object antes, Object depois, boolean monitorado, String displayName) {
        this.path = path;
        this.valorAntes = antes;
        this.valorDepois = depois;
        this.campoMonitorado = monitorado;
        this.displayName = displayName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Object getValorAntes() {
        return valorAntes;
    }

    public void setValorAntes(Object valorAntes) {
        this.valorAntes = valorAntes;
    }

    public Object getValorDepois() {
        return valorDepois;
    }

    public void setValorDepois(Object valorDepois) {
        this.valorDepois = valorDepois;
    }

    public boolean isCampoMonitorado() {
        return campoMonitorado;
    }

    public void setCampoMonitorado(boolean campoMonitorado) {
        this.campoMonitorado = campoMonitorado;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        String nomeCampo = displayName != null ? displayName : path;
        if (campoMonitorado) {
            return String.format("%s: '%s' -> '%s' => (monitorado=true)",
                    nomeCampo, valorAntes, valorDepois);
        } else {
            return String.format("%s: '%s' -> '%s' => (monitorado=false)", 
                    nomeCampo, valorAntes, valorDepois);
        }
    }
}
