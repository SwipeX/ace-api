package org.acebot.api.wrapper.animable;


import org.acebot.api.input.Mouse;
import org.acebot.api.methods.Calculations;
import org.acebot.api.methods.Nodes;
import org.acebot.api.wrapper.CharacterModel;
import org.acebot.api.wrapper.LDModel;
import org.acebot.core.bot.Bot;
import org.acebot.impl.*;
import org.acebot.util.Random;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 11/22/12
 * Time: 2:37 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Character implements Locatable, Interactable {

        public abstract org.acebot.impl.Character getAccessor();

        public LDModel getModel() {
                return new CharacterModel(getAccessor().getModel(), this);
        }

        public int getHeight() {
                return getAccessor().getHeight();
        }

        public PassiveAnimator getPassiveAnimator() {
                return getAccessor().getPassiveAnimator();
        }

        public int getRotation() {
                return getAccessor().getOrientation();
        }

        public int getOrientation() {
                return (630 - getRotation() * 45 / 0x800) % 360;
        }

        public int getVelocity() {
                return getAccessor().getVelocity();
        }

        public boolean isMoving() {
                return getVelocity() != 0;
        }

        public int getAnimation() {
                Animator animator = getAccessor().getAnimator();
                if (animator != null) {
                        Animation animation = animator.getAnimation();
                        if (animation != null) {
                                return animation.getId();
                        }
                }
                return -1;
        }

        public int getPassiveAnimation() {
                final PassiveAnimator passiveAnimator = getAccessor().getPassiveAnimator();
                if (passiveAnimator != null) {
                        final Animation animation = passiveAnimator.getAnimation();
                        if (animation != null) {
                                return animation.getId();
                        }
                }
                return -1;
        }

        @Override
        public boolean interact(String action, String option) {
                LDModel model = getModel();
                if (model != null) {
                        try {
                                Mouse.move(Random.nextPoint(model.getWireframe()));
                                return org.acebot.api.methods.Menu.select(action, option);
                        } catch (Throwable t) {
                                t.printStackTrace();
                        }
                }
                return getLocation().interact(action, option);
        }

        public boolean interact(String action) {
                return interact(action, null);
        }

        public Tile getLocation() {
                int x = Bot.getClient().getLandscapeData().getMapOffset().getXOffset() + getAccessor().getMinX();
                int y = Bot.getClient().getLandscapeData().getMapOffset().getYOffset() + getAccessor().getMinY();
                int z = 0;
                return new Tile(x, y, z);
        }

        public Point getScreenLocation() {
                return Calculations.locationToScreen(getLocation().getX(), getLocation().getY());
        }

        public Character getInteracting() {
                final int index = getAccessor().getInteracting();
                if (index == -1) {
                        return null;
                }
                if (index < 0x8000) {
                        return new NPC((org.acebot.impl.NPC) ((NPCNode) Nodes.lookup(Bot.getClient().getNPCNodeCache(), index)).getNPC());
                } else {
                        return new Player(Bot.getClient().getPlayers()[index - 0x8000]);
                }
        }

        private org.acebot.impl.CombatStatusData getAdrenalineBar() {
                LinkedListNode sentinel = getAccessor().getCombatStatusList().getSentinel();
                LinkedListNode current = sentinel.getNext();
                if (!sentinel.equals(current) && !sentinel.equals(current.getNext())) {
                        sentinel = (((CombatStatus) current).getData()).getSentinel();
                        if (!sentinel.equals(sentinel.getNext())) {
                                final org.acebot.impl.CombatStatusData adrenaline = (org.acebot.impl.CombatStatusData) sentinel.getNext();
                                if (adrenaline != null) {
                                        return adrenaline;
                                }
                        }
                }
                return null;
        }

        private org.acebot.impl.CombatStatusData getHealthBar() {
                LinkedListNode sentinel = getAccessor().getCombatStatusList().getSentinel();
                LinkedListNode current = sentinel.getNext();
                if (!sentinel.equals(current)) {
                        if (!sentinel.equals(current.getNext())) {
                                current = current.getNext();
                        }
                        sentinel = ((CombatStatus) current).getData().getSentinel();
                        if (!sentinel.equals(sentinel.getNext())) {
                                final org.acebot.impl.CombatStatusData health = (org.acebot.impl.CombatStatusData) sentinel.getNext();
                                if (health != null) {
                                        return health;
                                }
                        }
                }
                return null;
        }

        public int getHpPercent() {
                final org.acebot.impl.Character c = getAccessor();
                if (c != null) {
                        final org.acebot.impl.CombatStatusData healthData = getHealthBar();
                        if (healthData == null) {
                                return 100;
                        }
                        return (int) Math.ceil(healthData.getRatio() * 100 / 255);
                }
                return -1;
        }

        public int getHpRatio() {
                final org.acebot.impl.Character c = getAccessor();
                if (c != null) {
                        final org.acebot.impl.CombatStatusData healthData = getHealthBar();
                        if (healthData == null) {
                                return 255;
                        }
                        return healthData.getRatio();
                }
                return -1;
        }

        public int getAdrenalinePercent() {
                final org.acebot.impl.Character c = getAccessor();
                if (c != null) {
                        final org.acebot.impl.CombatStatusData adrenalineData = getAdrenalineBar();
                        if (adrenalineData == null) {
                                return 100;
                        }
                        return (int) Math.ceil(adrenalineData.getRatio() * 100 / 255);
                }
                return -1;
        }

        public int getAdrenalineRatio() {
                final org.acebot.impl.Character c = getAccessor();
                if (c != null) {
                        final org.acebot.impl.CombatStatusData adrenalineData = getAdrenalineBar();
                        if (adrenalineData == null) {
                                return 255;
                        }
                        return adrenalineData.getRatio();
                }
                return -1;
        }

        public boolean isInCombat() {
                final org.acebot.impl.Character c = getAccessor();
                return c != null && getHealthBar() != null;
        }

        public boolean isIdle() {
                final org.acebot.impl.Character c = getAccessor();
                return getAnimation() == -1 && !isInCombat() && getInteracting() == null && !isMoving();
        }
}
