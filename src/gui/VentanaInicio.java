package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cliente.Cliente;

public class VentanaInicio  extends JFrame {
	

	private static final long serialVersionUID = 1L;

	public VentanaInicio(Cliente c){
		setSize(400, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent e) {
				c.getTracker().savePosition("inicio", getLocation());
			}
		});
			     
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		add(panel);
		 
		JLabel texto = new JLabel("Introduzca su nombre de usuario:");
		texto.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(texto);
		 
		JTextField campoNombre = new JTextField(15);
		campoNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(campoNombre);
		 
		JLabel mensajeError = new JLabel("");
		mensajeError.setForeground(Color.RED);
		mensajeError.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(mensajeError);
		
        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
			    String nombreUsuario = campoNombre.getText();
			    if (nombreUsuario.isEmpty()) {
			    	mensajeError.setText("El nombre de usuario no puede ser vacío");
			    } 
			    try {
					c.comprobarNombreUsuario(nombreUsuario);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		    	try {
					c.setNombre(nombreUsuario);
					c.mensajeInicioCliente();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		        setVisible(false);
			    }   
        });

        JButton botonVolver = new JButton("Salir");
        botonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				try {
					c.mensajeCerrar();
				} catch (IOException | InterruptedException e1) {
					e1.printStackTrace();
				}
                dispose();
            }
        });

        panelBotones.add(botonAceptar, BorderLayout.WEST);
        panelBotones.add(botonVolver, BorderLayout.EAST);

        add(panelBotones, BorderLayout.SOUTH);
		}

}
