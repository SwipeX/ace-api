package org.acebot.api.wrapper.input;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class Keyboard extends Focus implements KeyListener {

        public abstract void _keyPressed(KeyEvent e);

        public abstract void _keyReleased(KeyEvent e);

        public abstract void _keyTyped(KeyEvent e);

        public void keyPressed(final KeyEvent e) {
                _keyPressed(e);
        }

        public void keyReleased(final KeyEvent e) {
                _keyReleased(e);
        }

        public void keyTyped(final KeyEvent e) {
                _keyTyped(e);
        }
}