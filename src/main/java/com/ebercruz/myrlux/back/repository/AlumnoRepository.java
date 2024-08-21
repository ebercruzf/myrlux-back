package com.ebercruz.myrlux.back.repository;

import com.ebercruz.myrlux.back.entity.AlumnoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<AlumnoEntity, Long> {

    boolean existsByEmail(String email);

}
