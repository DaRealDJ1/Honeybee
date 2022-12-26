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

public class StopErrors extends Effect {
    public static Boolean stoperrors = false;
    static {
        Skript.registerEffect(StopErrors.class, "Turn errors %boolean%");
    }

    private Expression<Boolean> b;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        this.b = (Expression<Boolean>) expressions[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "";//return "Kick player effect with expression player: " + player.toString(event, debug) + " and string expression: " + reason.toString(event, debug);
    }
    @Override
    protected void execute(Event event) {
        Bukkit.broadcastMessage("Test tryna run");
        try {
            // loop through MapManager.pixels
            for (MapType mapType : MapManager.pixels.values()) {
                break;
            }} catch (Exception ignored) {}
        if (b.getSingle(event)) {
            stoperrors = true;
            // send warning to console
            Bukkit.getLogger().warning("SkHoneybee errors have been turnt off. This is not recommended.");
        } else {
            stoperrors = false;
            Bukkit.getLogger().warning("SkHoneybee errors have been turnt on. This is recommended.");
        }
    }
}
