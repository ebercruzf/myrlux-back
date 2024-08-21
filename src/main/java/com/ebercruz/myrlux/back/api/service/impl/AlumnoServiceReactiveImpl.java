package com.ebercruz.myrlux.back.api.service.impl;


import com.ebercruz.myrlux.back.api.service.AlumnoServiceReactive;
import com.ebercruz.myrlux.back.dto.AlumnoDTO;
import com.ebercruz.myrlux.back.entity.AlumnoEntity;
import com.ebercruz.myrlux.back.repository.AlumnoRepository;
import com.ebercruz.myrlux.back.repository.AlumnoRepositoryReactive;
import com.ebercruz.myrlux.back.util.AlumnoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AlumnoServiceReactiveImpl implements AlumnoServiceReactive {

    @Autowired
    private AlumnoRepositoryReactive alumnoRepositoryReactive;

    @Autowired
    private AlumnoMapper alumnoMapper;

    @Override
    public Mono<AlumnoDTO> crearAlumno(AlumnoDTO alumnoDTO) {
       /* return Mono.just(alumnoDTO)
                .map(alumnoMapper::toEntity)
                .flatMap(alumnoRepositoryReactive::save)
                .map(alumnoMapper::toDTO);*/

        return null;
    }

    @Override
    public Flux<AlumnoEntity> listaAlumno() {
        return null;
    }

    @Override
    public Mono<AlumnoEntity> obtenerAlumno(Long id) {
        return null;
    }

    @Override
    public Mono<AlumnoEntity> actualizarAlumno(Long id, AlumnoEntity alumno) {
        return null;
    }

    @Override
    public Mono<Void> eliminarAlumno(Long id) {
        return null;
    }
}
