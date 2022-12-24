package SkHoneybee;

import org.bukkit.Bukkit;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapView;

import java.awt.*;
import java.util.HashMap;

public class MapType {

    String name;
    MapView mapView;
    MapCanvas mapCanvas;
    Player player;
    ItemFrame itemFrame;
    public static HashMap<String, Color> pixels = new HashMap<>();
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public HashMap<String, Color> getPixels() {
        return pixels;
    }
    public static Color getPixel(int x, int y) {
        return pixels.get(x + "," + y);
    }
    public static void setPixel(int x, int y, int r, int g, int b) {
        pixels.put(x + "," + y, new Color(r, g, b));
    }
    public static void setPixel(int x, int y, Color color) {
        pixels.put(x + "," + y, color);
    }
    public MapView getMapView() {
        return mapView;
    }
    public void setMapView(MapView mapView) {
        this.mapView = mapView;
    }
    public void setMapCanvas(MapCanvas mapCanvas) {
        this.mapCanvas = mapCanvas;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public MapCanvas getCanvas(Player player) {
        return this.mapCanvas;
    }
    public void setEntity(ItemFrame itemFrame) {
        this.itemFrame = itemFrame;
    }
    public ItemFrame getEntity() {
        return this.itemFrame;
    }

    // render
    public void queueRender() {
        HashMap<String, MapType> renderQueue = MapManager.renderQueue;
        renderQueue.put(name, this);

    }
    public void render() {
        MapView mapView = getMapView();
        MapCanvas mapCanvas = this.mapCanvas;
        Player player = this.player;
        // Rgb check for 0,0,0
        Boolean q = false;
        //clear canvas to no background no colour or anything
        mapView.getRenderers().clear();
        MapType mapType = this;
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 128; y++) {
                if (mapType.getPixel(x, y) != null) {
                    if (mapType.getPixel(x, y).getRed() < 2 && mapType.getPixel(x, y).getGreen() <2 && mapType.getPixel(x, y).getBlue() < 2) {
                        // set colour to -1
                        mapCanvas.setPixelColor(x, y, new java.awt.Color(0, 0, 0, 0));
                        continue;


                    }
                    mapCanvas.setPixelColor(x, y, mapType.getPixel(x, y));
                }
            }}}

    public void remove() {
        HashMap<String, MapType> pixels = MapManager.pixels;
        pixels.remove(name);
    }
}
