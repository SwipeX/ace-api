package org.acebot.api.methods;


import org.acebot.api.input.Mouse;
import org.acebot.core.bot.Bot;
import org.acebot.impl.*;
import org.acebot.script.Script;
import org.acebot.util.Deque;
import org.acebot.util.Queue;
import org.acebot.util.Random;
import org.acebot.util.Timer;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class Menu {

        private static final Pattern HTML_TAG = Pattern.compile("(^[^<]+>|<[^>]+>|<[^>]+$)");
        private static Rectangle mainIndex, collapsedIndex;

        /**
         * @return The location of the Menu.
         */
        public static Point getLocation() {
                final Client client = Bot.getClient();
                return new Point(client.getMenuX(), client.getMenuY());
        }

        /**
         * @return The location of the Collapsed Menu.
         */
        public static Point getCollapsedLocation() {
                final Client client = Bot.getClient();
                return new Point(client.getCollapsedMenuX(), client.getCollapsedMenuY());
        }

        /**
         * @return The width of the opened menu.
         */
        public static int getWidth() {
                final Client client = Bot.getClient();
                return client.getMenuWidth();
        }

        /**
         * @return The height of the opened menu.
         */
        public static int getHeight() {
                final Client client = Bot.getClient();
                return client.getMenuHeight();
        }

        /**
         * @return The width of the opened Collapsed.
         */
        public static int getCollapsedWidth() {
                final Client client = Bot.getClient();
                return client.getCollapsedMenuWidth();
        }

        public static boolean isOpen() {
                return Bot.getClient().isMenuOpen();
        }

        public static boolean isCollapsed() {
                return Bot.getClient().isMenuCollapsed();
        }

        public static boolean select(final String action) {
                return select(action, null);
        }

        public static boolean select(final String action, final String option) {
                int idx = getIndex(action, option);
                if (!isOpen()) {
                        if (idx == -1) {
                                return false;
                        }
                        if (idx == 0) {
                                Mouse.click(true);
                                return true;
                        }
                        Mouse.click(false);
                        final Timer t = new Timer(100);
                        while (t.isRunning() && !isOpen()) {
                                Script.sleep(5);
                        }
                        idx = getIndex(action, option);
                        return idx != -1 && clickIndex(idx);
                } else if (idx == -1) {
                        while (isOpen()) {
                                Point loc = getLocation();
                                if (loc.y > 20 && Random.nextBoolean()) {
                                        int moveX;
                                        if (loc.x > 15 && loc.x + getWidth() < 750) {
                                                if (Random.nextBoolean()) {
                                                        moveX = loc.x + Random.nextInt(-12, -7);
                                                } else {
                                                        moveX = loc.x + getWidth() + Random.nextInt(7, 12);
                                                }
                                        } else {
                                                if (loc.y < 16) {
                                                        moveX = loc.x + getWidth() + Random.nextInt(7, 20);
                                                } else {
                                                        moveX = loc.x + Random.nextInt(-20, -7);
                                                }
                                        }
                                        Mouse.move(moveX, Random.nextInt(loc.y - 30, loc.y - 7));
                                } else {
                                        int moveY = loc.y > 25 ? loc.y + Random.nextInt(-18, -10) : loc.y + Random.nextInt(10, 20);
                                        if (loc.x >= 30) {
                                                if (loc.x + getWidth() > 755 && Random.nextBoolean()) {
                                                        Mouse.move(Random.nextInt(loc.x - 20, loc.x - 10), moveY);
                                                } else {
                                                        if (Random.nextBoolean()) {
                                                                Mouse.move(Random.nextInt(loc.x + getWidth() + 10, loc.x + getWidth() + 20), moveY);
                                                        } else {
                                                                Mouse.move(Random.nextInt(loc.x - 20, loc.x - 10), moveY);
                                                        }
                                                }
                                        } else {
                                                Mouse.move(Random.nextInt(loc.x + getWidth() + 10, loc.x + getWidth() + 20), moveY);
                                        }
                                }
                        }
                        return false;
                }
                return clickIndex(idx);
        }

        public static boolean clickIndex(final int i) {
                if (!isOpen()) {
                        return false;
                }
                final String[] items = getItems();
                if (items.length <= i) {
                        return false;
                }
                if (isCollapsed()) {
                        final Queue<MenuGroupNode> groups = new Queue<MenuGroupNode>((NodeSubQueue) Bot.getClient().getCollapsedMenuItems());
                        int idx = 0, mainIdx = 0;
                        for (MenuGroupNode g = groups.getHead(); g != null; g = groups.getNext(), ++mainIdx) {
                                final Queue<MenuItemNode> cItems = new Queue<MenuItemNode>((NodeSubQueue) g.getItems());
                                int cIdx = 0;
                                for (MenuItemNode item = cItems.getHead(); item != null; item = cItems.getNext(), ++cIdx) {
                                        if (idx++ == i) {
                                                return cIdx == 0 ? clickMain(mainIdx) : clickCollapsed(mainIdx, cIdx);
                                        }
                                }
                        }
                        return false;
                } else {
                        return clickMain(i);
                }
        }

        public static String[] getItems() {
                final String[] options = getOptions(), actions = getActions();
                final LinkedList<String> output = new LinkedList<String>();
                final int len = Math.min(options.length, actions.length);
                for (int i = 0; i < len; i++) {
                        final String option = options[i];
                        final String action = actions[i];
                        if (option != null && action != null) {
                                final String text = action + " " + option;
                                output.add(text.trim());
                        }
                }
                return output.toArray(new String[output.size()]);
        }

        public static boolean contains(final String action) {
                return getIndex(action) != -1;
        }

        public static boolean contains(final String action, final String option) {
                return getIndex(action, option) != -1;
        }

        public static boolean clickMain(final int i) {
                final Point menuLoc = getLocation();
                mainIndex = new Rectangle(menuLoc.x + 3, menuLoc.y + 21 + 16 * i, getWidth() - 6, 16);
                final int x = Random.nextInt(mainIndex.x, mainIndex.x + mainIndex.width);
                final int y = mainIndex.y + Random.nextInt(1, 14);
                if (!mainIndex.contains(Mouse.getLocation())) {
                        Mouse.move(x, y);
                }
                if (isOpen() && mainIndex.contains(Mouse.getLocation())) {
                        mainIndex = null;
                        Mouse.click(true);
                        return true;
                }
                mainIndex = null;
                return false;
        }

        public static boolean clickCollapsed(final int mIdx, final int sIdx) {
                final Point menuLoc = getLocation();
                mainIndex = new Rectangle(menuLoc.x + 3, menuLoc.y + 21 + 16 * mIdx, getWidth() - 6, 16);
                int x = Random.nextInt(mainIndex.x, mainIndex.x + (int) (mainIndex.width * .7));
                int y = mainIndex.y + Random.nextInt(1, 14);
                if (!mainIndex.contains(Mouse.getLocation())) {
                        Mouse.move(x, y);
                }
                Script.sleep(100, 250);
                if (isOpen() && mainIndex.contains(Mouse.getLocation())) {
                        final Point cLoc = getCollapsedLocation();
                        y = Mouse.getLocation().y + Random.nextInt(-2, 3);
                        collapsedIndex = new Rectangle(cLoc.x + 3, cLoc.y + 21 + 16 * sIdx, getCollapsedWidth() - 6, 16);
                        x = Random.nextInt(collapsedIndex.x, collapsedIndex.x + collapsedIndex.width / 2);
                        if (isOpen() && mainIndex.contains(Mouse.getLocation())) {
                                Mouse.move(x, y);
                                Script.sleep(75, 250);
                                if (isOpen()) {
                                        y = collapsedIndex.y + Random.nextInt(1, 14);
                                        Mouse.move(x, y);
                                        if (isOpen() && collapsedIndex.contains(Mouse.getLocation())) {
                                                mainIndex = null;
                                                collapsedIndex = null;
                                                Mouse.click(true);
                                                return true;
                                        }
                                }
                        }
                }
                mainIndex = null;
                collapsedIndex = null;
                return false;
        }

        public static void draw(Graphics g) {
                if (isOpen()) {
                        if (mainIndex != null) {
                                g.fillRect(mainIndex.x, mainIndex.y, mainIndex.width, mainIndex.height);
                        }
                        if (collapsedIndex != null) {
                                g.fillRect(collapsedIndex.x, collapsedIndex.y, collapsedIndex.width, collapsedIndex.height);
                        }
                } else {
                        mainIndex = null;
                        collapsedIndex = null;
                }
        }

        public static String[] getActions() {
                return getMenuItemPart(true);
        }

        public static String[] getOptions() {
                return getMenuItemPart(false);
        }

        public static int getIndex(String action) {
                action = action.toLowerCase();
                final String[] items = getActions();
                for (int i = 0; i < items.length; i++) {
                        if (items[i].toLowerCase().contains(action)) {
                                return i;
                        }
                }
                return -1;
        }

        public static int getIndex(String action, String option) {
                if (option == null) {
                        return getIndex(action);
                }
                action = action.toLowerCase();
                option = option.toLowerCase();
                final String[] actions = getActions();
                final String[] options = getOptions();
                for (int i = 0; i < Math.min(actions.length, options.length); i++) {
                        if (actions[i].toLowerCase().contains(action) && options[i].toLowerCase().contains(option)) {
                                return i;
                        }
                }
                return -1;
        }

        public static String[] getMenuItemPart(final boolean firstPart) {
                final LinkedList<String> itemsList = new LinkedList<String>();
                final Client client = Bot.getClient();
                String firstAction = null;
                if (isCollapsed()) {
                        try {
                                final Queue<MenuGroupNode> menu = new Queue<MenuGroupNode>((NodeSubQueue) client.getCollapsedMenuItems());
                                for (MenuGroupNode mgn = menu.getHead(); mgn != null; mgn = menu.getNext()) {
                                        final Queue<MenuItemNode> cMenu = new Queue<MenuItemNode>((NodeSubQueue) mgn.getItems());
                                        for (MenuItemNode min = cMenu.getHead(); min != null; min = cMenu.getNext()) {
                                                if (firstAction == null || firstAction.isEmpty()) {
                                                        firstAction = (String) min.getAction();
                                                }
                                                itemsList.addLast(firstPart ?
                                                                stripFormatting((String) min.getAction()) :
                                                                stripFormatting((String) min.getOption()));
                                        }
                                }
                        } catch (final NullPointerException ignored) {
                        }
                } else {
                        try {
                                final Deque<MenuItemNode> menu = new Deque<MenuItemNode>((NodeDeque) client.getMenuItems());
                                for (MenuItemNode min = menu.getHead(); min != null; min = menu.getNext()) {
                                        if (firstAction == null || firstAction.isEmpty()) {
                                                firstAction = (String) min.getAction();
                                        }
                                        itemsList.addLast(firstPart ?
                                                        stripFormatting((String) min.getAction()) :
                                                        stripFormatting((String) min.getOption()));
                                }
                        } catch (final Throwable ignored) {
                        }
                }
                if (itemsList.size() > 1 && firstAction != null && firstAction.equals(isCollapsed() ? "Walk here" : "Cancel")) {
                        Collections.reverse(itemsList);
                }
                return itemsList.toArray(new String[itemsList.size()]);
        }

        private static String stripFormatting(final String input) {
                return HTML_TAG.matcher(input).replaceAll("");
        }
}
