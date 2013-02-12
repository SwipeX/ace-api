package org.acebot.api.methods;


import org.acebot.util.Time;

public class AbilityTabs {

        private static final int SETTING = 682;

        public static int getSetting() {
                return SETTING;
        }

        public static int getInterface() {
                return INTERFACE;
        }

        private static final int INTERFACE = 275;

        public enum MainTab {
                MELEE(new int[]{33, 34}, 24),
                RANGED(new int[]{35}, 13),
                MAGIC(new int[]{36}, 40),
                DEFENCE(new int[]{37, 38}, 56);

                public int[] getValues() {
                        return values;
                }

                public int getTabIndex() {
                        return tabIndex;
                }

                final int[] values;
                final int tabIndex;

                MainTab(final int[] values, final int tabIndex) {
                        this.values = values;
                        this.tabIndex = tabIndex;
                }

                public static MainTab getCurrent() {
                        for (final MainTab tab : values()) {
                                if (tab.isOpen()) {
                                        return tab;
                                }
                        }
                        return null;
                }

                public boolean isOpen() {
                        final int setting = Settings.get(SETTING);
                        for (final int value : getValues()) {
                                if ((setting & 0xFF) == value) {
                                        return true;
                                }
                        }
                        return false;
                }

                public boolean open() {
                        if (Tabs.ABILITIES.open()) {
                                if (!isOpen()) {
                                        if (Interfaces.get(INTERFACE, getTabIndex()).click()) {
                                                Time.sleep(260, 450);
                                        }
                                }
                        }
                        return isOpen();
                }

                @Override
                public String toString() {
                        return name().charAt(0) + name().substring(1).toLowerCase().toLowerCase().replaceAll("_", " ");
                }
        }

        public enum SubTab {
                ATTACK(MainTab.MELEE, 1, 16, 1, 14207),
                STRENGTH(MainTab.MELEE, 1, 16, 1, 14255),
                RANGED(MainTab.RANGED, 1, 16, 1, 14243),
                MAGIC_ABILITIES(MainTab.MAGIC, 1, 16, 1, 14231),
                COMBAT_SPELLS(MainTab.MAGIC, 1, 17, 14, 14521),
                TELEPORT_SPELLS(MainTab.MAGIC, 1, 17, 19, 14521),
                SKILLING_SPELLS(MainTab.MAGIC, 1, 17, 17, 14521),
                DEFENCE(MainTab.DEFENCE, 1, 16, 1, 14219),
                CONSTITUTION(MainTab.DEFENCE, 1, 16, 1, 14267);
                private final MainTab mainTab;
                private final int tabIndex;

                public MainTab getMainTab() {
                        return mainTab;
                }

                public int getTabIndex() {
                        return tabIndex;
                }

                public int getChildrenIndex() {
                        return childrenIndex;
                }

                public int getSkillChild() {
                        return skillChild;
                }

                public int getTexture() {
                        return texture;
                }

                private final int childrenIndex;
                private final int skillChild;
                private final int texture;

                SubTab(final MainTab mainTab, final int tabIndex, final int childrenIndex, final int skillChild, final int texture) {
                        this.mainTab = mainTab;
                        this.tabIndex = tabIndex;
                        this.childrenIndex = childrenIndex;
                        this.skillChild = skillChild;
                        this.texture = texture;
                }

                public static SubTab getCurrent() {
                        for (final SubTab at : SubTab.values()) {
                                if (at.isOpen()) {
                                        return at;
                                }
                        }
                        return null;
                }

                public boolean isOpen() {
                        return getMainTab().isOpen() && Interfaces.get(INTERFACE, getChildrenIndex()).getChild(getSkillChild()).getTextureId() == getTexture();
                }

                public boolean open() {
                        if (getMainTab().open()) {
                                if (!isOpen()) {
                                        if (Interfaces.get(INTERFACE, getTabIndex()).click()) {
                                                Time.sleep(260, 450);
                                        }
                                }
                        }
                        return getMainTab().isOpen() && isOpen();
                }

                @Override
                public String toString() {
                        return name().charAt(0) + name().substring(1).toLowerCase().toLowerCase().replaceAll("_", " ");
                }
        }
}
