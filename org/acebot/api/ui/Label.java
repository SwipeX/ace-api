package org.acebot.api.ui;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 1/5/13
 * Time: 4:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class Label extends Drawable {
        String text;

        public Label(String str) {
                text = str;
        }

        public void draw(Graphics g) {
                g.drawString(text, bounds.x + bounds.height / 2, bounds.width + bounds.width / 2);
        }
}
