package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import paa.calendario.*;

public class VentanaGoogleConnect extends Dialog {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private GoogleCalendar googleCal;
    private TextField tUser, tPass;

    public VentanaGoogleConnect(Frame f, GoogleCalendar googleCal) {
	super(f, "Google Connect" , true);
	setLocationRelativeTo(f);
	this.googleCal = googleCal;
	
	
	setLayout(new BorderLayout());
	Panel pnlCentro = new Panel(new FlowLayout());
	Panel pnlSur = new Panel(new FlowLayout());
	Label l1 = new Label("Username:");
	Label l2 = new Label("Password:");
	tUser = new TextField(29);
	tPass = new TextField(29);
	tPass.setEchoChar('*');
	
	pnlCentro.add(l1); pnlCentro.add(tUser);
	pnlCentro.add(l2); pnlCentro.add(tPass);
	add(pnlCentro, BorderLayout.CENTER);
	
	Button bAceptar = new Button("Aceptar");
	bAceptar.addActionListener(new Aceptar());
	Button bCancelar = new Button("Cancelar");
	bCancelar.addActionListener(new Cancelar());
	
	pnlSur.add(bAceptar); pnlSur.add(bCancelar);
	add(pnlSur, BorderLayout.SOUTH);
	
	setResizable(false);

	addWindowListener (new WindowAdapter (){ 
	    public void windowClosing(WindowEvent e) { 
		VentanaGoogleConnect.this.dispose(); 
	    } 
	});
	//pack();
	
    }

    class Aceptar implements ActionListener{ 
	public void actionPerformed(ActionEvent e){
	    String user = tUser.getText();
	    String pass = tPass.getText();
	    try {
		googleCal.conectar(user, pass);
	    } catch (GoogleCalendarException e1) {
		e1.printStackTrace();
	    }
	    
	    VentanaGoogleConnect.this.dispose();
		
	}
    }
    
    class Cancelar implements ActionListener{ 
	public void actionPerformed(ActionEvent e){
	    VentanaGoogleConnect.this.dispose();
		
	}
    }

}
