package es.ucm.gdv.desktopGame;
import  es.ucm.gdv.engine.desktop.Window;

public class Main {
        public static void main (String[] args){
            String titulo = "OFF THE LINE";
            Window w = new Window(titulo, 600, 400);

        }
}
/*
import javax.swing.JFrame;
        import javax.swing.JButton;
        import java.awt.event.ActionListener;
        import java.awt.event.ActionEvent;

public class HolaSwing extends JFrame{
    public HolaSwing(String titulo){
        super(titulo);
        _title = titulo;
    }
    public void init(){

        setSize(400,400);
        setLayout(new java.awt.GridLayout(1,1));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton boton;
        boton = new JButton("Pulsame");
        add(boton);
        boton.addActionListener((ActionEvent e) -> System.out.println("PULSADO!"));
    }
    private void onClick(){
        System.out.println("PULSADO!");
    }

    public static void main (String[] args){
        HolaSwing ventana = new HolaSwing("Hola swing");
        ventana.init();
        ventana.setVisible(true);

        System.out.println("Ventana visible");
    }
    private String _title;
}*/