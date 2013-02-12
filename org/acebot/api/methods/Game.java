package org.acebot.api.methods;


import org.acebot.core.bot.Bot;
import org.acebot.impl.Client;

public class Game {

        public final static int CLIENT_STATE_LOBBY_TRANSITION = 1;
        public final static int CLIENT_STATE_LOGGING_IN = 3;
        public final static int CLIENT_STATE_LOADING = 5;
        public final static int CLIENT_STATE_LOGGING_OUT = 8;
        public final static int CLIENT_STATE_LOGIN_SCREEN = 17;
        public final static int CLIENT_STATE_LOBBY = 18;
        public final static int CLIENT_STATE_LOGGED_IN = 19;

        public static int getClientState() {
                Client c = Bot.getClient();
                return c.getClientState();
        }

        public static boolean isLoggedIn() {
                return getClientState() == CLIENT_STATE_LOGGED_IN || getClientState() == CLIENT_STATE_LOADING;
        }

}
