package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import paa.calendario.IEvento;

public class VentanaEvento extends Dialog {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Label l1,l2,l3,l4,l5,l6,l7,l8, l9,l10;
    
    public VentanaEvento(Frame f, IEvento ev) {
	super(f, "Evento", true);
	Panel pnlCentro = new Panel(new GridLayout(0,2,0,0));
	Panel pnlSur = new Panel(new FlowLayout());
	setLocationRelativeTo(f);
	setLayout(new BorderLayout());
	
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	l1 = new Label("Nombre");
	l2 = new Label(ev.getNombre());
	l3 = new Label("Lugar");
	l4 = new Label(ev.getLugar());
	l5 = new Label("Fecha Inicio");
	l6 = new Label(formatter.format(ev.getFechaInicio()));
	l7 = new Label("Fecha Fin");
	l8 = new Label(formatter.format(ev.getFechaFin()));
	l9 = new Label("Descripcion");
	l10 = new Label(ev.getDescripcion());
	
	Button bAceptar = new Button("Aceptar");
	bAceptar.addActionListener(new Aceptar());
	
	pnlCentro.add(l1); pnlCentro.add(l2); pnlCentro.add(l3); pnlCentro.add(l4); pnlCentro.add(l5); pnlCentro.add(l6); 
	pnlCentro.add(l7); pnlCentro.add(l8); pnlCentro.add(l9); pnlCentro.add(l10); pnlSur.add(bAceptar); 
	
	add(pnlCentro, BorderLayout.CENTER);
	add(pnlSur, BorderLayout.SOUTH);
	
	setResizable(false);

	addWindowListener (new WindowAdapter (){ 
	    public void windowClosing(WindowEvent e) { 
		VentanaEvento.this.dispose(); 
	    } 
	});
	pack();
    }
    
    class Aceptar implements ActionListener{ 
	public void actionPerformed(ActionEvent e){
	    VentanaEvento.this.dispose();
		
	}
    }

}
