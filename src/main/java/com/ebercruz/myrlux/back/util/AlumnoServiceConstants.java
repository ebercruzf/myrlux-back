package com.ebercruz.myrlux.back.util;

public class AlumnoServiceConstants {


    private AlumnoServiceConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // Mensajes de éxito
    public static final String ALUMNO_CREADO = "Alumno creado exitosamente";
    public static final String ALUMNOS_RECUPERADOS = "Alumnos recuperados exitosamente";
    public static final String ALUMNO_ENCONTRADO = "Alumno encontrado exitosamente";
    public static final String ALUMNO_ACTUALIZADO = "Alumno actualizado exitosamente";
    public static final String ALUMNO_ELIMINADO = "Alumno eliminado exitosamente";

    // Mensajes de error
    public static final String ERROR_EMAIL_EXISTENTE = "Ya existe un alumno con este correo electrónico";
    public static final String ERROR_ID_INVALIDO = "ID de alumno inválido";
    public static final String ERROR_ALUMNO_NO_ENCONTRADO = "Alumno no encontrado";
    public static final String ERROR_ID_NULO = "El ID del alumno no puede ser nulo";
    public static final String ERROR_INTERNO = "Error interno del servidor";
    public static final String ERROR_NO_ALUMNOS = "No se encontraron alumnos";
    public static final String ERROR_ELIMINAR_ALUMNO = "Error interno del servidor al eliminar alumno";

    // Códigos de estado HTTP
    public static final String CODIGO_CREADO = "201";
    public static final String CODIGO_OK = "200";
    public static final String CODIGO_NO_ENCONTRADO = "404";
    public static final String CODIGO_BAD_REQUEST = "400";
    public static final String CODIGO_ERROR_INTERNO = "500";

    // Descripciones de log
    public static final String LOG_INICIANDO_VALIDACION = "Iniciando validación de AlumnoDTO: {}";
    public static final String LOG_VALIDACION_EXITOSA = "AlumnoDTO validado exitosamente";
    public static final String LOG_ERRORES_VALIDACION = "Errores de validación encontrados: {}";
    public static final String LOG_INICIANDO_ACTUALIZACION = "Iniciando actualización de alumno con ID: {}";
    public static final String LOG_ALUMNO_ACTUALIZADO = "Alumno con ID {} actualizado exitosamente";
    public static final String LOG_INICIANDO_ELIMINACION = "Iniciando eliminación de alumno con ID: {}";
    public static final String LOG_ERROR_ELIMINAR = "Error al eliminar alumno con ID {}: {}";
    public static final String LOG_ERROR_RECUPERAR = "Error al recuperar alumnos";
    public static final String LOG_ERROR_OBTENER = "Error al obtener alumno";
    public static final String LOG_ERROR_ACTUALIZAR = "Error al actualizar alumno: {}";
    public static final String LOG_INTENTO_ACTUALIZAR_NULO = "Intento de actualizar alumno con ID nulo";
    public static final String LOG_INTENTO_ELIMINAR_NULO = "Intento de eliminar alumno con ID nulo";
    public static final String LOG_INTENTO_ELIMINAR_INEXISTENTE = "Intento de eliminar alumno inexistente con ID: {}";
}
