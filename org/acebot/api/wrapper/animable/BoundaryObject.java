package org.acebot.api.wrapper.animable;


import org.acebot.api.input.Mouse;
import org.acebot.api.wrapper.LDModel;
import org.acebot.core.bot.Bot;
import org.acebot.impl.ObjectDefLoader;
import org.acebot.util.Random;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 12/7/12
 * Time: 5:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class BoundaryObject implements GameObject {

        org.acebot.impl.BoundaryObject object;
        Tile location;

        public BoundaryObject(org.acebot.impl.BoundaryObject boundary2, int x, int y) {
                object = boundary2;
                location = new Tile(Bot.getClient().getLandscapeData().getMapOffset().getXOffset() + x, Bot.getClient().getLandscapeData().getMapOffset().getYOffset() + y);
        }

        public org.acebot.impl.BoundaryObject getObject() {
                return object;
        }

        public LDModel getModel() {
                return new LDModel(object.getModel(), getLocation());  //To change body of implemented methods use File | Settings | File Templates.
        }

        public int getId() {
                return object.getId();  //To change body of implemented methods use File | Settings | File Templates.
        }

        public ObjectDefLoader getObjectDefLoader() {
                return object.getObjectDefLoader();  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Tile getLocation() {
                return location;
        }

        @Override
        public Point getScreenLocation() {
                return getModel().getCentralPoint();
        }

        @Override
        public boolean interact(String action, String option) {
                LDModel model = getModel();
                if (model != null) {
                        try {
                                Mouse.move(Random.nextPoint(model.getWireframe()));
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
