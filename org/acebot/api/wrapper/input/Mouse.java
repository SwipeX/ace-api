package org.acebot.api.wrapper.input;


import javax.accessibility.Accessible;
import java.awt.*;
import java.awt.event.*;

public abstract class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener, Accessible {

        private int mouseX;
        private int mouseY;
        private int mousePressX = -1;
        private int mousePressY = -1;
        private long mousePressTime = -1;
        private boolean mousePresent;
        private boolean mousePressed;

        public int getX() {
                return mouseX;
        }

        public int getY() {
                return mouseY;
        }

        public Point getPoint() {
                return new Point(getX(), getY());
        }

        public int getPressX() {
                return mousePressX;
        }

        public int getPressY() {
                return mousePressY;
        }

        public long getPressTime() {
                return mousePressTime;
        }

        public boolean isPressed() {
                return mousePressed;
        }

        public boolean isPresent() {
                return mousePresent;
        }

        public final void mouseClicked(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                _mouseClicked(e);
                e.consume();
        }

        public final void mouseDragged(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                _mouseDragged(e);
                e.consume();
        }

        public final void mouseEntered(MouseEvent e) {
                mousePresent = true;
                mouseX = e.getX();
                mouseY = e.getY();
                _mouseEntered(e);
                e.consume();
        }

        public final void mouseExited(MouseEvent e) {
                mousePresent = false;
                mouseX = e.getX();
                mouseY = e.getY();
                _mouseExited(e);
                e.consume();
        }

        public final void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                _mouseMoved(e);
                e.consume();
        }

        public final void mousePressed(MouseEvent e) {
                mousePressed = true;
                mouseX = e.getX();
                mouseY = e.getY();
                _mousePressed(e);
                e.consume();
        }

        public final void mouseReleased(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                mousePressX = e.getX();
                mousePressY = e.getY();
                mousePressTime = System.currentTimeMillis();
                mousePressed = false;
                _mouseReleased(e);
                e.consume();
        }

        public void mouseWheelMoved(MouseWheelEvent e) {
                try {
                        _mouseWheelMoved(e);
                } catch (AbstractMethodError ame) {
                        ame.printStackTrace();
                }
                e.consume();
        }

        public abstract void _mouseClicked(MouseEvent e);

        public abstract void _mouseDragged(MouseEvent e);

        public abstract void _mouseEntered(MouseEvent e);

        public abstract void _mouseExited(MouseEvent e);

        public abstract void _mouseMoved(MouseEvent e);

        public abstract void _mousePressed(MouseEvent e);

        public abstract void _mouseReleased(MouseEvent e);

        public abstract void _mouseWheelMoved(MouseWheelEvent e);
}
