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

        try {
            _logo = javax.imageio.ImageIO.read(new java.io.File("java.png"));
            _imageWidth = _logo.getWidth(null);  
        } catch(Exception e){
            System.out.println(e);
        }   
        _x = 0;

        _lastFrameTime = System.nanoTime();
    }
    
    public void paint(Graphics g){
        //System.out.println("Repintado " + ++_repintado);
        //Graphics2D g2d = (Graphics2D) g;
        /*
        g.setColor(Color.blue);
        g.fillRect(100,100,200,200);
        g.setColor(new Color(255,0,0,128));
        g.fillRect(0,0,200,200);
        */

        //super.paint(g);

        g.setColor(Color.blue);
        g.fillRect(0, 0, getWidth(), getHeight());

        if(_logo != null){
            long currentTime = System.nanoTime();
            long nanoDelta = currentTime - _lastFrameTime;
            _lastFrameTime = currentTime;

            g.drawImage(_logo, (int)_x, 100, null);
            _x += ((double)_incX) * nanoDelta / 1.0E9;

            if(_x < 0){
                _x = -_x;
                _incX *= -1;
            } else if (_x >= (getWidth() - _imageWidth)) {
                _x = 2 * (getWidth() - _imageWidth) - _x;
                _incX *= -1;
            }
        }

        try {
            Thread.sleep(15);
        } catch(Exception e) { }

        repaint();
    }

    public static void main (String[] args){
        Paint ventana = new Paint("Paint");
        ventana.init();
        ventana.setVisible(true);        
    }

    //private int _repintado = 0;
    Image _logo;
    double _x;
    int _incX = 50; //Pixeles por segundo
    int _imageWidth;

    long _lastFrameTime;
}