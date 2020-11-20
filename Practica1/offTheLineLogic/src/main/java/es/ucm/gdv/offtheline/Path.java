package es.ucm.gdv.offtheline;

import java.util.ArrayList;
import java.util.List;

import es.ucm.gdv.engine.Graphics;

public class Path extends GameObject {
    List<float[]> vertices;

    public Path(List<float[]> v){
        super(0, 0, 0, 0);
        vertices = v;
    }

    @Override
    public void update(double deltaTime) { }

    @Override
    public void render(Graphics g) {
        g.setColor(255, 255, 255);
        for (int i = 1; i < vertices.size(); i++) {
            g.drawLine((int)vertices.get(i - 1)[0],(int) vertices.get(i - 1)[1], (int)vertices.get(i)[0], (int)vertices.get(i)[1]);
        }
        g.drawLine((int)vertices.get(0)[0],(int) vertices.get(0)[1], (int)vertices.get(vertices.size() - 1)[0], (int)vertices.get(vertices.size() - 1)[1]);
    }
}
