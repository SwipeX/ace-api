package org.acebot.api.ui;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 1/19/13
 * Time: 10:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Tile extends Drawable {
        boolean isDouble;
        Color foreGround = Color.WHITE;
        Color backGround = Color.BLUE;
        String text;

        public Tile(int x, int y, boolean adouble, Color c, String text) {
                isDouble = adouble;
                backGround = c;
                this.text = text;
                if (adouble)
                        bounds = new Rectangle(x, y, 150, 60);
                else
                        bounds = new Rectangle(x, y, 75, 60);

        }

        @Override
        public void draw(Graphics g) {
                g.setColor(backGround);
                g.fill3DRect(bounds.x, bounds.y, bounds.width, bounds.height, true);
                g.setColor(foreGround);
                g.drawString(text, bounds.x + 25, bounds.y + 25);
        }
}
