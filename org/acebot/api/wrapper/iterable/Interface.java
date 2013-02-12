package org.acebot.api.wrapper.iterable;


import org.acebot.core.bot.Bot;
import org.acebot.impl.Client;

import java.awt.*;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 11/22/12
 * Time: 2:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class Interface {

        org.acebot.impl.Interface inter;
        int id;

        public Interface(int i, org.acebot.impl.Interface ic) {
                id = i;
                inter = ic;
        }

        private InterfaceChild[] childCache = new InterfaceChild[0];
        private final Object CACHE_LOCK = new Object();

        public InterfaceChild[] getChildren() {
                synchronized (CACHE_LOCK) {
                        final Object[] children = getChildrenInternal();
                        if (children == null) {
                                return childCache.clone();
                        } else {
                                if (childCache.length < children.length) {
                                        final int prevLen = childCache.length;
                                        childCache = Arrays.copyOf(childCache, children.length);
                                        for (int i = prevLen; i < childCache.length; i++) {
                                                childCache[i] = new InterfaceChild(this, i);
                                        }
                                }
                                return childCache.clone();
                        }
                }
        }

        public boolean validate() {
                return true;
        }

        public int getId() {
                return id;
        }

        public Rectangle getBounds() {
                int a = getChildren()[0].getBoundsArrayIndex();
                if (a > 0) {
                        return Bot.getClient().getInterfaceBounds()[a];
                }
                return null;
        }

        org.acebot.impl.InterfaceChild[] getChildrenInternal() {
                final Client client = Bot.getClient();
                if (client == null) {
                        return null;
                }
                final Object[] inters = client.getInterfaceArray();
                if (inters != null && id < inters.length && inters[id] != null) {
                        final org.acebot.impl.Interface base = (org.acebot.impl.Interface) inters[id];
                        return base.getComponents();
                }
                return null;
        }
}
