package org.acebot.api.methods;


import org.acebot.api.input.Keyboard;
import org.acebot.api.wrapper.animable.Locatable;
import org.acebot.api.wrapper.animable.Tile;
import org.acebot.core.bot.Bot;
import org.acebot.util.Random;
import org.acebot.util.Time;
import org.acebot.util.Timer;

import java.awt.event.KeyEvent;

/**
 * @author Timer
 */
public class Camera {

        public static int getX() {
                return Bot.getClient().getCameraX();
        }

        public static int getY() {
                return Bot.getClient().getCameraY();
        }

        public static int getZ() {
                return Bot.getClient().getCameraZ();
        }

        public static int getYaw() {
                return Bot.getClient().getCameraYaw();
        }

        public static int getPitch() {
                return Bot.getClient().getCameraPitch();
        }

        public synchronized static boolean setPitch(final boolean up) {
                if (up) {
                        return setPitch(100);
                }
                return setPitch(0);
        }

        public synchronized static boolean setPitch(final int percent) {
                int curAlt = getPitch();
                int lastAlt = 0;
                if (curAlt == percent) {
                        return true;
                }
                final boolean up = curAlt < percent;
                Keyboard.pressKey(up ? (char) KeyEvent.VK_UP : (char) KeyEvent.VK_DOWN);
                final Timer timer = new Timer(100);
                while (timer.isRunning()) {
                        if (lastAlt != curAlt) {
                                timer.reset();
                        }
                        lastAlt = curAlt;
                        Time.sleep(Random.nextInt(5, 10));
                        curAlt = getPitch();
                        if (up && curAlt >= percent) {
                                break;
                        } else if (!up && curAlt <= percent) {
                                break;
                        }
                }
                Keyboard.releaseKey(up ? (char) KeyEvent.VK_UP : (char) KeyEvent.VK_DOWN);
                return curAlt == percent;
        }

        public synchronized static void setAngle(final char direction) {
                switch (direction) {
                        case 'n':
                                setAngle(0);
                                break;
                        case 'w':
                                setAngle(90);
                                break;
                        case 's':
                                setAngle(180);
                                break;
                        case 'e':
                                setAngle(270);
                                break;
                        default:
                                setAngle(0);
                                break;
                }
        }
//	public synchronized static boolean setNorth() {
//		return WidgetComposite.getCompass().click(true);
//	}
//
//	public synchronized static boolean setNorth(final int up) {
//		return WidgetComposite.getCompass().click(true) && setPitch(up);
//	}

        public synchronized static void setAngle(int degrees) {
                degrees %= 360;
                if (getAngleTo(degrees) > 5) {
                        Keyboard.pressKey((char) KeyEvent.VK_LEFT);
                        final Timer timer = new Timer(500);
                        int ang, prev = -1;
                        while ((ang = getAngleTo(degrees)) > 5 && Bot.getClient().getClientState() == 11 && timer.isRunning()) {
                                if (ang != prev) {
                                        timer.reset();
                                }
                                prev = ang;
                                Time.sleep(10);
                        }
                        Keyboard.releaseKey((char) KeyEvent.VK_LEFT);
                } else if (getAngleTo(degrees) < -5) {
                        Keyboard.pressKey((char) KeyEvent.VK_RIGHT);
                        final Timer timer = new Timer(500);
                        int ang, prev = -1;
                        while ((ang = getAngleTo(degrees)) < -5 && Bot.getClient().getClientState() == 11 && timer.isRunning()) {
                                if (ang != prev) {
                                        timer.reset();
                                }
                                prev = ang;
                                Time.sleep(10);
                        }
                        Keyboard.releaseKey((char) KeyEvent.VK_RIGHT);
                }
        }

        public static int getAngleTo(final int degrees) {
                int ca = getYaw();
                if (ca < degrees) {
                        ca += 360;
                }
                int da = ca - degrees;
                if (da > 180) {
                        da -= 360;
                }
                return da;
        }

        public synchronized static void turnTo(final Locatable l) {
                turnTo(l, 0);
        }

        public synchronized static void turnTo(final Locatable l, final int dev) {
                int angle = getMobileAngle(l);
                angle = Random.nextInt(angle - dev, angle + dev + 1);
                setAngle(angle);
        }

        public static int getMobileAngle(final Locatable mobile) {
                final Tile t = mobile.getLocation();
                final Tile me = Players.getLocal().getLocation();
                int angle = ((int) Math.toDegrees(Math.atan2(t.getY() - me.getY(), t.getX() - me.getX()))) - 90;
                if (angle < 0) {
                        angle = 360 + angle;
                }
                return angle % 360;
        }
}
