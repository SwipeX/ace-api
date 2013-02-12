package org.acebot.api.wrapper.animable;


/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 11/24/12
 * Time: 8:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class Offset {

        public int getX() {
                return x;
        }

        public int getY() {
                return y;
        }

        public Offset(int x, int y) {
                this.x = x;
                this.y = y;
        }

        int x;
        int y;
}
