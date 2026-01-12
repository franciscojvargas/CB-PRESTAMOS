# CB-PRESTAMOS - API REST para Gestión de Solicitudes de Préstamos Personales

API REST desarrollada con Spring Boot para gestionar solicitudes de préstamos personales de una entidad bancaria.

## Requisitos

- Java 11 o superior
- Maven 3.6 o superior

## Estados y Transiciones

La API gestiona los siguientes estados:

- **PENDIENTE**: Estado inicial de todas las solicitudes
- **APROBADA**: Solicitud aprobada (solo desde PENDIENTE)
- **RECHAZADA**: Solicitud rechazada (solo desde PENDIENTE)
- **CANCELADA**: Solicitud cancelada (solo desde APROBADA)

### Reglas de Transición

1. **PENDIENTE** --> **APROBADA**
2. **PENDIENTE** --> **RECHAZADA**
3. **APROBADA** --> **CANCELADA**
4. Cualquier otra transición --> Error 400

Los estados **RECHAZADA** y **CANCELADA** son estados finales y no pueden ser modificados.

## Validaciones

- **nombreSolicitante**: Obligatorio, no puede estar vacío
- **importeSolicitado**: Obligatorio, debe ser mayor que 0.01
- **divisa**: Obligatorio, debe ser un código de 3 letras (ej: EUR, USD, GBP)
- **documentoIdentificativo**: Obligatorio, no puede estar vacío
- **estado**: Obligatorio al actualizar, debe ser un estado válido

## Tecnologías Utilizadas

- **Spring Boot 2.7.14**: Framework principal
- **Spring Web**: Para REST API
- **Spring Validation**: Para validación de datos
- **Java 11**: Versión de Java utilizada
- **Maven**: Gestor de dependencias

## Notas

- La fecha de creación se establece automáticamente al crear la solicitud
- El estado inicial de todas las solicitudes es **PENDIENTE**
