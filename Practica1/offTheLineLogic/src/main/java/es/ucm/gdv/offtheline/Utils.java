package es.ucm.gdv.offtheline;

public class Utils {

    static Vector2 segmentsIntersection(Vector2 start1, Vector2 end1, Vector2 start2, Vector2 end2) {
        if (intersect(start1, end1, start2, end2))
            return lineLineIntersection(start1, end1, start2, end2);
        else
            return null;
    }

    // From https://github.com/steveshogren/VectorMath/blob/master/src/com/example/android/lasergame/Intersection.java
    static boolean intersect(Vector2 start1, Vector2 end1, Vector2 start2, Vector2 end2) {
        double A1 = end1.y - start1.y;
        double B1 = start1.x - end1.x;
        double C1 = A1 * start1.x + B1 * start1.y;

        double A2 = end2.y - start2.y;
        double B2 = start2.x - end2.x;
        double C2 = A2 * start2.x + B2 * start2.y;

        double det = (A1 * B2) - (A2 * B1);

        if (det == 0) {
            if ((A1 * start2.x) + (B1 * start2.y) == C1) {
                if ((Math.min(start1.x, end1.x) < start2.x) && (Math.max(start1.x, end1.x) > start2.x))
                    return true;

                if ((Math.min(start1.x, end1.x) < end2.x) && (Math.max(start1.x, end1.x) > end2.x))
                    return true;

                return false;
            }

            return false;
        } else {
            double x = (B2 * C1 - B1 * C2) / det;
            double y = (A1 * C2 - A2 * C1) / det;

            if ((x >= Math.min(start1.x, end1.x) && x <= Math.max(start1.x, end1.x))
                    && (y >= Math.min(start1.y, end1.y) && y <= Math.max(start1.y, end1.y))) {
                if ((x >= Math.min(start2.x, end2.x) && x <= Math.max(start2.x, end2.x))
                        && (y >= Math.min(start2.y, end2.y) && y <= Math.max(start2.y, end2.y))) {
                    return true;
                }
            }

            return false;
        }
    }

    // From https://www.geeksforgeeks.org/program-for-point-of-intersection-of-two-lines/
    static Vector2 lineLineIntersection(Vector2 start1, Vector2 end1, Vector2 start2, Vector2 end2) {
        // Line AB represented as a1x + b1y = c1
        double a1 = end1.y - start1.y;
        double b1 = start1.x - end1.x;
        double c1 = a1*(start1.x) + b1*(start1.y);

        // Line CD represented as a2x + b2y = c2
        double a2 = end2.y - start2.y;
        double b2 = start2.x - end2.x;
        double c2 = a2*(start2.x)+ b2*(start2.y);

        double determinant = a1*b2 - a2*b1;

        if (determinant == 0)
        {
            // The lines are parallel. This is simplified
            // by returning a pair of FLT_MAX
            return new Vector2(Float.MAX_VALUE, Float.MAX_VALUE);
        }
        else
        {
            float x = (float)((b2*c1 - b1*c2)/determinant);
            float y = (float)((a1*c2 - a2*c1)/determinant);
            return new Vector2(x, y);
        }
    }

    static double module(Vector2 A, Vector2 B) {
        return Math.sqrt((B.x-A.x)*(B.x-A.x) + (B.y-A.y)*(B.y-A.y));
    }

    static Vector2 segmentFromTwoPoints(Vector2 A, Vector2 B) {
        return new Vector2(B.x - A.x, B.y - A.y);
    }
}
