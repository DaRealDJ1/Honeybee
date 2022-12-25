package SkHoneybee.elements.effects;

import SkHoneybee.MapManager;
import SkHoneybee.MapType;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class SetPixel extends Effect {
    static {
        Skript.registerEffect(SetPixel.class, "Set pixel [at] %number%, %number% [on] [map] %string% to [colour|color] %number%, %number%, %number%");
    }

    private Expression<Number> x;
    private Expression<Number> y;
    private Expression<String> name;
    private Expression<Number> r;
    private Expression<Number> b;
    private Expression<Number> g;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        this.x = (Expression<Number>) expressions[0];
        this.y = (Expression<Number>) expressions[1];
        this.name = (Expression<String>) expressions[2];
        this.r = (Expression<Number>) expressions[3];
        this.g = (Expression<Number>) expressions[4];
        this.b = (Expression<Number>) expressions[5];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "";//return "Kick player effect with expression player: " + player.toString(event, debug) + " and string expression: " + reason.toString(event, debug);
    }
    @Override
    protected void execute(Event event) {
        MapType mapType = MapManager.pixels.get(name.getSingle(event));
        Bukkit.broadcastMessage("editing " + mapType);
        mapType.setPixel(x.getSingle(event).intValue(), y.getSingle(event).intValue(), r.getSingle(event).intValue(), g.getSingle(event).intValue(), b.getSingle(event).intValue());
        mapType.queueRender();
    }

}
