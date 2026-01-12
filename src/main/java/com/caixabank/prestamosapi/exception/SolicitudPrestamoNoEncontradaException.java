package com.caixabank.prestamosapi.exception;

import java.util.UUID;

public class SolicitudPrestamoNoEncontradaException extends RuntimeException {
    
    public SolicitudPrestamoNoEncontradaException(UUID id) {
        super("Solicitud de préstamo no encontrada con ID: " + id);
    }
}