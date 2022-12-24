package SkHoneybee.elements.expressions;

import SkHoneybee.elements.effects.CreateMap;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class LastMap extends SimpleExpression<String> {
    static {
        Skript.registerExpression(LastMap.class, String.class, ExpressionType.SIMPLE, "Last [Created] map");
    }


    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
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
        return "Last created map";
    }

    @Override
    protected String[] get(Event e) {
        return new String[]{CreateMap.lastmap};
    }
}
