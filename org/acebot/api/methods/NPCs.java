package org.acebot.api.methods;


import org.acebot.api.wrapper.animable.NPC;
import org.acebot.core.bot.Bot;
import org.acebot.impl.NPCNode;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 11/22/12
 * Time: 2:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class NPCs {

        public static NPC[] getAll() {
                LinkedList<NPC> temp = new LinkedList<NPC>();
                for (NPCNode n : Bot.getClient().getNPCNodeArray()) {
                        if (n != null) {
                                temp.add(new NPC((org.acebot.impl.NPC) n.getNPC()));
                        }
                }
                return temp.toArray(new NPC[temp.size()]);
        }

        public static NPC getNearest(final String... name) {
                for (NPC npc : getAll()) {
                        for (String str : name) {
                                if (npc.getName().equals(str)) {
                                        return npc;
                                }
                        }
                }
                return null;
        }
}
