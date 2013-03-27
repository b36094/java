package view;


import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.util.*;
import java.text.SimpleDateFormat;
import paa.calendario.*;
import almacen.AlmacenEventos;

/**
 * Fichero: InterfazAWTCalendario.java
 * Clase que implementa la Interfaz Grafica del Gestor de Calendarios con awt.
 * @author jmb
 * @version 1.0
 */
public class InterfazAWTCalendario extends Frame {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
	 * 
	 */
	//	private IAlmacenEventos almacenEventos;	
	private MenuBar barraDeMenu;
	private Panel pnlNorte,pnlCentro,pnlNorte2, pnlButtonsLista, pnlButtons;
	private Button bCambiarConf, bCrearEv, bBorrarEv;
	private Label statusBar,l2;
	private List calList, evList;
	private int mes;
	private Calendar cal;
	private String calSelec;
	private AlmacenEventos almacen;
	private Button [] buttons;
	private ArrayList <IEvento> eventosDia;
	private GoogleCalendar googleCal;
	/**
	 * Constructor.
	 */
	public InterfazAWTCalendario(String titulo, int ancho, int alto){
		
		super(titulo);
		setSize(ancho,alto);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setBackground(Color.LIGHT_GRAY);
		
		almacen = new AlmacenEventos();
		googleCal = new GoogleCalendar();
		
		boolean res = almacen.recuperar("almacen.dat");
		if(!res){
		    almacen.addCalendario("local");
		}
	
		//Sitio los menus desplegables
		barraDeMenu = new MenuBar();
		setMenuBar(barraDeMenu);
		
		Menu archivo = new Menu("Archivo");
		MenuItem salir = new MenuItem("Salir");
		MenuItem cambiarDeConfiguracion = new MenuItem("Cambiar de configuración");
		salir.addActionListener(new Salir());
		archivo.add(cambiarDeConfiguracion);
		archivo.addSeparator();
		archivo.add(salir);
		barraDeMenu.add(archivo);
		
		Menu calendarios = new Menu("Calendarios");
		MenuItem nuevoCalendario = new MenuItem("Nuevo Calendario");
		nuevoCalendario.addActionListener(new NuevoCalendario());
		MenuItem borrarCalendario = new MenuItem("Borrar Calendario");
		borrarCalendario.addActionListener(new BorrarCalendario());
		calendarios.add(nuevoCalendario);
		calendarios.add(borrarCalendario);
		barraDeMenu.add(calendarios);
		
		Menu eventos = new Menu("Eventos");
		MenuItem crearEvento = new MenuItem("Crear Evento");
		crearEvento.addActionListener(new CrearEvento());
		MenuItem borrarEvento = new MenuItem("Borrar evento");
		borrarEvento.addActionListener(new BorrarEvento());
		eventos.add(crearEvento);
		eventos.add(borrarEvento);
		barraDeMenu.add(eventos);
		
		// Creo el menu de ayuda
		Menu ayuda = new Menu("Ayuda");
		MenuItem acercaDe = new MenuItem("Acerca de");
		acercaDe.addActionListener(new AcercaDe());
		ayuda.add(acercaDe);
		// Sitio el menu de ayuda en su sitio
		barraDeMenu.setHelpMenu(ayuda);	
		
		//************Contenedor**************
		
		// Barra botones
		pnlNorte = new Panel(new FlowLayout(FlowLayout.LEFT));
		bCambiarConf = new Button("Cambiar configuración");
		bCrearEv = new Button("Crear Evento");
		bCrearEv.addActionListener(new CrearEvento());
		bBorrarEv = new Button("Borrar Evento");
		bBorrarEv.addActionListener(new BorrarEvento());
		pnlNorte.add(bCambiarConf);
		pnlNorte.add(bCrearEv);
		pnlNorte.add(bBorrarEv);
		add(pnlNorte, BorderLayout.NORTH);
		
		// Bara de estado
		statusBar = new Label("Cliente Calendario v1.0 para PAA");
		add(statusBar, BorderLayout.SOUTH);	
		
		// Panel centro
		pnlCentro = new Panel(new BorderLayout());
		add(pnlCentro, BorderLayout.CENTER);
		
		// Zona de calendarios
    		calList = new List(2, false);
    	
    		mostrarCal();
    		ArrayList<String> calAl = new ArrayList<String>(almacen.getCalendarios());
    		int index = calAl.indexOf("local") +1;
    		calList.select(index);
    		calSelec = calList.getSelectedItem();
    		pnlCentro.add(calList, BorderLayout.WEST);
    	    	
    		// Barra informacion y control calendario
    		pnlNorte2 = new Panel(new GridLayout(0,4,0,0));
		pnlNorte2.setBackground(Color.white);
		
		Label l1 = new Label ("Calendarios:");
		pnlNorte2.add(l1);
		Locale sp = new Locale("es_ES");
		
		cal = Calendar.getInstance(sp);
		String str = new String(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, sp) +" "+ cal.get(Calendar.YEAR));
		
		l2 = new Label(str,Label.CENTER);
		Button anterior = new Button("<<Anterior");
		anterior.addActionListener(new Anterior());
		Button siguiente = new Button("Siguiente>>");
		siguiente.addActionListener(new Siguiente());

		pnlNorte2.add(anterior);
		pnlNorte2.add(l2);
		pnlNorte2.add(siguiente);
		pnlCentro.add(pnlNorte2, BorderLayout.NORTH);
		
		// botonera mes y lista eventos
		pnlButtonsLista = new Panel(new GridLayout(2,0,0,0));
		pnlButtons = new Panel(new GridLayout(0,7,0,0));
		
		
//		viewCal = new ViewCal(cal,almacen, calList.getSelectedItem());
		buttons = new Button[49];
		String [] diasSemana = {"L","M","X","J","V","S","D"};
		int i,j;
		for(i = 0; i < diasSemana.length; i++){
		    Button b = new Button(diasSemana[i]);
		    
		    b.setForeground(Color.RED);
		    buttons[i] = b;
		    pnlButtons.add(b);
		}
		
		for(j = i; j < buttons.length; j++){
		    Button b = new Button();
		    buttons[j] = b;
		    buttons[j].addActionListener(new ListarEventos());
		    pnlButtons.add(buttons[j]);
		}
		
		pintarMes();
		pnlButtonsLista.add(pnlButtons);
		
		evList = new List();
		evList.addActionListener(new InfoEvento());
		pnlButtonsLista.add(evList);
		
		pnlCentro.add(pnlButtonsLista,BorderLayout.CENTER);
		
		
		addWindowListener(new SalirWindowAdapter());
		
	}//Constructor
	
	class ListarEventos implements ActionListener{  // Clase interna
	    public void actionPerformed(ActionEvent e){
		
		String str = new String(e.getActionCommand());
		int dia = Integer.parseInt(str);
		cal.set(Calendar.DAY_OF_MONTH, dia);
		mostrarEvDia();
	    }
	}

	private final class SalirWindowAdapter extends WindowAdapter {
		public void windowClosing(WindowEvent e){
			
		    almacen.guardar("almacen.dat");
		    dispose();		
		    System.exit(0);
		}
	}
	
	private final class Salir implements ActionListener {
		public void actionPerformed(ActionEvent e){
		    
		    almacen.guardar("almacen.dat");
		    dispose();		
		    System.exit(0);
		}
	}

	
	/**
	 * Clase que implementa la accion del menu "acerca de".
	 */
	class AcercaDe implements ActionListener{  // Clase interna
		public void actionPerformed(ActionEvent e){
			VentanaAcercaDe ventanaAcercaDe = new VentanaAcercaDe(InterfazAWTCalendario.this);
			ventanaAcercaDe.setVisible(true);
		}
	}
	
	class NuevoCalendario implements ActionListener{  // Clase interna
	    public void actionPerformed(ActionEvent e){
		VentanaNuevoCal nuevoCal = new VentanaNuevoCal(InterfazAWTCalendario.this, almacen);
		nuevoCal.setSize(450, 100);
		nuevoCal.setResizable(false);
		nuevoCal.setVisible(true);
		mostrarCal();
			
	    }
	}
	
	class InfoEvento implements ActionListener{
	    public void actionPerformed(ActionEvent e){
		
		int index = evList.getSelectedIndex();
		IEvento ev = eventosDia.get(index);
		VentanaEvento vEv = new VentanaEvento(InterfazAWTCalendario.this, ev);
		//vEv.setResizable(false);
		vEv.setVisible(true);	
	    }
	}
	
	class BorrarCalendario implements ActionListener{  // Clase interna
	    public void actionPerformed(ActionEvent e){
		
		if(!calSelec.equals("local") && !calSelec.equals("google")){
		    almacen.delCalendario(calSelec);
		    mostrarCal();
		}
	    }
	}
	
	class SeleccionCalendario implements ItemListener{

	    public void itemStateChanged(ItemEvent e){
		calSelec = calList.getSelectedItem();
		//Date d = cal.getTime();
		if(calSelec.equals("google")){
		    VentanaGoogleConnect vGoogle = new VentanaGoogleConnect(InterfazAWTCalendario.this, googleCal);
		    vGoogle.setSize(400, 130);
		    vGoogle.setVisible(true);
		}
		    /*try {
			eventosDia = new ArrayList<IEvento>(googleCal.getEventosDia(d));
			
		    } catch (GoogleCalendarException e1) {
			e1.printStackTrace();
		    } 
		    
		    
		    
		}
		else{
		    eventosDia = new ArrayList<IEvento>(almacen.getEventosDia(calSelec,d));
		    System.out.println(calSelec);
		}*/
		//mostrarEvDia();
	    }

	}
	
	class Siguiente implements ActionListener{  // Clase interna
	    public void actionPerformed(ActionEvent e){

		mes = cal.get(Calendar.MONTH) + 1; 
		cal.set(Calendar.MONTH, mes);

		String str = new String(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("es_ES")) +
			" "+ cal.get(Calendar.YEAR));
		l2.setText(str);

		pintarMes();

		pnlButtonsLista.repaint();
		l2.repaint();


	    }
	}
	
	class Anterior implements ActionListener{  // Clase interna
	    public void actionPerformed(ActionEvent e){

		mes = cal.get(Calendar.MONTH) - 1; 
		cal.set(Calendar.MONTH, mes);
		//System.out.println(cal.getTime());

		String str = new String(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("es_ES")) +" "+ cal.get(Calendar.YEAR));
		l2.setText(str);

		pintarMes();

		pnlButtonsLista.repaint();
		l2.repaint();


	    }
	}
	
	class CrearEvento implements ActionListener{  // Clase interna
	    public void actionPerformed(ActionEvent e){
		Date d = cal.getTime();
		VentanaNuevoEvento nEv = new VentanaNuevoEvento
			(InterfazAWTCalendario.this,d, almacen,googleCal,calSelec);

		nEv.setSize(450, 250);
		nEv.setResizable(false);
		nEv.setVisible(true);
		mostrarEvDia();

	    }
	}
	
	class BorrarEvento implements ActionListener{  // Clase interna
	    public void actionPerformed(ActionEvent e){

		int index = evList.getSelectedIndex();
		if(!calSelec.equals("google") ){
		    almacen.delEvento(calSelec, eventosDia.get(index));
		    mostrarEvDia();
		}
		else if (calSelec.equals("google")){
		    try {
			googleCal.delEvento(eventosDia.get(index));
		    } catch (GoogleCalendarException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		    }
		    mostrarEvDia();
		}
		

	    }
	}
	
	public void pintarMes(){

	    int i;
	    String enteroString;

	    cal.set(Calendar.HOUR_OF_DAY, 0); 
	    cal.clear(Calendar.MINUTE);
	    cal.clear(Calendar.SECOND);
	    cal.clear(Calendar.MILLISECOND);


	    // get start of the month
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    int diaSem = cal.get(Calendar.DAY_OF_WEEK);

	    //System.out.println("Start of the month:       " + cal.getTime());
	    for(int h = 7; h<buttons.length; h++){
		buttons[h].setEnabled(false);
		buttons[h].setLabel("");

	    }

	    int j;
	    int offset = 6;
	    for( j=1;j<diaSem-1;j++){
		buttons[j+offset].setEnabled(false);
		buttons[j+offset].setLabel("");
	    }

	    offset += j-1; 

	    int dias = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	    //System.out.println(dias);

	    for(i = 1; i<= dias; i++){
		enteroString = Integer.toString(i);
		buttons[i+offset].setEnabled(true);
		buttons[i+offset].setLabel(enteroString);

	    }

	    offset +=i;
	    for(int k=offset;k<buttons.length;k++){
		buttons[k].setEnabled(false);
		buttons[k].setLabel("");
	    }
	}

	public void mostrarEvDia(){
	    String str;
	    Date d = cal.getTime();

	    if(calSelec.equals("google")){
		try {
		    System.out.println(googleCal.conected);
		    //Set <IEvento> eventos = googleCal.getEventosDia(d);
		    eventosDia = new ArrayList<IEvento>(googleCal.getEventosDia(d));

		} catch (GoogleCalendarException e1) {
		    e1.printStackTrace();
		} 
	    }
	    else{
		eventosDia = new ArrayList<IEvento>(almacen.getEventosDia(calSelec,d));
		//System.out.println(calSelec);
	    }
	    
	    evList.removeAll();
	    SimpleDateFormat f = new SimpleDateFormat("HH:mm");

	    for(IEvento s: eventosDia){
		str = f.format(s.getFechaInicio());
		str += " " + s.getNombre();
		evList.add(str);
	    }	
	}
	
	public void mostrarCal(){
	    ArrayList<String> calendarios = new ArrayList<String>(almacen.getCalendarios());
	    calList.removeAll();
	    calList.add("google");
	    for(String cal: calendarios){
		calList.add(cal);
	    }
	    calList.addItemListener(new SeleccionCalendario());
	}

	/**
	 * Metodo main.
	 */
	public static void main(String[] args) {
		
		InterfazAWTCalendario mimarco = new InterfazAWTCalendario("Gestor de Calendarios",600,400);
		mimarco.setResizable(false);
		mimarco.setVisible(true);
	} // Main
}
