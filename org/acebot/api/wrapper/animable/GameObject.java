package org.acebot.api.wrapper.animable;


import org.acebot.api.wrapper.LDModel;
import org.acebot.impl.ObjectDefLoader;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 12/7/12
 * Time: 10:34 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GameObject extends Locatable, Interactable {

        int getId();

        ObjectDefLoader getObjectDefLoader();

        public Object getObject();

        public LDModel getModel();
}
