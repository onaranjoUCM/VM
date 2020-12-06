package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Font;
import es.ucm.gdv.engine.Graphics;

public class Button extends GameObject {

    private Font f_;
    private String text_;
    private String fontfile_;
    int colorR_;
    int colorG_;
    int colorB_;
    int id_;

    public Button(float posX, float posY, int W, int H, int id, String fontfile, String text, int colorR, int colorG, int colorB, Graphics g){
        super(posX, posY, W, H);
        text_ = text;
        fontfile_ = fontfile;
        colorR_ = colorR;
        colorG_ = colorG;
        colorB_ = colorB;
        f_ = g.newFont(fontfile_, H_,true);
        id_ = id;
    }
    @Override
    public void update(double deltaTime) {

    }

    public boolean button_pressed(int clickX, int clickY) {
        // Check if the click event was captured inside the button boundaries
        if(clickX >= posX_ && clickX <= W_+ posX_ && clickY <= posY_ && clickY >= posY_ - H_ ){
            return true;
        }
        return false;
    }

    @Override
    public void render(Graphics g) {
        g.scale(1f , -1f);
        g.setFont(f_);
        g.setColor(colorR_, colorG_, colorB_);
        g.drawText(text_, (int)posX_,(int)posY_);
        g.scale(1f , -1f);
    }

    public int getId_(){
        return id_;
    }
}
