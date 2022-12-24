package SkHoneybee;

import org.bukkit.Bukkit;
import org.bukkit.map.MapView;
import org.w3c.dom.css.RGBColor;

import java.util.HashMap;

public class MapManager {
    public static HashMap<String, MapType> pixels = new HashMap<>();
    public static HashMap<String, MapType> renderQueue = new HashMap<>();

    public static void render() {
        for (String key : renderQueue.keySet()) {
            MapType mapType = renderQueue.get(key);
            mapType.render();
            renderQueue.remove(key);
        }
    }
}
