package SkHoneybee;

import org.bukkit.Bukkit;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapView;
import org.bukkit.util.io.BukkitObjectInputStream;

import java.awt.*;
import java.io.*;
import java.util.HashMap;

public class MapType implements Serializable {

    String name;
    MapView mapView;
    MapCanvas mapCanvas;
    Player player;
    ItemFrame itemFrame;
    HashMap<String, Color> pixels = new HashMap<>();
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ItemStack getItem() {
        ItemFrame e = itemFrame;
        ItemStack item = e.getItem();
        return item;
    }
    public HashMap<String, Color> getPixels() {
        return pixels;
    }
    public Color getPixel(int x, int y) {
        return pixels.get(x + "," + y);
    }
    public void setPixel(int x, int y, int r, int g, int b) {
        pixels.put(x + "," + y, new Color(r, g, b));
    }
    public void setPixels(HashMap<String, Color> pixels) {
        this.pixels = pixels;
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
    public MapType getMapType() {
        return this;
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
        MapType mapType = getMapType();
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 128; y++) {
                if (mapType.getPixel(x, y) != null) {
                    if (mapType.getPixel(x, y).getRed() < 2 && mapType.getPixel(x, y).getGreen() <2 && mapType.getPixel(x, y).getBlue() < 2) {
                        // set colour to -1
                        try {
                            mapCanvas.setPixelColor(x, y, new java.awt.Color(0, 0, 0, 0));
                        } catch (Exception ignored) {
                        }
                        continue;


                    }
                    mapCanvas.setPixelColor(x, y, mapType.getPixel(x, y));
                }
            }}}
}