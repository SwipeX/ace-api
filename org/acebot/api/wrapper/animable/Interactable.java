package org.acebot.api.wrapper.animable;


/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 12/10/12
 * Time: 9:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Interactable {

        public boolean interact(String action, String option);

        public boolean interact(String action);
}
