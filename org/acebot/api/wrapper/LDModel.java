package org.acebot.api.wrapper;


import org.acebot.api.input.Mouse;
import org.acebot.api.methods.Calculations;
import org.acebot.api.wrapper.animable.Tile;
import org.acebot.core.bot.Bot;
import org.acebot.impl.Animable;
import org.acebot.impl.Model;
import org.acebot.util.Random;

import java.awt.*;
import java.util.ArrayList;

/**
 * Copyright Tim Dekker
 *
 * @author Swipe
 *         Date: 12/9/12
 */
public class LDModel {

        public org.acebot.impl.LDModel model;
        public Tile loc;
        public Animable a;

        public LDModel(Model model, Tile a) {
                this.model = (org.acebot.impl.LDModel) model;
                loc = new Tile(a.getX() - Bot.getClient().getLandscapeData().getMapOffset().getXOffset(), a.getY() - Bot.getClient().getLandscapeData().getMapOffset().getYOffset());
                update();
        }

        public LDModel(Model model, Animable a) {
                this.model = (org.acebot.impl.LDModel) model;
                this.a = a;
                update();
        }

        public void draw(Graphics g) {
                for (Polygon p : getWireframe()) {
                        g.drawPolygon(p);
                }
        }

        public Point getCentralPoint() {
                org.acebot.impl.LDModel ldModel = (org.acebot.impl.LDModel) model;
                if (ldModel == null || ldModel.getIndices1() == null || ldModel.getIndices1().length < 1)
                        return new Point(-1, -1);
                int numFaces = Math.min(ldModel.getXPoints().length, Math.min(ldModel.getYPoints().length, ldModel.getZPoints().length));
                if (numFaces < 1) {
                        return new Point(-1, -1);
                }
                int totalXAverage = 0;
                int totalYAverage = 0;
                int totalHeightAverage = 0;
                double locX;
                double locY;
                if (a == null) {
                        locX = (loc.getX() + 0.5) * 512;
                        locY = (loc.getY() + 0.5) * 512;
                } else {
                        locX = (a.getMinX() + 0.5) * 512;
                        locY = (a.getMinY() + 0.5) * 512;
                        if (this.a.getMinX() != this.a.getMaxX()) {
                                locX += (this.a.getMaxX() + 0.5) * 512;
                                locX /= 2;
                        }
                        if (this.a.getMinY() != this.a.getMaxY()) {
                                locY += (this.a.getMaxY() + 0.5) * 512;
                                locY /= 2;
                        }
                }
                int height = Calculations.tileHeight((int) locX, (int) locY);
                final int[][] points = projectVertices();
                short[] trix = ldModel.getIndices1();
                short[] triy = ldModel.getIndices2();
                short[] triz = ldModel.getIndices3();
                int numTriangles = Math.min(trix.length, Math.min(triy.length, triz.length));
                for (int i = 0; i < numTriangles; i++) {
                        final int index1 = trix[i];
                        final int index2 = triy[i];
                        final int index3 = triz[i];
                        if (points[index1][2] + points[index2][2] + points[index3][2] == 3) {
                                totalXAverage += (ldModel.getXPoints()[index1] + ldModel.getXPoints()[index2] + ldModel.getXPoints()[index3]) / 3;
                                totalYAverage += (ldModel.getZPoints()[index1] + ldModel.getZPoints()[index2] + ldModel.getZPoints()[index3]) / 3;
                                totalHeightAverage += (ldModel.getYPoints()[index1] + ldModel.getYPoints()[index2] + ldModel.getYPoints()[index3]) / 3;
                        }
                }
                final Point averagePoint = Calculations.worldToScreen(
                                locX + totalXAverage / numFaces,
                                height + totalHeightAverage / numFaces,
                                locY + totalYAverage / numFaces
                );
                if (Calculations.isOnScreen(averagePoint)) {
                        return averagePoint;
                }
                return new Point(-1, -1);
        }

        public boolean interact(final String action) {
                return interact(action, null);
        }

        public boolean interact(final String action, final String option) {
                final int[][] vert = projectVertices();
                final int rand = Random.nextInt(0, vert.length);
                Point p = new Point(vert[rand][0], vert[rand][1]);
                Mouse.move(p);
                return org.acebot.api.methods.Menu.select(action, option);
        }

        public Polygon[] getWireframe() {
                org.acebot.impl.LDModel ldModel = model;
                if (ldModel == null)
                        return new Polygon[]{};
                ArrayList<Polygon> polys = new ArrayList<Polygon>();
                int[][] screenPoints = projectVertices();
                if (screenPoints == new int[][]{})
                        return new Polygon[]{};
                short[] trix = ldModel.getIndices1();
                short[] triy = ldModel.getIndices2();
                short[] triz = ldModel.getIndices3();
                int numTriangles = Math.min(trix.length, Math.min(triy.length, triz.length));
                for (int i = 0; i < numTriangles; i++) {
                        try {
                                int index1 = trix[i];
                                int index2 = triy[i];
                                int index3 = triz[i];
                                int point1X = screenPoints[index1][0];
                                int point1Y = screenPoints[index1][1];
                                int point2X = screenPoints[index2][0];
                                int point2Y = screenPoints[index2][1];
                                int point3X = screenPoints[index3][0];
                                int point3Y = screenPoints[index3][1];
                                if (point1X == -1 || point1Y == -1 ||
                                                point2X == -1 || point2Y == -1 ||
                                                point3X == -1 || point3Y == -1)
                                        continue;
                                Polygon p = new Polygon();
                                p.addPoint(point1X, point1Y);
                                p.addPoint(point2X, point2Y);
                                p.addPoint(point3X, point3Y);
                                polys.add(p);
                        } catch (Exception e) {
                        }
                }
                return polys.toArray(new Polygon[]{});
        }

        public int[][] projectVertices() {
                try {
                        double locX;
                        double locY;
                        float[] data = Bot.getLDGTK().getMatrix().getFloats();
                        org.acebot.impl.LDModel ldModel = model;
                        if (ldModel == null)
                                return new int[][]{{-1, -1, -1}};
                        if (a == null) {
                                locX = (loc.getX() + 0.5) * 512;
                                locY = (loc.getY() + 0.5) * 512;
                        } else {
                                locX = (a.getMinX() + 0.5) * 512;
                                locY = (a.getMinY() + 0.5) * 512;
                                if (this.a.getMinX() != this.a.getMaxX()) {
                                        locX += (this.a.getMaxX() + 0.5) * 512;
                                        locX /= 2;
                                }
                                if (this.a.getMinY() != this.a.getMaxY()) {
                                        locY += (this.a.getMaxY() + 0.5) * 512;
                                        locY /= 2;
                                }
                        }
                        int numVertices = Math.min(ldModel.getXPoints().length, Math.min(ldModel.getYPoints().length, ldModel.getZPoints().length));
                        if (numVertices < 1)
                                return new int[][]{};
                        int[][] screen = new int[numVertices][3];
                        float xOff = data[12];
                        float yOff = data[13];
                        float zOff = data[15];
                        float xX = data[0];
                        float xY = data[4];
                        float xZ = data[8];
                        float yX = data[1];
                        float yY = data[5];
                        float yZ = data[9];
                        float zX = data[3];
                        float zY = data[7];
                        float zZ = data[11];
                        int height = Calculations.tileHeight((int) locX, (int) locY);
                        for (int index = 0; index < numVertices; index++) {
                                int vertexX = (int) (ldModel.getXPoints()[index] + locX);
                                int vertexY = ldModel.getYPoints()[index] + height;
                                int vertexZ = (int) (ldModel.getZPoints()[index] + locY);
                                float _z = (zOff + (zX * vertexX + zY * vertexY + zZ * vertexZ));
                                float _x = (xOff + (xX * vertexX + xY * vertexY + xZ * vertexZ));
                                float _y = (yOff + (yX * vertexX + yY * vertexY + yZ * vertexZ));
                                float fx = ((float) 256.0 + ((float) 256.0 * _x) / _z);
                                float fy = ((float) 166.0 + ((float) 167.0 * _y) / _z);
                                if (fx < 520 && fx > 0 && fy < 390 && fy > 50) {
                                        screen[index][0] = (int) fx;
                                        screen[index][1] = (int) fy;
                                        screen[index][2] = 1;
                                } else {
                                        screen[index][0] = -1;
                                        screen[index][1] = -1;
                                        screen[index][2] = 0;
                                }
                        }
                        return screen;
                } catch (Exception e) {
                        return new int[][]{};
                }
        }

        public void update() {
        }
}
