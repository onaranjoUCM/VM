package es.ucm.gdv.offtheline;

public class Utils {
    // Checks if two lines intersect and returns the intersection point
    static Vector2 segmentsIntersection(Vector2 start1, Vector2 end1, Vector2 start2, Vector2 end2) {
        if (intersect(start1, end1, start2, end2))
            return lineLineIntersection(start1, end1, start2, end2);
        else
            return null;
    }

    // Checks if two lines intersect
    // Source: https://github.com/steveshogren/VectorMath/blob/master/src/com/example/android/lasergame/Intersection.java
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

    // Returns the intersection point of two lines (Assumes the lines DO intersect)
    // Source: https://www.geeksforgeeks.org/program-for-point-of-intersection-of-two-lines/
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

    // Returns the point in a line closest to a given point
    // Source: https://stackoverflow.com/questions/40352842/calculate-distance-along-line-closest-to-other-point-aka-project
    static Vector2 getClosestPointOnSegment(Vector2 segA, Vector2 segB, Vector2 point)     {
        double xDelta = segB.x - segA.x;
        double yDelta = segB.y - segA.y;

        if ((xDelta == 0) && (yDelta == 0))
            throw new IllegalArgumentException("Segment start equals segment end");

        double u = ((point.x - segA.x) * xDelta + (point.y - segA.y) * yDelta) / (xDelta * xDelta + yDelta * yDelta);

        final Vector2 closestPoint;
        if (u < 0)
            closestPoint = segA;
        else if (u > 1)
            closestPoint = segB;
        else
            closestPoint = new Vector2((int) Math.round(segA.x + u * xDelta), (int) Math.round(segA.y + u * yDelta));

        return closestPoint;
    }

    // Returns the square of the distance between a point and a line
    static float sqrDistancePointSegment(Segment seg, Vector2 point) {
        Vector2 closestSegPoint = getClosestPointOnSegment(seg.pointA_, seg.pointB_, point);
        return sqrDistanceBetweenTwoPoints(closestSegPoint, point);
    }

    // Returns the direction of the vector made from two given points
    static Vector2 directionFromTwoPoints(Vector2 A, Vector2 B) {
        float num = (B.y - A.y);
        float den = (B.x - A.x);

        double angle = Math.toDegrees(Math.atan(num / den));
        if ((B.x < A.x)) angle += 180;

        return new Vector2((float)Math.cos(Math.toRadians(angle)), (float)Math.sin(Math.toRadians(angle)));
    }

    // Returns the square of the distance between two points
    static float sqrDistanceBetweenTwoPoints(Vector2 A, Vector2 B) {
        return (float)(A.x - B.x) * (A.x - B.x) + (A.y - B.y) * (A.y - B.y);
    }
}
