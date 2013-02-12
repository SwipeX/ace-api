package org.acebot.api.methods;


import org.acebot.api.wrapper.iterable.InterfaceChild;
import org.acebot.api.wrapper.iterable.InventoryItem;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 12/5/12
 * Time: 4:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class Inventory {

        public static InventoryItem[] getAll() {
                ArrayList<InventoryItem> items = new ArrayList<InventoryItem>();
                InterfaceChild[] children = Interfaces.get(679, 0).getChildren();
                for (int i = 0; i < 28; i++) {
                        InterfaceChild ic = children[i];
                        if (ic != null && ic.getId() > 0) {
                                items.add(new InventoryItem(ic));
                        }
                }
                return items.toArray(new InventoryItem[]{});
        }

        public static InventoryItem getItem(final int id) {
                for (InventoryItem item : getAll()) {
                        if (item.getId() == id) {
                                return item;
                        }
                }
                return null;
        }

        public static int getCount() {
                return getCount(false);
        }

        public static int getCount(final boolean cache) {
                int total = 0;
                if (!cache) {
                        Tabs.INVENTORY.open();
                }
                for (InventoryItem ii : getAll()) {
                        if (ii != null && ii.getDefinition() != null) {
                                total++;
                        }
                }
                return total;
        }

        public static boolean isFull() {
                return getCount(false) == 28;
        }

        public static boolean isEmpty() {
                return getCount(false) == 0;
        }
}
