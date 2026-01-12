package com.caixabank.prestamosapi.controlador;

import com.caixabank.prestamosapi.dto.CrearSolicitudPrestamoDTO;
import com.caixabank.prestamosapi.dto.RespuestaSolicitudPrestamoDTO;
import com.caixabank.prestamosapi.dto.ActualizarEstadoDTO;
import com.caixabank.prestamosapi.exception.TransicionEstadoInvalidaException;
import com.caixabank.prestamosapi.exception.SolicitudPrestamoNoEncontradaException;
import com.caixabank.prestamosapi.modelo.SolicitudPrestamo;
import com.caixabank.prestamosapi.servicio.ServicioSolicitudPrestamo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/prestamos")
@Validated
public class ControladorSolicitudPrestamo {

    private final ServicioSolicitudPrestamo servicioSolicitudPrestamo;

    public ControladorSolicitudPrestamo(ServicioSolicitudPrestamo servicioSolicitudPrestamo) {
        this.servicioSolicitudPrestamo = servicioSolicitudPrestamo;
    }

    @PostMapping
    public ResponseEntity<RespuestaSolicitudPrestamoDTO> crearSolicitudPrestamo(
            @Valid @RequestBody CrearSolicitudPrestamoDTO dto) {
        SolicitudPrestamo solicitudPrestamo = servicioSolicitudPrestamo.crearSolicitudPrestamo(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RespuestaSolicitudPrestamoDTO(solicitudPrestamo));
    }

    @GetMapping
    public ResponseEntity<List<RespuestaSolicitudPrestamoDTO>> obtenerTodasLasSolicitudes() {
        List<SolicitudPrestamo> solicitudesPrestamos = servicioSolicitudPrestamo.obtenerTodasLasSolicitudes();
        List<RespuestaSolicitudPrestamoDTO> respuestasDTO = solicitudesPrestamos.stream()
                .map(RespuestaSolicitudPrestamoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(respuestasDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaSolicitudPrestamoDTO> obtenerSolicitudPrestamoPorId(@PathVariable UUID id) {
        SolicitudPrestamo solicitudPrestamo = servicioSolicitudPrestamo.obtenerSolicitudPrestamoPorId(id);
        return ResponseEntity.ok(new RespuestaSolicitudPrestamoDTO(solicitudPrestamo));
    }

    @PostMapping("/{id}/estado")
    public ResponseEntity<RespuestaSolicitudPrestamoDTO> actualizarEstadoSolicitudPrestamo(
            @PathVariable UUID id,
            @Valid @RequestBody ActualizarEstadoDTO dto) {
        SolicitudPrestamo solicitudPrestamo = servicioSolicitudPrestamo.actualizarEstadoSolicitudPrestamo(id, dto.getEstado());
        return ResponseEntity.ok(new RespuestaSolicitudPrestamoDTO(solicitudPrestamo));
    }

    @ExceptionHandler(SolicitudPrestamoNoEncontradaException.class)
    public ResponseEntity<String> manejarSolicitudPrestamoNoEncontrada(SolicitudPrestamoNoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(TransicionEstadoInvalidaException.class)
    public ResponseEntity<String> manejarTransicionEstadoInvalida(TransicionEstadoInvalidaException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<String> manejarExcepcionesValidacion(
            org.springframework.web.bind.MethodArgumentNotValidException ex) {
        StringBuilder errores = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores.toString());
    }
}