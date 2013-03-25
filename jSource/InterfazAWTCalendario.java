package view;


import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import almacen.AlmacenEventos;
import almacen.Evento;

//import almacen.AlmacenEventos;
//import almacen.Evento;
//import paa.calendario.*;

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
	private Panel pnlNorte,pnlSur,pnlCentro,pnlNorte2;
	private Button bCambiarConf, bCrearEv, bBorrarEv;
	private Label statusBar,l2;
	private List calList;
	private ViewCal viewCal;
	private int mes;
	private Calendar cal,cale;
	private SimpleDateFormat date;
	private String [] meses;
	private AlmacenEventos al;
	/**
	 * Constructor.
	 */
	public InterfazAWTCalendario(String titulo, int ancho, int alto){
		
		super(titulo);
		setSize(ancho,alto);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setBackground(Color.LIGHT_GRAY);
		
		/*AlmacenEventos al = new AlmacenEventos();
		al.addCalendario("local");
		//al.guardar("almacen.dat");
*/		
	
		
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
		MenuItem borrarCalendario = new MenuItem("Borrar Calendario");
		calendarios.add(nuevoCalendario);
		calendarios.add(borrarCalendario);
		barraDeMenu.add(calendarios);
		
		Menu eventos = new Menu("Eventos");
		MenuItem crearEvento = new MenuItem("Crear Evento");
		MenuItem borrarEvento = new MenuItem("Borrar evento");
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
		bCrearEv.addActionListener(new CrearActionListener());
		bBorrarEv = new Button("Borrar Evento");
		bBorrarEv.addActionListener(new BorrarActionListener());
		pnlNorte.add(bCambiarConf);
		pnlNorte.add(bCrearEv);
		pnlNorte.add(bBorrarEv);
		add(pnlNorte, BorderLayout.NORTH);
		
		// Bara de estado
		statusBar = new Label("Cliente Calendario v1.0 para PAA");
		add(statusBar, BorderLayout.SOUTH);
		
		
		/*// Zona de calendarios
    		List calList = new List(2, false);
    		calList.add("local");
    		calList.add("google");
    		add(calList, BorderLayout.WEST);*/		
		
		// Panel centro
		pnlCentro = new Panel(new BorderLayout());
		add(pnlCentro, BorderLayout.CENTER);
		
		// Zona de calendarios
    		calList = new List(2, false);
    		calList.add("local");
    		calList.add("google");
    		pnlCentro.add(calList, BorderLayout.WEST);
		
		//
    		
    		FlowLayout f = new FlowLayout(FlowLayout.LEFT,0, -2);
    		/*GridBagLayout g = new GridBagLayout();
    		GridBagConstraints c = new GridBagConstraints();
    		*/
    		pnlNorte2 = new Panel(f);
		pnlNorte2.setBackground(Color.white);
		
		//c.fill = GridBagConstraints.VERTICAL;
		
		/*c.weightx = 1.0;
		//c.weighty = 1.0;
		c.anchor = GridBagConstraints.WEST;*/
		Label l1 = new Label ("Calendarios:            ");
		//g.setConstraints(l1, c);
		
		
		pnlNorte2.add(l1);
		/*c.weightx = 1.0;
		c.weighty = 1.0;
		c.anchor = GridBagConstraints.CENTER;*/
		Locale sp = new Locale("es_ES");
		cal = Calendar.getInstance(sp);
		String str = new String(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, sp) +" "+ cal.get(Calendar.YEAR));
		
		l2 = new Label(str);
		//System.out.print(f.getHgap());
		Button anterior = new Button("<<Anterior     ");
		Button siguiente = new Button("   Siguiente>>");
		siguiente.addActionListener(new CambioMesListener());
		//System.out.print(anterior.getSize());

		pnlNorte2.add(anterior);
		pnlNorte2.add(l2);
		pnlNorte2.add(siguiente);
		
		pnlCentro.add(pnlNorte2, BorderLayout.NORTH);
		//System.out.print(anterior.getSize());
		
		viewCal = new ViewCal(cal);
		pnlCentro.add(viewCal,BorderLayout.CENTER);
		
		
		addWindowListener(new SalirWindowAdapter());
		
	}//Constructor

	private final class SalirWindowAdapter extends WindowAdapter {
		public void windowClosing(WindowEvent e){
			dispose();		
			System.exit(0);
		}
	}
	
	private final class Salir implements ActionListener {
		public void actionPerformed(ActionEvent e){
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
	
	class CambioMesListener implements ActionListener{  // Clase interna
		public void actionPerformed(ActionEvent e){
		    
		    mes = cal.get(Calendar.MONTH) + 1; 
		    cal.set(Calendar.MONTH, mes);
		    System.out.println(cal.getTime());
		    
		    String str = new String(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("es_ES")) +" "+ cal.get(Calendar.YEAR));
		    l2.setText(str);
		    
		    viewCal.pintarMes(cal);
		    
		    viewCal.repaint();
		    l2.repaint();
		    
			
		}
	}
	
	class CrearActionListener implements ActionListener{  // Clase interna
		public void actionPerformed(ActionEvent e){
		    Date d = viewCal.getDate();
		    NuevoEvento nEv = new NuevoEvento(InterfazAWTCalendario.this,d);
		    
		    nEv.setSize(450, 235);
		    nEv.setResizable(false);
		    nEv.setVisible(true);
	
		}
	}
	
	class BorrarActionListener implements ActionListener{  // Clase interna
		public void actionPerformed(ActionEvent e){
		    Date d = viewCal.getDate();
		    NuevoEvento nEv = new NuevoEvento(InterfazAWTCalendario.this,d);
		    
		    nEv.setSize(450, 235);
		    nEv.setResizable(false);
		    nEv.setVisible(true);
	
		}
	}
	
	/**
	 * Otras clases privadas .
	 */

	/**
	 * Metodo main.
	 */
	public static void main(String[] args) {
		
		InterfazAWTCalendario mimarco = new InterfazAWTCalendario("Gestor de Calendarios",600,400);
		mimarco.setResizable(false);
		//mimarco.pack();
		mimarco.setVisible(true);
	} // Main
}
