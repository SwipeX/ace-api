package org.acebot.api.methods;


public enum Abilities {
        SLICE(AbilityTabs.SubTab.ATTACK, 1, 14207, 0, 3, false),
        BARGE(AbilityTabs.SubTab.ATTACK, 2, 14208, 0, 20, true),
        SEVER(AbilityTabs.SubTab.ATTACK, 3, 14209, 0, 30, false),
        HAVOC(AbilityTabs.SubTab.ATTACK, 4, 14210, 0, 10, true),
        SMASH(AbilityTabs.SubTab.ATTACK, 5, 14211, 0, 10, false),
        BACKHAND(AbilityTabs.SubTab.ATTACK, 6, 14212, 0, 15, false),
        SLAUGHTER(AbilityTabs.SubTab.ATTACK, 7, 14213, 50, 30, false),
        FLURRY(AbilityTabs.SubTab.ATTACK, 8, 14214, 20, 50, true),
        HURRICANE(AbilityTabs.SubTab.ATTACK, 9, 14215, 50, 30, true),
        OVERPOWER(AbilityTabs.SubTab.ATTACK, 10, 14216, 100, 30, false),
        MASSACRE(AbilityTabs.SubTab.ATTACK, 11, 14217, 100, 30, true),
        METEOR_STRIKE(AbilityTabs.SubTab.ATTACK, 12, 14218, 100, 30, true),
        DISMEMBER(AbilityTabs.SubTab.STRENGTH, 1, 14255, 0, 30, true),
        KICK(AbilityTabs.SubTab.STRENGTH, 2, 14256, 0, 15, false),
        PUNISH(AbilityTabs.SubTab.STRENGTH, 3, 14257, 0, 5, false),
        FURY(AbilityTabs.SubTab.STRENGTH, 4, 14258, 0, 20, false),
        DECIMATE(AbilityTabs.SubTab.STRENGTH, 5, 14259, 0, 10, true),
        CLEAVE(AbilityTabs.SubTab.STRENGTH, 6, 14260, 0, 10, false),
        ASSAULT(AbilityTabs.SubTab.STRENGTH, 7, 14261, 50, 30, true),
        QUAKE(AbilityTabs.SubTab.STRENGTH, 8, 14262, 50, 20, true),
        DESTROY(AbilityTabs.SubTab.STRENGTH, 9, 14263, 50, 20, true),
        BERSERK(AbilityTabs.SubTab.STRENGTH, 10, 14264, 100, 60, true),
        FRENZY(AbilityTabs.SubTab.STRENGTH, 11, 14265, 100, 60, true),
        PULVERIZE(AbilityTabs.SubTab.STRENGTH, 12, 14266, 100, 60, true),
        PIECING_SHOT(AbilityTabs.SubTab.RANGED, 1, 14243, 0, 3, false),
        BLINDING_SHOT(AbilityTabs.SubTab.RANGED, 2, 14244, 0, 15, false),
        ESCAPE(AbilityTabs.SubTab.RANGED, 3, 14245, 0, 20, true),
        SNIPE(AbilityTabs.SubTab.RANGED, 4, 14246, 0, 10, true),
        FRAGMENTATION_SHOT(AbilityTabs.SubTab.RANGED, 5, 14247, 0, 15, false),
        RICOCHET(AbilityTabs.SubTab.RANGED, 6, 14248, 0, 10, false),
        SNAP_SHOT(AbilityTabs.SubTab.RANGED, 7, 14249, 50, 20, false),
        RAPID_FIRE(AbilityTabs.SubTab.RANGED, 8, 14250, 50, 20, true),
        BOMBARDMENT(AbilityTabs.SubTab.RANGED, 9, 14251, 50, 30, true),
        INCENDIARY_SHOT(AbilityTabs.SubTab.RANGED, 10, 14252, 100, 60, true),
        UNLOAD(AbilityTabs.SubTab.RANGED, 11, 14253, 100, 60, true),
        DEAD_SHOT(AbilityTabs.SubTab.RANGED, 12, 14254, 100, 30, false),
        WRACK(AbilityTabs.SubTab.MAGIC_ABILITIES, 1, 14231, 0, 3, false),
        SURGE(AbilityTabs.SubTab.MAGIC_ABILITIES, 2, 14233, 0, 20, true),
        IMPACT(AbilityTabs.SubTab.MAGIC_ABILITIES, 3, 14234, 0, 15, false),
        CHAIN(AbilityTabs.SubTab.MAGIC_ABILITIES, 4, 14232, 0, 10, false),
        COMBUST(AbilityTabs.SubTab.MAGIC_ABILITIES, 5, 14235, 0, 15, false),
        DRAGON_BREATH(AbilityTabs.SubTab.MAGIC_ABILITIES, 6, 14236, 0, 10, true),
        ASPHYXIATE(AbilityTabs.SubTab.MAGIC_ABILITIES, 7, 14237, 50, 20, true),
        DETONATE(AbilityTabs.SubTab.MAGIC_ABILITIES, 8, 14238, 50, 30, true),
        WILD_MAGIC(AbilityTabs.SubTab.MAGIC_ABILITIES, 9, 14239, 50, 20, true),
        METAMORPHOSIS(AbilityTabs.SubTab.MAGIC_ABILITIES, 10, 14241, 100, 60, true),
        TSUNAMI(AbilityTabs.SubTab.MAGIC_ABILITIES, 11, 14240, 100, 60, true),
        OMNIPOWER(AbilityTabs.SubTab.MAGIC_ABILITIES, 12, 14242, 100, 30, false),
        ANTICIPATION(AbilityTabs.SubTab.DEFENCE, 1, 14219, 0, 24, false),
        FREEDOM(AbilityTabs.SubTab.DEFENCE, 2, 14220, 0, 30, false),
        PROVOKE(AbilityTabs.SubTab.DEFENCE, 3, 14221, 0, 10, true),
        RESONATE(AbilityTabs.SubTab.DEFENCE, 4, 14222, 0, 30, true),
        PREPARATION(AbilityTabs.SubTab.DEFENCE, 5, 14223, 0, 20, true),
        BASH(AbilityTabs.SubTab.DEFENCE, 6, 14224, 0, 15, false),
        REFLECT(AbilityTabs.SubTab.DEFENCE, 7, 14225, 50, 20, true),
        DELIBERATE(AbilityTabs.SubTab.DEFENCE, 8, 14226, 50, 30, true),
        REVENGE(AbilityTabs.SubTab.DEFENCE, 9, 14227, 50, 20, true),
        BARRICADE(AbilityTabs.SubTab.DEFENCE, 10, 14228, 100, 60, true),
        REJUVINATE(AbilityTabs.SubTab.DEFENCE, 11, 14229, 100, 60, false),
        IMMORTALITY(AbilityTabs.SubTab.DEFENCE, 12, 14230, 100, 120, true),
        REGENERATE(AbilityTabs.SubTab.CONSTITUTION, 1, 14267, 0, 0, false),
        INCITE(AbilityTabs.SubTab.CONSTITUTION, 2, 14268, Settings.get(679) / 10, 0, false),
        MOMENTUM(AbilityTabs.SubTab.CONSTITUTION, 7, 14674, 100, 0, false),
        SINGLE_WAY_WILDERNESS(AbilityTabs.SubTab.CONSTITUTION, 8, 14269, -1, 0, false),;

        public AbilityTabs.SubTab getTab() {
                return tab;
        }

        public int getChildIndex() {
                return childIndex;
        }

        public int getTextureId() {
                return textureId;
        }

        public int getCost() {
                return cost;
        }

        public int getCooldown() {
                return cooldown;
        }

        public boolean isMemberAbility() {
                return memberAbility;
        }

        public boolean isNormalAbility() {
                return getCost() == 0;
        }

        public boolean isThresholdAbility() {
                return getCost() == 50;
        }

        public boolean isUltimateAbility() {
                return getCost() == 100;
        }

        private final AbilityTabs.SubTab tab;
        private final int childIndex, textureId, cost, cooldown;
        private final boolean memberAbility;

        Abilities(final AbilityTabs.SubTab tab, final int childIndex, final int textureId, final int cost, final int cooldown, final boolean memberAbility) {
                this.tab = tab;
                this.childIndex = childIndex;
                this.textureId = textureId;
                this.cost = cost;
                this.cooldown = cooldown;
                this.memberAbility = memberAbility;
        }
}
