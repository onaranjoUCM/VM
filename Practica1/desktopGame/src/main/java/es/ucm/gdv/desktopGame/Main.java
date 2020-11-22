package es.ucm.gdv.desktopGame;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import  es.ucm.gdv.engine.desktop.Window;
import es.ucm.gdv.offtheline.OffTheLineLogic;
import es.ucm.gdv.engine.desktop.Engine;
import es.ucm.gdv.offtheline.Player;

public class Main {
        public static void main (String[] args){
            Window w = new Window("OFF THE LINE", 600, 400);
            w.createBufferStrategy(2);
            BufferStrategy strategy = w.getBufferStrategy();

            Engine e = new Engine(w);
            OffTheLineLogic logic = new OffTheLineLogic(e);

            long lastFrameTime = System.nanoTime();
            while(true) {
                long currentTime = System.nanoTime();
                long nanoDelta = currentTime - lastFrameTime;
                lastFrameTime = currentTime;
                logic.update((double) nanoDelta / 1.0E9);

                // Render single frame
                do {
                    do {
                        Graphics graphics = strategy.getDrawGraphics();
                        e.getGraphics().setGraphics(graphics);
                        logic.render();
                        graphics.dispose();
                    // Repeat the rendering if the drawing buffer contents were restored
                    } while (strategy.contentsRestored());

                    // Display the buffer
                    strategy.show();
                // Repeat the rendering if the drawing buffer was lost
                } while(strategy.contentsLost());
            }
        }
}