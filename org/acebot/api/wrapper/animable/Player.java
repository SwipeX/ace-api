package org.acebot.api.wrapper.animable;


import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 11/22/12
 * Time: 2:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class Player extends Character implements Locatable, Interactable {

        org.acebot.impl.Player player;

        public Player(org.acebot.impl.Player p) {
                player = p;
        }

        public String getName() {
                return player.getName();
        }

        public int getTeam() {
                return player.getTeam();
        }

        public int getLevel() {
                return player.getP2PLevel();
        }

        public int getGender() {
                return player.getGender();
        }

        public int getPrayerIcon() {
                return player.getPrayerIcon();
        }

        public int getSkullIcon() {
                return player.getSkullIcon();
        }

        @Override
        public org.acebot.impl.Character getAccessor() {
                return player;
        }

        public Point getScreenLocation() {
                return getModel().getCentralPoint();
        }
}
