package SkHoneybee.elements.effects;

import SkHoneybee.MapManager;
import SkHoneybee.MapType;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.util.Direction;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.*;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class CreateMap extends Effect {
    public static String lastmap = "Invalid";
    static {
        Skript.registerEffect(CreateMap.class, "create map at %location% named %string% [With [background] [colour|color] %number%, %number%, %number%] facing %direction%");
    }

    private Expression<Location> location;
    private Expression<String> string;

    private Expression<Number> r;
    private Expression<Number> g;
    private Expression<Number> b;

    private Expression<Direction> direction;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
        this.location = (Expression<Location>) expressions[0];
        this.string = (Expression<String>) expressions[1];
        this.r = (Expression<Number>) expressions[2];
        this.g = (Expression<Number>) expressions[3];
        this.b = (Expression<Number>) expressions[4];
        this.direction = (Expression<Direction>) expressions[5];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "";//return "Kick player effect with expression player: " + player.toString(event, debug) + " and string expression: " + reason.toString(event, debug);
    }
    private class render extends MapRenderer {
        @Override
        public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
            HashMap<String, MapType> pixels = MapManager.pixels;
            for (String key : pixels.keySet()) {
                if (pixels.get(key).getMapView() == mapView) {
                    MapType mapType = pixels.get(key);
                    mapType.setMapCanvas(mapCanvas);
                    mapType.setPlayer(player);
                    mapType.render();
                }
            }
        }
    }


    @Override
    protected void execute(Event event) {
        if (MapManager.pixels.containsKey(string.getSingle(event))) {
            Skript.error("Map already exists cannot override!" + " NAME: " + string.getSingle(event));
            return;
        }
        BlockFace face = BlockFace.valueOf(direction.getSingle(event).toString().toUpperCase());
        // offset loc by 1 block in direction
        Location loc = location.getSingle(event).getBlock().getRelative(face).getLocation();
        World world = loc.getWorld();
        // use methods in Renderer class to create a map
        Entity map = world.spawnEntity(loc, EntityType.ITEM_FRAME);
        map.setCustomName("Map");
        map.setCustomNameVisible(false);
        ItemFrame itemFrame = (ItemFrame) map;


        // make an empty map and initilise through mapEvent
        ItemStack mapItem = new ItemStack(Material.FILLED_MAP);
        MapMeta mapMeta = (MapMeta) mapItem.getItemMeta();
        MapView mapView = Bukkit.createMap(world);
        mapView.getRenderers().clear();
        mapView.addRenderer(new render());
        mapMeta.setMapView(mapView);
        mapItem.setItemMeta(mapMeta);

        // set the map to the item frame
        itemFrame.setItem(mapItem);
        itemFrame.setRotation(Rotation.NONE);
        itemFrame.setVisible(false);
        itemFrame.setFixed(true);
        itemFrame.setInvulnerable(true);
        itemFrame.setGlowing(true);
        itemFrame.setSilent(true);

        // set 1000 random pixels to a random color
        // check if map already exists
        MapType mapType = new MapType();
        mapType.setEntity((ItemFrame) map);
        mapType.setName(string.getSingle(event));
        mapType.setMapView(mapView);
        // set pixels x 0-127, y 0-127 to R, G, B
        if (!r.equals(1) && !g.equals(1) && !b.equals(1)) {
            for (int x = 0; x < 128; x++) {
                for (int y = 0; y < 128; y++) {
                    mapType.setPixel(x, y, r.getSingle(event).intValue(), g.getSingle(event).intValue(), b.getSingle(event).intValue());
                }
            }
        }
        MapManager.pixels.put(string.getSingle(event), mapType);
        lastmap = string.getSingle(event);
        // render the map
        mapType.render();
    }
}