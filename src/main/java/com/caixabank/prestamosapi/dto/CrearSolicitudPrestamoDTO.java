package com.caixabank.prestamosapi.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

public class CrearSolicitudPrestamoDTO {
    
    @NotBlank(message = "El nombre del solicitante es obligatorio")
    private String nombreSolicitante;

    @NotNull(message = "El importe solicitado es obligatorio")
    @DecimalMin(value = "0.01", message = "El importe debe ser mayor que cero")
    private BigDecimal importeSolicitado;

    @NotBlank(message = "La divisa es obligatoria")
    @Pattern(regexp = "^[A-Z]{3}$", message = "La divisa debe ser un código de 3 letras (ej: EUR, USD)")
    private String divisa;

    @NotBlank(message = "El documento identificativo es obligatorio")
    private String documentoIdentificativo;

    public CrearSolicitudPrestamoDTO() {
    }

    public CrearSolicitudPrestamoDTO(String nombreSolicitante, BigDecimal importeSolicitado, 
                                     String divisa, String documentoIdentificativo) {
        this.nombreSolicitante = nombreSolicitante;
        this.importeSolicitado = importeSolicitado;
        this.divisa = divisa;
        this.documentoIdentificativo = documentoIdentificativo;
    }

    // Getters y Setters
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
}