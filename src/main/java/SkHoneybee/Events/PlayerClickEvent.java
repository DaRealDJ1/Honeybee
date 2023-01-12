package SkHoneybee.Events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class PlayerClickEvent implements Listener {
    @EventHandler
    public void onPlayerClick(org.bukkit.event.player.PlayerInteractEvent event) {
        Player player = event.getPlayer();
        // get map item that was clicked
        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.FILLED_MAP) {
            return;
        }
        // get map id
        int mapId = item.getDurability();
        // get clicked pixel coordinates
        Block block = event.getClickedBlock();
        int pixelX = block.getX() % 128;
        int pixelY = block.getY() % 128;

        // broadcast all valeus
        Bukkit.broadcastMessage("Map ID: " + mapId);
        Bukkit.broadcastMessage("Pixel X: " + pixelX);
        Bukkit.broadcastMessage("Pixel Y: " + pixelY);
    }
}
