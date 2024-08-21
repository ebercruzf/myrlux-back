package com.ebercruz.myrlux.back.api.service.impl;


import com.ebercruz.myrlux.back.api.service.AlumnoService;
import com.ebercruz.myrlux.back.dto.AlumnoDTO;
import com.ebercruz.myrlux.back.entity.AlumnoEntity;
import com.ebercruz.myrlux.back.repository.AlumnoRepository;
import com.ebercruz.myrlux.back.util.*;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para manejar operaciones relacionadas con los alumnos.
 * Este servicio proporciona métodos para crear, recuperar, actualizar y eliminar información de alumnos.
 *
 * @author Eber Cruz (www.ebercruz.com)
 * @version 1.0
 */
@Service
public class AlumnoServiceImpl implements AlumnoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlumnoServiceImpl.class);
    private AlumnoRepository alumnoRepository;

    private AlumnoMapper alumnoMapper;

    private final Validator validator;

    /**
     * Constructor para AlumnoServiceImpl.
     *
     * @param alumnoRepository Repositorio para operaciones de persistencia de alumnos
     * @param alumnoMapper     Mapper para conversiones entre DTO y entidad de alumnos
     * @param validator        Validador para los DTOs de alumnos
     */
    public AlumnoServiceImpl(AlumnoRepository alumnoRepository, AlumnoMapper alumnoMapper, Validator validator) {
        this.alumnoRepository = alumnoRepository;
        this.alumnoMapper = alumnoMapper;
        this.validator = validator;
    }

    /**
     * Crea un nuevo alumno en la base de datos.
     *
     * @param alumnoDTO DTO con la información del alumno a crear
     * @return Mono con la respuesta que contiene el DTO del alumno creado
     */

    @Override
    public Mono<ResponseEntity<ApiResponse<AlumnoDTO>>> crearAlumno(AlumnoDTO alumnoDTO) {
        return Mono.just(alumnoDTO)
                .flatMap(this::validateAlumnoDTO)
                .flatMap(dto -> checkEmailExistence(dto.getEmail())
                        .flatMap(exists -> exists ?
                                // Si el email existe, retorna un error
                                Mono.error(new CustomerExcepction.BadRequestException(AlumnoServiceConstants.ERROR_EMAIL_EXISTENTE)) :
                                // Si no existe, continúa con el DTO
                                Mono.just(dto)
                        )) // Java 21: Expresión ternaria mejorada para mejor legibilidad en flujos reactivos
                .map(alumnoMapper::toEntityAlumno)
                .flatMap(this::saveAlumno)
                .map(alumnoMapper::toDTOAlumno)
                // Crea la respuesta exitosa
                .map(savedAlumnoDTO ->
                        ResponseEntity.status(HttpStatus.CREATED)
                                .body(new ApiResponse<>(savedAlumnoDTO, AlumnoServiceConstants.ALUMNO_CREADO, AlumnoServiceConstants.CODIGO_CREADO, true))
                )
                .onErrorResume(ex -> {
                    if (ex instanceof CustomerExcepction.BadRequestException badRequest) { // Java 21: Pattern matching for instanceof, más conciso y seguro
                        return Mono.just(ResponseEntity.badRequest()
                                .body(new ApiResponse<>(null, badRequest.getMessage(), AlumnoServiceConstants.CODIGO_BAD_REQUEST, false)));
                    }
                    return ErrorHandler.handleCreateError(ex);
                })
                .subscribeOn(Schedulers.boundedElastic());
    }


    /**
     * Valida un DTO de alumno.
     *
     * @param alumnoDTO DTO del alumno a validar
     * @return Mono con el DTO validado o un error si la validación falla
     */
    private Mono<AlumnoDTO> validateAlumnoDTO(AlumnoDTO alumnoDTO) {
        LOGGER.debug(AlumnoServiceConstants.LOG_INICIANDO_VALIDACION, alumnoDTO);
        Errors errors = new BeanPropertyBindingResult(alumnoDTO, "alumnoDTO");
        validator.validate(alumnoDTO, errors);

        record ValidationResult(boolean valid, List<String> errorMessages) {} // Java 21: Record patterns

        return Mono.just(errors)
                .map(err -> {
                    if (err.hasErrors()) {
                        // Recopila todos los mensajes de error
                        var errorMessages = err.getAllErrors().stream()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .toList(); // Java 21: toList() es ahora el método preferido para coleccionar streams en listas
                        return new ValidationResult(false, errorMessages);
                    } else {
                        return new ValidationResult(true, List.of());
                    }
                })
                .flatMap(result -> {
                    if (result.valid()) { // Java 21: Uso del método generado automáticamente por el record
                        LOGGER.debug(AlumnoServiceConstants.LOG_VALIDACION_EXITOSA);
                        return Mono.just(alumnoDTO);
                    } else {
                        LOGGER.warn(AlumnoServiceConstants.LOG_ERRORES_VALIDACION, result.errorMessages());
                        return Mono.error(new CustomerExcepction.ValidationException("Errores de validación", result.errorMessages()));
                    }
                });
    }

    /**
     * Verifica si ya existe un alumno con el email proporcionado.
     *
     * @param email Email a verificar
     * @return Mono con un booleano que indica si el email ya existe
     */
    private Mono<Boolean> checkEmailExistence(String email) {
        // Ejecuta la verificación en un hilo separado para no bloquear
        return Mono.fromCallable(() -> alumnoRepository.existsByEmail(email))
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Guarda una entidad de alumno en la base de datos.
     *
     * @param alumnoEntity Entidad de alumno a guardar
     * @return Mono con la entidad de alumno guardada
     */
    private Mono<AlumnoEntity> saveAlumno(AlumnoEntity alumnoEntity) {
        // Ejecuta la operación de guardado en un hilo separado para no bloquear
        return Mono.fromCallable(() -> alumnoRepository.save(alumnoEntity))
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Recupera una lista de todos los alumnos de la base de datos.
     *
     * @return Mono con la respuesta que contiene una lista de DTOs de alumnos
     *
     * Es reactivo y no bloqueante, gracias al uso de Mono y Schedulers.boundedElastic().
     *
     */
    @Override
    public Mono<ResponseEntity<ApiResponse<List<AlumnoDTO>>>> listaAlumno() {
        return Mono.fromCallable(() -> alumnoRepository.findAll().stream() //Se usa fromCallable para ejecutar esta operación de manera asíncrona y no bloquear el hilo principal.
                        .map(alumnoMapper::toDTOAlumno)
                        .toList()) // Java 16+: Uso de toList() para una colección más eficiente de streams
                .map(list -> switch (list) { // Java 21: Switch expression con pattern matching para un control de flujo más expresivo
                    case List<AlumnoDTO> l when l.isEmpty() ->
                            ResponseEntity.status(HttpStatus.NOT_FOUND)
                                    .body(new ApiResponse<List<AlumnoDTO>>(null, AlumnoServiceConstants.ERROR_NO_ALUMNOS, AlumnoServiceConstants.CODIGO_NO_ENCONTRADO, false));
                    case List<AlumnoDTO> l ->
                            ResponseEntity.ok(new ApiResponse<List<AlumnoDTO>>(l, AlumnoServiceConstants.ALUMNOS_RECUPERADOS, AlumnoServiceConstants.CODIGO_OK, true));
                    // Java 21: Pattern matching for switch permite un manejo más expresivo de casos
                })
                .onErrorResume(ex -> {
                    LOGGER.error(AlumnoServiceConstants.LOG_ERROR_RECUPERAR, ex);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ApiResponse<List<AlumnoDTO>>(null, AlumnoServiceConstants.ERROR_INTERNO, AlumnoServiceConstants.CODIGO_ERROR_INTERNO, false)));
                })
                .subscribeOn(Schedulers.boundedElastic());  //boundedElastic Proporciona un pool de hilos elástico y limitado permitiendo que otras operaciones continúen sin bloqueo
    }

    /**
     * Obtiene un alumno específico por su ID.
     *
     * @param id ID del alumno a buscar
     * @return Mono con la respuesta que contiene el DTO del alumno encontrado
     */
    @Override
    public Mono<ResponseEntity<ApiResponse<AlumnoDTO>>> obtenerAlumno(Long id) {
        // Valida el ID antes de proceder
        if (id == null || id <= 0) {
            return Mono.just(ResponseEntity.badRequest()
                    .body(new ApiResponse<AlumnoDTO>(null, AlumnoServiceConstants.ERROR_ID_INVALIDO, AlumnoServiceConstants.CODIGO_BAD_REQUEST, false)));
        }

        return Mono.fromCallable(() -> alumnoRepository.findById(id))
                .map(optionalAlumno -> {
                    if (optionalAlumno.isPresent()) {
                        // Si el alumno existe, lo convierte a DTO y retorna una respuesta exitosa
                        AlumnoEntity alumnoEntity = optionalAlumno.get();
                        AlumnoDTO alumnoDTO = alumnoMapper.toDTOAlumno(alumnoEntity);
                        return ResponseEntity.ok(new ApiResponse<AlumnoDTO>(alumnoDTO, AlumnoServiceConstants.ALUMNO_ENCONTRADO, AlumnoServiceConstants.CODIGO_OK, true));
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ApiResponse<AlumnoDTO>(null, AlumnoServiceConstants.ERROR_ALUMNO_NO_ENCONTRADO, AlumnoServiceConstants.CODIGO_NO_ENCONTRADO, false));
                    }
                })
                // Maneja errores que puedan ocurrir durante la búsqueda
                .onErrorResume(ex -> {
                    LOGGER.error(AlumnoServiceConstants.LOG_ERROR_OBTENER, ex);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ApiResponse<AlumnoDTO>(null, AlumnoServiceConstants.ERROR_INTERNO, AlumnoServiceConstants.CODIGO_ERROR_INTERNO, false)));
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Actualiza la información de un alumno existente.
     *
     * @param id        ID del alumno a actualizar
     * @param alumnoDTO DTO con la nueva información del alumno
     * @return Mono con la respuesta que contiene el DTO del alumno actualizado
     */
    @Override
    public Mono<ResponseEntity<ApiResponse<AlumnoDTO>>> actualizarAlumno(Long id, AlumnoDTO alumnoDTO) {
        LOGGER.debug(AlumnoServiceConstants.LOG_INICIANDO_ACTUALIZACION, id);
        return Mono.just(id)
                .flatMap(idValue -> switch (idValue) { // Java 21: Switch expression con pattern matching
                    case null -> {
                        LOGGER.warn(AlumnoServiceConstants.LOG_INTENTO_ACTUALIZAR_NULO);
                        yield Mono.just(ResponseEntity.badRequest()
                                .body(new ApiResponse<AlumnoDTO>(null, AlumnoServiceConstants.ERROR_ID_NULO, AlumnoServiceConstants.CODIGO_BAD_REQUEST, false))); // Tipo específico AlumnoDTO
                    }
                    case Long validId -> validateAlumnoDTO(alumnoDTO)
                            .flatMap(validatedDTO -> Mono.fromCallable(() -> alumnoRepository.findById(validId))
                                    .flatMap(optionalAlumno -> optionalAlumno
                                            .map(existingAlumno -> {
                                                alumnoMapper.updateAlumnoFromDTO(validatedDTO, existingAlumno);
                                                return saveAlumno(existingAlumno);
                                            })
                                            .orElseGet(() -> {
                                                LOGGER.warn(AlumnoServiceConstants.ERROR_ALUMNO_NO_ENCONTRADO, validId);
                                                return Mono.error(new CustomerExcepction.ResourceNotFoundException(AlumnoServiceConstants.ERROR_ALUMNO_NO_ENCONTRADO));
                                            })
                                    ) // Java 21: Uso mejorado de Optional con map y orElseGet
                            )
                            .map(alumnoMapper::toDTOAlumno)
                            .map(updatedAlumnoDTO -> {
                                LOGGER.info(AlumnoServiceConstants.LOG_ALUMNO_ACTUALIZADO, validId);
                                return ResponseEntity.ok(new ApiResponse<AlumnoDTO>(updatedAlumnoDTO, AlumnoServiceConstants.ALUMNO_ACTUALIZADO, AlumnoServiceConstants.CODIGO_OK, true)); // Tipo específico AlumnoDTO
                            });
                }) // Java 21: Fin del switch expression
                .onErrorResume(ex -> {
                    if (ex instanceof CustomerExcepction.ResourceNotFoundException resourceNotFound) { // Java 21: Pattern matching for instanceof
                        LOGGER.error(AlumnoServiceConstants.LOG_ERROR_ACTUALIZAR, resourceNotFound.getMessage());
                        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ApiResponse<AlumnoDTO>(null, resourceNotFound.getMessage(), AlumnoServiceConstants.CODIGO_NO_ENCONTRADO, false))); // Tipo específico AlumnoDTO
                    }
                    LOGGER.error(AlumnoServiceConstants.LOG_ERROR_ACTUALIZAR, ex.getMessage());
                    return ErrorHandler.handleUpdateError(ex);
                })
                .subscribeOn(Schedulers.boundedElastic()); // Java 21: Uso de Schedulers.boundedElastic() para operaciones bloqueantes
    }


    /**
     * Elimina un alumno de la base de datos.
     *
     * @param id ID del alumno a eliminar
     * @return Mono con la respuesta indicando el resultado de la operación
     */
    @Override
    public Mono<ResponseEntity<ApiResponse<Void>>> eliminarAlumno(Long id) {
        LOGGER.debug(AlumnoServiceConstants.LOG_INICIANDO_ELIMINACION, id);
        if (id == null) {
            LOGGER.warn(AlumnoServiceConstants.LOG_INTENTO_ELIMINAR_NULO);
            return Mono.just(ResponseEntity.badRequest()
                    .body(new ApiResponse<>(null, AlumnoServiceConstants.ERROR_ID_NULO, AlumnoServiceConstants.CODIGO_BAD_REQUEST, false)));
        }

        return Mono.fromCallable(() -> alumnoRepository.findById(id))
                .flatMap(optionalAlumno -> {
                    if (optionalAlumno.isPresent()) {
                        return Mono.fromRunnable(() -> alumnoRepository.deleteById(id))
                                .then(Mono.just(ResponseEntity.ok(new ApiResponse<Void>(null, AlumnoServiceConstants.ALUMNO_ELIMINADO, AlumnoServiceConstants.CODIGO_OK, true))));
                    } else {
                        LOGGER.warn(AlumnoServiceConstants.LOG_INTENTO_ELIMINAR_INEXISTENTE, id);
                        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ApiResponse<Void>(null, AlumnoServiceConstants.ERROR_ALUMNO_NO_ENCONTRADO, AlumnoServiceConstants.CODIGO_NO_ENCONTRADO, false)));
                    }
                })
                .onErrorResume(ex -> {
                    LOGGER.error(AlumnoServiceConstants.LOG_ERROR_ELIMINAR, id, ex.getMessage(), ex);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ApiResponse<>(null, AlumnoServiceConstants.ERROR_ELIMINAR_ALUMNO, AlumnoServiceConstants.CODIGO_ERROR_INTERNO, false)));
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}
