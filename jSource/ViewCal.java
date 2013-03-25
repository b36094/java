package view;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.*;

import paa.calendario.IEvento;

import almacen.AlmacenEventos;
public class ViewCal extends Panel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Button[] buttons ;
    private Panel pnl;
    private List lista;
    private Calendar cal;
    private AlmacenEventos almacen;
    private Date d;
    //private SimpleDateFormat f;
    

    public ViewCal(Calendar cal) {
	super.setLayout(new GridLayout(2,0,0,0));
	pnl = new Panel(new GridLayout(0,7,0,0));
	lista = new List();
	d = new Date();
	this.cal = cal;
	almacen = new AlmacenEventos();
	//almacen.addCalendario("local");

	
	int i;
	//String enteroString;
	//cal = Calendar.getInstance();
	String [] diasSemana = {"L","M","X","J","V","S","D"};
	buttons = new Button[49];
	
	
	for(i = 0; i< diasSemana.length; i++){
	    Button b = new Button(diasSemana[i]);
	    
	    b.setForeground(Color.RED);
	    buttons[i] = b;
	    pnl.add(b);
	    
	}
	//System.out.println(i);
	int j;
	for (j =i; j<buttons.length; j++){
	    Button b = new Button();
	    buttons[j] = b;
	    buttons[j].addActionListener(new ListarEventos());
	    pnl.add(buttons[j]);
	}
	
	pintarMes(cal);
	
	add(pnl);
	add(lista);
    }
    
    public Date getDate(){
	return d;
    }
    
    public void pintarMes(Calendar cal){
	
	int i;
	String enteroString;
	this.cal=cal;
	
	cal.set(Calendar.HOUR_OF_DAY, 0); 
	cal.clear(Calendar.MINUTE);
	cal.clear(Calendar.SECOND);
	cal.clear(Calendar.MILLISECOND);

	
	// get start of the month
	cal.set(Calendar.DAY_OF_MONTH, 1);
	int diaSem = cal.get(Calendar.DAY_OF_WEEK);
	
	System.out.println("Start of the month:       " + cal.getTime());
	for(int h = 7; h<buttons.length; h++){
	    buttons[h].setEnabled(false);
	    buttons[h].setLabel("");
	    
	}
	
	int j;
	int offset = 6;
	for( j=1;j<diaSem-1;j++){
	    buttons[j+offset].setEnabled(false);
	    buttons[j+offset].setLabel("");
	    
	    //add(buttons[j+offset]);
	}
	
	offset += j-1; 
	
	int dias = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	System.out.println(dias);
	
	for(i = 1; i<= dias; i++){
	    enteroString = Integer.toString(i);
	    buttons[i+offset].setEnabled(true);
	    buttons[i+offset].setLabel(enteroString);

	}
	
	offset +=i;
	//System.out.println(offset+"-" + buttons.length);
	for(int k=offset;k<buttons.length;k++){
	    buttons[k].setEnabled(false);
	    buttons[k].setLabel("");
	}
    }
    
    class ListarEventos implements ActionListener{  // Clase interna
	public void actionPerformed(ActionEvent e){
	    
	    boolean res = false;
	    String str = new String(e.getActionCommand());
	    int dia = Integer.parseInt(str);
	    cal.set(Calendar.DAY_OF_MONTH, dia);
	    res = almacen.recuperar("almacen.dat");
	    if(!res){
		almacen.addCalendario("local");
	    }
	    d = cal.getTime();
	    //System.out.println(d);
	   
	    Set <IEvento> eventos = (Set<IEvento>) almacen.getEventosDia("local",d);
	    lista.removeAll();
	    SimpleDateFormat f = new SimpleDateFormat("HH:mm");
	    
	    for(IEvento s: eventos){
		str = f.format(s.getFechaInicio());
		str += " " + s.getNombre();
		lista.add(str);
		//lista.addItemListener(new EvListener());
	    }	
	}
    }
    
   /* public int getEvSecciondao(){
	//lista.get
    }*/

}
