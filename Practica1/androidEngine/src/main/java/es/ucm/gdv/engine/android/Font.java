package es.ucm.gdv.engine.android;

import android.content.res.AssetManager;
import android.graphics.Typeface;

public class Font implements es.ucm.gdv.engine.Font {

    private Typeface font_;
    private int size_;
    private boolean isBold_;

    Font(AssetManager assetManager, String filename, int size, boolean isBold){
        font_ = Typeface.createFromAsset(assetManager, filename);
        size_ = size;
        isBold_ = isBold;
    }

    public Typeface getFont() {
        return font_;
    }

    public int getSize() { return size_; }

    public boolean isBold() { return isBold_; }

}
