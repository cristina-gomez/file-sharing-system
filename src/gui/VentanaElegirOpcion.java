package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cliente.Cliente;

public class VentanaElegirOpcion extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	public VentanaElegirOpcion(Cliente c) {
		setSize(400, 180);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent e) {
				c.getTracker().savePosition("elegir", getLocation());
			}
		});
			     
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		add(panel);
		
		JLabel textoBienvenida = new JLabel("Bienvenido/a, " + c.getNombre());
		panel.add(textoBienvenida);
	    textoBienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(15));
		JComboBox<String> opciones = new JComboBox<>(new String[] {
	            "Seleccione una opción", "Consultar información disponible", "Descargar información"
	    });
	    opciones.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel.add(opciones);
	    
	    JLabel mensajeError = new JLabel(" ");
        mensajeError.setForeground(Color.RED);
        mensajeError.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(mensajeError);
  
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) opciones.getSelectedItem();
                if ("Seleccione una opción".equals(seleccion)) {
                    mensajeError.setText("Debe seleccionar una opción");
                } else {
                    if (seleccion.equals("Consultar información disponible")) {
                    	try {
							c.mensajeConsultarInformacion();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
                    } else if (seleccion.equals("Descargar información")) {
                    	c.mostrarDescargar();
                    }
                	setVisible(false);
                }
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
