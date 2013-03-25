package almacen;
import java.util.*;
import paa.calendario.IEvento;
import java.io.Serializable;

/**
 * Clase encargada gestionar la ordenacion por la fecha de inicio de los eventos 
 * de un calendario.
 * 
 * @author Paul Draghici
 *
 */
public class CompFecha implements Comparator<IEvento>, Serializable {

	private static final long serialVersionUID = 1L;

	public CompFecha() {
	}
	
	/**
	 * Compara dos eventos por la fecha de inicio y si esta coincide se comparan los nombres.
	 * Este metedo es usado por TreeSet para almacenar ordenadamente los eventos.
	 * @param o1 evento 1
	 * @param o2 evento 2
	 * @return -1 devuelve el resultado de la comparacion de las fechas de inicio si no son iguales
	 * o en caso contrario el resultado de la comparacion los nombres.
	 */
	public int compare(IEvento o1, IEvento o2) {
		
		if(o1.getFechaInicio().compareTo(o2.getFechaInicio()) == 0)
			return o1.getNombre().compareTo(o2.getNombre());
		
		return (-o1.getFechaInicio().compareTo(o2.getFechaInicio()));
	}

}
