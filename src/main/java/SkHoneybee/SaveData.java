package SkHoneybee;

import SkHoneybee.elements.effects.CreateMap;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapView;

import java.awt.*;
import java.awt.Color;
import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class SaveData {
    public static Boolean isLoading = true;
    public static HashMap<String, HashMap<String, Color>> loadPixels = new HashMap<>();
    public static void save() {
        // loop keys of pixels
        for (String key : MapManager.pixels.keySet()) {
            HashMap map = new HashMap<>();
            HashMap<String, Color> pixels = MapManager.pixels.get(key).getPixels();
            map.put("pixels", pixels);
            String entityid = MapManager.pixels.get(key).getEntity().getUniqueId().toString();
            map.put("entityid", entityid);
            // get map id
            MapView mapView = MapManager.pixels.get(key).getMapView();
            int mapid = mapView.getId();
            map.put("mapid", mapid);

// save to file
            FileOutputStream fos = null;
            ObjectOutputStream out = null;
            try {
                fos = new FileOutputStream("plugins/SkHoneybee/Maps/" + key + ".dat");
                out = new ObjectOutputStream(fos);
                out.writeObject(map);
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void load() {
        // get all files in folder
        File folder = new File("plugins/SkHoneybee/Maps/");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                // load file
                FileInputStream fis = null;
                ObjectInputStream in = null;
                try {
                    fis = new FileInputStream("plugins/SkHoneybee/Maps/" + file.getName());
                    in = new ObjectInputStream(fis);
                    HashMap hashmap = (HashMap) in.readObject();
                    in.close();
                    Entity entity = Bukkit.getEntity(UUID.fromString((String) hashmap.get("entityid")));
                    MapView mapView = Bukkit.getMap((int) hashmap.get("mapid"));
                    // kill entity create new map
                    BlockFace blockFace = entity.getFacing();
                    Location location = entity.getLocation();
                    World world = entity.getWorld();
                    entity.remove();
                    // use createmap for inspiration
                    Entity map = world.spawnEntity(location, EntityType.ITEM_FRAME);
                    map.setCustomName("Map");
                    map.setCustomNameVisible(false);
                    ItemFrame itemFrame = (ItemFrame) map;
                    Bukkit.createMap(world);
                    // use the map id
                    int id = mapView.getId();
                    ItemStack item = new ItemStack(Material.FILLED_MAP, 1, (short) id);
                    itemFrame.setItem(item);
                    itemFrame.setFacingDirection(blockFace);
                    itemFrame.setItem(item);
                    itemFrame.setRotation(Rotation.NONE);
                    itemFrame.setVisible(false);
                    itemFrame.setFixed(true);
                    itemFrame.setInvulnerable(true);
                    itemFrame.setGlowing(true);
                    itemFrame.setSilent(true);

                    mapView.addRenderer(new CreateMap.render());

                    //MapManager.pixels.put(file.getName(), (MapType) hashmap.get("pixels"));
                    loadPixels.put(file.getName(), (HashMap<String, Color>) hashmap.get("pixels"));

                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
        isLoading = false;
    }
}