package es.ucm.gdv.engine.desktop;

import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Window extends JFrame {
    BufferStrategy strategy;
    public Window(String titulo, int W, int H){
        super(titulo);
        setSize(W, H);
        setLayout(new java.awt.GridLayout(1,1));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        //getContentPane().setBackground(Color.BLACK);
    }
}
