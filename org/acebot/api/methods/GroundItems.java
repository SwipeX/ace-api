package org.acebot.api.methods;
/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 12/2/12
 * Time: 3:02 PM
 * To change this template use File | Settings | File Templates.
 */

import org.acebot.api.wrapper.animable.Tile;
import org.acebot.api.wrapper.iterable.GroundItem;
import org.acebot.core.bot.Bot;
import org.acebot.impl.HashTable;
import org.acebot.impl.Item;
import org.acebot.impl.NodeDequeCache;
import org.acebot.util.Deque;

import java.util.ArrayList;
import java.util.List;

public class GroundItems {

        public static GroundItem[] getAll() {
                ArrayList<GroundItem> temp = new ArrayList<GroundItem>();
                Tile tile = Players.getLocal().getLocation();
                int minX = Math.max(Bot.getClient().getLandscapeData().getMapOffset().getXOffset(), tile.getX() - 104), minY = Math.max(Bot.getClient().getLandscapeData().getMapOffset().getYOffset(), tile.getY() - 104);
                int maxX = Math.min(Bot.getClient().getLandscapeData().getMapOffset().getXOffset() + 104, tile.getX() + 104), maxY = Math.min(Bot.getClient().getLandscapeData().getMapOffset().getYOffset() + 104, tile.getY() + 104);
                for (int x = minX; x < maxX; x++) {
                        for (int y = minY; y < maxY; y++) {
                                GroundItem[] items = getItemsAt(x, y);
                                for (final GroundItem item : items) {
                                        if (item != null) {
                                                temp.add(item);
                                        }
                                }
                        }
                }
                return temp.toArray(new GroundItem[]{});
        }

        public static GroundItem[] getItemsAt(int x, int y) {
                final List<GroundItem> groundItems = new ArrayList<GroundItem>();
                HashTable itemTable = Bot.getClient().getItemCache();
                int floor = 0;
                final int index = (x | y << 14 | floor << 28);
                final NodeDequeCache itemNodeListCache = ((NodeDequeCache) Nodes.lookup(itemTable, index));
                if (itemNodeListCache == null || itemNodeListCache.getDeque() == null) {
                        return new GroundItem[0];
                }
                final Deque<Item> itemDeque = new Deque<Item>(itemNodeListCache.getDeque());
                for (Item item = itemDeque.getHead(); item != null; item = itemDeque.getNext()) {
                        groundItems.add(new GroundItem(new Tile(x, y, floor), item));
                }
                return groundItems.toArray(new GroundItem[groundItems.size()]);
        }

        public static GroundItem getNearestItemByID(int... ids) {
                GroundItem temp = null;
                double dist = Double.MAX_VALUE;
                for (GroundItem ao : getAll()) {
                        int id = ao.getId();
                        for (int i : ids) {
                                if (i == id) {
                                        double distance = Calculations.distanceTo(ao.getLoc().getX(), ao.getLoc().getY());
                                        if (distance < dist) {
                                                dist = distance;
                                                temp = ao;
                                        }
                                }
                        }
                }
                return temp;
        }

        public static GroundItem getNearestItemByName(String... names) {
                GroundItem temp = null;
                double dist = Double.MAX_VALUE;
                for (GroundItem ao : getAll()) {
                        String name = ao.getItem().getDefinition().getName();
                        for (String i : names) {
                                if (i.equals(name)) {
                                        double distance = Calculations.distanceTo(ao.getLoc().getX(), ao.getLoc().getY());
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