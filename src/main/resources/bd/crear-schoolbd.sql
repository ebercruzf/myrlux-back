-- psql -U tu_usuario -f -f crear-schoolbd.sql
-- psql -U postgres -d schoolbd
-- Paso 1: Conectarse a una base de datos diferente (por ejemplo, 'postgres')
\c postgres;

-- Paso 2: Terminar todas las conexiones a la base de datos 'schoolbd'
SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname = 'schoolbd' AND pid <> pg_backend_pid();

-- Paso 3: Ahora podemos borrar la base de datos de forma segura
DROP DATABASE IF EXISTS schoolbd;

-- Paso 4: Crear la nueva base de datos
CREATE DATABASE schoolbd;

-- Paso 5: Conectarse a la nueva base de datos
\c schoolbd;

-- Paso 6: Crear las tablas
CREATE TABLE Alumnos (
    id_alumno SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    genero CHAR(1) NOT NULL,
    direccion VARCHAR(100),
    telefono VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    fecha_ingreso DATE NOT NULL
);

CREATE TABLE Cursos (
    id_curso SERIAL PRIMARY KEY,
    nombre_curso VARCHAR(100) NOT NULL,
    descripcion TEXT,
    creditos INTEGER NOT NULL
);

CREATE TABLE Departamentos (
    id_departamento SERIAL PRIMARY KEY,
    nombre_departamento VARCHAR(100) NOT NULL
);

CREATE TABLE Profesores (
    id_profesor SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    id_departamento INTEGER NOT NULL,
    FOREIGN KEY (id_departamento) REFERENCES Departamentos(id_departamento)
);

CREATE TABLE Matriculas (
    id_matricula SERIAL PRIMARY KEY,
    id_alumno INTEGER NOT NULL,
    id_curso INTEGER NOT NULL,
    fecha_matricula DATE NOT NULL,
    calificacion DECIMAL(3,1),
    FOREIGN KEY (id_alumno) REFERENCES Alumnos(id_alumno)
);

CREATE TABLE Asistencias (
    id_asistencia SERIAL PRIMARY KEY,
    id_alumno INTEGER NOT NULL,
    id_curso INTEGER NOT NULL,
    fecha DATE NOT NULL,
    estado VARCHAR(10) NOT NULL,
    FOREIGN KEY (id_alumno) REFERENCES Alumnos(id_alumno)
);

-- Paso 7: Insertar los datos (como en el script anterior)
-- ... (inserta aquí los INSERT statements del script anterior)