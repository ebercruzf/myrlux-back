package com.ebercruz.myrlux.back.api.service.impl;

import com.ebercruz.myrlux.back.dto.AlumnoDTO;
import com.ebercruz.myrlux.back.entity.AlumnoEntity;
import com.ebercruz.myrlux.back.repository.AlumnoRepository;
import com.ebercruz.myrlux.back.util.AlumnoMapper;
import com.ebercruz.myrlux.back.util.CustomerExcepction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AlumnoServiceImplTest {

    @Mock
    private AlumnoRepository alumnoRepository;

    @Mock
    private AlumnoMapper alumnoMapper;

    @Mock
    private Validator validator;

    @InjectMocks
    private AlumnoServiceImpl alumnoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearAlumno_Success() {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        AlumnoEntity alumnoEntity = new AlumnoEntity();

        // Update this line
        doNothing().when(validator).validate(any(), any(Errors.class));

        when(alumnoRepository.existsByEmail(anyString())).thenReturn(false);
        when(alumnoMapper.toEntityAlumno(any())).thenReturn(alumnoEntity);
        when(alumnoRepository.save(any())).thenReturn(alumnoEntity);
        when(alumnoMapper.toDTOAlumno(any())).thenReturn(alumnoDTO);

        StepVerifier.create(alumnoService.crearAlumno(alumnoDTO))
                .expectNextMatches(response ->
                        response.getStatusCode() == HttpStatus.CREATED &&
                                response.getBody().getData() == alumnoDTO &&
                                response.getBody().isSuccess()
                )
                .verifyComplete();

        // Verify that validate was called
        verify(validator).validate(eq(alumnoDTO), any(Errors.class));
    }

    @Test
    void crearAlumno_EmailExists() {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setEmail("existing@example.com");

        doNothing().when(validator).validate(any(), any(Errors.class));
        when(alumnoRepository.existsByEmail("existing@example.com")).thenReturn(true);

        StepVerifier.create(alumnoService.crearAlumno(alumnoDTO))
                .expectNextMatches(response ->
                        response.getStatusCode() == HttpStatus.BAD_REQUEST &&
                                !response.getBody().isSuccess() &&
                                response.getBody().getMessage().contains("Ya existe un alumno con este correo electrónico")
                )
                .verifyComplete();

        verify(validator).validate(eq(alumnoDTO), any(Errors.class));
        verify(alumnoRepository).existsByEmail("existing@example.com");
    }

    @Test
    void crearAlumno_EmailDoesNotExist() {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setEmail("new@example.com");
        AlumnoEntity alumnoEntity = new AlumnoEntity();

        doNothing().when(validator).validate(any(), any(Errors.class));
        when(alumnoRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(alumnoMapper.toEntityAlumno(any())).thenReturn(alumnoEntity);
        when(alumnoRepository.save(any())).thenReturn(alumnoEntity);
        when(alumnoMapper.toDTOAlumno(any())).thenReturn(alumnoDTO);

        StepVerifier.create(alumnoService.crearAlumno(alumnoDTO))
                .expectNextMatches(response ->
                        response.getStatusCode() == HttpStatus.CREATED &&
                                response.getBody().isSuccess() &&
                                response.getBody().getData() == alumnoDTO
                )
                .verifyComplete();

        verify(validator).validate(eq(alumnoDTO), any(Errors.class));
        verify(alumnoRepository).existsByEmail("new@example.com");
        verify(alumnoRepository).save(any());
    }

    @Test
    void listaAlumno_Success() {
        List<AlumnoEntity> alumnoEntities = Arrays.asList(new AlumnoEntity(), new AlumnoEntity());
        List<AlumnoDTO> alumnoDTOs = Arrays.asList(new AlumnoDTO(), new AlumnoDTO());
        when(alumnoRepository.findAll()).thenReturn(alumnoEntities);
        when(alumnoMapper.toDTOAlumno(any())).thenReturn(alumnoDTOs.get(0), alumnoDTOs.get(1));

        StepVerifier.create(alumnoService.listaAlumno())
                .expectNextMatches(response ->
                        response.getStatusCode() == HttpStatus.OK &&
                                response.getBody().getData().size() == 2 &&
                                response.getBody().isSuccess()
                )
                .verifyComplete();
    }

    @Test
    void listaAlumno_Empty() {
        when(alumnoRepository.findAll()).thenReturn(Collections.emptyList());

        StepVerifier.create(alumnoService.listaAlumno())
                .expectNextMatches(response ->
                        response.getStatusCode() == HttpStatus.NOT_FOUND &&
                                !response.getBody().isSuccess()
                )
                .verifyComplete();
    }

    @Test
    void obtenerAlumno_Success() {
        Long id = 1L;
        AlumnoEntity alumnoEntity = new AlumnoEntity();
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        when(alumnoRepository.findById(id)).thenReturn(Optional.of(alumnoEntity));
        when(alumnoMapper.toDTOAlumno(alumnoEntity)).thenReturn(alumnoDTO);

        StepVerifier.create(alumnoService.obtenerAlumno(id))
                .expectNextMatches(response ->
                        response.getStatusCode() == HttpStatus.OK &&
                                response.getBody().getData() == alumnoDTO &&
                                response.getBody().isSuccess()
                )
                .verifyComplete();
    }

    @Test
    void obtenerAlumno_NotFound() {
        Long id = 1L;
        when(alumnoRepository.findById(id)).thenReturn(Optional.empty());

        StepVerifier.create(alumnoService.obtenerAlumno(id))
                .expectNextMatches(response ->
                        response.getStatusCode() == HttpStatus.NOT_FOUND &&
                                !response.getBody().isSuccess()
                )
                .verifyComplete();
    }

    @Test
    void actualizarAlumno_Success() {
        Long id = 1L;
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        AlumnoEntity existingAlumno = new AlumnoEntity();

        // Update validation mocking
        doNothing().when(validator).validate(any(), any(Errors.class));

        when(alumnoRepository.findById(id)).thenReturn(Optional.of(existingAlumno));
        when(alumnoRepository.save(any())).thenReturn(existingAlumno);
        when(alumnoMapper.toDTOAlumno(any())).thenReturn(alumnoDTO);

        StepVerifier.create(alumnoService.actualizarAlumno(id, alumnoDTO))
                .expectNextMatches(response ->
                        response.getStatusCode() == HttpStatus.OK &&
                                response.getBody().getData() == alumnoDTO &&
                                response.getBody().isSuccess()
                )
                .verifyComplete();

        // Verify that validate was called
        verify(validator).validate(eq(alumnoDTO), any(Errors.class));
    }

    @Test
    void actualizarAlumno_NotFound() {
        Long id = 1L;
        AlumnoDTO alumnoDTO = new AlumnoDTO();

        // Update validation mocking
        doNothing().when(validator).validate(any(), any(Errors.class));

        when(alumnoRepository.findById(id)).thenReturn(Optional.empty());

        StepVerifier.create(alumnoService.actualizarAlumno(id, alumnoDTO))
                .expectNextMatches(response ->
                        response.getStatusCode() == HttpStatus.NOT_FOUND &&
                                !response.getBody().isSuccess()
                )
                .verifyComplete();

        // Verify that validate was called
        verify(validator).validate(eq(alumnoDTO), any(Errors.class));
    }

    @Test
    void actualizarAlumno_ValidationError() {
        Long id = 1L;
        AlumnoDTO alumnoDTO = new AlumnoDTO();

        doAnswer(invocation -> {
            Errors errors = invocation.getArgument(1);
            errors.rejectValue("nombre", "error.code", "error message");
            return null;
        }).when(validator).validate(any(), any(Errors.class));

        StepVerifier.create(alumnoService.actualizarAlumno(id, alumnoDTO))
                .expectNextMatches(response ->
                        response.getStatusCode() == HttpStatus.BAD_REQUEST &&
                                !response.getBody().isSuccess() &&
                                response.getBody().getMessage().equals("Errores de validación") &&
                                response.getBody().getErrors().contains("error message")
                )
                .verifyComplete();
    }

    @Test
    void eliminarAlumno_Success() {
        Long id = 1L;
        when(alumnoRepository.findById(id)).thenReturn(Optional.of(new AlumnoEntity()));
        doNothing().when(alumnoRepository).deleteById(id);

        StepVerifier.create(alumnoService.eliminarAlumno(id))
                .expectNextMatches(response ->
                        response.getStatusCode() == HttpStatus.OK &&
                                response.getBody().isSuccess() &&
                                response.getBody().getMessage().equals("Alumno eliminado exitosamente")
                )
                .verifyComplete();

        verify(alumnoRepository).findById(id);
        verify(alumnoRepository).deleteById(id);
    }

    @Test
    void eliminarAlumno_NotFound() {
        Long id = 1L;
        when(alumnoRepository.findById(id)).thenReturn(Optional.empty());

        StepVerifier.create(alumnoService.eliminarAlumno(id))
                .expectNextMatches(response ->
                        response.getStatusCode() == HttpStatus.NOT_FOUND &&
                                !response.getBody().isSuccess() &&
                                response.getBody().getMessage().equals("Alumno no encontrado")
                )
                .verifyComplete();

        verify(alumnoRepository).findById(id);
        verify(alumnoRepository, never()).deleteById(any());
    }

    @Test
    void eliminarAlumno_InternalServerError() {
        Long id = 1L;
        when(alumnoRepository.findById(id)).thenThrow(new RuntimeException("Database error"));

        StepVerifier.create(alumnoService.eliminarAlumno(id))
                .expectNextMatches(response ->
                        response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR &&
                                !response.getBody().isSuccess() &&
                                response.getBody().getMessage().equals("Error interno del servidor al eliminar alumno")
                )
                .verifyComplete();

        verify(alumnoRepository).findById(id);
        verify(alumnoRepository, never()).deleteById(any());
    }


    @Test
    void crearAlumno_ValidInput() {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setEmail("new@example.com");
        AlumnoEntity alumnoEntity = new AlumnoEntity();

        doNothing().when(validator).validate(any(), any(Errors.class));
        when(alumnoRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(alumnoMapper.toEntityAlumno(any())).thenReturn(alumnoEntity);
        when(alumnoRepository.save(any())).thenReturn(alumnoEntity);
        when(alumnoMapper.toDTOAlumno(any())).thenReturn(alumnoDTO);

        StepVerifier.create(alumnoService.crearAlumno(alumnoDTO))
                .expectNextMatches(response ->
                        response.getStatusCode() == HttpStatus.CREATED &&
                                response.getBody().isSuccess() &&
                                response.getBody().getData() == alumnoDTO
                )
                .verifyComplete();

        verify(validator).validate(eq(alumnoDTO), any(Errors.class));
        verify(alumnoRepository).existsByEmail("new@example.com");
        verify(alumnoRepository).save(any());
    }

    @Test
    void crearAlumno_InvalidInput() {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setEmail("invalid@example.com");

        doAnswer(invocation -> {
            Errors errors = invocation.getArgument(1);
            errors.rejectValue("email", "invalid.email", "Invalid email format");
            return null;
        }).when(validator).validate(any(), any(Errors.class));

        StepVerifier.create(alumnoService.crearAlumno(alumnoDTO))
                .expectNextMatches(response ->
                        response.getStatusCode() == HttpStatus.BAD_REQUEST &&
                                !response.getBody().isSuccess() &&
                                response.getBody().getMessage().contains("Errores de validación")
                )
                .verifyComplete();

        verify(validator).validate(eq(alumnoDTO), any(Errors.class));
        verify(alumnoRepository, never()).existsByEmail(anyString());
        verify(alumnoRepository, never()).save(any());
    }



}
