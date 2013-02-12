package org.acebot.api.methods;


import org.acebot.api.input.Keyboard;
import org.acebot.api.input.Mouse;
import org.acebot.api.wrapper.iterable.InterfaceChild;
import org.acebot.api.wrapper.iterable.InventoryItem;
import org.acebot.api.wrapper.iterable.Item;
import org.acebot.util.Random;
import org.acebot.util.Time;

import java.awt.*;

public class AbilityBar {

        private static final int INTERFACE = 640;
        private static final int SHOW_BUTTON = 3;
        private static final int HIDE_BUTTON = 30;
        private static final int PAGE = 25;
        private static final int PAGE_UP = 24;
        private static final int PAGE_DOWN = 23;
        private static final int LOCK = 26;
        private static final int TRASH_CAN = 27;
        private static final int LOCKED_TEXTURE = 14286;
        private static final int UNLOCKED_TEXTURE = 14289;
        private static final int EMPTY_TEXTURE = 14520;

        public static boolean isOpen() {
                return Interfaces.get(INTERFACE, SHOW_BUTTON).isVisible();
        }

        public static boolean toggleAbilityBar(final boolean hide) {
                if (Interfaces.get(INTERFACE, (hide ? HIDE_BUTTON : SHOW_BUTTON)).isVisible()) {
                        if (Interfaces.get(INTERFACE, (hide ? HIDE_BUTTON : SHOW_BUTTON)).interact((hide ? "Minimise" : "Expand"))) {
                                Time.sleep(200, 350);
                        }
                }
                return !Interfaces.get(INTERFACE, (hide ? HIDE_BUTTON : SHOW_BUTTON)).isVisible();
        }

        public static boolean isLocked() {
                toggleAbilityBar(false);
                return Interfaces.get(INTERFACE, LOCK).getTextureId() == LOCKED_TEXTURE;
        }

        public static boolean toggleLock(final boolean lock) {
                toggleAbilityBar(false);
                final int texture = lock ? LOCKED_TEXTURE : UNLOCKED_TEXTURE;
                if (Interfaces.get(INTERFACE, LOCK).getTextureId() != texture) {
                        if (Interfaces.get(INTERFACE, LOCK).interact("Toggle lock")) {
                                Time.sleep(600, 850);
                        }
                }
                return Interfaces.get(INTERFACE, LOCK).getTextureId() == texture;
        }

        public static int getPage() {
                return Integer.parseInt(Interfaces.get(INTERFACE, PAGE).getText());
        }

        public static boolean nextPage() {
                toggleAbilityBar(false);
                final int old = getPage();
                if (Interfaces.get(INTERFACE, PAGE_UP).interact("Next")) {
                        Time.sleep(200, 350);
                }
                return getPage() == (old == 1 ? 5 : old - 1);
        }

        public static boolean previousPage() {
                toggleAbilityBar(false);
                final int old = getPage();
                if (Interfaces.get(INTERFACE, PAGE_DOWN).interact("Previous")) {
                        Time.sleep(200, 350);
                }
                return getPage() == (old == 5 ? 1 : old + 1);
        }

        public static boolean setPage(final int page) {
                toggleAbilityBar(false);
                if (Interfaces.get(INTERFACE, PAGE).interact(String.valueOf(page))) {
                        Time.sleep(200, 350);
                }
                return getPage() == page;
        }

        public static boolean useItem(final Item item) {
                toggleAbilityBar(false);
                for (final AbilitySlots as : AbilitySlots.values()) {
                        if (Interfaces.get(INTERFACE, as.getAbilityTextureChild()).getChildId() == item.getItem().getId()) {
                                Keyboard.sendKey(as.getKeybind());
                                return true;
                        }
                }
                return false;
        }

        public static boolean useItem(final Item item, final String action) {
                toggleAbilityBar(false);
                for (final AbilitySlots as : AbilitySlots.values()) {
                        if (Interfaces.get(INTERFACE, as.getAbilityTextureChild()).getChildId() == item.getItem().getId()) {
                                return Interfaces.get(INTERFACE, as.getAbilityTextureChild()).interact(action);
                        }
                }
                return false;
        }

        public static boolean useAbility(final Abilities ability) {
                toggleAbilityBar(false);
                for (final AbilitySlots as : AbilitySlots.values()) {
                        if (Interfaces.get(INTERFACE, as.getAbilityTextureChild()).getTextureId() == ability.getTextureId()) {
                                return useSlot(as);
                        }
                }
                return false;
        }

        public static boolean useSlot(final AbilitySlots slot) {
                toggleAbilityBar(false);
                Keyboard.sendKey(slot.getKeybind());
                return true;
        }

        public static boolean addItem(final InventoryItem item, final AbilitySlots slot) {
                toggleAbilityBar(false);
                if (item != null) {
                        if (Interfaces.get(INTERFACE, slot.getAbilityTextureChild()).getChildId() != item.getItem().getId()) {
                                if (Tabs.INVENTORY.open()) {
                                        InterfaceChild itemChild = item.getItem();
                                        InterfaceChild slotChild = Interfaces.get(INTERFACE, slot.getAbilityTextureChild());
                                        Mouse.drag(Random.nextPoint(itemChild.getAdjustedBounds()), Random.nextPoint(slotChild.getAdjustedBounds()));
                                        Time.sleep(250, 675);
                                        return Interfaces.get(INTERFACE, slot.getAbilityTextureChild()).getChildId() == item.getId();
                                }
                        }
                        return Interfaces.get(INTERFACE, slot.getAbilityTextureChild()).getChildId() == item.getItem().getId();
                }
                return false;
        }

        public static boolean containsAbility(final Abilities ability) {
                for (AbilitySlots as : AbilitySlots.values()) {
                        if (as.containsAbility(ability)) {
                                return true;
                        }
                }
                return false;
        }

        public static boolean addAbility(final Abilities ability, final AbilitySlots slot) {
                toggleAbilityBar(false);
                if (ability.getTab().open()) {
                        InterfaceChild abilityChild = Interfaces.get(AbilityTabs.getInterface(), ability.getTab().getChildrenIndex()).getChild(ability.getChildIndex());
                        InterfaceChild slotChild = Interfaces.get(INTERFACE, slot.getAbilityTextureChild());
                        Mouse.drag(Random.nextPoint(abilityChild.getAdjustedBounds()), Random.nextPoint(slotChild.getAdjustedBounds()));
                        Time.sleep(250, 675);
                }
                return Interfaces.get(INTERFACE, slot.getAbilityTextureChild()).getTextureId() == ability.getTextureId();
        }

        public static boolean emptySlot(final AbilitySlots slot) {
                toggleAbilityBar(false);
                if (Interfaces.get(INTERFACE, slot.getAbilityTextureChild()).getTextureId() != EMPTY_TEXTURE) {
                        final Point start = Random.nextPoint(Interfaces.get(INTERFACE, slot.getAbilityTextureChild()).getAdjustedBounds());
                        final Point dest = Random.nextPoint(Interfaces.get(INTERFACE, TRASH_CAN).getAdjustedBounds());
                        Mouse.drag(start, dest);
                        Time.sleep(250, 675);
                }
                return Interfaces.get(INTERFACE, slot.getAbilityTextureChild()).getTextureId() == EMPTY_TEXTURE;
        }

        public static boolean removeAbility(final Abilities ability) {
                toggleAbilityBar(false);
                for (final AbilitySlots as : AbilitySlots.values()) {
                        if (Interfaces.get(INTERFACE, as.getAbilityTextureChild()).getTextureId() == ability.getTextureId()) {
                                return emptySlot(as);
                        }
                }
                return false;
        }

        public static boolean removeItem(final InventoryItem item) {
                toggleAbilityBar(false);
                for (final AbilitySlots as : AbilitySlots.values()) {
                        if (Interfaces.get(INTERFACE, as.getAbilityTextureChild()).getChildId() == item.getId()) {
                                return emptySlot(as);
                        }
                }
                return true;
        }
}
