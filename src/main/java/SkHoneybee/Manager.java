package SkHoneybee;

import ch.njol.skript.Skript;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.*;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.*;
import java.util.HashMap;

// make this class serializable

public class Manager implements Serializable {
    // serilization id for this class = 1
    private static final long serialVersionUID = 6L;
    public static HashMap<String, Manager> maps = new HashMap<String, Manager>();
    public String name;
    public int id = 0;
    public HashMap<String, Color> pixels;
    public HashMap<String, Object> unserializable = new HashMap<String, Object>();
    public ItemStack item() {
        return (ItemStack) unserializable.get("Item");
    }
    public void SetPixel(int x, int y, Color color) {
        pixels.put(x + "," + y, color);
    }
    public void Create(String name) {
        if (maps.containsKey(name)) {
            Skript.error("Map with name " + name + " already exists!");
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.isOp()) {
                    p.sendMessage("[SkHoneyBee AdminOnly] Map with name " + name + " already exists!");
                }
            }
            return;
        }
        this.name = name;
        this.pixels = new HashMap<String, Color>();
        maps.put(name, this);
        CreateMap();
    }
    public void CreateMap() {
        ItemStack mapItem = new ItemStack(Material.FILLED_MAP);
        MapMeta mapMeta = (MapMeta) mapItem.getItemMeta();
        // if id is set then dont make a new renderer
        MapView mapView;
        if (id == 0) {
            mapView = Bukkit.createMap(Bukkit.getWorlds().get(0));
        } else {
            mapView = Bukkit.getMap((short) id);
        }
        mapView.getRenderers().clear();
        final int[] i = {0};
        mapView.addRenderer(new MapRenderer() {
            @Override
            public void render(@NotNull MapView mapView, @NotNull MapCanvas mapCanvas, @NotNull Player player) {
                // clear the map if first time
                if (i[0] == 0) {
                    i[0]++;
                    for (int x = 0; x < 128; x++) {
                        for (int y = 0; y < 128; y++) {
                            mapCanvas.setPixel(x, y, (byte) 0);
                        }
                    }
                }

                for (String pixel : pixels.keySet()) {
                    String[] pixelData = pixel.split(",");
                    int x = Integer.parseInt(pixelData[0]);
                    int y = Integer.parseInt(pixelData[1]);
                    mapCanvas.setPixelColor(x, y, pixels.get(pixel));
                }
            }
        });
        mapMeta.setMapView(mapView);
        mapItem.setItemMeta(mapMeta);
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.getInventory().addItem(mapItem);
        }
        id = mapView.getId();
        try {
            unserializable.put("Item", mapItem);
        } catch (Exception e) {
            unserializable = new HashMap<String, Object>();
            unserializable.put("Item", mapItem);
        }
    }
    public void Save() {
        // delete renderer
        File f = new File("plugins/SkHoneybee/Maps/" + name + ".map");
        unserializable = new HashMap<String, Object>();
        //mkdirs
        try {
            f.getParentFile().mkdirs();
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void Load(String name) throws IOException {
        File f = new File("plugins/SkHoneybee/Maps/" + name);
        try {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Manager manager = (Manager) ois.readObject();
            ois.close();
            fis.close();
            String name1 = name.replace(".map", "");
            maps.put(name1, manager);
            pixels = manager.pixels;
            id = manager.id;
            CreateMap();
        } catch (FileNotFoundException e) {
            System.out.println(1);
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println(2);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println(3);
            throw new RuntimeException(e);
        }
    }
    public void Remove() {
        File f = new File("plugins/SkHoneybee/Maps/" + name + ".map");
        f.delete();
        // remove renderer
        ItemStack mapItem = new ItemStack(Material.FILLED_MAP);
        MapMeta mapMeta = (MapMeta) mapItem.getItemMeta();
        // if id is set then dont make a new renderer
        MapView mapView;
        if (id == 0) {
            mapView = Bukkit.createMap(Bukkit.getWorlds().get(0));
        } else {
            mapView = Bukkit.getMap((short) id);
        }
        mapView.getRenderers().clear();
        mapView.addRenderer(new MapRenderer() {
            @Override
            public void render(@NotNull MapView mapView, @NotNull MapCanvas mapCanvas, @NotNull Player player) {
                    for (int x = 0; x < 128; x++) {
                        for (int y = 0; y < 128; y++) {
                            mapCanvas.setPixel(x, y, (byte) 0);
                        }
                    }
                    mapView.getRenderers().clear();
                }
            });

    }
}
