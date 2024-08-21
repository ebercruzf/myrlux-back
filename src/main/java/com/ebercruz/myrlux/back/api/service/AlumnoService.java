package com.ebercruz.myrlux.back.api.service;

import com.ebercruz.myrlux.back.dto.AlumnoDTO;
import com.ebercruz.myrlux.back.dto.AlumnoResponse;
import com.ebercruz.myrlux.back.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AlumnoService {

    Mono<ResponseEntity<ApiResponse<AlumnoDTO>>> crearAlumno(AlumnoDTO alumnoDTO);
    Mono<ResponseEntity<ApiResponse<List<AlumnoDTO>>>>listaAlumno();
    Mono<ResponseEntity<ApiResponse<AlumnoDTO>>> obtenerAlumno(Long id);
    Mono<ResponseEntity<ApiResponse<AlumnoDTO>>> actualizarAlumno(Long id, AlumnoDTO alumnoDTO);
    Mono<ResponseEntity<ApiResponse<Void>>> eliminarAlumno(Long id);


}
