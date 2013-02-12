package org.acebot.api.methods;


import org.acebot.api.wrapper.iterable.InterfaceChild;
import org.acebot.core.bot.Bot;

public enum Skills {
	ATTACK(0, 1),
	DEFENSE(1, 18),
	STRENGTH(2, 4),
	CONSTITUTION(3, 2),
	RANGE(4, 35),
	PRAYER(5, 53),
	MAGIC(6, 66),
	COOKING(7, 47),
	WOODCUTTING(8, 78),
	FLETCHING(9, 72),
	FISHING(10, 29),
	FIREMAKING(11, 65),
	CRAFTING(12, 59),
	SMITHING(13, 16),
	MINING(14, 3),
	HERBLORE(15, 23),
	AGILITY(16, 10),
	THIEVING(17, 41),
	SLAYER(18, 85),
	FARMING(19, 91),
	RUNECRAFTING(20, 79),
	HUNTER(21, 103),
	CONSTRUCTION(22, 97),
	SUMMONING(23, 109),
	DUNGEONEERING(24, 115);
	public static final int[] EXPERIENCE_TABLE = {0, 0, 83, 174, 276, 388, 512, 650, 801, 969, 1154, 1358, 1584, 1833, 2107,
			2411, 2746, 3115, 3523, 3973, 4470, 5018, 5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031, 13363, 14833,
			16456, 18247, 20224, 22406, 24815, 27473, 30408, 33648, 37224, 41171, 45529, 50339, 55649, 61512, 67983,
			75127, 83014, 91721, 101333, 111945, 123660, 136594, 150872, 166636, 184040, 203254, 224466, 247886, 273742,
			302288, 333804, 368599, 407015, 449428, 496254, 547953, 605032, 668051, 737627, 814445, 899257, 992895,
			1096278, 1210421, 1336443, 1475581, 1629200, 1798808, 1986068, 2192818, 2421087, 2673114, 2951373, 3258594,
			3597792, 3972294, 4385776, 4842295, 5346332, 5902831, 6517253, 7195629, 7944614, 8771558, 9684577, 10692629,
			11805606, 13034431, 14391160, 15889109, 17542976, 19368992, 21385073, 23611006, 26068632, 28782069,
			31777943, 35085654, 38737661, 42769801, 47221641, 52136869, 57563718, 63555443, 70170840, 77474828,
			85539082, 94442737, 104273167};
	private static final int SKILLS_INTERFACE = 320;
	private final int index, childIndex;

	Skills(int index, int childIndex) {
		this.index = index;
		this.childIndex = childIndex;
	}

	public static int getExperienceAt(final int level) {
		return EXPERIENCE_TABLE[level];
	}

	public static int getLevelAt(final int exp) {
		for (int i = 0; i < EXPERIENCE_TABLE.length; i++) {
			if (exp < EXPERIENCE_TABLE[i]) {
				return i - 1;
			}
		}
		return -1;
	}

	public int getCurrentLevel() {
		if (!Game.isLoggedIn()) {
			return -1;
		}
		return Bot.getClient().getLocalStorage().getSkillDataArray()[index].getCurrentLevel();
	}

	public int getRealLevel() {
		if (!Game.isLoggedIn()) {
			return -1;
		}
		return Bot.getClient().getLocalStorage().getSkillDataArray()[index].getRealLevel();
	}

	public int getExperience() {
		if (!Game.isLoggedIn()) {
			return -1;
		}
		return Bot.getClient().getLocalStorage().getSkillDataArray()[index].getExperience();
	}

	public int getRemainingExp(final int target) {
		if (target > getMaxLevel() || target <= getRealLevel()) {
			return 0;
		}
		return getExperienceAt(target) - getExperience();
	}

	public int getMaxLevel() {
		if (this.equals(DUNGEONEERING)) {
			return 120;
		} else {
			return 99;
		}
	}

	public String getSkillName() {
		return name().charAt(0) + name().substring(1).toLowerCase();
	}

	public int getIndex() {
		return index;
	}

	public int getChildIndex() {
		return childIndex;
	}

	public InterfaceChild getChild() {
		return Interfaces.get(SKILLS_INTERFACE, childIndex);
	}
}
