package SkHoneybee.Elements.Maps.Expressions;

import SkHoneybee.Events.Custom;
import SkHoneybee.MapManager;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class eventX extends SimpleExpression<Double> {
    static {
        Skript.registerExpression(eventX.class, Double.class, ExpressionType.SIMPLE, "event-x");
    }

    @Override
    public Class<? extends Double> getReturnType() {
        return Double.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "";//"bungee total of " + (this.server != null ? this.server.toString(event,debug) : " all servers");
    }

    @Override
    @Nullable
    protected Double[] get(Event e) {
        return new Double[]{Custom.eventX};
    }
}
