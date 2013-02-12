package org.acebot.api.methods;


import org.acebot.api.input.Keyboard;
import org.acebot.api.wrapper.iterable.Interface;
import org.acebot.api.wrapper.iterable.InterfaceChild;
import org.acebot.core.bot.Bot;
import org.acebot.impl.Client;
import org.acebot.util.Time;
import org.acebot.util.Timer;

import java.awt.event.KeyEvent;

public enum Tabs {
        NONE(-1, null, -1),
        COMBAT(0, "Combat", KeyEvent.VK_F5),
        TASK_LIST(1, "Noticeboard", -1),
        STATS(2, "Stats", -1),
        COMBAT_ACADEMY(3, "Combat Academy", -1),
        INVENTORY(4, "Inventory", KeyEvent.VK_F1),
        EQUIPMENT(5, "Worn Equipment", KeyEvent.VK_F2),
        PRAYER(6, "Prayer List", KeyEvent.VK_F3),
        ABILITIES(7, "Ability Book", KeyEvent.VK_F4),
        EXTRAS(8, "Extras", -1),
        FRIENDS(9, "Friends List", -1),
        FRIENDS_CHAT(10, "Friends Chat", -1),
        CLAN_CHAT(11, "Clan Chat", -1),
        OPTIONS(12, "Options", -1),
        EMOTES(13, "Emotes", -1),
        MUSIC(14, "Music Player", -1),
        NOTES(15, "Notes", -1),
        LOGOUT(16, "Exit", -1);
        private static final int LOGOUT_INTERFACE = 182;
        private final String description;
        private final int functionKey;
        private final int index;

        Tabs(final int index, final String description, final int functionKey) {
                this.description = description;
                this.functionKey = functionKey;
                this.index = index;
        }

        private static InterfaceChild getTab(final Tabs tab) {
                Client client = Bot.getClient();
                final Interface iFace = Interfaces.get(client.getClientInterfaceIndex());
                if (iFace != null) {
                        if (tab.getIndex() < 0 || tab.getIndex() > 16) {
                                return null;
                        }
                        for (final InterfaceChild child : iFace.getChildren()) {
                                final String[] actions = child.getActions();
                                if (actions != null && actions.length > 0 && actions[0].equalsIgnoreCase(tab.getDescription())) {
                                        return child;
                                }
                        }
                }
                return null;
        }

        public static Tabs getCurrent() {
                for (final Tabs t : Tabs.values()) {
                        final InterfaceChild child = getTab(t);
                        if (child != null && child.getTextureId() != -1) {
                                return t;
                        }
                }
                final InterfaceChild logout = Interfaces.get(LOGOUT_INTERFACE, 1);
                return logout != null && logout.validate() && logout.isShowing() ? Tabs.LOGOUT : Tabs.NONE;
        }

        public String getDescription() {
                return description;
        }

        public int getFunctionKey() {
                return functionKey;
        }

        public boolean hasFunctionKey() {
                return functionKey != -1;
        }

        public int getIndex() {
                return index;
        }

        public boolean open() {
                return open(false);
        }

        public boolean open(final boolean useFKey) {
                if (this == Tabs.NONE) {
                        return false;
                } else if (isOpen()) {
                        return true;
                }
                if (useFKey && hasFunctionKey()) {
                        Keyboard.sendKey((char) getFunctionKey());
                } else {
                        InterfaceChild child = getTab(this);
                        if (child == null)
                                return false;
                        if (child.click(true)) {
                                final Timer timer = new Timer(800);
                                while (timer.isRunning() && getCurrent() != this) {
                                        Time.sleep(15);
                                }
                        }
                }
                return getCurrent() == this;
        }

        public boolean isOpen() {
                return getCurrent() == this;
        }

        @Override
        public String toString() {
                return description + " (" + index + ")";
        }
}
