package es.ucm.gdv.engine.desktop;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Window extends JFrame{

    public Window(String titulo, int W, int H){
        super(titulo);
        init(W, H);
        setVisible(true);
        getContentPane().setBackground(Color.BLACK);
    }
    public void init(int W, int H){
        setSize(W, H);
        setLayout(new java.awt.GridLayout(1,1));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
