package almacen;
import java.util.*;
import paa.calendario.IEvento;
import java.io.Serializable;

/**
 * Clase encargada gestionar la ordenacion por el nombre de los eventos 
 * de un calendario.
 * 
 * @author Paul Draghici
 *
 */
public class CompNombre implements Comparator<IEvento>, Serializable{

	private static final long serialVersionUID = 1L;

	public CompNombre() {
	}
	
	/**
	 * Compara dos eventos por el nombre y si este coincide se compara la fecha de inicio.
	 * Este metedo es usado por TreeSet para almacenar ordenadamente los eventos.
	 * @param o1 evento 1
	 * @param o2 evento 2
	 * @return -1 devuelve el resultado de la comparacion de los nombres si no son iguales
	 * o en caso contrario el resultado de la comparacion de las fechas de inicio.
	 */
	public int compare(IEvento o1, IEvento o2) {
		
		if(o1.getNombre().compareTo(o2.getNombre()) == 0)
			return (-o1.getFechaInicio().compareTo(o2.getFechaInicio()));
		
		return o1.getNombre().compareTo(o2.getNombre());
	}
}
