package es.ucm.gdv.engine.desktop;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import es.ucm.gdv.engine.TouchEvent;

public class Input implements MouseListener, MouseMotionListener, KeyListener {

    public List<TouchEvent> getTouchEvents() {
        return null;
    }

    private void registerEvent(MouseEvent mouseEvent, TouchEvent.EventType type)
    {
        //Rellenamos el evento
        TouchEvent evt = new TouchEvent();
        evt.posX = mouseEvent.getX();
        evt.posY = mouseEvent.getY();
        evt.id = mouseEvent.getID();
        evt.type = type;

        //addEvent(evt);
    }

    private void registerEvent(KeyEvent keyEvent, TouchEvent.EventType type)
    {
        //Rellenamos el evento
        TouchEvent evt = new TouchEvent();
        evt.posX = 0;
        evt.posY = 0;
        evt.id = keyEvent.getID();
        evt.type = type;

        //addEvent(evt);
    }

    public void mousePressed(MouseEvent mouseEvent)
    {
        registerEvent(mouseEvent, TouchEvent.EventType.PRESSED);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent)
    {
        registerEvent(mouseEvent, TouchEvent.EventType.RELEASED);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) { }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) { }

    @Override
    public void mouseExited(MouseEvent mouseEvent) { }

    //DE MOUSE MOTION LISTENER
    @Override
    public void mouseMoved(MouseEvent mouseEvent)
    {
        registerEvent(mouseEvent, TouchEvent.EventType.MOVED);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) { }



    @Override
    public void keyPressed(KeyEvent keyEvent)
    {
        if(keyEvent.getKeyCode() == KeyEvent.VK_SPACE)
            registerEvent(keyEvent, TouchEvent.EventType.PRESSED);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) { }

    @Override
    public void keyTyped(KeyEvent keyEvent) { }
}
