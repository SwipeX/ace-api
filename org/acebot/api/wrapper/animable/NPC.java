package org.acebot.api.wrapper.animable;


import org.acebot.impl.NPCDef;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 11/22/12
 * Time: 2:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class NPC extends Character implements Locatable, Interactable {

        org.acebot.impl.NPC npc;

        public NPC(org.acebot.impl.NPC n) {
                npc = n;
        }

        public String getName() {
                return npc.getName();
        }

        public int getLevel() {
                return npc.getLevel();
        }

        public NPCDef getNPCDef() {
                return npc.getDef();
        }

        @Override
        public org.acebot.impl.Character getAccessor() {
                return this.npc;
        }

        @Override
        public Point getScreenLocation() {
                return getModel().getCentralPoint();
        }
}
