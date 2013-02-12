package org.acebot.api.methods;


import org.acebot.core.bot.Bot;
import org.acebot.impl.HintArrow;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 12/19/12
 * Time: 1:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class HintArrows {

        public static HintArrow[] getAll() {
                return Bot.getClient().getHintArrows();
        }
}
