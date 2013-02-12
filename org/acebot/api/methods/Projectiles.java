package org.acebot.api.methods;


import org.acebot.api.wrapper.iterable.Projectile;
import org.acebot.core.bot.Bot;
import org.acebot.impl.NodeDeque;
import org.acebot.impl.ProjectileNode;
import org.acebot.util.Deque;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 12/19/12
 * Time: 1:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class Projectiles {

        public static Projectile[] getAll() {
                final List<Projectile> projectiles = new ArrayList<Projectile>();
                NodeDeque proDeque = Bot.getClient().getProjectiles();
                final Deque<ProjectileNode> pros = new Deque<ProjectileNode>(proDeque);
                for (ProjectileNode node = pros.getHead(); node != null; node = pros.getNext()) {
                        projectiles.add(new Projectile(node.getProjectile()));
                }
                return projectiles.toArray(new Projectile[]{});
        }
}
