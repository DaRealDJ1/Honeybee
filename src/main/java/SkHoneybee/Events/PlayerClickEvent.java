package SkHoneybee.Events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class PlayerClickEvent implements Listener {
    // on right click on item frame
    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Interact(e.getPlayer(), e.getClickedBlock(), null);
    }
    @EventHandler
    public void onRightClick2(PlayerInteractEntityEvent e) {
        Interact(e.getPlayer(), null, e.getRightClicked());
    }

    public void Interact(Player p, Block b, Entity e) {
        // if b is null
        ItemFrame frame = null;
        if (b != null) {
            Bukkit.broadcastMessage("E222");
            // get block
            Block block = b;
            // get target block
            RayTraceResult target = p.rayTraceBlocks(3.5);

            BlockFace facing = target.getHitBlockFace();
            // get item frame at target block ~ facing
            Location l2 = target.getHitBlock().getLocation().add(facing.getDirection());
            // get item frame at target block ~ facing
            // draw end rod at l2 particle
            // use particle
            Particle particle = Particle.END_ROD;
            // loop entities in radius 4 of player
            for (Entity entity : p.getNearbyEntities(5, 5, 5)) {
                // if block at entity is block at l2
                if (entity.getLocation().getBlock().equals(l2.getBlock())) {
                    // if entity is item frame
                    if (entity instanceof ItemFrame) {
                        // set frame to entity
                        frame = (ItemFrame) entity;
                        break;
                    }
                }
            }
        } else {
            frame = (ItemFrame) e;
        }
        RayTraceResult target = p.rayTraceBlocks(3.5);
        // get x/z from corner of block at frame to target
        double x = target.getHitPosition().getX() - frame.getLocation().getBlock().getX();
        double z = target.getHitPosition().getZ() - frame.getLocation().getBlock().getZ();
        ItemStack item = frame.getItem();
        // get tag "map" from item
        // durability != map id
        // get map id from item
        int id = 0;
        // get map from id
        id = item.getDurability();
        String name = SkHoneybee.MapManager.mapsById.get(id);
        Bukkit.broadcastMessage("Frame: " + frame);
        Bukkit.broadcastMessage("ID: " + id);
        Bukkit.broadcastMessage("ITEM: " + item);
        // get map-id
        Bukkit.getPluginManager().callEvent(new Custom(p, x, z, name, id));
    }
}
