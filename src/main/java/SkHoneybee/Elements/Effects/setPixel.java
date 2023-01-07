package SkHoneybee.Elements.Effects;

import SkHoneybee.Manager;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.data.BukkitClasses;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Direction;
import ch.njol.skript.util.SkriptColor;
import ch.njol.util.Kleenean;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.HashMap;

public class setPixel extends Effect {
    static {
        Skript.registerEffect(setPixel.class, "Set pixel [at] %number%, %number% of [map] [named] %string% to %color%");
    }

    private Expression<Number> x;
    private Expression<Number> y;
    private Expression<String> name;
    private Expression<SkriptColor> color;
    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        this.x = (Expression<Number>) expressions[0];
        this.y = (Expression<Number>) expressions[1];
        this.name = (Expression<String>) expressions[2];
        this.color = (Expression<SkriptColor>) expressions[3];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "";//return "Kick player effect with expression player: " + player.toString(event, debug) + " and string expression: " + reason.toString(event, debug);
    }

    @Override
    protected void execute(Event event) {
        Manager manager = Manager.maps.get(this.name.getSingle(event));
        int r = this.color.getSingle(event).asBukkitColor().getRed();
        int g = this.color.getSingle(event).asBukkitColor().getGreen();
        int b = this.color.getSingle(event).asBukkitColor().getBlue();
        Color colour = new Color(r, g, b);
        manager.SetPixel(this.x.getSingle(event).intValue(), this.y.getSingle(event).intValue(), colour);

    }
}
