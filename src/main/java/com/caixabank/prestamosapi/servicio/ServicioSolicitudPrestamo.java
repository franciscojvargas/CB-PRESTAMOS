package com.caixabank.prestamosapi.servicio;

import com.caixabank.prestamosapi.dto.CrearSolicitudPrestamoDTO;
import com.caixabank.prestamosapi.exception.TransicionEstadoInvalidaException;
import com.caixabank.prestamosapi.exception.SolicitudPrestamoNoEncontradaException;
import com.caixabank.prestamosapi.modelo.SolicitudPrestamo;
import com.caixabank.prestamosapi.modelo.EstadoSolicitud;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ServicioSolicitudPrestamo {

    private final Map<java.util.UUID, SolicitudPrestamo> solicitudesPrestamos = new ConcurrentHashMap<>();

    public SolicitudPrestamo crearSolicitudPrestamo(CrearSolicitudPrestamoDTO dto) {
        SolicitudPrestamo solicitudPrestamo = new SolicitudPrestamo(
            dto.getNombreSolicitante(),
            dto.getImporteSolicitado(),
            dto.getDivisa().toUpperCase(),
            dto.getDocumentoIdentificativo()
        );
        solicitudesPrestamos.put(solicitudPrestamo.getId(), solicitudPrestamo);
        return solicitudPrestamo;
    }

    public List<SolicitudPrestamo> obtenerTodasLasSolicitudes() {
        return new ArrayList<>(solicitudesPrestamos.values());
    }

    public SolicitudPrestamo obtenerSolicitudPrestamoPorId(java.util.UUID id) {
        SolicitudPrestamo solicitudPrestamo = solicitudesPrestamos.get(id);
        if (solicitudPrestamo == null) {
            throw new SolicitudPrestamoNoEncontradaException(id);
        }
        return solicitudPrestamo;
    }

    public SolicitudPrestamo actualizarEstadoSolicitudPrestamo(java.util.UUID id, EstadoSolicitud nuevoEstado) {
        SolicitudPrestamo solicitudPrestamo = obtenerSolicitudPrestamoPorId(id);
        EstadoSolicitud estadoActual = solicitudPrestamo.getEstado();

        if (!esTransicionEstadoValida(estadoActual, nuevoEstado)) {
            throw new TransicionEstadoInvalidaException(estadoActual, nuevoEstado);
        }

        solicitudPrestamo.setEstado(nuevoEstado);
        return solicitudPrestamo;
    }

    private boolean esTransicionEstadoValida(EstadoSolicitud estadoActual, EstadoSolicitud nuevoEstado) {
        if (estadoActual == nuevoEstado) {
            return false;
        }
        
        switch (estadoActual) {
            case PENDIENTE:
                return nuevoEstado == EstadoSolicitud.APROBADA || nuevoEstado == EstadoSolicitud.RECHAZADA;
            case APROBADA:
                return nuevoEstado == EstadoSolicitud.CANCELADA;
            case RECHAZADA:
                return false;
            case CANCELADA:
                return false;
            default:
                return false;
        }
    }
}