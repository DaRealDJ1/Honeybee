package SkHoneybee.Elements.Expressions;

import SkHoneybee.MapManager;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class AllMaps extends SimpleExpression<String> {
    static {
        Skript.registerExpression(AllMaps.class, String.class, ExpressionType.SIMPLE, "Last [created] Map");
    }


    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "Last created map";
    }

    @Override
    protected String[] get(Event e) {
        String[] maps = new String[MapManager.pixels.size()];
        // using keySet() for iteration over keys
        int i = 0;
        for (String key : MapManager.pixels.keySet()) {
            maps[i] = key;
            i++;
        }
        return maps;
    }
}
