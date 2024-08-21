package com.ebercruz.myrlux.back.util;
import com.ebercruz.myrlux.back.dto.AlumnoDTO;
import com.ebercruz.myrlux.back.entity.AlumnoEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class AlumnoMapper {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public AlumnoEntity toEntityAlumno(AlumnoDTO dto) {
        if (dto == null) {
            return null;
        }

        AlumnoEntity entity = new AlumnoEntity();


        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setFechaNacimiento(parseLocalDate(dto.getFechaNacimiento()));
        entity.setGenero(dto.getGenero());
        entity.setDireccion(dto.getDireccion());
        entity.setTelefono(dto.getTelefono());
        entity.setEmail(dto.getEmail());
        entity.setFechaIngreso(parseLocalDate(dto.getFechaIngreso()));
        return entity;
    }

    public AlumnoDTO toDTOAlumno(AlumnoEntity entity) {
        if (entity == null) {
            return null;
        }

        AlumnoDTO dto = new AlumnoDTO();
        dto.setId(entity.getId().toString());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setFechaNacimiento(formatLocalDate(entity.getFechaNacimiento()));
        dto.setGenero(entity.getGenero());
        dto.setDireccion(entity.getDireccion());
        dto.setTelefono(entity.getTelefono());
        dto.setEmail(entity.getEmail());
        dto.setFechaIngreso(formatLocalDate(entity.getFechaIngreso()));
        return dto;
    }

    private LocalDate parseLocalDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateString, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            // Considerar lanzar una excepción personalizada o loguear el error
            return null;
        }
    }

    private String formatLocalDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : null;
    }

    public void updateAlumnoFromDTO(AlumnoDTO dto, AlumnoEntity entity){

        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setFechaNacimiento(parseLocalDate(dto.getFechaNacimiento()));
        entity.setGenero(dto.getGenero());
        entity.setDireccion(dto.getDireccion());
        entity.setTelefono(dto.getTelefono());
        entity.setEmail(dto.getEmail());
        entity.setFechaIngreso(parseLocalDate(dto.getFechaIngreso()));

    }
}