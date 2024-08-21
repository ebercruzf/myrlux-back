package com.ebercruz.myrlux.back.api.controller;

import com.ebercruz.myrlux.back.api.service.AlumnoService;
import com.ebercruz.myrlux.back.dto.AlumnoDTO;
import com.ebercruz.myrlux.back.util.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class AlumnoControllerTest {

    @Mock
    private AlumnoService alumnoService;

    @InjectMocks
    private AlumnoController alumnoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void homeTest() {
        StepVerifier.create(alumnoController.home())
                .expectNext("Bienvenido a la aplicación MyrluxBack")
                .verifyComplete();
    }

    @ParameterizedTest
    @MethodSource("crearAlumnoCases")
    void crearAlumnoTest(HttpStatus status, String message, String code, boolean success) {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        ApiResponse<AlumnoDTO> apiResponse = new ApiResponse<>(status == HttpStatus.CREATED ? alumnoDTO : null, message, code, success);
        ResponseEntity<ApiResponse<AlumnoDTO>> responseEntity = ResponseEntity.status(status).body(apiResponse);

        when(alumnoService.crearAlumno(any(AlumnoDTO.class))).thenReturn(Mono.just(responseEntity));

        StepVerifier.create(alumnoController.crearAlumno(alumnoDTO))
                .expectNext(responseEntity)
                .verifyComplete();
    }

    @ParameterizedTest
    @MethodSource("listaAlumnoCases")
    void listaAlumnoTest(HttpStatus status, String message, String code, boolean success) {
        List<AlumnoDTO> alumnos = Arrays.asList(new AlumnoDTO(), new AlumnoDTO());
        ApiResponse<List<AlumnoDTO>> apiResponse = new ApiResponse<>(status == HttpStatus.OK ? alumnos : null, message, code, success);
        ResponseEntity<ApiResponse<List<AlumnoDTO>>> responseEntity = ResponseEntity.status(status).body(apiResponse);

        when(alumnoService.listaAlumno()).thenReturn(Mono.just(responseEntity));

        StepVerifier.create(alumnoController.listaAlumno())
                .expectNext(responseEntity)
                .verifyComplete();
    }

    @ParameterizedTest
    @MethodSource("obtenerAlumnoCases")
    void obtenerAlumnoTest(HttpStatus status, String message, String code, boolean success) {
        Long id = 1L;
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        ApiResponse<AlumnoDTO> apiResponse = new ApiResponse<>(status == HttpStatus.OK ? alumnoDTO : null, message, code, success);
        ResponseEntity<ApiResponse<AlumnoDTO>> responseEntity = ResponseEntity.status(status).body(apiResponse);

        when(alumnoService.obtenerAlumno(anyLong())).thenReturn(Mono.just(responseEntity));

        StepVerifier.create(alumnoController.obtenerAlumno(id))
                .expectNext(responseEntity)
                .verifyComplete();
    }

    @ParameterizedTest
    @MethodSource("actualizarAlumnoCases")
    void actualizarAlumnoTest(HttpStatus status, String message, String code, boolean success) {
        Long id = 1L;
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        ApiResponse<AlumnoDTO> apiResponse = new ApiResponse<>(status == HttpStatus.OK ? alumnoDTO : null, message, code, success);
        ResponseEntity<ApiResponse<AlumnoDTO>> responseEntity = ResponseEntity.status(status).body(apiResponse);

        when(alumnoService.actualizarAlumno(anyLong(), any(AlumnoDTO.class))).thenReturn(Mono.just(responseEntity));

        StepVerifier.create(alumnoController.actualizarAlumno(id, alumnoDTO))
                .expectNext(responseEntity)
                .verifyComplete();
    }

    @ParameterizedTest
    @MethodSource("eliminarAlumnoCases")
    void eliminarAlumnoTest(HttpStatus status, String message, String code, boolean success) {
        Long id = 1L;
        ApiResponse<Void> apiResponse = new ApiResponse<>(null, message, code, success);
        ResponseEntity<ApiResponse<Void>> responseEntity = ResponseEntity.status(status).body(apiResponse);

        when(alumnoService.eliminarAlumno(anyLong())).thenReturn(Mono.just(responseEntity));

        StepVerifier.create(alumnoController.eliminarAlumno(id))
                .expectNext(responseEntity)
                .verifyComplete();
    }

    private static Stream<Arguments> crearAlumnoCases() {
        return Stream.of(
                Arguments.of(HttpStatus.CREATED, "Alumno creado exitosamente", "201", true),
                Arguments.of(HttpStatus.BAD_REQUEST, "Error de validación", "400", false),
                Arguments.of(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", "500", false)
        );
    }

    private static Stream<Arguments> listaAlumnoCases() {
        return Stream.of(
                Arguments.of(HttpStatus.OK, "Lista de alumnos obtenida exitosamente", "200", true),
                Arguments.of(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", "500", false)
        );
    }

    private static Stream<Arguments> obtenerAlumnoCases() {
        return Stream.of(
                Arguments.of(HttpStatus.OK, "Alumno obtenido exitosamente", "200", true),
                Arguments.of(HttpStatus.NOT_FOUND, "Alumno no encontrado", "404", false),
                Arguments.of(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", "500", false)
        );
    }

    private static Stream<Arguments> actualizarAlumnoCases() {
        return Stream.of(
                Arguments.of(HttpStatus.OK, "Alumno actualizado exitosamente", "200", true),
                Arguments.of(HttpStatus.NOT_FOUND, "Alumno no encontrado", "404", false),
                Arguments.of(HttpStatus.BAD_REQUEST, "Error de validación", "400", false),
                Arguments.of(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", "500", false)
        );
    }

    private static Stream<Arguments> eliminarAlumnoCases() {
        return Stream.of(
                Arguments.of(HttpStatus.OK, "Alumno eliminado exitosamente", "200", true),
                Arguments.of(HttpStatus.NOT_FOUND, "Alumno no encontrado", "404", false),
                Arguments.of(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", "500", false)
        );
    }
}