package es.ucm.gdv.offtheline;

import java.awt.geom.Line2D;

public class Utils {
    static void segmentsIntersection(float x11, float y11, float x12, float y12, float x21, float y21, float x22, float y22){
        boolean x = Line2D.linesIntersect(x11,y11,x12,y12,x21,y21,x22,y22);
    }
}
