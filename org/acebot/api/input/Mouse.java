package org.acebot.api.input;


import org.acebot.core.bot.Bot;

import org.acebot.impl.Client;


import org.acebot.util.Random;
import org.acebot.util.Time;

import java.applet.Applet;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Tim, Marneus901, Static
 * Date: 11/29/12
 * Time: 8:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class Mouse {

    public static org.acebot.api.wrapper.input.Mouse mouse = ((org.acebot.api.wrapper.input.Mouse) Bot.getClient().getMouse());

    private static Speed speed = Speed.NORMAL;

    private static List<Point> current = new ArrayList<>();
    private static List<Point> previous = new ArrayList<>();

    public static enum Speed {
        SLOW(7, 12, 16),
        NORMAL(4, 6, 9), // 5 8 12
        FAST(2, 4, 6);

        public final int fast;
        public final int normal;
        public final int slow;

        Speed(final int fast, final int normal, final int slow) {
            this.fast = fast;
            this.normal = normal;
            this.slow = slow;
        }
    }

    public static void setSpeed(Speed speed) {
        Mouse.speed = speed;
    }

    public static Speed getSpeed() {
        return speed;
    }

    public static Point[] getPreviousPath() {
        return previous.toArray(new Point[previous.size()]);
    }

    public static Point[] getCurrentPath() {
        return current.toArray(new Point[current.size()]);
    }

    public static MouseClick getMouseClick() {
        Client client = Bot.getClient();
        for (MouseClick mouseClick : MouseClick.values()) {
            if (client.getMouseClickIndex() == mouseClick.getClickIndex()) {
                return mouseClick;
            }
        }
        return null;
    }

    public static MouseAction getMouseAction() {
        Client client = Bot.getClient();
        for (MouseAction mouseAction : MouseAction.values()) {
            if (client.getMouseActionIndex() == mouseAction.getActionId()) {
                return mouseAction;
            }
        }
        return null;
    }

    public static Component getTarget() {
        Applet applet = Bot.getApplet();
        return applet != null ? applet.getComponent(0) : null;
    }

    private static boolean isOffScreen(int x, int y) {
        return isOffScreen(new Point(x, y));
    }

    private static boolean isOffScreen(Point p) {
        if (p != null) {
            Applet applet = Bot.getApplet();
            return applet != null && (p.x < 0 || p.x > applet.getWidth() || p.y < 0 || p.y > applet.getHeight());
        }
        return true;
    }

    public static boolean isOffScreen() {
        return isOffScreen(new Point(mouse.getX(), mouse.getY()));
    }

    public static synchronized boolean move(int x, int y) {
        previous.clear();
        Point mouse = getLocation();
        if (mouse.distance(x, y) <= 2) {
            current.clear();
            return true;
        }
        List<Point> tracked = new ArrayList<>();
        Point origin = new Point(mouse.x, mouse.y);
        if (origin.distance(x, y) <= 100) {
            double dist;
            while ((dist = mouse.distance(x, y)) >= 2) {
                int vx = (mouse.x - x);
                int vy = (mouse.y - y);
                int dx = (int) Math.round(vx / dist);
                if (Math.abs(x - mouse.x) <= 5) {
                    dx += Random.nextInt(-1, 1);
                }
                int dy = (int) Math.round(vy / dist);
                if (Random.nextInt(1, 10) <= 3) {
                    dy += mouse.y > y ? 1 : -1;
                }
                Point p = new Point(mouse.x - dx, mouse.y - dy);
                current.add(p);
                hop(p.x, p.y);
                Time.sleep(speed.normal);
                mouse = getLocation();
            }
        } else {
            Applet applet = Bot.getApplet();
            if (applet == null) {
                return false;
            }
            Dimension dimensions = new Dimension(applet.getWidth(), applet.getHeight());
            Point p0 = new Point((int) ((origin.x + x) / 1.5D), (int) ((origin.y + y) / 1.5D));
            final int p0x1 = p0.x + Random.nextInt((int) (-(origin.x + x) / 1.5D), (int) ((origin.x + x) / 1.5D));
            if (p0x1 > 0 && p0x1 < dimensions.width) {
                p0.x = p0x1;
            }
            int p0y1 = p0.y + Random.nextInt((int) (-(origin.x + x) / 1.5D), (int) ((origin.y + y) / 1.5D));
            if (p0y1 > 0 && p0y1 < dimensions.height) {
                p0.y = p0y1;
            }
            Point p1 = new Point(origin.x, origin.y);
            Point p2 = new Point(x, y);
            Point p3 = new Point((int) ((origin.x + x) / 1.5D), (int) ((origin.y + y) / 1.5D));
            int p0x2 = p3.x + Random.nextInt((int) (-(origin.x + x) / 1.5D), (int) ((origin.x + x) / 1.5D));
            if (p0x2 > 0 && p0x2 < dimensions.width) {
                p3.x = p0x2;
            }
            int p0y2 = p3.y + Random.nextInt((int) (-(origin.x + x) / 1.5D), (int) ((origin.y + y) / 1.5D));
            if (p0y2 > 0 && p0y2 < dimensions.height) {
                p3.y = p0y2;
            }
            double total = Random.nextInt(1, 10) < 5 ? 1.0D : Random.nextInt(1, 5) <= 3 ? 0.99D : 1.01D;
            double increment = Random.nextInt(1, 10) < 5 ? 0.01D : Random.nextInt(1, 5) <= 3 ? 0.02D : 0.025D;
            for (double t = 0; t <= total; t += increment) {
                double tx = 0.5F * ((2D * p1.x) + (p2.x - p0.x) * t + (2D * p0.x - 5D * p1.x + 4D * p2.x - p3.x) * t * t + (3D * p1.x - p0.x - 3D * p2.x + p3.x) * t * t * t);
                double ty = 0.5F * ((2D * p1.y) + (p2.y - p0.y) * t + (2D * p0.y - 5D * p1.y + 4D * p2.y - p3.y) * t * t + (3D * p1.y - p0.y - 3D * p2.y + p3.y) * t * t * t);
                Point move = new Point((int) tx, (int) ty);
                if (tracked.contains(move)) {
                    continue;
                }
                current.add(move);
                tracked.add(move);
                hop(move.x, move.y);
                if (t >= 0.75D) {
                    Time.sleep(speed.slow);
                } else if (t <= 0.4D) {
                    Time.sleep(speed.fast);
                } else {
                    Time.sleep(speed.normal);
                }
                if (new Point(x, y).distance(tx, ty) <= 3) {
                    break;
                }
            }
        }
        previous.addAll(current);
        current.clear();
        return true;
    }

    public static void move(Point p) {
        move(p.x, p.y);
    }

    public static void hop(int x, int y) {
        if (isOffScreen(x, y)) {
            return;
        }
        Applet applet = Bot.getApplet();
        if (applet == null) {
            return;
        }
        mouse.mouseMoved(new MouseEvent(applet, MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, x, y, 0, false));
    }

    public static void hop(Point p) {
        hop(p.x, p.y);
    }

    public static void press(int x, int y, int button) {
        Applet applet = Bot.getApplet();
        if (applet == null) {
            return;
        }
        mouse.mousePressed(new MouseEvent(applet, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), 0, x, y, 1, false, button));
    }

    public static void press(Point p, int button) {
        press(p.x, p.y, button);
    }

    public static void press(int button) {
        press(getLocation(), button);
    }

    public static void release(int button) {
        release(getLocation(), button);
    }

    public static void release(int x, int y, int button) {
        Applet applet = Bot.getApplet();
        if (applet == null) {
            return;
        }
        if (mouse == null) {
            return;
        }
        mouse.mouseReleased(new MouseEvent(applet, MouseEvent.MOUSE_RELEASED, System.currentTimeMillis(), 0, x, y, 1, false, button));
        mouse.mouseClicked(new MouseEvent(applet, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, x, y, 1, false, button));
    }

    public static void release(Point p, int button) {
        release(p.x, p.y, button);
    }

    public static void click(Point p, boolean left) {
        move(p);
        click(left);
    }

    public static void click(final Point p) {
        click(p, true);
    }

    public static void click(int x, int y) {
        click(new Point(x, y));
    }

    public static void click(boolean left) {
        int button;
        if (!left) {
            button = MouseEvent.BUTTON3;
        } else {
            button = MouseEvent.BUTTON1;
        }
        press(button);
        Time.sleep(Random.nextInt(50, 120));
        release(button);
    }

    public static void click(int x, int y, boolean left) {
        move(x, y);
        click(left);
    }

    public static void exit(int x, int y) {
        Applet applet = Bot.getApplet();
        if (applet == null) {
            return;
        }
        mouse._mouseExited(new MouseEvent(applet, MouseEvent.MOUSE_EXITED, System.currentTimeMillis(), 0, x, y, 0, false, MouseEvent.NOBUTTON));
    }

    public static void exit(Point p) {
        exit(p.x, p.y);
    }

    public static void enter(int x, int y) {
        Applet applet = Bot.getApplet();
        if (applet == null) {
            return;
        }
        Component target = getTarget();
        if (target == null) {
            return;
        }
        target.dispatchEvent(new MouseEvent(applet, MouseEvent.MOUSE_ENTERED, System.currentTimeMillis(), 0, x, y, 0, false, MouseEvent.NOBUTTON));
    }

    public static void enter(Point p) {
        enter(p.x, p.y);
    }

    public static void getFocus() {
        Applet applet = Bot.getApplet();
        if (applet == null) {
            return;
        }
        Component target = getTarget();
        if (target == null) {
            return;
        }
        target.dispatchEvent(new FocusEvent(applet, FocusEvent.FOCUS_GAINED));
    }

    public static Point getRealLocation() {
        Applet applet = Bot.getApplet();
        if (applet == null) {
            return new Point(-1, -1);
        }
        Point p = applet.getMousePosition();
        return p != null ? p : new Point(-1, -1);
    }

    public static void drag(Point start, Point end) {
        move(start.x, start.y);
        Time.sleep(125, 250);
        press(start.x, start.y, 1);
        Time.sleep(75, 150);
        move(end.x, end.y);
        Time.sleep(125, 250);
        release(1);
        Time.sleep(125, 250);
    }

    public static Point getLocation() {
        return mouse.getPoint();
    }

    public enum MouseClick {
        NONE(0),
        YELLOW(1),
        RED(2);

        private int clickIndex;

        MouseClick(int clickIndex) {
            this.clickIndex = clickIndex;
        }

        public int getClickIndex() {
            return clickIndex;
        }

        public String toString() {
            return name().charAt(0) + name().substring(1).toLowerCase() + "  (" + getClickIndex() + ")";
        }
    }

    public enum MouseAction {
        NOTHING(36),
        GENERIC(41),
        ATTACK(42),
        TALK(44),
        TAKE(45),
        USE(46),
        EAT(47),
        DRINK(48),
        OPEN(49),
        READ(50),
        EQUIP(51),
        CLIMB_UP(52),
        CLIMB_DOWN(53),
        CLIMB(54),
        INSPECT(56),
        EXIT(57),
        ENTER(57),
        MINING(58),
        WOODCUTTING(59),
        FISHING(60),
        PRAYER(61),
        CRAFTING(62),
        SMITHING(63),
        FIREMAKING(65),
        RUNECRAFTING(66),
        CAST_SPELL_WIND_STRIKE(67),
        CAST_SPELL_CONFUSE(90),
        CAST_SPELL_WATER_STRIKE(68),
        CAST_SPELL_LEVEL1_ENCHANT(103),
        CAST_SPELL_EARTH_STRIKE(69),
        CAST_SPELL_WEAKEN(91),
        CAST_SPELL_FIRE_STRIKE(70),
        CAST_SPELL_WIND_BOLT(71),
        CAST_SPELL_CURSE(92),
        CAST_SPELL_BIND(93),
        CAST_SPELL_LOW_LEVEL_ALCHEMY(109),
        CAST_SPELL_WATER_BOLT(72),
        CAST_SPELL_LEVEL2_ENCHANT(104),
        CAST_SPELL_EARTH_BOLT(73),
        CAST_SPELL_TELEKINETIC_GRAB(111),
        CAST_SPELL_FIRE_BOLT(74),
        CAST_SPELL_CRUMBLE_UNDEAD(87),
        CAST_SPELL_WIND_BLAST(75),
        CAST_SPELL_SUPERHEAT_ITEM(115),
        CAST_SPELL_WATER_BLAST(76),
        CAST_SPELL_LEVEL3_ENCHANT(105),
        CAST_SPELL_IBAN_BLAST(89),
        CAST_SPELL_SNARE(94),
        CAST_SPELL_MAGIC_DART(88),
        CAST_SPELL_EARTH_BLAST(77),
        CAST_SPELL_HIGH_LEVEL_ALCHEMY(110),
        CAST_SPELL_LEVEL4_ENCHANT(106),
        CAST_SPELL_FIRE_BLAST(78),
        CAST_SPELL_SARADOMIN_STRIKE(100),
        CAST_SPELL_CLAWS_OF_GUTHIX(101),
        CAST_SPELL_FLAMES_OF_ZAMORAK(102),
        CAST_SPELL_WIND_WAVE(82),
        CAST_SPELL_WATER_WAVE(81),
        CAST_SPELL_VULNERABILITY(95),
        CAST_SPELL_LEVEL5_ENCHANT(107),
        CAST_SPELL_EARTH_WAVE(80),
        CAST_SPELL_ENFEEBLE(96),
        CAST_SPELL_TELEOTHER_LUMBRIDGE(114),
        CAST_SPELL_FIRE_WAVE(79),
        CAST_SPELL_ENTANGLE(97),
        CAST_SPELL_STUN(98),
        CAST_SPELL_WIND_SURGE(151),
        CAST_SPELL_TELEOTHER_FALADOR(112),
        CAST_SPELL_TELEPORT_BLOCK(99),
        CAST_SPELL_WATER_SURGE(152),
        CAST_SPELL_LEVEL6_ENCHANT(108),
        CAST_SPELL_EARTH_SURGE(153),
        CAST_SPELL_TELEOTHER_CAMELOT(113),
        CAST_SPELL_FIRE_SURGE(154),
        SETUP(172),
        AGILITY(181),
        SELECT_TARGET(185);

        private int ACTION_ID;

        private MouseAction(int actionID) {
            this.ACTION_ID = actionID;
        }

        public int getActionId() {
            return ACTION_ID;
        }

        public String toString() {
            return name().charAt(0) + name().substring(1).toLowerCase().replaceAll("_", " ") + "   (" + getActionId() + ")";
        }
    }
}