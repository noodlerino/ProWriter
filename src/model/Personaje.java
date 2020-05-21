package model;
// Generated 14-may-2020 19:33:07 by Hibernate Tools 5.4.14.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Personaje generated by hbm2java
 */
public class Personaje implements java.io.Serializable {

	private static final long serialVersionUID = -7626081051608511419L;
	
	private Integer id;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private Integer edad;
	private String descripcion;
	private String imagen;
	private Set<Libro> libros = new HashSet<Libro>(0);
	private Set<Escena> escenas = new HashSet<Escena>(0);

	public Personaje() {
	}

	public Personaje(String nombre) {
		this.nombre = nombre;
	}
	
	public Personaje(String nombre, String apellido1, String apellido2, Integer edad, String descripcion, String imagen, Set<Libro> libros) {
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.edad = edad;
		this.descripcion = descripcion;
		this.imagen = imagen;
		this.libros = libros;
	}

	public Personaje(String nombre, String apellido1, String apellido2, Integer edad, String descripcion, String imagen,
			Set<Libro> libros, Set<Escena> escenas) {
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.edad = edad;
		this.descripcion = descripcion;
		this.imagen = imagen;
		this.libros = libros;
		this.escenas = escenas;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return this.apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return this.apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public Integer getEdad() {
		return this.edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getImagen() {
		return this.imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public Set<Libro> getLibros() {
		return this.libros;
	}

	public void setLibros(Set<Libro> libros) {
		this.libros = libros;
	}

	public Set<Escena> getEscenas() {
		return this.escenas;
	}

	public void setEscenas(Set<Escena> escenas) {
		this.escenas = escenas;
	}

	@Override
	public String toString() {
		return getNombre();
	}
	
	

}
