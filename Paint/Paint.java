import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;

public class Paint extends JFrame{
    
    public Paint(String titulo){
        super(titulo);
    }
    public void init(){

        setSize(400,400);       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try{
            _logo = javax.imageio.ImageIO.read(new java.io.File("java.png"));
        }
        catch(Exception e){
            System.out.println(e);
        }   
        _x=0;     
    }
    
    public void paint(Graphics g){
        //System.out.println("Repintado " + ++_repintado);
        //Graphics2D g2d = (Graphics2D) g;
       /*g.setColor(Color.blue);
        g.fillRect(100,100,200,200);
        g.setColor(new Color(255,0,0,128));
        g.fillRect(0,0,200,200);*/
        if(_logo !=null){
            g.drawImage(_logo, _x, 100, null);
            _x +=_incX;
            if(_x < 0){
                _X = -_X;
                _incX *= -1;
            }
            else{
                
            }
        }
    }

    public static void main (String[] args){
        Paint ventana = new Paint("Paint");
        ventana.init();
        ventana.setVisible(true);        
    }
    //private int _repintado = 0;
    Image _logo;
    int _x;
    int _incX = 5;
}