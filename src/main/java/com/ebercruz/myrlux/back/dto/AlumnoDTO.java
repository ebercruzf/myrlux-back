package com.ebercruz.myrlux.back.dto;

import com.ebercruz.myrlux.back.util.ValidDate;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class AlumnoDTO {

    private String id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String apellido;

    @NotBlank(message = "La fecha de nacimiento es obligatoria")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "La fecha de nacimiento debe tener el formato YYYY-MM-DD")
    @ValidDate(message = "La fecha de nacimiento debe ser una fecha válida en el pasado", past = true)
    private String fechaNacimiento;

    @NotNull(message = "El género es obligatorio")
    @Pattern(regexp = "^[MF]$", message = "El género debe ser 'M' o 'F'")
    private String genero;

    @Size(max = 100, message = "La dirección no debe exceder los 100 caracteres")
    private String direccion;

    @Pattern(regexp = "^\\+?[0-9]{10,20}$", message = "El teléfono debe tener entre 10 y 20 dígitos, opcionalmente precedido por un '+'")
    private String telefono;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El formato del email no es válido")
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "El email debe tener un formato válido")
    @Size(max = 100, message = "El email no debe exceder los 100 caracteres")
    private String email;

    @NotBlank(message = "La fecha de ingreso es obligatoria")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "La fecha de ingreso debe tener el formato YYYY-MM-DD")
    @ValidDate(message = "La fecha de ingreso debe ser una fecha válida en el pasado o presente", pastOrPresent = true)
    private String fechaIngreso;

    public AlumnoDTO() {

    }

    public AlumnoDTO(String nombre, String apellido, String fechaNacimiento, String genero, String direccion, String telefono, String email, String fechaIngreso) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.fechaIngreso = fechaIngreso;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }


    @Override
    public String toString() {
        return "AlumnoDTO{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", genero='" + genero + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", fechaIngreso='" + fechaIngreso + '\'' +
                '}';
    }
}
