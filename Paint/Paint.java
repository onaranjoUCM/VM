import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferStrategy;

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
    }
    
    public void update(double deltaTime) {
        if(_logo != null){
            _x += deltaTime * _incX;

            if(_x < 0){
                _x = -_x;
                _incX *= -1;
            } else if (_x >= (getWidth() - _imageWidth)) {
                _x = 2 * (getWidth() - _imageWidth) - _x;
                _incX *= -1;
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(0, 0, getWidth(), getHeight());
        if(_logo != null){
            g.drawImage(_logo, (int)_x, 100, null);
        }
    }

    public static void main (String[] args){
        Paint ventana = new Paint("Paint");
        ventana.init();
        ventana.setIgnoreRepaint(false);
        ventana.setVisible(true);
        
        long lastFrameTime = System.nanoTime();

        ventana.createBufferStrategy(2);
        BufferStrategy strategy = ventana.getBufferStrategy();

        while(true) {
            // Calculamos deltaTime
            long currentTime = System.nanoTime();
            long nanoDelta = currentTime - lastFrameTime;
            lastFrameTime = currentTime;

            ventana.update((double) nanoDelta / 1.0E9);
            Graphics g = strategy.getDrawGraphics();
            try {
                ventana.render(g);
            } finally {
                g.dispose();
            }

            // La forma correcta de usarlo esta en 
            // la documentacion de BufferStrategy
            strategy.show();
            
            /*
            try {
                Thread.sleep(15);
            } catch(Exception e) { }
            */
        }
    }

    Image _logo;
    double _x;
    int _incX = 50; //Pixeles por segundo
    int _imageWidth;
}