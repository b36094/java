package almacen;
import java.util.*;
import paa.calendario.IAlmacenEventos;
import paa.calendario.IEvento;
import java.io.*;
import java.text.*;

/**
 * Clase encargada almacenar y gestionar eventos
 * @author Paul Draghici
 *
 */
public class AlmacenEventos implements IAlmacenEventos, Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private static final int ORDENARPORFECHA = -1;
	private static final int ORDENARPORNOMBRE = 1;
	private Map<String,SortedSet<IEvento>> almacen; 

	/**
	 * Crea un nuevo almacen de eventos
	 */
	public AlmacenEventos() {
		
		almacen = new HashMap<String,SortedSet<IEvento>>();
		
	}

	/**
	 * Anyade un nuevo calendario al almacen de eventos si no esta
	 * presente en el almacen.
	 * @param cal calendario que se desea anyadir
	 * @return true si se ha anyadido con exito y false en caso contrario
	 */
	public boolean addCalendario(String cal) {
	
		boolean res = false;
		
		if(!containsCalendario(cal)){
			almacen.put(cal, new TreeSet<IEvento>(new CompFecha()));
			res = true;
		}
		return res;
	}

	/**
	 * Anyade un nuevo evento al calendario especificado si no esta
	 * presente en el calendario.
	 * @param cal calendario al que se desea anyadir el evento
	 * @param ev nuevo evento que se anyade al calendario
	 * @return true si se ha anyadido con exito y false en caso contrario
	 */
	public boolean addEvento(String cal, IEvento ev) {
		
		boolean res = false;
		SortedSet<IEvento> eventos = getEventos(cal);
		
		if(eventos!=null){
			if(!containsEvento(cal, ev.getId())){
				eventos.add(ev);
				res = true;
			}
		}
		return res;
	}

	/**
	 * Indica si ya existe en el almacen de calendarios el calendario
	 * correspondiente
	 * @param cal calendario a comprobar
	 * @return true si existe y false en caso contrario
	 */
	public boolean containsCalendario(String cal) {
		boolean res = false;
		
		if(cal==null) 
			throw new NullPointerException("Calendario no valido");
		
		res = almacen.containsKey(cal);
		return res;
	}

	/**
	 * Indica si ya existe en el calendario un evento con ese identificador
	 * @param cal calendario donde se busca el evento
	 * @param id identificador del evento a comprobar
	 * @return true si existe y false en caso contrario
	 */
	public boolean containsEvento(String cal, String id) {
		
		boolean res = false;
		SortedSet<IEvento> eventos = getEventos(cal);
		IEvento ev = getEvento(cal, id);
		
		if(ev!=null && eventos!=null)
			res = eventos.contains(ev);
		
		return res;
	}

	/**
	 * Borra un calendario del almacen de eventos si esta presente.
	 * No realiza cambios si no existe el calendario del mismo nombre
	 * @param cal calendario que se desea borrar
	 * @return true si se ha borrado con exito y false en caso contrario
	 */
	public boolean delCalendario(String cal) {
		
		boolean res = false;
		Set<String> calendarios = getCalendarios();
		
		if(containsCalendario(cal)){
			calendarios.remove(cal);
			res = true;
		}
		return res;
	}

	/**
	 * Borra el evento situado en la posicion correspondiente dentro del calendario.
	 * La posicion ess un entero entre 1 y el numero de eventos que contiene el calendario.
	 * @param cal calendario del que se desea borrar el evento
	 * @param pos posicion en la que se encuentra el evento a borrar
	 * @return true si se ha borrado con exito y false en caso contrario
	 */
	public boolean delEvento(String cal, int pos) {
		
		boolean res = false;
		IEvento evento = getEvento(cal, pos);
		SortedSet<IEvento> eventos = getEventos(cal);
		
		if(evento!=null && eventos!=null){
			eventos.remove(evento);
			res = true;
		}
		return res;
	}

	/**
	 * Borra del calendario el evento que se pasa como parametro.
	 * @param cal calendario del que se desea borrar el evento
	 * @param ev  evento a borrar
	 * @return true si se ha borrado con exito y false en caso contrario
	 */
	public boolean delEvento(String cal, IEvento ev) {
		
		boolean res = false;
		SortedSet<IEvento> eventos = getEventos(cal);
		
		if(ev!=null &&  eventos!=null){
			res = eventos.remove(ev);
		}
		return res;
	}

	/**
	 * Borra del calendario el evento cuyo identificador se pasa como parametro.
	 * @param cal calendario del que se desea borrar el evento
	 * @param id  identificador del evento a borrar
	 * @return true si se ha borrado con exito y false en caso contrario
	 */
	public boolean delEvento(String cal, String id) {
		
		boolean res = false;
		IEvento evento = getEvento(cal, id);
		SortedSet<IEvento> eventos = getEventos(cal);
		
		if(evento!=null && eventos!=null){
			res=eventos.remove(evento);
		}
		return res;
	}

	/**
	 * Devuelve un conjunto con los nombres de los calendarios 
	 * que existen en el almacen de eventos.
	 * @return calendarios existentes en el almacen
	 */
	public Set<String> getCalendarios() {

		return almacen.keySet();
	}

	/**
	 * Devuelve el evento 
	 * situado en la posicion correspondiente dentro del calendario
	 * @param cal calendario del que se quiere obtener el evento
	 * @param pos posicion del evento que se desea obtener
	 * @return evento que se encuentra en la posicion especificada
	 */
	public IEvento getEvento(String cal, int pos) {
		
		if(containsCalendario(cal) && pos > 0 &&pos<= getNumEventos(cal)){
			List<IEvento> eventos = new ArrayList <IEvento> (getEventos(cal));
			return eventos.get(pos-1);
		}else 
			return null;
	}

	/**
	 * Devuelve del calendario el evento que posee el identificador 
	 * que se pasa como parametro.
	 * @param cal calendario del que se quiere obtener el evento
	 * @param id identificador del evento que se desea obtener
	 * @return evento que posee el identificador especificado
	 */
	public IEvento getEvento(String cal, String id) {
		
		IEvento evento = null;
		SortedSet<IEvento> eventos = getEventos(cal);
		
		if(eventos!=null){
			for(IEvento e: eventos){
				if(e.getId().equals(id)){
					evento = e;
					break;
				}
			}
		}
		return evento;
	}

	/**
	 * Devuelve todos los eventos del calendario
	 * correspondiente ordenados segun el criterio actual.
	 * @param cal calendario del que se quiere obtener los eventos
	 * @return eventos que posee el calendario almacenados en un Set
	 */
	public SortedSet<IEvento> getEventos(String cal) {
		
		if(containsCalendario(cal)){
			SortedSet<IEvento> eventos = almacen.get(cal);
			return eventos;
		}else 
			return null;
	}

	/**
	 * Devuelve los eventos del calendario que tienen el nombre que se 
	 * pasa como parametro, ordenados segun el criterio actual.
	 * @param cal calendario del que se quiere obtener los eventos
	 * @param nombre nombre de los eventos que se desean obtener
	 * @return eventos solicitados almacenados en un Set
	 */
	public SortedSet<IEvento> getEventos(String cal, String nombre) {
		
		SortedSet<IEvento> eventos = new TreeSet<IEvento>(new CompFecha());
		SortedSet<IEvento> eventosCal = getEventos(cal);
		
		if(eventosCal!=null){
			for(IEvento e: eventosCal){
				if(nombre.equals(e.getNombre()))
					eventos.add(e);	
			}
			return eventos;
		}else
			return null;
	}

	/**
	 * Devuelve los eventos del calendario correspondiente
	 * al dia que se pasa como parametro, ordenados segun el 
	 * criterio actual.
	 * @param cal calendario del que se quiere obtener los eventos
	 * @param date dia del que se quiern obtener los eventos
	 * @return eventos solicitados almacenados en un Set
	 */
	public SortedSet<IEvento> getEventosDia(String cal, Date date) {

		SortedSet<IEvento> eventos = new TreeSet<IEvento>(new CompFecha());
		SortedSet<IEvento> eventosCal = getEventos(cal);
		SimpleDateFormat f = new SimpleDateFormat("ddMMyyyy");
		
		if(eventosCal!=null){
			for(IEvento e: eventosCal){
				if(f.format(date).equals(f.format(e.getFechaInicio())))
					eventos.add(e);	
			}
			return eventos;
		}else
			return null;
	}

	/**
	 * Devuelve el numero de eventos que contiene el 
	 * calendario cuyo nombre se indica.
	 * @param cal calendario del que se quiere obtener el numero de eventos.
	 * @return numero eventos
	 */
	public int getNumEventos(String cal) {
		
		int numEv = 0;
		SortedSet<IEvento> eventos = getEventos(cal);
		
		if(eventos!=null)
			numEv = eventos.size();
		
		return numEv;
	}

	/**
	 * Guarda el almacen de eventos en el fichero cuyo nombre se pasa 
	 * como parametro. Devuelve true si se ha guardado correctamente y 
	 * false si hay algun tipo de error o excepcion.
	 * @param nombreFichero nombre del fichero donde se quiere 
	 * guardar el almacen
	 * @return true si la operacion se ha realizado con exito y false
	 * en caso contrario
	 */
	public boolean guardar(String nombreFichero) {
		
		boolean res = false;
		ObjectOutputStream salida= null;
		
		try{
			salida= new ObjectOutputStream(new FileOutputStream (nombreFichero));
			salida.writeObject(almacen);
			res = true;
		}catch (FileNotFoundException e){
			res = false;
		}catch (IOException e){
			res = false;
		}finally{
			if (salida!=null)
				try {
				salida.close();
				} catch (IOException e) {
				e.printStackTrace();
				}
		}
		
		return res;
	}

	/**
	 * Permite cambiar el criterio de ordenacion del calendario correspondiente
	 * @param cal calendario que se desea ordenar
	 * @param ordenaPor criterio de ordenacion
	 * @return true si la operacion se ha realizado con exito y false en caso contrario
	 */
	public boolean ordenarPor(String cal, int ordenaPor) {
		
		boolean res = false;
		SortedSet<IEvento> eventosCal = getEventos(cal);
		
		if(ordenaPor == ORDENARPORFECHA){
			SortedSet<IEvento> eventos = new TreeSet<IEvento> (new CompFecha());
			if(eventosCal!=null){
				eventos.addAll(eventosCal);
				almacen.put(cal, eventos);	
				res = true;
			}
		}
		else if(ordenaPor == ORDENARPORNOMBRE){
			
			SortedSet<IEvento> eventos = new TreeSet<IEvento> (new CompNombre());
			if(eventosCal!=null){
				eventos.addAll(eventosCal);
				almacen.put(cal, eventos);	
				res = true;	
			}
		}
		
		return res;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Recupera un almacen de eventos del fichero cuyo nombre se pasa 
	 * como parametro. 
	 * @param nombreFichero nombre del fichero de donde se quiere recuperar
	 * el almacen.
	 * @return false si el fichero no existe o hay algun tipo de 
	 * excepcion y ademas crea un almacen vacio. 
	 * true si lo recupera.
	 */
	public boolean recuperar(String nombreFichero) {
		
		boolean res = false;
		ObjectInputStream entrada = null;
		try {
			entrada = new ObjectInputStream ( new FileInputStream (nombreFichero));
			//entrada.readObject();
		} catch (FileNotFoundException e1) {
			
		    guardar(nombreFichero);
		    
		    try {
			entrada = new ObjectInputStream ( new FileInputStream (nombreFichero));
		    } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error1");
		    } catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error2");
		    }
		    
		}catch (IOException e1) {
			res = false;
			System.out.println("Error2");
			
		} 
		
		try{
			almacen = (HashMap<String, SortedSet<IEvento>>)entrada.readObject();
			if(almacen.isEmpty())
			    res=false;
			else res = true;
		} catch (IOException e) {
			res = false;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			res = false;
			e.printStackTrace();
		}finally {
			try {
				entrada.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

}
