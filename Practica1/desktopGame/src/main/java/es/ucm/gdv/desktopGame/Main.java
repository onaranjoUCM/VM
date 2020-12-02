package es.ucm.gdv.desktopGame;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import es.ucm.gdv.engine.desktop.Input;
import  es.ucm.gdv.engine.desktop.Window;
import es.ucm.gdv.offtheline.OffTheLineLogic;
import es.ucm.gdv.engine.desktop.Engine;

public class Main {
        public static void main (String[] args){
            Input i = new Input();
            Window w = new Window("OFF THE LINE", 640, 480, i);
            w.createBufferStrategy(2);
            BufferStrategy strategy = w.getBufferStrategy();

            Engine e = new Engine(w);

            OffTheLineLogic logic = null;
            File json = new File("levels.json");
            try {
                logic = new OffTheLineLogic(e, new FileInputStream(json), i);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

            long lastFrameTime = System.nanoTime();
            while(true) {
                long currentTime = System.nanoTime();
                long nanoDelta = currentTime - lastFrameTime;
                lastFrameTime = currentTime;
                logic.handleInput();
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