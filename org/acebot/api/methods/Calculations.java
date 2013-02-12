package org.acebot.api.methods;


import org.acebot.api.wrapper.animable.Locatable;
import org.acebot.api.wrapper.animable.Player;
import org.acebot.api.wrapper.animable.Tile;
import org.acebot.core.bot.Bot;
import org.acebot.impl.AbstractTileData;
import org.acebot.impl.LDGraphicsToolkit;

import java.awt.*;
import java.util.Random;
import java.util.Vector;

public class Calculations {

        public static int angleToTile(Tile t) {
                Tile me = Players.getLocal().getLocation();
                int angle = (int) Math.toDegrees(Math.atan2(t.getY() - me.getY(), t.getX() - me.getX()));
                return angle >= 0 ? angle : 360 + angle;
        }

        public static double distanceBetween(Point pt1, Point pt2) {
                return distance(pt1.x, pt2.x, pt1.y, pt2.y);
        }

        public static double distanceBetween(Tile t1, Tile t2) {
                return distance(t1.getX(), t2.getX(), t1.getY(), t2.getY());
        }

        public static double distance(int x1, int x2, int y1, int y2) {
                return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        }

        public static double distanceTo(Locatable l) {
                return distanceTo(l.getLocation().getX(), l.getLocation().getY());
        }

        public static double distanceTo(Tile t) {
                return distanceTo(t.getX(), t.getY());
        }

        public static double distanceTo(int x, int y) {
                int x2 = Players.getLocal().getLocation().getX();
                int y2 = Players.getLocal().getLocation().getY();
                return Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
        }

        public static Tile[] getSurrounding(final Tile t) {
                final Vector<Tile> neighbors = new Vector<Tile>();
                for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                                if (j != i || i != 0) {
                                        neighbors.add(new Tile(t.getX() + i, t.getY() + j));
                                }
                        }
                }
                return neighbors.toArray(new Tile[neighbors.size()]);
        }

        public static Polygon getTilePolygon(int x, int y) {
                Polygon p = new Polygon();
                Point center = Calculations.locationToScreen(x, y);
                Point n = Calculations.locationToScreen(x, y + 1);
                Point s = Calculations.locationToScreen(x, y - 1);
                Point e = Calculations.locationToScreen(x + 1, y);
                Point w = Calculations.locationToScreen(x - 1, y);
                p.addPoint((center.x + n.x + w.x) / 3, (center.y + n.y + w.y) / 3);
                p.addPoint((center.x + s.x + w.x) / 3, (center.y + s.y + w.y) / 3);
                p.addPoint((center.x + s.x + e.x) / 3, (center.y + s.y + e.y) / 3);
                p.addPoint((center.x + n.x + e.x) / 3, (center.y + n.y + e.y) / 3);
                return p;
        }

        public static Point groundToScreen(int x, int y) {
                try {
                        int z = tileHeight(x, y);
                        return worldToScreen(x, z, y);
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return new Point(-1, -1);
        }

        public static Point groundToScreen(int x, int y, int height) {
                try {
                        int z = tileHeight(x, y) - height;
                        return worldToScreen(x, z, y);
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return new Point(-1, -1);
        }

        public static boolean isOnScreen(Tile t) {
                return isOnScreen(t.getX(), t.getY());
        }

        public static boolean isOnScreen(Point p) {
                return Bot.getCanvas().contains(p);
        }

        public static boolean isOnScreen(int x, int y) {
                Point p = Calculations.locationToScreen(x, y);
                return (p.x > 0 && p.x < 515 && p.y > 54 && p.y < 388);
        }

        public static Point locationToScreen(int x, int y, int height) {
                x -= Bot.getClient().getLandscapeData().getMapOffset().getXOffset();
                y -= Bot.getClient().getLandscapeData().getMapOffset().getYOffset();
                return groundToScreen((int) ((x + 0.5) * 512), (int) ((y + 0.5) * 512), height);
        }

        public static Point locationToScreen(int x, int y) {
                try {
                        x = x - Bot.getClient().getLandscapeData().getMapOffset().getXOffset();
                        y = y - Bot.getClient().getLandscapeData().getMapOffset().getYOffset();
                        return groundToScreen((int) ((x + 0.5) * 512), (int) ((y + 0.5) * 512));
                } catch (Exception e) {
                }
                return new Point(-1, -1);
        }

        public static int random(int min, int max) {
                return new Random().nextInt(max - min) + min;
        }

        public static int tileHeight(int x, int y) {
                try {
                        int p = 0;
                        int x1 = x >> 9;
                        int y1 = y >> 9;
                        byte[][][] settings = Bot.getClient().getLandscapeData().getGroundBytes().getGroundBytes();
                        if (settings != null && x1 >= 0 && x1 < 104 && y1 >= 0 && y1 < 104) {
                                if (p <= 3 && (settings[1][x1][y1] & 2) != 0) {
                                        ++p;
                                }
                                AbstractTileData[] planes = Bot.getClient().getLandscapeData().getGroundInfo().getTileData();
                                if (planes != null && p < planes.length && planes[p] != null) {
                                        int[][] heights = planes[p].getHeights();
                                        if (heights != null) {
                                                int x2 = x & 0x200 - 1;
                                                int y2 = y & 0x200 - 1;
                                                int start_h = heights[x1][y1] * (0x200 - x2) + heights[x1 + 1][y1] * x2 >> 9;
                                                int end_h = heights[x1][1 + y1] * (0x200 - x2) + heights[x1 + 1][y1 + 1] * x2 >> 9;
                                                return start_h * (512 - y2) + end_h * y2 >> 9;
                                        }
                                }
                        }
                } catch (Exception e) {
                }
                return 0;
        }

        public static boolean tileOnMap(Tile t) {
                if (t == null)
                        return false;
                return !worldToMap(t.getX(), t.getY()).equals(new Point(-1, -1));
        }

        public static Point tileToScreen(Tile t) {
                return locationToScreen(t.getX(), t.getY());
        }

        public static Point tileToMap(Tile t) {
                Player p = Players.getLocal();
                if (p != null) {
                        Point center = worldToMap(p.getLocation().getX(), p.getLocation().getY());
                        Point tile = worldToMap(t.getX(), t.getY());
                        if (Calculations.distanceBetween(center, tile) < 70)
                                return worldToMap(t.getX(), t.getY());
                }
                return new Point(-1, -1);
        }

        public static Point worldToMap(int x, int y) {
                x -= Bot.getClient().getLandscapeData().getMapOffset().getXOffset();
                y -= Bot.getClient().getLandscapeData().getMapOffset().getYOffset();
                int regionTileX = Players.getLocal().getLocation().getX() - Bot.getClient().getLandscapeData().getMapOffset().getXOffset();
                int regionTileY = Players.getLocal().getLocation().getY() - Bot.getClient().getLandscapeData().getMapOffset().getYOffset();
                if (x > 104 || x < 0 || y > 104 || y < 0)
                        return new Point(-1, -1);
                final int cX = (int) (x * 4 + 2) - (regionTileX << 9) / 0x80;
                final int cY = (int) (y * 4 + 2) - (regionTileY << 9) / 0x80;
                final int actDistSq = cX * cX + cY * cY;
                final int mmDist = Math.max(154 / 2, 154 / 2) + 10;
                if (mmDist * mmDist >= actDistSq) {
                        int angle = 0x3fff & (int) Bot.getClient().getMinimapAngle();
                        final boolean mmsettingis4 = Bot.getClient().getMinimapSetting() == 4;
                        if (!mmsettingis4) {
                                angle = 0x3fff & Bot.getClient().getMinimapOffset() + (int) Bot.getClient().getMinimapAngle();
                        }
                        int sin = SIN_TABLE[angle];
                        int cos = COS_TABLE[angle];
                        if (!mmsettingis4) {
                                final int fact = 0x100 + Bot.getClient().getMinimapScale();
                                sin = 0x100 * sin / fact;
                                cos = 0x100 * cos / fact;
                        }
                        Point ret = new Point((cos * cX + sin * cY >> 0xf) + 550 + 154 / 2, -(cos * cY - sin * cX >> 0xf) + 58 + 154 / 2);
                        if (x == regionTileX && y == regionTileY)
                                return ret;
                }
                return new Point(-1, -1);
        }

        public static Point worldToScreen(final double x, final double y, final double z) {
                if (Bot.getLDGTK() == null) {
                        return new Point(1, 1);
                }
                if (Bot.getLDGTK().getMatrix() == null) {
                        return new Point(1, 1);
                }
                if (Bot.getLDGTK().getMatrix().getFloats() == null) {
                        return new Point(1, 1);
                }
                final LDGraphicsToolkit toolkit = Bot.getLDGTK();
                final float _z = (float) (getZOff() + (getZX() * x + getZY() * y + getZZ() * z));
                final float _x = (float) (getXOff() + (getXX() * x + getXY() * y + getXZ() * z));
                final float _y = (float) (getYOff() + (getYX() * x + getYY() * y + getYZ() * z));
                if (_x >= -_z && _x <= _z && _y >= -_z && _y <= _z) {
                        if (isFixed()) {
                                return new Point(
                                                Math.round(260 + (256 * _x) / _z),
                                                Math.round(171 + (167 * _y) / _z)
                                );
                        }
                        return new Point(
                                        Math.round(toolkit.getAbsoluteX() + (256 * _x) / _z),
                                        Math.round(toolkit.getAbsoluteY() + (167 * _y) / _z)
                        );
                }
                return new Point(-1, -1);
        }

        public static boolean isFixed() {
                return Bot.getClient().getClientInterfaceIndex() != 746;
        }

        public static float getZOff() {
                return Bot.getLDGTK().getMatrix().getFloats()[15];
        }

        public static float getYOff() {
                return Bot.getLDGTK().getMatrix().getFloats()[13];
        }

        public static float getXOff() {
                return Bot.getLDGTK().getMatrix().getFloats()[12];
        }

        public static float getZX() {
                return Bot.getLDGTK().getMatrix().getFloats()[3];
        }

        public static float getZY() {
                return Bot.getLDGTK().getMatrix().getFloats()[7];
        }

        public static float getZZ() {
                return Bot.getLDGTK().getMatrix().getFloats()[11];
        }

        public static float getXX() {
                return Bot.getLDGTK().getMatrix().getFloats()[0];
        }

        public static float getXY() {
                return Bot.getLDGTK().getMatrix().getFloats()[4];
        }

        public static float getXZ() {
                return Bot.getLDGTK().getMatrix().getFloats()[8];
        }

        public static float getYX() {
                return Bot.getLDGTK().getMatrix().getFloats()[1];
        }

        public static float getYY() {
                return Bot.getLDGTK().getMatrix().getFloats()[5];
        }

        public static float getYZ() {
                return Bot.getLDGTK().getMatrix().getFloats()[9];
        }

        public static final int[] SIN_TABLE = new int[0x4000];
        public static final int[] COS_TABLE = new int[0x4000];

        static {
                final double d = 0.00038349519697141029D;
                for (int i = 0; i < 0x4000; i++) {
                        Calculations.SIN_TABLE[i] = (int) (32768D * Math.sin(i * d));
                        Calculations.COS_TABLE[i] = (int) (32768D * Math.cos(i * d));
                }
        }
}