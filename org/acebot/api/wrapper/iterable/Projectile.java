package org.acebot.api.wrapper.iterable;


import org.acebot.api.methods.Calculations;
import org.acebot.api.wrapper.animable.Locatable;
import org.acebot.api.wrapper.animable.Tile;
import org.acebot.core.bot.Bot;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 12/19/12
 * Time: 1:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class Projectile implements Locatable {

        org.acebot.impl.Projectile projectile;

        public Projectile(org.acebot.impl.Projectile pro) {
                projectile = pro;
        }

        public org.acebot.impl.Projectile getProjectile() {
                return projectile;
        }

        public Tile getLocation() {
                int x = Bot.getClient().getLandscapeData().getMapOffset().getXOffset() + projectile.getMinX();
                int y = Bot.getClient().getLandscapeData().getMapOffset().getYOffset() + projectile.getMinY();
                int z = 0;
                return new Tile(x, y, z);
        }

        @Override
        public Point getScreenLocation() {
                return Calculations.tileToScreen(getLocation());  //To change body of implemented methods use File | Settings | File Templates.
        }
}
