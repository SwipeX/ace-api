package org.acebot.api.methods;

import org.acebot.api.wrapper.animable.NPC;
import org.acebot.api.wrapper.animable.Tile;
import org.acebot.api.wrapper.iterable.Interface;
import org.acebot.api.wrapper.iterable.InterfaceChild;
import org.acebot.util.Time;
import org.acebot.util.Timer;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 12/22/12
 * Time: 11:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class Bank {
        public static final int[] BANK_NPC_IDS = new int[]{
                        44, 45, 166, 494, 495, 496, 497, 498, 499, 553, 909, 953, 958, 1036, 1360, 1702, 2163, 2164, 2354, 2355,
                        2568, 2569, 2570, 2718, 2759, 3046, 3198, 3199, 3293, 3416, 3418, 3824, 4456, 4457, 4458, 4459, 4519, 4907,
                        5257, 5258, 5259, 5260, 5488, 5776, 5777, 5901, 6200, 6362, 7049, 7050, 7605, 8948, 9710, 13932, 14923, 14924,
                        14925, 15194
        };
        public static final int[] BANK_BOOTH_IDS = new int[]{
                        782, 2213, 3045, 5276, 6084, 10517, 11338, 11758, 12759, 12798, 12799, 12800, 12801, 14369, 14370,
                        16700, 19230, 20325, 20326, 20327, 20328, 22819, 24914, 25808, 26972, 29085, 34205, 34752, 35647,
                        35648, 36262, 36786, 37474, 49018, 49019, 52397, 52589
        };
        public static final int[] BANK_COUNTER_IDS = new int[]{
                        42217, 42377, 42378
        };
        public static final int[] BANK_CHEST_IDS = new int[]{
                        2693, 4483, 8981, 12308, 14382, 20607, 21301, 27663, 42192, 57437, 62691
        };
        public static final int[] UNDEPOSITABLE_ITEM_IDS = new int[]{2528, 6796, 23713, 23714, 23715, 23716, 23717, 23718,
                        23719, 23720, 23721, 23722, 23723, 23724, 23725, 23726, 23727, 23728, 23729, 23730, 23731, 23732, 23733,
                        23734, 23735, 23736, 23737, 23738, 23739, 23740, 23741, 23742, 23743, 23744, 23745, 23746, 23747, 23748,
                        23749, 23750, 23751, 23752, 23753, 23754, 23755, 23756, 23757, 23758, 23759, 23760, 23761, 23762, 23763,
                        23764, 23765, 23766, 23767, 23768, 23769, 23770, 23771, 23773, 23774, 23775, 23776, 23777, 23778, 23779,
                        23780, 23781, 23782, 23783, 23784, 23785, 23786, 23787, 23788, 23789, 23790, 23791, 23792, 23793, 23794,
                        23795, 23796, 23797, 23798, 23799, 23800, 23801, 23802, 23803, 23804, 23805, 23806, 23807, 23808, 23809,
                        23810, 23811, 23812, 23813, 23814, 23815, 23816, 23817, 24154, 24155
        };
        public static final Tile[] UNREACHABLE_BANK_TILES = new Tile[]{
                        new Tile(3191, 3445, 0), new Tile(3180, 3433, 0)
        };

        public static final int INTERFACE_BANK = 762;
        public static final int INTERFACE_SLOTS_CONTAINER = 95;
        public static final int INTERFACE_SCROLLBAR = 116;
        public static final int INTERFACE_BUTTON_CLOSE_BANK = 45;
        public static final int INTERFACE_BUTTON_DEPOSIT_INVENTORY = 34;
        public static final int INTERFACE_BUTTON_DEPOSIT_EQUIPMENT = 38;
        public static final int INTERFACE_BUTTON_DEPOSIT_FAMILIAR = 40;
        public static final int INTERFACE_BUTTON_DEPOSIT_POUCH = 36;
        public static final int INTERFACE_BUTTON_SEARCH = 18;
        public static final int INTERFACE_BUTTON_WITHDRAW_NOTED = 20;

        public static final int INTERFACE_BANKPIN = 13;

        public static final int SETTING_WITHDRAWAL_MODE = 115;
        public static final int SETTING_BANK_TAB = 1248;

        public static boolean isOpen() {
                final Interface bank = getInterface();
                return bank != null && bank.validate();
        }

        public static void open() {
                NPC banker = NPCs.getNearest("Banker", "");
                if (banker != null) {
                        banker.interact("Open Bank");
                }
        }

        public static boolean close() {
                if (!isOpen()) {
                        return true;
                }
                final InterfaceChild closeButton = Interfaces.get(INTERFACE_BANK, INTERFACE_BUTTON_CLOSE_BANK);
                return closeButton != null && closeButton.interact("Close");
        }


        public static Interface getInterface() {
                return Interfaces.get(INTERFACE_BANK);
        }

        public static boolean depositInventory() {
                if (!isOpen()) {
                        return false;
                }
                if (Inventory.getCount() == 0) {
                        return true;
                }
                final InterfaceChild child = Interfaces.get(INTERFACE_BANK, INTERFACE_BUTTON_DEPOSIT_INVENTORY);
                final int invCount = Inventory.getCount();
                if (child != null && child.click(true)) {
                        final Timer t = new Timer(2000);
                        while (t.isRunning() && Inventory.getCount() == invCount) {
                                Time.sleep(5);
                        }
                }
                return invCount != Inventory.getCount();
        }

        public static boolean depositEquipment() {
                if (!isOpen()) {
                        return false;
                }
                final InterfaceChild child = Interfaces.get(INTERFACE_BANK, INTERFACE_BUTTON_DEPOSIT_EQUIPMENT);
                return child != null && child.click(true);
        }

        public static boolean depositFamiliarInventory() {
                if (!isOpen()) {
                        return false;
                }
                final InterfaceChild child = Interfaces.get(INTERFACE_BANK, INTERFACE_BUTTON_DEPOSIT_FAMILIAR);
                return child != null && child.click(true);
        }

        public static boolean depositMoneyPouch() {
                if (!isOpen()) {
                        return false;
                }
                final InterfaceChild child = Interfaces.get(INTERFACE_BANK, INTERFACE_BUTTON_DEPOSIT_POUCH);
                return child != null && child.click(true);
        }


}
