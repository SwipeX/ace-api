package org.acebot.api.methods;


import org.acebot.api.input.Mouse;
import org.acebot.api.wrapper.animable.Tile;
import org.acebot.core.bot.Bot;
import org.acebot.util.Time;
import org.acebot.util.Timer;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 12/10/12
 * Time: 4:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Walking {

        private static final int WIDGET = 750;
        private static final int WIDGET_RUN = 2;
        private static final int WIDGET_RUN_ENERGY = 6;

        public static void clickOnScreen(Tile t) {
                Mouse.click(t.getScreenLocation());
        }

        public static void clickOnMap(Tile t) {
                Mouse.click(Calculations.tileToMap(t));
        }

        public static Tile getDestination() {
                return new Tile(Bot.getClient().getDestX(), Bot.getClient().getDestY());
        }

        public static boolean isRunEnabled() {
                return Settings.get(493) == 1;
        }

        public static int getEnergy() {
                try {
                        return Integer.parseInt(Interfaces.get(WIDGET, WIDGET_RUN_ENERGY).getText());
                } catch (final NumberFormatException ignored) {
                        return -1;
                }
        }

        public static void setRun(final boolean enabled) {
                if (isRunEnabled() != enabled && Interfaces.get(WIDGET, WIDGET_RUN).click()) {
                        final Timer t = new Timer(1800);
                        while (t.isRunning() && isRunEnabled() != enabled) {
                                Time.sleep(5);
                        }
                }
        }

        public static Tile getClosestOnMap(Tile tile) {
                if (tile.isOnMap()) {
                        return tile;
                }
                final Tile location = Players.getLocal().getLocation();
                tile = tile.derive(-location.getX(), -location.getY());
                final double angle = Math.atan2(tile.getY(), tile.getX());
                return new Tile(
                                location.getX() + (int) (16d * Math.cos(angle)),
                                location.getY() + (int) (16d * Math.sin(angle)),
                                tile.getZ()
                );
        }

        /**
         * @param t
         * @return true if it made it, false if not.
         */
        public static boolean traverse(Tile t) {
                if (t.isOnMap()) {
                        clickOnMap(t);
                        return true;
                } else {
                        clickOnMap(getClosestOnMap(t));
                        return false;
                }
        }

        public static boolean traverse(Tile t, long timeout) {
                final long start = System.currentTimeMillis();
                while (System.currentTimeMillis() - start < timeout) {
                        if (Walking.getDestination() == null || Calculations.distanceTo(Walking.getDestination()) < 4)
                                traverse(t);
                        Time.sleep(2, 10);
                }
                return Calculations.distanceTo(t) < 4;
        }
}
