package org.acebot.api.wrapper.iterable;
/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 12/5/12
 * Time: 4:22 PM
 * To change this template use File | Settings | File Templates.
 */

import org.acebot.api.methods.Nodes;
import org.acebot.core.bot.Bot;
import org.acebot.impl.HardReference;
import org.acebot.impl.ItemDef;
import org.acebot.impl.Node;
import org.acebot.impl.SoftReference;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 12/2/12
 * Time: 3:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class InventoryItem {

        InterfaceChild item;
        int Id;
        int stackSize;
        String name;

        public InventoryItem(InterfaceChild i) {
                item = i;
                Id = i.getChildId();
                name = i.getName();
        }

        public int getId() {
                return Id;
        }

        public String getName() {
                return getDefinition().getName();
        }

        public InterfaceChild getItem() {
                return item;
        }

        public ItemDef getDefinition() {
                try {
                        final Node ref = Nodes.lookup(Bot.getClient().getItemDefLoader().getItemDefCache().getTable(), getId());
                        if (ref != null) {
                                if (ref instanceof HardReference) {
                                        return ((ItemDef) ((HardReference) ref).getReference());
                                } else if (ref instanceof SoftReference) {
                                        final Object def = ((SoftReference) ref).getReference().get();
                                        if (def != null) {
                                                return (ItemDef) def;
                                        }
                                }
                        }
                        return null;
                } catch (final ClassCastException e) {
                        return null;
                }
        }
}
