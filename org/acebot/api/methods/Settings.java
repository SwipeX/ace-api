package org.acebot.api.methods;


import org.acebot.core.bot.Bot;
import org.acebot.impl.LocalStorage;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 12/5/12
 * Time: 1:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class Settings {

        /**
         * @param index - setting "id" or position in array
         * @return the value of the setting
         * @author Swipe
         */
        public static int get(int index) {
                return getData()[index];
        }

        public static int[] getData() {
                final LocalStorage storage = Bot.getClient().getLocalStorage();
                if (storage != null) {
                        return storage.getSettings().getData().clone();
                }
                return new int[0];
        }
}

