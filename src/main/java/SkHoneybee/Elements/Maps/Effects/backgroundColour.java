package SkHoneybee.Elements.Maps.Effects;

import SkHoneybee.MapManager;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.ColorRGB;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class backgroundColour extends Effect {
    static {
        Skript.registerEffect(backgroundColour.class, "set (background|full) [colour|color] of %string% to %color%");
    }

    private Expression<String> name;
    private Expression<ColorRGB> colour;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        this.name = (Expression<String>) expressions[0];
        this.colour = (Expression<ColorRGB>) expressions[1];
        return true;//ad
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "";//return "Kick player effect with expression player: " + player.toString(event, debug) + " and string expression: " + reason.toString(event, debug);
    }

    @Override
    protected void execute(Event event) {
        MapManager map = MapManager.maps.get(name.getSingle(event));
        // loop 127 times twice
        int r = colour.getSingle(event).asBukkitColor().getRed();
        int g = colour.getSingle(event).asBukkitColor().getGreen();
        int b = colour.getSingle(event).asBukkitColor().getBlue();
        Color color = new Color(r, g, b);
        for (int i = 0; i < 128; i++) {
            for (int j = 0; j < 128; j++) {
                map.SetPixel(i, j, color);
            }
        }

    }
}
