package es.ucm.gdv.desktopGame;

import  es.ucm.gdv.engine.desktop.Window;
import es.ucm.gdv.offtheline.OffTheLineLogic;
import es.ucm.gdv.engine.desktop.Engine;

public class Main {
        public static void main (String[] args){
            Engine e = new Engine();
            Window w = new Window("OFF THE LINE", 600, 400);
            e.init(w);

            OffTheLineLogic logic = new OffTheLineLogic();
            logic.init(e);
            logic.run();
        }
}