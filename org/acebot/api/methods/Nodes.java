package org.acebot.api.methods;


import org.acebot.impl.HashTable;
import org.acebot.impl.Node;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 12/2/12
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class Nodes {

        public static Node lookup(HashTable nc, long id) {
                try {
                        if (nc == null || nc.getBuckets() == null || id < 0) {
                                return null;
                        }
                        for (Node node : nc.getBuckets()) {
                                for (Node in = node.getNext(); in != null && !in.equals(node); in = in.getNext()) {
                                        try {
                                                if (in.getId() == id || in.getId() - 536870912 == id) {
                                                        return in;
                                                } else {
                                                        // System.out.println(id + " was found tho...");
                                                }
                                        } catch (Exception e) {
                                                System.out.println("Node Lookup Error");
                                        }
                                }
                        }
                } catch (Exception e) {
                        System.out.println("Node Lookup Error");
                }
                return null;
        }
}