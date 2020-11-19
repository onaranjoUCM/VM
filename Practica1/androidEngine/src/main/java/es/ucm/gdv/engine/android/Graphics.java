package es.ucm.gdv.engine.android;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import es.ucm.gdv.engine.Font;

public class Graphics implements es.ucm.gdv.engine.Graphics {

    //IMPORTANTEEEEEEEEEEE
    //por tanto en lugar de hacer que vuestro Graphics herede de SurfaceView, organizarlo para que en el momento de pintar tenga el canvas donde hacerlo
    //o hacérselo llegar en cada frame*/

    Canvas _canvas;
    Paint _paint;

    public Graphics() {
        super();
        _paint = new Paint();
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        return null;
    }

    @Override
    public void clear(int r, int g, int b) {
        _canvas.drawColor(Color.rgb(r, g, b));
    }

    @Override
    public void translate(int x, int y) {

    }

    @Override
    public void scale(int x, int y) {

    }

    @Override
    public void rotate(float angle) {

    }

    @Override
    public void save() {

    }

    @Override
    public void restore() {

    }

    @Override
    public void setColor(int r, int g, int b) {

    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {

    }

    @Override
    public void fillRect(int x1, int y1, int x2, int y2) {

    }

    @Override
    public void drawText(String text, int x, int y) {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    public void setCanvas(Canvas c) {
        _canvas = c;
    }
}
