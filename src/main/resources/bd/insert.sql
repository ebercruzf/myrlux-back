-- Insertando datos en la tabla Alumnos
INSERT INTO Alumnos (nombre, apellido, fecha_nacimiento, genero, direccion, telefono, email, fecha_ingreso)
VALUES
    ('Juan', 'Pérez', '2000-05-15', 'M', 'Calle Principal 123', '5558981234', 'juan.perez@email.com', '2022-09-01'),
    ('María', 'González', '2001-08-01', 'F', 'Avenida Central 456', '5553335678', 'maria.gonzalez@email.com', '2022-09-01');

-- Insertando datos en la tabla Cursos
INSERT INTO Cursos (nombre_curso, descripcion, creditos)
VALUES
    ('Matemáticas I', 'Curso básico de matemáticas', 4),
    ('Historia Universal', 'Panorama general de la historia mundial', 3);

-- Insertando datos en la tabla Departamentos
INSERT INTO Departamentos (nombre_departamento)
VALUES
    ('Ciencias'),
    ('Humanidades');

-- Insertando datos en la tabla Profesores
INSERT INTO Profesores (nombre, apellido, id_departamento)
VALUES
    ('Carlos', 'Rodríguez', 1),
    ('Ana', 'Martínez', 2);

-- Insertando datos en la tabla Matriculas
INSERT INTO Matriculas (id_alumno, id_curso, fecha_matricula, calificacion)
VALUES
    (1, 1, '2022-09-05', 8.5),
    (1, 2, '2022-09-05', 7.8),
    (2, 1, '2022-09-06', 9.0);

-- Insertando datos en la tabla Asistencias
INSERT INTO Asistencias (id_alumno, id_curso, fecha, estado)
VALUES
    (1, 1, '2022-09-10', 'presente'),
    (1, 2, '2022-09-11', 'presente'),
    (2, 1, '2022-09-10', 'ausente');