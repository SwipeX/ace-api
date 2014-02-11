package org.acebot.api.methods;


import org.acebot.api.wrapper.animable.Player;
import org.acebot.core.bot.Bot;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 11/22/12
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class Players {

        public static Player getLocal() {
                return new Player(Bot.getClient().getMyPlayer());
        }

        public static Player[] getLoaded() {
                ArrayList<Player> loadedPlayers = new ArrayList<Player>();
                for (org.acebot.impl.Player player : Bot.getClient().getPlayers()) {
                        if (player != null) {
                                loadedPlayers.add(new Player(player));
                        }
                }
                return loadedPlayers.toArray(new Player[loadedPlayers.size()]);
        }
        
        public static Player getNearest(final String... name) {
        for (final Player p : Players.getLoaded()) {
            for (final String str : name) {
                if (p.getName().equals(str)) {
                    return p;
                }
            }
        }
        return null;
    }
}
