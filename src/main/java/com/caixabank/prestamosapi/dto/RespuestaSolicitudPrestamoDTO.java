package com.caixabank.prestamosapi.dto;

import com.caixabank.prestamosapi.modelo.SolicitudPrestamo;
import com.caixabank.prestamosapi.modelo.EstadoSolicitud;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class RespuestaSolicitudPrestamoDTO {
    private UUID id;
    private String nombreSolicitante;
    private BigDecimal importeSolicitado;
    private String divisa;
    private String documentoIdentificativo;
    private LocalDateTime fechaCreacion;
    private EstadoSolicitud estado;

    public RespuestaSolicitudPrestamoDTO() {
    }

    public RespuestaSolicitudPrestamoDTO(SolicitudPrestamo solicitudPrestamo) {
        this.id = solicitudPrestamo.getId();
        this.nombreSolicitante = solicitudPrestamo.getNombreSolicitante();
        this.importeSolicitado = solicitudPrestamo.getImporteSolicitado();
        this.divisa = solicitudPrestamo.getDivisa();
        this.documentoIdentificativo = solicitudPrestamo.getDocumentoIdentificativo();
        this.fechaCreacion = solicitudPrestamo.getFechaCreacion();
        this.estado = solicitudPrestamo.getEstado();
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public BigDecimal getImporteSolicitado() {
        return importeSolicitado;
    }

    public void setImporteSolicitado(BigDecimal importeSolicitado) {
        this.importeSolicitado = importeSolicitado;
    }

    public String getDivisa() {
        return divisa;
    }

    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    public String getDocumentoIdentificativo() {
        return documentoIdentificativo;
    }

    public void setDocumentoIdentificativo(String documentoIdentificativo) {
        this.documentoIdentificativo = documentoIdentificativo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }
}