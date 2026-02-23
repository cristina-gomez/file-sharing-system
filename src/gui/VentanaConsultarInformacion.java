package gui;

import javax.swing.*;

import cliente.Cliente;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VentanaConsultarInformacion extends JFrame {
	
	private static final long serialVersionUID = 1L;

    public VentanaConsultarInformacion(Cliente c, List<String> usuarios, List<List<String>> contenido) {
        setTitle("Lista de Archivos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent e) {
				c.getTracker().savePosition("consultar", getLocation());
			}
		});
   
        List<String> lineasFinales = new ArrayList<>();
        for (int i = 0; i < usuarios.size(); i++) {
            String usuario = usuarios.get(i);
            List<String> ficheros = contenido.get(i);
            lineasFinales.add(usuario + ":");
            for (String fichero : ficheros) {
                lineasFinales.add(fichero);
            }
        }

        JList<String> lista = new JList<>(lineasFinales.toArray(new String[0]));
        JScrollPane scrollPane = new JScrollPane(lista);
        add(scrollPane, BorderLayout.CENTER);
        
        JButton boton = new JButton("Volver atrás");
        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
					c.mostrarElegirOpcionDesdeConsultar();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            	setVisible(false);

            }
        });
        JPanel panelBoton = new JPanel();
        panelBoton.add(boton);
        add(panelBoton, BorderLayout.SOUTH);
    }
}
