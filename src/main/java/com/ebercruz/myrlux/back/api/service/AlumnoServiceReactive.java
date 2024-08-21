package com.ebercruz.myrlux.back.api.service;

import com.ebercruz.myrlux.back.dto.AlumnoDTO;
import com.ebercruz.myrlux.back.entity.AlumnoEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlumnoServiceReactive {

    public Mono<AlumnoDTO> crearAlumno(AlumnoDTO alumno);

    public Flux<AlumnoEntity> listaAlumno();

    public Mono<AlumnoEntity> obtenerAlumno(Long id);

    public Mono<AlumnoEntity> actualizarAlumno(Long id, AlumnoEntity alumno);

    public Mono<Void> eliminarAlumno(Long id);
}
