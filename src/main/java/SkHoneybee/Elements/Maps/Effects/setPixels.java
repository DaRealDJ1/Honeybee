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

public class setPixels extends Effect {
    static {
        Skript.registerEffect(setPixels.class, "Set pixels [between] %number%(,| )[ ]%number%[ and ]%number%(,| )[ ]%number% of [map] [named] %string% to %color%");
    }

    private Expression<Number> x;
    private Expression<Number> y;
    private Expression<Number> x2;
    private Expression<Number> y2;
    private Expression<String> name;
    private Expression<ColorRGB> colour;
    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        this.x = (Expression<Number>) expressions[0];
        this.y = (Expression<Number>) expressions[1];
        this.x2 = (Expression<Number>) expressions[2];
        this.y2 = (Expression<Number>) expressions[3];
        this.name = (Expression<String>) expressions[4];
        this.colour = (Expression<ColorRGB>) expressions[5];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "";//return "Kick player effect with expression player: " + player.toString(event, debug) + " and string expression: " + reason.toString(event, debug);
    }

    @Override
    protected void execute(Event event) {
        MapManager mapManager = MapManager.maps.get(this.name.getSingle(event));
        int r = this.colour.getSingle(event).asBukkitColor().getRed();
        int g = this.colour.getSingle(event).asBukkitColor().getGreen();
        int b = this.colour.getSingle(event).asBukkitColor().getBlue();
        Color colour = new Color(r, g, b);
        for (int x = this.x.getSingle(event).intValue(); x <= this.x2.getSingle(event).intValue(); x++) {
            for (int y = this.y.getSingle(event).intValue(); y <= this.y2.getSingle(event).intValue(); y++) {
                mapManager.SetPixel(x, y, colour);
            }
        }

    }
}
