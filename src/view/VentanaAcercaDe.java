package view;


import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class VentanaAcercaDe extends Dialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VentanaAcercaDe (Frame f) {
		super(f, "Acerca de ...", true);
		setLocationRelativeTo(f);
		add(new Label("Esqueleto de la P2 de PAA. Curso 2012-2013"));
		addWindowListener (new WindowAdapter (){ 
	          public void windowClosing(WindowEvent e) { 
	        	  VentanaAcercaDe.this.dispose(); 
	             } 
	          });
		pack();
	}
}
