package SkHoneybee.Elements.Maps.Expressions;

import SkHoneybee.MapManager;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.ArrayList;


public class drawCircle extends SimpleExpression<Vector> {
    static {
        Skript.registerExpression(drawCircle.class, Vector.class, ExpressionType.SIMPLE, "Circle [with] radius %number%");
    }
    private Expression<Number> server;

    @Override
    public Class<? extends Vector> getReturnType() {
        return Vector.class;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        server = (Expression<Number>) exprs[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "drawCircle effect";
    }

    @Override
    @Nullable
    protected Vector[] get(Event e) {
        int radius = server.getSingle(e).intValue();
        // loop radius times
        ArrayList<Vector> vectors = new ArrayList<>();
        int i = 0;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (x * x + z * z <= radius * radius) {
                    vectors.add(new Vector(x, 0, z));
                    i++;
                }
            }
        }
        return vectors.toArray(new Vector[0]);

    }
}