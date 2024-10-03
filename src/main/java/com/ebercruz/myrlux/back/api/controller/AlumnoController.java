package com.ebercruz.myrlux.back.api.controller;

import com.ebercruz.myrlux.back.api.service.AlumnoService;
import com.ebercruz.myrlux.back.dto.AlumnoDTO;
import com.ebercruz.myrlux.back.dto.AlumnoResponse;
import com.ebercruz.myrlux.back.util.ApiException;
import com.ebercruz.myrlux.back.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Controlador REST para la gestión de alumnos.
 *
 * Este controlador maneja las operaciones CRUD para la entidad Alumno.
 * Utiliza programación reactiva con Spring WebFlux para manejar las solicitudes
 * de manera no bloqueante.
 *
 * Características clave:
 * - Implementa operaciones CRUD para alumnos (crear, leer, actualizar, eliminar).
 * - Utiliza validación de entrada con la anotación @Valid.
 * - Las respuestas están envueltas en objetos ApiResponse para consistencia.
 * - Interactúa con AlumnoService para la lógica de negocio.
 *
 * Manejo de Excepciones:
 * Aunque no se ve explícitamente en el código, este controlador se beneficia
 * del GlobalExceptionHandler (que se definio con  @RestControllerAdvice)  para manejar excepciones.
 * Cualquier excepción no manejada aquí será interceptada y procesada por el GlobalExceptionHandler,
 * asegurando respuestas de error consistentes en toda la aplicación.
 *
 * @author Eber Cruz (www.ebercruz.com)
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "${spring.application.api-path}")
@Order(Ordered.HIGHEST_PRECEDENCE)
@Tag(name = "Alumno", description = "API para la gestión de alumnos")
public class AlumnoController implements AlumnoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlumnoController.class);
    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping("/home")
    public Mono<String> home() {
        LOGGER.info("Processing home APIController: {}");
        return Mono.just("Bienvenido a la aplicación MyrluxBack");
    }

    @PostMapping("crear/alumno")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un nuevo alumno")
    public Mono<ResponseEntity<ApiResponse<AlumnoDTO>>> crearAlumno(@Valid @RequestBody AlumnoDTO alumnoDTO) {
        return alumnoService.crearAlumno(alumnoDTO);
    }

    @GetMapping("/lista/alumno")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtener todos los alumnos")
    public Mono<ResponseEntity<ApiResponse<List<AlumnoDTO>>>> listaAlumno() {
        return alumnoService.listaAlumno();
    }

    @GetMapping("/obtener/alumno/{id}")
    @Operation(summary = "Obtener un alumno por ID")
    public Mono<ResponseEntity<ApiResponse<AlumnoDTO>>> obtenerAlumno(@PathVariable Long id) {
        return alumnoService.obtenerAlumno(id);
    }

    @PutMapping("/actualizar/alumno/{id}")
    @Operation(summary = "Actualizar un alumno")
    public Mono<ResponseEntity<ApiResponse<AlumnoDTO>>> actualizarAlumno(@PathVariable Long id, @Valid @RequestBody AlumnoDTO alumnoDTO) {
        return alumnoService.actualizarAlumno(id, alumnoDTO);
    }

    @DeleteMapping("/eliminar/alumno/{id}")
    @Operation(summary = "Eliminar un alumno")
    public Mono<ResponseEntity<ApiResponse<Void>>> eliminarAlumno(@PathVariable Long id) {
        return alumnoService.eliminarAlumno(id);
    }
}

