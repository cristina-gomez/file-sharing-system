package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import javax.swing.*;

import cliente.Cliente;

public class VentanaDescargar extends JFrame {

    private static final long serialVersionUID = 1L;
    JLabel mensajeError;

    public VentanaDescargar(Cliente c) {
    	setSize(400, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent e) {
				c.getTracker().savePosition("descargar", getLocation());
			}
		});
			     
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		add(panel);
		 
		JLabel texto = new JLabel("Introduzca el nombre del fichero a descargar:");
		texto.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(texto);
		 
		JTextField campoNombre = new JTextField(15);
		campoNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(campoNombre);
		 
		mensajeError = new JLabel("");
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
                String nombreFichero = campoNombre.getText();
                if (nombreFichero.isEmpty()) {
                    mensajeError.setText("El nombre de fichero no puede ser vacío");
                } else
					try {
						c.mensajeDescargarFichero(nombreFichero);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
            }
        });

        JButton botonVolver = new JButton("Volver atrás");
        botonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
					c.mostrarElegirOpcionDesdeDescargar();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
                setVisible(false);
            }
        });
        panelBotones.add(botonAceptar, BorderLayout.WEST);
        panelBotones.add(botonVolver, BorderLayout.EAST);

        add(panelBotones, BorderLayout.SOUTH);
    }
    
    public void usuarioYaPoseeArchivo() {
		mensajeError.setForeground(Color.RED);
    	 mensajeError.setText("El usuario ya posee ese archivo");
    }
    
    public void ficheroNoExistente() {
		mensajeError.setForeground(Color.RED);
        mensajeError.setText("No hay ningún fichero con ese nombre");
    }
    
    public void imagenDescargadaCorrectamente() {
		mensajeError.setForeground(Color.GREEN);
        mensajeError.setText("Imagen descargada correctamente");
    }
}
