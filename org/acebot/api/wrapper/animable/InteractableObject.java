package org.acebot.api.wrapper.animable;


import org.acebot.api.input.Mouse;
import org.acebot.api.methods.Calculations;
import org.acebot.api.wrapper.LDModel;
import org.acebot.core.bot.Bot;
import org.acebot.impl.ObjectDefLoader;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 12/7/12
 * Time: 5:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class InteractableObject implements GameObject {

        org.acebot.impl.InteractableObject object;

        public InteractableObject(org.acebot.impl.InteractableObject object) {
                this.object = object;
        }

        public org.acebot.impl.InteractableObject getObject() {
                return object;
        }

        public org.acebot.api.wrapper.LDModel getModel() {
                return new org.acebot.api.wrapper.LDModel(object.getModel(), object);
        }

        public int getId() {
                return object.getId();
        }

        public ObjectDefLoader getObjectDefLoader() {
                return object.getDefLoader();
        }

        @Override
        public Tile getLocation() {
                return new Tile(Bot.getClient().getLandscapeData().getMapOffset().getXOffset() + object.getMinX(), Bot.getClient().getLandscapeData().getMapOffset().getYOffset() + object.getMinY());
        }

        @Override
        public Point getScreenLocation() {
                Point temp;
                if (Calculations.isOnScreen(temp = getModel().getCentralPoint())) {
                        return temp;
                }
                return Calculations.tileToScreen(getLocation());
        }

        @Override
        public boolean interact(String action, String option) {
                LDModel model = getModel();
                if (model != null) {
                        try {
                                Mouse.move(getScreenLocation());
                                return org.acebot.api.methods.Menu.select(action, option);
                        } catch (Throwable t) {
                                t.printStackTrace();
                        }
                }
                return getLocation().interact(action, option);
        }

        @Override
        public boolean interact(String action) {
                return interact(action, null);
        }
}
