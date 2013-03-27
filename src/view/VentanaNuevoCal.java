package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import almacen.AlmacenEventos;

public class VentanaNuevoCal extends Dialog {
    private static final long serialVersionUID = 1L;
    private Panel pnl;
    private TextField tNCal;
    private Button bCrear,bCancelar;
    private AlmacenEventos almacen;

	public VentanaNuevoCal (Frame f, AlmacenEventos almacen) {
		super(f, "Nuevo Calendario", true);
		this.almacen = almacen;
		setLocationRelativeTo(f);
		setLayout(new BorderLayout());
		pnl = new Panel(new FlowLayout(FlowLayout.LEFT));
		Label l = new Label("Nombre calendario:", Label.CENTER);
		pnl.add(l);
		
		tNCal = new TextField(28);
		pnl.add(tNCal);
		
		add(pnl, BorderLayout.CENTER);
		
		Panel pnlSur = new Panel(new FlowLayout());
		bCrear = new Button("Crear");
		bCrear.addActionListener(new CrearListener());
		
		bCancelar = new Button("Cancelar");
		bCancelar.addActionListener(new CancelarListener());
		pnlSur.add(bCrear); pnlSur.add(bCancelar);
		add(pnlSur, BorderLayout.SOUTH);

		addWindowListener (new WindowAdapter (){ 
	          public void windowClosing(WindowEvent e) { 
	        	  VentanaNuevoCal.this.dispose(); 
	             } 
	          });
	}
	
	class CancelarListener implements ActionListener{ 
		public void actionPerformed(ActionEvent e){
		    VentanaNuevoCal.this.dispose();
			
		}
	    }
	
	class CrearListener implements ActionListener{ 
		public void actionPerformed(ActionEvent e){
		    almacen.addCalendario(tNCal.getText());
		    VentanaNuevoCal.this.dispose();
			
		}
	    }

}
