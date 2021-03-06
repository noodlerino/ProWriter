package model;
// Generated 23-may-2020 19:25:06 by Hibernate Tools 5.4.14.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Localidad generated by hbm2java
 */
public class Localidad implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6339041432824733632L;
	private Integer id;
	private String nombre;
	private String descripcion;
	private Set<Escena> escenas = new HashSet<Escena>(0);

	public Localidad() {
	}

	public Localidad(String nombre) {
		this.nombre = nombre;
	}

	public Localidad(String nombre, String descripcion, Set<Escena> escenas) {
		this.nombre = nombre;
		this.descripcion = descripcion;
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

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<Escena> getEscenas() {
		return this.escenas;
	}

	public void setEscenas(Set<Escena> escenas) {
		this.escenas = escenas;
	}

	@Override
	public String toString() {
		return this.getNombre();
	}
	
	

}
