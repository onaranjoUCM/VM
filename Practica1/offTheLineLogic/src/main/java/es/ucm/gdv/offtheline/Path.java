package es.ucm.gdv.offtheline;

import java.util.List;

import es.ucm.gdv.engine.Graphics;

public class Path extends GameObject {
    List<Vector2> vertices;
    List<Vector2> directions;

    public Path(List<Vector2> v, List<Vector2> d){
        super(0, 0, 0, 0);
        vertices = v;
        directions = d;
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
}
