package es.ucm.gdv.engine.desktop;

import java.awt.Color;

import javax.swing.JFrame;

public class Window extends JFrame {
    public Window(String titulo, int W, int H, Input i){
        super(titulo);
        setSize(W, H);
        setLayout(new java.awt.GridLayout(1,1));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        getContentPane().setBackground(Color.BLACK);
        this.addMouseListener(i);
    }
}
