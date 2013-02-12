package org.acebot.api.ui;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 1/5/13
 * Time: 9:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class CheckBox extends Drawable {
        String text;
        boolean checked;

        public CheckBox(String str, Point location) {
                this.text = text;
                bounds = new Rectangle(location.x, location.y, 10 + text.length() * 2, 9);
        }

        public void setChecked(boolean isChecked) {
                checked = isChecked;
        }

        public void draw(Graphics g) {
                g.setColor(Color.BLACK);
                g.drawRect(bounds.x, bounds.y, 7, 7);
                if (checked) {
                        g.setColor(Color.GREEN);
                        g.drawLine(bounds.x, bounds.y, bounds.x + 3, bounds.y + 7);
                        g.drawLine(bounds.x + 3, bounds.y + 7, bounds.x + 8, bounds.y - 2);
                } else {
                        g.setColor(Color.RED);
                        g.drawLine(bounds.x + 7, bounds.y, bounds.x, bounds.y + 7);
                        g.drawLine(bounds.x, bounds.y + 7, bounds.x + 7, bounds.y);
                }
                g.setColor(Color.BLACK);
                g.drawString(text, bounds.x + 10, bounds.y + 4);

        }
}
