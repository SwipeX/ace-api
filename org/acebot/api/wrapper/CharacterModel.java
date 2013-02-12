package org.acebot.api.wrapper;


import org.acebot.api.methods.Calculations;
import org.acebot.impl.Model;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 12/13/12
 * Time: 4:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class CharacterModel extends LDModel {

        org.acebot.api.wrapper.animable.Character character;

        public CharacterModel(Model model, org.acebot.api.wrapper.animable.Character a) {
                super(model, a.getLocation());
                character = a;
                update();
        }

        @Override
        public void update() {
                if (character != null && model != null) {
                        final int theta = character.getOrientation() & 0x3fff;
                        final int sin = Calculations.SIN_TABLE[theta];
                        final int cos = Calculations.COS_TABLE[theta];
                        int numFaces = Math.min(model.getXPoints().length, Math.min(model.getYPoints().length, model.getZPoints().length));
                        final int[] x = model.getXPoints();
                        final int[] z = model.getZPoints();
                        for (int i = 0; i < numFaces; ++i) {
                                model.getXPoints()[i] = x[i] * cos + z[i] * sin >> 15;
                                model.getZPoints()[i] = z[i] * cos - x[i] * sin >> 15;
                        }
                }
        }
}
