package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Font;
import es.ucm.gdv.engine.Graphics;

public class Text extends GameObject {
    private Font f_;
    private String text_;
    private String fontfile_;
    int colorR_;
    int colorG_;
    int colorB_;
    int size_;
    public Text(float posX, float posY, int size, String fontfile, String text, int colorR, int colorG, int colorB, Graphics g){
        super(posX, posY, 0, 0);
        text_ = text;

        fontfile_ = fontfile;
        colorR_ = colorR;
        colorG_ = colorG;
        colorB_ = colorB;
        size_ = size;
        f_ = g.newFont(fontfile_, size_,true);
    }
    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render(Graphics g) {
        g.scale(1f , -1f);
        g.setFont(f_);
        g.setColor(colorR_, colorG_, colorB_);
        g.drawText(text_, (int)posX_,(int)posY_);
        g.scale(1f , -1f);
    }
}
