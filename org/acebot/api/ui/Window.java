package org.acebot.api.ui;

import java.awt.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 1/5/13
 * Time: 9:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Window extends Drawable {
        List<Drawable> children;
        Color Background;
        Color Foreground;
        String title;

        public Window(String titl, Rectangle boundaries) {
                bounds = boundaries;
                title = titl;
        }

        public void draw(Graphics g) {
                g.setColor(Background);
                g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
                g.setColor(Color.WHITE);
                g.drawString(title, bounds.x + 20, bounds.y + 2);
                for (Drawable child : children) {
                        child.draw(g);
                }
        }

        public void add(Drawable drawable) {
                drawable.parent = this;
                drawable.bounds.x += bounds.x;
                drawable.bounds.y += bounds.y;
                children.add(drawable);
        }
}
