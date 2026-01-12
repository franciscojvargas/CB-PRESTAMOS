package com.caixabank.prestamosapi.dto;

import com.caixabank.prestamosapi.modelo.EstadoSolicitud;

import javax.validation.constraints.NotNull;

public class ActualizarEstadoDTO {
    
    @NotNull(message = "El estado es obligatorio")
    private EstadoSolicitud estado;

    public ActualizarEstadoDTO() {
    }

    public ActualizarEstadoDTO(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }
}