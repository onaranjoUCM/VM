package es.ucm.gdv.engine.desktop;

import java.io.InputStream;

public class Font implements es.ucm.gdv.engine.Font {
    java.awt.Font font_;

    Font(String filename, int size, boolean N, Engine en){
        java.awt.Font F = null;
        try (InputStream is = en.openInputStream(filename)){
            F = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
        } catch (Exception e) {
            System.err.println("Error al cargan la fuente de texto "+filename+": " + e);
        }
        if(N) font_ = F.deriveFont(java.awt.Font.BOLD, size);
        else font_ = F.deriveFont(java.awt.Font.PLAIN, size);
    }

    public java.awt.Font getFont() { return font_; }
}
