package SkHoneybee.elements.expressions;

import SkHoneybee.MapManager;
import SkHoneybee.MapType;
import SkHoneybee.elements.effects.CreateMap;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class ItemByName extends SimpleExpression<ItemStack> {
    static {
        Skript.registerExpression(ItemByName.class, ItemStack.class, ExpressionType.SIMPLE, "Item from [Map] %-string%");
    }

    private Expression<String> name;
    @Override
    public Class<? extends ItemStack> getReturnType() {
        return ItemStack.class;
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
        return "Item from map";
    }

    @Override
    protected ItemStack[] get(Event e) {
        MapType map = MapManager.pixels.get(name.getSingle(e));
        if (map == null) return null;
        return new ItemStack[]{map.getItem()};
    }
}
