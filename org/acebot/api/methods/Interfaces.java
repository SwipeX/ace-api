package org.acebot.api.methods;


import org.acebot.api.wrapper.iterable.InterfaceChild;
import org.acebot.core.bot.Bot;
import org.acebot.impl.Interface;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 11/22/12
 * Time: 2:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class Interfaces {

        public static org.acebot.api.wrapper.iterable.Interface[] getAll() {
                Interface[] iArray = Bot.getClient().getInterfaceArray();
                boolean[] valids = Bot.getClient().getValidInterfaceArray();
                final ArrayList<org.acebot.api.wrapper.iterable.Interface> validInterfaces = new ArrayList<org.acebot.api.wrapper.iterable.Interface>();
                for (int index = 0; index < iArray.length; index++) {
                        if (valids.length > index && valids[index]) {
                                validInterfaces.add(new org.acebot.api.wrapper.iterable.Interface(index, iArray[index]));
                        } else {
                                validInterfaces.add(null);
                        }
                }
                return validInterfaces.toArray(new org.acebot.api.wrapper.iterable.Interface[validInterfaces.size()]);
        }

        public static org.acebot.api.wrapper.iterable.Interface get(int id) {
                return getAll()[id];
        }

        public static InterfaceChild get(int index1, int index2) {
                org.acebot.api.wrapper.iterable.Interface i = get(index1);
                if (i != null) {
                        if (i.getChildren().length > index2) {
                                return i.getChildren()[index2];
                        }
                }
                return null;
        }

        public static InterfaceChild getChild(final int id) {
                final int x = id >> 0x10;
                final int y = id & 0xffff;
                return get(x, y);
        }
}
