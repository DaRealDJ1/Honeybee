package SkHoneybee.Elements;

import SkHoneybee.MapManager;
import SkHoneybee.MapType;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class removeMap extends Effect {
    static {
        Skript.registerEffect(removeMap.class, "Remove map %string%");
    }

    private Expression<String> name;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        this.name = (Expression<String>) expressions[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "";//return "Kick player effect with expression player: " + player.toString(event, debug) + " and string expression: " + reason.toString(event, debug);
    }
    @Override
    protected void execute(Event event) {
        Bukkit.broadcastMessage("NAME: " + name.getSingle(event));
        MapType mapManager = MapManager.pixels.get(name.getSingle(event));
        ItemFrame itemFrame = mapManager.getEntity();
        itemFrame.remove();
        Bukkit.broadcastMessage("FRAME: " + itemFrame);
        MapManager.pixels.remove(name.getSingle(event));
    }

}
