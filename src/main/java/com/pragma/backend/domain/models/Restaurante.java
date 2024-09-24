package com.pragma.backend.domain.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Restaurante {
	
	private final Long id;

	@NotNull(message = "El nombre no puede ser null")
	@NotEmpty(message = "El nombre no puede ser vacio")
	@Pattern(regexp = "^(?!\\d+$)[\\w\\s]+$", message = "El nombre del restaurante no se puede crear con solo numeros")
	private final String nombre;
	
	@NotNull(message = "El NIT no puede ser null")
	@NotEmpty(message = "El NIT no puede ser vacio")
	@Pattern(regexp = "^\\d+$", message = "El NIT debe ser numérico")
    private final String nit;
    
	@NotNull(message = "La direccion no puede ser null")
	@NotEmpty(message = "La direccion no puede ser vacio")
    private final String direccion;
    
	@NotNull(message = "El telefono no puede ser null")
	@NotEmpty(message = "El telefono no puede ser vacio")
	@Size(max = 13, message= "El telefono debe tener 13 caracteres como maximo")
	@Pattern(regexp = "^(\\+\\d{1,2}|\\d{1,2})\\s?\\d+$", message = "El telefono debe ser numérico y puede comenzar con un '+' seguido de uno o dos dígitos, o solo con uno o dos dígitos, con un espacio opcional antes de los siguientes dígitos, estructura que debe tener: (indicativo pais opcional +57) +(espacio opcional)+ (numero celular 3219876543) = +57 3219876543, tambien puede quedar +573219876543 o 3219876543.")
    private final String telefono;
    
	@NotNull(message = "La url del logo no puede ser null")
	@NotEmpty(message = "La url del logo no puede ser vacio")
    private final String urlLogo;
    
	@NotNull(message = "El usuario no puede ser null")
    private final Long idUsuarioPropietario;

	public Restaurante(Long id,String nombre, String nit, String direccion, String telefono, String urlLogo,
			Long idUsuarioPropietario) {
		this.id = id;
		this.nombre = nombre;
		this.nit = nit;
		this.direccion = direccion;
		this.telefono = telefono;
		this.urlLogo = urlLogo;
		this.idUsuarioPropietario = idUsuarioPropietario;
	}
	
	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getNit() {
		return nit;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getUrlLogo() {
		return urlLogo;
	}

	public Long getIdUsuarioPropietario() {
		return idUsuarioPropietario;
	}
    
}
