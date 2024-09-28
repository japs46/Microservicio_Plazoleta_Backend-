package com.pragma.backend.domain.models;

import java.util.Date;

public class Usuario {

	private final Long id;
	
	private final String nombre;
	
	private final String apellido;
	
	private final String documentoDeIdentidad;
	
	private final String celular;
	
	private final Date fechaNacimiento;
	
	private final String correo;
	
	private final String clave;

    private final Rol rol;

	public Usuario(Long id, String nombre, String apellido, String documentoDeIdentidad, String celular,
			Date fechaNacimiento, String correo, String clave,Rol rol) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.documentoDeIdentidad = documentoDeIdentidad;
		this.celular = celular;
		this.fechaNacimiento = fechaNacimiento;
		this.correo = correo;
		this.clave = clave;
		this.rol = rol;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getDocumentoDeIdentidad() {
		return documentoDeIdentidad;
	}

	public String getCelular() {
		return celular;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getCorreo() {
		return correo;
	}

	public String getClave() {
		return clave;
	}

	public Rol getRol() {
		return rol;
	}
	
	@Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", documentoDeIdentidad='" + documentoDeIdentidad + '\'' +
                ", celular='" + celular + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", correo='" + correo + '\'' +
                ", claveEncriptada='" + clave + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
	
}
