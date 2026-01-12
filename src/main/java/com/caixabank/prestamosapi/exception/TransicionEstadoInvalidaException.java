package com.caixabank.prestamosapi.exception;

import com.caixabank.prestamosapi.modelo.EstadoSolicitud;

public class TransicionEstadoInvalidaException extends RuntimeException {
    
    public TransicionEstadoInvalidaException(EstadoSolicitud estadoActual, EstadoSolicitud nuevoEstado) {
        super(String.format("Transición de estado no válida: de %s a %s", estadoActual, nuevoEstado));
    }
}