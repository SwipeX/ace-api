package org.acebot.api.ui;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 1/5/13
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Drawable {
        protected Rectangle bounds;

        public Drawable getParent() {
                return parent;
        }

        public Rectangle getBounds() {
                return bounds;
        }

        Drawable parent;

        public void outline(Graphics g) {
                g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }

        public abstract void draw(Graphics g);

        public void setRelativeBounds() {
                if (parent != null) {
                        bounds.x += parent.bounds.x;
                        bounds.y += parent.bounds.y;
                }
        }
}
