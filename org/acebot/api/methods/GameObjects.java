package org.acebot.api.methods;


import org.acebot.api.wrapper.animable.FloorObject;
import org.acebot.api.wrapper.animable.GameObject;
import org.acebot.api.wrapper.animable.Tile;
import org.acebot.api.wrapper.animable.WallObject;
import org.acebot.core.bot.Bot;
import org.acebot.impl.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 11/28/12
 * Time: 12:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameObjects {

        public static org.acebot.api.wrapper.animable.GameObject[] getAllAnimableObjects() {
                try {
                        ArrayList<org.acebot.api.wrapper.animable.GameObject> allObjects = new ArrayList<org.acebot.api.wrapper.animable.GameObject>();
                        Ground[][][] array = Bot.getClient().getLandscapeData().getGroundInfo().getGroundArray();
                        Ground[][] a1 = array[0];
                        for (Ground[] a2 : a1) {
                                if (a2 == null) {
                                        continue;
                                }
                                for (Ground gd : a2) {
                                        if (gd == null) {
                                                continue;
                                        }
                                        AnimableNode list = gd.getAnimableNodeList();
                                        if (list != null) {
                                                for (AnimableNode node = list; node != null; node = node.getNext()) {
                                                        Animable data = node.getAnimable();
                                                        if (data != null && data instanceof InteractableObject) {
                                                                org.acebot.api.wrapper.animable.InteractableObject fo = new org.acebot.api.wrapper.animable.InteractableObject((InteractableObject) data);
                                                                if (fo != null) {
                                                                        allObjects.add(fo);
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                        return allObjects.toArray(new org.acebot.api.wrapper.animable.GameObject[]{});
                } catch (Exception e) {
                }
                return new org.acebot.api.wrapper.animable.GameObject[]{};
        }

        public static org.acebot.api.wrapper.animable.GameObject[] getAllBoundaryObjects() {
                try {
                        ArrayList<org.acebot.api.wrapper.animable.GameObject> allObjects = new ArrayList<org.acebot.api.wrapper.animable.GameObject>();
                        Ground[][][] array = Bot.getClient().getLandscapeData().getGroundInfo().getGroundArray();
                        Ground[][] a1 = array[0];
                        int x = 0;
                        for (Ground[] a2 : a1) {
                                if (a2 == null) {
                                        continue;
                                }
                                int y = 0;
                                for (Ground gd : a2) {
                                        if (gd == null) {
                                                continue;
                                        }
                                        if (gd.getBoundary1() != null) {
                                                org.acebot.api.wrapper.animable.BoundaryObject fo = new org.acebot.api.wrapper.animable.BoundaryObject(gd.getBoundary1(), x, y);
                                                if (fo != null) {
                                                        allObjects.add(fo);
                                                }
                                        }
                                        if (gd.getBoundary2() != null) {
                                                org.acebot.api.wrapper.animable.BoundaryObject fo = new org.acebot.api.wrapper.animable.BoundaryObject(gd.getBoundary2(), x, y);
                                                if (fo != null) {
                                                        allObjects.add(fo);
                                                }
                                        }
                                        y++;
                                }
                                x++;
                        }
                        return allObjects.toArray(new org.acebot.api.wrapper.animable.BoundaryObject[]{});
                } catch (Exception e) {
                }
                return new org.acebot.api.wrapper.animable.BoundaryObject[]{};
        }

        public static org.acebot.api.wrapper.animable.GameObject[] getAllFloorObjects() {
                try {
                        ArrayList<org.acebot.api.wrapper.animable.GameObject> allObjects = new ArrayList<org.acebot.api.wrapper.animable.GameObject>();
                        Ground[][][] array = Bot.getClient().getLandscapeData().getGroundInfo().getGroundArray();
                        Ground[][] a1 = array[0];
                        int x = 0;
                        for (Ground[] a2 : a1) {
                                if (a2 == null) {
                                        continue;
                                }
                                int y = 0;
                                for (Ground gd : a2) {
                                        if (gd == null) {
                                                continue;
                                        }
                                        if (gd.getFloorDecoration() != null) {
                                                FloorObject fo = new FloorObject(gd.getFloorDecoration(), x, y);
                                                if (fo != null) {
                                                        allObjects.add(fo);
                                                }
                                        }
                                        y++;
                                }
                                x++;
                        }
                        return allObjects.toArray(new FloorObject[]{});
                } catch (Exception e) {
                }
                return new FloorObject[]{};
        }

        public static org.acebot.api.wrapper.animable.GameObject[] getAllWallObjects() {
                try {
                        ArrayList<org.acebot.api.wrapper.animable.GameObject> allObjects = new ArrayList<org.acebot.api.wrapper.animable.GameObject>();
                        Ground[][][] array = Bot.getClient().getLandscapeData().getGroundInfo().getGroundArray();
                        Ground[][] a1 = array[0];
                        int x = 0;
                        for (Ground[] a2 : a1) {
                                if (a2 == null) {
                                        continue;
                                }
                                int y = 0;
                                for (Ground gd : a2) {
                                        if (gd == null) {
                                                continue;
                                        }
                                        if (gd.getWallDecoration1() != null && gd.getWallDecoration1() instanceof org.acebot.impl.WallObject) {
                                                WallObject fo = new WallObject(gd.getWallDecoration1(), x, y);
                                                if (fo != null) {
                                                        allObjects.add(fo);
                                                }
                                        }
                                        if (gd.getWallDecoration2() != null && gd.getWallDecoration2() instanceof org.acebot.impl.WallObject) {
                                                WallObject fo = new WallObject(gd.getWallDecoration2(), x, y);
                                                if (fo != null) {
                                                        allObjects.add(fo);
                                                }
                                        }
                                        y++;
                                }
                                x++;
                        }
                        return allObjects.toArray(new WallObject[]{});
                } catch (Exception e) {
                }
                return new WallObject[]{};
        }

        public static GameObject[] getAllAnimatedObjects() {
                ArrayList<GameObject> animatedObjects = new ArrayList<GameObject>();
                try {
                        Ground[][][] tiles = Bot.getClient().getLandscapeData().getGroundInfo().getGroundArray();
                        for (int x = 0; x < tiles[0].length; ++x) {
                                for (int y = 0; y < tiles[0][x].length; ++y) {
                                        Ground g1 = tiles[0][x][y];
                                        if (g1 == null) {
                                                continue;
                                        }
                                        if (g1.getBoundary1() != null) {
                                                org.acebot.impl.BoundaryObject object = g1.getBoundary1();
                                                if (object instanceof Boundary) {
                                                        animatedObjects.add(new org.acebot.api.wrapper.animable.BoundaryObject(object, x, y));
                                                }
                                        }
                                        if (g1.getBoundary2() != null) {
                                                org.acebot.impl.BoundaryObject object = g1.getBoundary1();
                                                if (object instanceof Boundary) {
                                                        animatedObjects.add(new org.acebot.api.wrapper.animable.BoundaryObject(object, x, y));
                                                }
                                        }
                                        if (g1.getWallDecoration1() != null) {
                                                org.acebot.impl.WallObject object = g1.getWallDecoration1();
                                                if (object instanceof org.acebot.impl.WallObject) {
                                                        animatedObjects.add(new WallObject(object, x, y));
                                                }
                                        }
                                        if (g1.getWallDecoration2() != null) {
                                                org.acebot.impl.WallObject object = g1.getWallDecoration2();
                                                if (object instanceof org.acebot.impl.WallObject) {
                                                        animatedObjects.add(new WallObject(object, x, y));
                                                }
                                        }
                                        if (g1.getFloorDecoration() != null) {
                                                org.acebot.impl.FloorObject object = g1.getFloorDecoration();
                                                if (object instanceof org.acebot.impl.FloorObject) {
                                                        animatedObjects.add(new FloorObject(object, x, y));
                                                }
                                        }
                                        if (g1.getAnimableNodeList() != null) {
                                                AnimableNode node = g1.getAnimableNodeList();
                                                Animable object = node.getAnimable();
                                                if (object instanceof InteractableObject) {
                                                        animatedObjects.add(new org.acebot.api.wrapper.animable.InteractableObject((InteractableObject) object));
                                                }
                                        }
                                }
                        }
                } catch (Exception e) {
                }
                animatedObjects.removeAll(Collections.singleton(null));
                return animatedObjects.toArray(new GameObject[]{});
        }

        public static GameObject[] getAll() {
                return getAllAnimatedObjects();
        }

        public static GameObject[] getAll(int id) {
                ArrayList<GameObject> objects = new ArrayList<GameObject>();
                for (GameObject go : getAll()) {
                        if (go.getId() == id) {
                                objects.add(go);
                        }
                }
                return objects.toArray(new GameObject[]{});
        }

        public static GameObject getObjectAt(Tile t) {
                if (t == null) {
                        return null;
                }
                return getObjectAt(t.getX(), t.getY());
        }

        public static GameObject getObjectAt(int x, int y) {
                for (GameObject go : getAll()) {
                        if (go.getLocation().equals(new Tile(x, y, 0))) {
                                return go;
                        }
                }
                return null;
        }

        public static GameObject getNearest(int... ids) {
                GameObject temp = null;
                double dist = Double.MAX_VALUE;
                for (GameObject ao : getAll()) {
                        int id = ao.getId();
                        for (int i : ids) {
                                if (i == id) {
                                        double distance = Calculations.distanceTo(ao.getLocation().getX(), ao.getLocation().getY());
                                        if (distance < dist) {
                                                dist = distance;
                                                temp = ao;
                                        }
                                }
                        }
                }
                return temp;
        }
}

