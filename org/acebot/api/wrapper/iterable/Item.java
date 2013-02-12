package org.acebot.api.wrapper.iterable;


import org.acebot.api.methods.Nodes;
import org.acebot.core.bot.Bot;
import org.acebot.impl.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 12/2/12
 * Time: 3:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class Item {

        org.acebot.impl.Item item;

        public Item(org.acebot.impl.Item i) {
                item = i;
        }

        public org.acebot.impl.Item getItem() {
                return item;
        }

        public ItemDef getDefinition() {
                try {
                        final Node ref = Nodes.lookup(Bot.getClient().getItemDefLoader().getItemDefCache().getTable(), item.getId());
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

        public Model getModel() {
                try {
                        final Node ref = Nodes.lookup(Bot.getClient().getItemDefLoader().getModelCache().getTable(), item.getId());
                        if (ref == null) {
                                //	System.out.println("NULL REF FOR " + item.getId());
                        }
                        if (ref != null) {
                                //	System.out.println("Reference for " + item.getId() + " is valid!");
                                if (ref instanceof HardReference) {
                                        return ((Model) ((HardReference) ref).getReference());
                                } else if (ref instanceof SoftReference) {
                                        final Object def = ((SoftReference) ref).getReference().get();
                                        if (def != null) {
                                                return (Model) def;
                                        }
                                }
                        }
                        return null;
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
        }
}
