package SkHoneybee.elements.effects;

import SkHoneybee.MapManager;
import SkHoneybee.MapType;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Color;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class SetPixel extends Effect {
    static {
        Skript.registerEffect(SetPixel.class, "Set pixel [at] %number%, %number% [on] [of] [map] %string% to %color%");
    }

    private Expression<Number> x;
    private Expression<Number> y;
    private Expression<String> name;
    private Expression<ch.njol.skript.util.Color> rgb;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        this.x = (Expression<Number>) expressions[0];
        this.y = (Expression<Number>) expressions[1];
        this.name = (Expression<String>) expressions[2];
        this.rgb = (Expression<Color>) expressions[3];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "";//return "Kick player effect with expression player: " + player.toString(event, debug) + " and string expression: " + reason.toString(event, debug);
    }
    @Override
    protected void execute(Event event) {
        Color color = rgb.getSingle(event);
        int r = color.asBukkitColor().getRed();
        int g = color.asBukkitColor().getGreen();
        int b = color.asBukkitColor().getBlue();


        MapType mapType = MapManager.pixels.get(name.getSingle(event));
        Bukkit.broadcastMessage("editing " + mapType);
        mapType.setPixel(x.getSingle(event).intValue(), y.getSingle(event).intValue(), r, g, b);
        mapType.queueRender();
    }

}
