# CB-PRESTAMOS

API REST desarrollada con Spring Boot para gestionar solicitudes de préstamos personales.

## Instrucciones para Ejecutar el Proyecto

### Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- **Java 11** o superior
- **Maven 3.6** o superior

Puedes verificar las versiones instaladas ejecutando:

```bash
java -version
mvn -version
```

### Pasos para Ejecutar

1. **Navegar al directorio del proyecto:**
   ```bash
   cd CB-PRESTAMOS
   ```

2. **Compilar el proyecto:**
   ```bash
   mvn clean compile
   ```
   Este comando compilará el código fuente y descargará las dependencias necesarias.

3. **Ejecutar la aplicación:**
   ```bash
   mvn spring-boot:run
   ```
   
   La aplicación iniciará en el puerto **8080** por defecto. Verás mensajes de inicio en la consola indicando que el servidor está corriendo.

4. **Verificar que la aplicación está funcionando:**
   
   Puedes hacer una petición GET para comprobar que el servidor responde:
   ```bash
   curl http://localhost:8080/api/prestamos
   ```
   
   Deberías recibir una respuesta JSON vacía `[]` si no hay solicitudes, o un error 404 si el endpoint no está disponible.

5. **Probar los endpoints:**
   
   A continuación se muestran ejemplos de cómo usar cada endpoint:
   
   **Crear una solicitud de préstamo:**
   ```bash
   curl -X POST http://localhost:8080/api/prestamos \
     -H "Content-Type: application/json" \
     -d '{
       "nombreSolicitante": "Juan Pérez",
       "importeSolicitado": 5000.00,
       "divisa": "EUR",
       "documentoIdentificativo": "12345678A"
     }'
   ```
   
   **Obtener todas las solicitudes:**
   ```bash
   curl http://localhost:8080/api/prestamos
   ```
   
   **Obtener una solicitud por ID:**
   ```bash
   curl http://localhost:8080/api/prestamos/{uuid}
   ```
   *Nota: Reemplaza `{uuid}` con el ID real de una solicitud.*
   
   **Actualizar el estado de una solicitud:**
   ```bash
   curl -X POST http://localhost:8080/api/prestamos/{uuid}/estado \
     -H "Content-Type: application/json" \
     -d '{"estado": "APROBADA"}'
   ```
   
   También puedes usar herramientas como **Postman** o **HTTPie** para probar los endpoints.

6. **Detener la aplicación:**
   
   Presiona `Ctrl + C` en la terminal donde está corriendo la aplicación.

### Nota Importante

**Los datos se pierden al reiniciar la aplicación** ya que utiliza almacenamiento en memoria (`ConcurrentHashMap`).

## Arquitectura y Decisiones Técnicas

### Arquitectura

Arquitectura en capas estándar de Spring Boot:

- **Controlador**: Expone endpoints REST (`ControladorSolicitudPrestamo`)
- **Servicio**: Lógica de negocio y validaciones (`ServicioSolicitudPrestamo`)
- **Modelo**: Entidades del dominio (`SolicitudPrestamo`, `EstadoSolicitud`)

Se utilizan **DTOs** para separar la presentación del modelo de dominio.

### Decisiones Técnicas Principales

- **Spring Boot 2.7.14**: Framework elegido por simplicidad y ecosistema
- **Almacenamiento en memoria** (`ConcurrentHashMap`): Almacenamiento temporal en memoria
- **UUID**: Identificadores únicos que no revelan información del volumen de solicitudes
- **BigDecimal**: Para precisión exacta en cálculos financieros
- **Bean Validation**: Validación declarativa con anotaciones (`@Valid`, `@NotBlank`, etc.)
- **Enum para estados**: Type-safety en tiempo de compilación
- **Excepciones personalizadas**: Manejo centralizado con `@ExceptionHandler`

### Estados y Transiciones

- **PENDIENTE**: Estado inicial de todas las solicitudes
- **PENDIENTE** → **APROBADA** o **RECHAZADA**
- **APROBADA** → **CANCELADA**
- **RECHAZADA** y **CANCELADA** son estados finales y no pueden modificarse

### Validaciones

- `nombreSolicitante`: Obligatorio, no puede estar vacío
- `importeSolicitado`: Obligatorio, debe ser mayor que 0.01
- `divisa`: Obligatorio, código de 3 letras mayúsculas (EUR, USD, etc.)
- `documentoIdentificativo`: Obligatorio, no puede estar vacío

**Nota**: La fecha de creación se establece automáticamente al crear la solicitud.

## Mejoras y Extensiones Futuras

### Funcionales

- **Persistencia**: Base de datos relacional con Spring Data JPA
- **Seguridad**: Spring Security con JWT y roles (ej: solo gerentes pueden aprobar)
- **Auditoría**: Historial de cambios de estado (quién, cuándo)
- **Búsqueda avanzada**: Paginación, filtros y búsqueda por nombre/documento
- **Notificaciones**: Emails al cambiar estado
- **Validaciones de negocio**: Validar DNI/NIE, límites por perfil, scoring crediticio

### Técnicas / Arquitecturales

- **Testing**: Tests unitarios
- **Observabilidad**: Logging estructurado, métricas (Micrometer/Prometheus), health checks
- **Seguridad**: HTTPS, rate limiting, validación contra ataques comunes
- **Containerización**: Docker para desarrollo y Kubernetes para producción