package SkHoneybee.elements.effects;

import SkHoneybee.MapManager;
import SkHoneybee.MapType;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class RemoveMap extends Effect {
    static {
        Skript.registerEffect(RemoveMap.class, "Remove map %string%");
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
        return "RemoveMap";//return "Kick player effect with expression player: " + player.toString(event, debug) + " and string expression: " + reason.toString(event, debug);
    }
    @Override
    protected void execute(Event event) {
        MapType mapManager = MapManager.pixels.get(name.getSingle(event));
        ItemFrame itemFrame = mapManager.getEntity();
        itemFrame.remove();
        MapManager.pixels.remove(name.getSingle(event));
    }

}
