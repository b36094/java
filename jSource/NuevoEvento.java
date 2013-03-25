package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.SortedSet;

import paa.calendario.IEvento;

import almacen.AlmacenEventos;
import almacen.Evento;

public class NuevoEvento extends Dialog {

    private static final long serialVersionUID = 1L;
    private static int id = 0;
    private Panel pnl;
    private Label l1,l2,l3,l4,l5,l6, l7, l8, l9, l10, l11, l12;
    private TextField tNombre, tLugar, tDescr ;
    private Choice hIni,mIni,hFin, mFin;
    private Button bCrear, bCancel;
    private SimpleDateFormat format;
    private Date d;
    //private AlmacenEventos al;

    public NuevoEvento (Frame f, Date d) {
	super(f, "Nuevo Evento", true);
	setLocationRelativeTo(f);
	setLayout(new FlowLayout(FlowLayout.LEFT));
	//GridBagConstraints c = new GridBagConstraints();
	format = new SimpleDateFormat("HHmm");
	this.d = d;
	//this.al = al;
	
	
	
	SimpleDateFormat dFormat = new SimpleDateFormat("dd-MM-yyyy");
	l1 = new Label("FECHA NUEVO EVENTO        ");
	l2 = new Label(dFormat.format(d));
	l3 = new Label("Nombre                               ");
	tNombre = new TextField(24);
	l4 = new Label("Hora de inicio                      ");
	l5 = new Label("Hora:");
	hIni = new Choice();
	for(int i=0; i<24; i++){
	    hIni.add(Integer.toString(i));
	}
	l6 = new Label("Minutos:");
	mIni = new Choice();
	for(int i=0; i<=60; i++){
	    mIni.add(Integer.toString(i));
	}
	
	l7 = new Label("Hora de finalizacion            ");
	l8 = new Label("Hora:");
	
	hFin = new Choice();
	for(int i=0; i<24; i++){
	    hFin.add(Integer.toString(i));
	}
	l9 = new Label("Minutos:");
	mFin = new Choice();
	for(int i=0; i<=60; i++){
	    mFin.add(Integer.toString(i));
	}
	
	l10 = new Label("Lugar                                   ");
	tLugar = new TextField(24);
	
	l11 = new Label("Descripcion                         ");
	tDescr = new TextField(24);
	
	l12 = new Label ("                              ");
	bCrear = new Button ("Crear");
	bCrear.addActionListener(new CrearListener());
	
	bCancel = new Button ("Cancelar");
	bCancel.addActionListener(new ExitListener());
	
	
	add(l1); add(l2); add(l3); add(tNombre); add(l4); add(l5);
	add(hIni); add(l6); add(mIni); add(l7); add(l8); add(hFin); 
	add(l9); add(mFin); add(l10); add(tLugar); add(l11); add(tDescr);
	add(l12); add(bCrear); add(bCancel);
	
	//add(new Label("Esqueleto de la P2 de PAA. Curso 2012-2013"));
	addWindowListener (new WindowAdapter (){ 
          public void windowClosing(WindowEvent e) { 
        	  NuevoEvento.this.dispose(); 
             } 
          });
    }
    
    class CrearListener implements ActionListener{  // Clase interna
	public void actionPerformed(ActionEvent e){
	    AlmacenEventos almacen = new AlmacenEventos();
	    boolean res = almacen.recuperar("almacen.dat");
	    if(!res){
		almacen.addCalendario("local");
	    }
	    System.out.println(res);
	    //System.out.println(res);
	    
	    Evento ev = new Evento();
	    id = recuperarId("id");
	    //System.out.println(id);
	    id++;
	    ev.setId(Integer.toString(id));
	    ev.setNombre(tNombre.getText());
	    ev.setLugar(tLugar.getText());
	    ev.setDescripcion(tDescr.getText());
	    
	    
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(d);
	    int h = Integer.parseInt(hIni.getSelectedItem());
	    int m = Integer.parseInt(mIni.getSelectedItem());
	    cal.set(Calendar.HOUR, h);
	    cal.set(Calendar.MINUTE, m);
	    cal.clear(Calendar.SECOND);
	    d = cal.getTime();
	    ev.setFechaInicio(d);
	    System.out.println(d);
	    
	    h = Integer.parseInt(hFin.getSelectedItem());
	    m = Integer.parseInt(mFin.getSelectedItem());
	    cal.set(Calendar.HOUR, h);
	    cal.set(Calendar.MINUTE, m);
	
	    d = cal.getTime();
	    ev.setFechaFin(d);
	    System.out.println(id);
	    
	    almacen.addEvento("local", ev);
	    res= almacen.guardar("almacen.dat");
	    guardarId("id");
	    //System.out.println(res);
	    NuevoEvento.this.dispose();
	}
    }
    class ExitListener implements ActionListener{ 
	public void actionPerformed(ActionEvent e){
	    NuevoEvento.this.dispose();
		
	}
    }
    
    public boolean guardarId(String nombreFichero) {
	
	boolean res = false;
	ObjectOutputStream salida= null;
	
	try{
		salida= new ObjectOutputStream(new FileOutputStream (nombreFichero));
		salida.writeInt(id);
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
    
    public int recuperarId(String nombreFichero) {
	
	//boolean res = false;
	int rInt = 0;
	ObjectInputStream entrada = null;
	try {
		entrada = new ObjectInputStream ( new FileInputStream (nombreFichero));
	} catch (FileNotFoundException e1) {
		
	    guardarId(nombreFichero);
	    
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
		//res = false;
		System.out.println("Error2");	
	} 
	
	try{
		rInt = entrada.readInt();
	} catch (IOException e) {
		//res = false;
		e.printStackTrace();
	}finally {
		try {
			entrada.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	return rInt;
}

}
