package almacen;
import paa.calendario.IEvento;
import java.io.Serializable;
import java.util.*;

/**
 * Clase encargada de crear y almacenar informacion 
 * correspondiente a un evento.
 * @author Paul Draghici
 *
 */

public class Evento implements IEvento, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String nombre;
	private Date fechaInicio;
	private Date fechaFin;
	private String lugar;
	private String descripcion;

	/**
	 * Crea un nuevo evento vacio
	 */
	public Evento() {
	}

	/**
	 * Devuelve el identificador del evento
	 * @return id del evento
	 */
	public String getId(){
		return id;
	}
	
	/**
	 * Devuelve el nombre del evento
	 * @return nombre del evento
	 */
	public String getNombre(){
		return nombre;
	}
	
	/**
	 * Devuelve la fecha de inicio del evento
	 * @return fecha inicio del evento
	 */
	public Date getFechaInicio(){
		return fechaInicio;
	}
	
	/**
	 * Devuelve la fecha de fin del evento
	 * @return fecha de fin del evento
	 */
	public Date getFechaFin(){
		return fechaFin;
	}
	
	/**
	 * Devuelve el lugar del evento
	 * @return lugar del evento
	 */
	public String getLugar(){
		return lugar;
	}
	
	/**
	 * Devuelve la descripcion del evento
	 * @return descripcion del evento
	 */
	public String getDescripcion(){
		return descripcion;
	}
	
	/**
	 * Cambia el identificador del evento
	 * @param id nuevo identificador del evento
	 */
	public void setId(String id){
		this.id=id;
	}
	
	/**
	 * Cambia el nombre del evento
	 * @param nombre nuevo nombre del evento
	 */
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	
	/**
	 * Cambia la fecha de inicio del evento
	 * @param fechaInicio nueva fecha de inicio del evento
	 */
	public void setFechaInicio(Date fechaInicio){
		this.fechaInicio=fechaInicio;
	}
	
	/**
	 * Cambia la fecha de fin del evento
	 * @param fechaFin nueva fecha de fin del evento
	 */
	public void setFechaFin(Date fechaFin){
		this.fechaFin=fechaFin;
	}
	
	/**
	 * Cambia el lugar del evento
	 * @param lugar nuevo lugar del evento
	 */
	public void setLugar(String lugar){
		this.lugar=lugar;
	}
	
	/**
	 * Cambia la descripcion del evento
	 * @param descripcion nueva descripcion del evento
	 */
	public void setDescripcion(String descripcion){
		this.descripcion=descripcion;
	}

	/**
	 * metodo que asigna un identificador unico a un objeto
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	
	/**
	 * metodo para que el Set pueda comprobar si dos objetos son iguales y 
	 * evitar asi repetidos al insertar
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Evento))
			return false;
		Evento other = (Evento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Evento [id=" + id + ", nombre=" + nombre + ", fechaInicio="
				+ fechaInicio + ", fechaFin=" + fechaFin + ", lugar=" + lugar
				+ ", descripcion=" + descripcion + "]";
	}
	
	
}
