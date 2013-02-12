package org.acebot.api.methods;


import org.acebot.api.input.Keyboard;
import org.acebot.util.Time;

import java.util.regex.Pattern;

public enum AbilitySlots {
        SLOT_ONE(0, 70, 32, 36),
        SLOT_TWO(1, 75, 72, 73),
        SLOT_THREE(2, 79, 76, 77),
        SLOT_FOUR(3, 83, 80, 81),
        SLOT_FIVE(4, 87, 84, 85),
        SLOT_SIX(5, 91, 88, 89),
        SLOT_SEVEN(6, 95, 92, 93),
        SLOT_EIGHT(7, 99, 96, 97),
        SLOT_NINE(8, 103, 100, 101),
        SLOT_TEN(9, 107, 104, 105),
        SLOT_ELEVEN(10, 111, 108, 109),
        SLOT_TWELVE(11, 115, 112, 113);
        private final int index, keybind, abilityTexture, cdTexture;
        private static final int INTERFACE = 640;

        AbilitySlots(final int index, final int keybind, final int abilityTexture, final int cdTexture) {
                this.index = index;
                this.keybind = keybind;
                this.abilityTexture = abilityTexture;
                this.cdTexture = cdTexture;
        }

        public boolean containsAbility(final Abilities ability) {
                return Interfaces.get(INTERFACE, getAbilityTextureChild()).getTextureId() == ability.getTextureId();
        }

        public char getKeybind() {
                return Interfaces.get(INTERFACE, getKeybindChild()).getText().charAt(0);
        }

        public boolean setKeybind(final char hotkey) {
                Pattern pattern = Pattern.compile("([a-z0-9\\.\\/\\[\\]#';,]\\\\)", Pattern.CASE_INSENSITIVE);
                if (!pattern.matcher(String.valueOf(hotkey)).find()) {
                        return false;
                }
                if (Interfaces.get(INTERFACE, getAbilityTextureChild()).click(false)) {
                        if (Menu.select("Customize-keybind")) {
                                Time.sleep(250, 650);
                                Keyboard.sendKey(hotkey);
                                Time.sleep(150, 250);
                        }
                }
                return getKeybind() == hotkey;
        }

        public int getIndex() {
                return index;
        }

        public int getKeybindChild() {
                return keybind;
        }

        public int getAbilityTextureChild() {
                return abilityTexture;
        }

        public int getCdTextureChild() {
                return cdTexture;
        }
}