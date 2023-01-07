package SkHoneybee.Elements.Effects;

import SkHoneybee.Manager;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Direction;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.nio.channels.FileChannel;
import java.util.HashMap;

public class renderMap extends Effect {
    static {
        Skript.registerEffect(createMap.class, "Render map [named] %string% at %location% facing %direction%");
    }

    private Expression<String> name;
    private Expression<Location> location;
    private Expression<Direction> direction;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        this.name = (Expression<String>) expressions[0];
        this.location = (Expression<Location>) expressions[1];
        this.direction = (Expression<Direction>) expressions[2];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "";//return "Kick player effect with expression player: " + player.toString(event, debug) + " and string expression: " + reason.toString(event, debug);
    }

    @Override
    protected void execute(Event event) {
        BlockFace face = BlockFace.valueOf(direction.getSingle(event).toString().toUpperCase());
        Location loc = location.getSingle(event).getBlock().getRelative(face).getLocation();
        String name = this.name.getSingle(event);
        World world = loc.getWorld();
        HashMap<String, Manager> maps = Manager.maps;
        Manager manager = maps.get(name);

        Entity map = world.spawnEntity(loc, EntityType.ITEM_FRAME);



    }

}
