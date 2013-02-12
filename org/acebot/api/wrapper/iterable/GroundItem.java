package org.acebot.api.wrapper.iterable;


import org.acebot.api.methods.Calculations;
import org.acebot.api.wrapper.LDModel;
import org.acebot.api.wrapper.animable.Locatable;
import org.acebot.api.wrapper.animable.Tile;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 12/2/12
 * Time: 3:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class GroundItem implements Locatable {

        Item item;
        Tile loc;

        public GroundItem(Tile loc, org.acebot.impl.Item item) {
                this.item = new Item(item);
                this.loc = loc;
        }

        public Item getItem() {
                return item;
        }

        public int getId() {
                return item.getItem().getId();
        }

        public int getStackSize() {
                return item.getItem().getStackSize();
        }

        public Tile getLoc() {
                return loc;
        }

        @Override
        public Tile getLocation() {
                return loc;
        }

        @Override
        public Point getScreenLocation() {
                return Calculations.locationToScreen(getLocation().getX(), getLocation().getY());
        }

        public LDModel getLDModel() {
                return new LDModel(item.getModel(), loc);
        }
}
