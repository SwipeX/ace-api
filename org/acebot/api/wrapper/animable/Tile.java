package org.acebot.api.wrapper.animable;


import org.acebot.api.input.Mouse;
import org.acebot.api.methods.Calculations;
import org.acebot.core.bot.Bot;
import org.acebot.util.Random;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 11/23/12
 * Time: 2:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class Tile implements Locatable {

        int x;
        int y;
        int z;

        public Tile(int a, int b, int c) {
                x = a;
                y = b;
                z = c;
        }

        public Tile(int a, int b) {
                x = a;
                y = b;
                z = 0;
        }

        public int getX() {
                return x;
        }

        public int getY() {
                return y;
        }

        public int getZ() {
                return z;
        }

        @Override
        public Tile getLocation() {
                return this;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Point getScreenLocation() {
                return Calculations.locationToScreen(getLocation().getX(), getLocation().getY());
        }

        public Rectangle getBounds() {
                return new Rectangle(getScreenLocation().x, getScreenLocation().y, 39, 39);
        }

        public String toString() {
                return "(" + x + "," + y + ")";
        }

        public Tile derive(final int x, final int y) {
                return new Tile(this.x + x, this.y + y, this.z);
        }

        public Point getMapPoint() {
                return Calculations.worldToMap(x, y);
        }

        public boolean isOnMap() {
                final Point p = getMapPoint();
                return p.x != -1 && p.y != -1;
        }

        public boolean isOnScreen() {
                return Bot.getCanvas().contains(getScreenLocation());
        }

        public boolean interact(String action, String option) {
                Mouse.move(Random.nextPoint(getBounds()));
                return org.acebot.api.methods.Menu.select(action, option);
        }

        public boolean interact(String action) {
                return interact(action, null);
        }
}
