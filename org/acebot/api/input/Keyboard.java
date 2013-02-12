package org.acebot.api.input;


import org.acebot.core.bot.Bot;
import org.acebot.core.ui.BotFrame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Tim && Marneus901 <3
 * Date: 11/29/12
 * Time: 8:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class Keyboard {

    public static org.acebot.api.wrapper.input.Keyboard keyboard = ((org.acebot.api.wrapper.input.Keyboard) Bot.getClient().getKeyboard());

    public static int getLocation(final char ch) {
        if (ch >= KeyEvent.VK_SHIFT && ch <= KeyEvent.VK_ALT) {
            return new Random().nextInt((KeyEvent.KEY_LOCATION_RIGHT + 1) - KeyEvent.KEY_LOCATION_LEFT) + KeyEvent.KEY_LOCATION_LEFT;
        }
        return KeyEvent.KEY_LOCATION_STANDARD;
    }

    public static void pressKey(char s) {
        int code = s;
        if (s >= 'a' && s <= 'z') {
            code -= 32;
        }
        Component keyboardTarget = Bot.getApplet().getComponent(0);
        KeyEvent event = new KeyEvent(keyboardTarget, KeyEvent.KEY_PRESSED, 0, 0, code, s, Keyboard.getLocation(s));
        keyboard.keyPressed(event);
        event = new KeyEvent(keyboardTarget, KeyEvent.KEY_TYPED, 0, 0, KeyEvent.VK_UNDEFINED, s, 0);
        keyboard.keyTyped(event);
    }

    public static void releaseKey(char s) {
        int code = s;
        if (s >= 'a' && s <= 'z') {
            code -= 32;
        }
        Component keyboardTarget = Bot.getApplet().getComponent(0);
        KeyEvent event = new KeyEvent(keyboardTarget, KeyEvent.KEY_RELEASED, 0, 0, code, s, Keyboard.getLocation(s));
        keyboard.keyReleased(event);
    }

    public static void sendKey(char s) {
        int code = s;
        if (s >= 'a' && s <= 'z') {
            code -= 32;
        }
        Component keyboardTarget = Bot.getApplet().getComponent(0);
        KeyEvent event = new KeyEvent(keyboardTarget, KeyEvent.KEY_PRESSED, 0, 0, code, s, Keyboard.getLocation(s));
        keyboard.keyPressed(event);
        event = new KeyEvent(keyboardTarget, KeyEvent.KEY_TYPED, 0, 0, KeyEvent.VK_UNDEFINED, s, 0);
        keyboard.keyReleased(event);
        event = new KeyEvent(keyboardTarget, KeyEvent.KEY_RELEASED, 0, 0, code, s, Keyboard.getLocation(s));
        keyboard.keyTyped(event);
    }

    public static void sendKeys(String str) {
        for (int i = 0; i < str.length(); ++i) {
            sendKey(str.charAt(i));
            try {
                Thread.sleep(new Random().nextInt(100));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
