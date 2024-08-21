package com.ebercruz.myrlux.back.repository;


import com.ebercruz.myrlux.back.dto.AlumnoDTO;
import org.reactivestreams.Publisher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AlumnoRepositoryReactive implements ReactiveCrudRepository<AlumnoDTO, Long> {
    @Override
    public <S extends AlumnoDTO> Mono<S> save(S entity) {
        return null;
    }

    @Override
    public <S extends AlumnoDTO> Flux<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends AlumnoDTO> Flux<S> saveAll(Publisher<S> entityStream) {
        return null;
    }

    @Override
    public Mono<AlumnoDTO> findById(Long aLong) {
        return null;
    }

    @Override
    public Mono<AlumnoDTO> findById(Publisher<Long> id) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(Long aLong) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(Publisher<Long> id) {
        return null;
    }

    @Override
    public Flux<AlumnoDTO> findAll() {
        return null;
    }

    @Override
    public Flux<AlumnoDTO> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public Flux<AlumnoDTO> findAllById(Publisher<Long> idStream) {
        return null;
    }

    @Override
    public Mono<Long> count() {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Long aLong) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Publisher<Long> id) {
        return null;
    }

    @Override
    public Mono<Void> delete(AlumnoDTO entity) {
        return null;
    }

    @Override
    public Mono<Void> deleteAllById(Iterable<? extends Long> longs) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends AlumnoDTO> entities) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends AlumnoDTO> entityStream) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll() {
        return null;
    }
}

