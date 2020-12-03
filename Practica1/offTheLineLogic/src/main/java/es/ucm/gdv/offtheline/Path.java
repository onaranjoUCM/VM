package es.ucm.gdv.offtheline;

import java.util.ArrayList;
import java.util.List;

import es.ucm.gdv.engine.Graphics;

public class Path extends GameObject {
    private List<Vector2> vertices;
    private List<Vector2> directions;
    List<Segment> segments;

    public Path(List<Vector2> v, List<Vector2> d){
        super(0, 0, 0, 0);
        vertices = v;
        directions = d;
        fillSegments();
    }

    @Override
    public void update(double deltaTime) { }

    @Override
    public void render(Graphics g) {
        g.setColor(255, 255, 255);
        for (int i = 1; i < vertices.size(); i++) {
            g.drawLine((int)vertices.get(i-1).x,(int) vertices.get(i - 1).y, (int)vertices.get(i).x, (int)vertices.get(i).y);
        }

        g.drawLine((int)vertices.get(0).x,(int) vertices.get(0).y, (int)vertices.get(vertices.size() - 1).x, (int)vertices.get(vertices.size() - 1).y);
    }

    private void fillSegments() {
        segments = new ArrayList<>();
        for(int i = 0; i < vertices.size(); i++) {
            if (directions.isEmpty()) {
                if (i + 1 < vertices.size())
                    segments.add(new Segment(vertices.get(i), vertices.get(i + 1)));
                else
                    segments.add(new Segment(vertices.get(i), vertices.get(0)));
            } else {
                if (i + 1 < vertices.size())
                    segments.add(new Segment(vertices.get(i), vertices.get(i + 1), directions.get(i)));
                else
                    segments.add(new Segment(vertices.get(i), vertices.get(0), directions.get(i)));
            }
        }
    }
}
