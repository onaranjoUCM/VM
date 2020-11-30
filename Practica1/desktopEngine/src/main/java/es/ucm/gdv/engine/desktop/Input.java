package es.ucm.gdv.engine.desktop;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import es.ucm.gdv.engine.TouchEvent;

public class Input extends es.ucm.gdv.engine.Input implements MouseListener {
    private TouchEvent event_;

    public Input(){
        super();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        event_ = new TouchEvent();
        int posX= mouseEvent.getX();
        int posY= mouseEvent.getY();
        event_.init(posX, posY,mouseEvent.getID(), mouseEvent.getButton());
        addEvent(event_);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        event_ = new TouchEvent();
        int posX= mouseEvent.getX();
        int posY= mouseEvent.getY();
        int id = 1;
        event_.init(posX, posY, id, mouseEvent.getButton());
        addEvent(event_);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        event_ = new TouchEvent();
        int posX= mouseEvent.getX();
        int posY= mouseEvent.getY();
        event_.init(posX, posY,mouseEvent.getID(), mouseEvent.getButton());
        addEvent(event_);
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        event_ = new TouchEvent();
        int posX= mouseEvent.getX();
        int posY= mouseEvent.getY();
        event_.init(posX, posY,mouseEvent.getID(), mouseEvent.getButton());
        addEvent(event_);
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        event_ = new TouchEvent();
        int posX= mouseEvent.getX();
        int posY= mouseEvent.getY();
        event_.init(posX, posY,mouseEvent.getID(), mouseEvent.getButton());
        addEvent(event_);
    }
}
