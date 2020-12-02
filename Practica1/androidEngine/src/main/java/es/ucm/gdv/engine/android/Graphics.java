package es.ucm.gdv.engine.android;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
public class Graphics implements es.ucm.gdv.engine.Graphics {

    //IMPORTANTEEEEEEEEEEE
    //por tanto en lugar de hacer que vuestro Graphics herede de SurfaceView, organizarlo para que en el momento de pintar tenga el canvas donde hacerlo
    //o hacérselo llegar en cada frame*/

    Canvas _canvas;
    Paint _paint;
    AssetManager assetManager_;

    public Graphics(AssetManager assetManager) {
        super();
        _paint = new Paint();
        assetManager_ = assetManager;
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        Font f = new Font(assetManager_, filename, size, isBold);
        setFont(f);
        return f;
    }

    @Override
    public void clear(int r, int g, int b) {
        _canvas.drawColor(Color.rgb(r, g, b));
    }

    @Override
    public void translate(int x, int y) {
        _canvas.translate(x, y);
    }

    @Override
    public void scale(float x, float y) {
        _canvas.scale(x, y);
    }

    @Override
    public void rotate(float angle) {
        _canvas.rotate(angle);
    }

    @Override
    public void save() {
        _canvas.save();
    }

    @Override
    public void restore() {
        _canvas.restore();
    }

    @Override
    public void setColor(int r, int g, int b) {
        _paint.setColor(Color.rgb(r, g, b));
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        _canvas.drawLine(x1, y1, x2, y2, _paint);
    }

    @Override
    public void fillRect(int x1, int y1, int x2, int y2) {
        _canvas.drawRect(x1, y1, x2, y2, _paint);
    }

    @Override
    public void setFont(es.ucm.gdv.engine.Font f) {
        if (f != null) {
            _paint.setTypeface(((Font) f).getFont());
            _paint.setFakeBoldText(((Font) f).isBold());
            _paint.setTextSize(((Font) f).getSize());
        }
    }

    @Override
    public void drawText(String text, int x, int y) {
        _canvas.drawText(text, x, y, _paint);
    }

    @Override
    public int getWidth() {
        return _canvas.getWidth();
    }

    @Override
    public int getHeight() {
        return _canvas.getHeight();
    }

    public void setCanvas(Canvas c) {
        _canvas = c;
    }
}
