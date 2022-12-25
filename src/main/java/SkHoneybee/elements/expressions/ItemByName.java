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
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class ItemByName extends SimpleExpression<ItemStack> {
    static {
        Skript.registerExpression(ItemByName.class, ItemStack.class, ExpressionType.SIMPLE, "Item from %string%");
    }
    private Expression<String> server;

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
        server = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "bungee total of " + (this.server != null ? this.server.toString(event,debug) : " all servers");
    }

    @Override
    @Nullable
    protected ItemStack[] get(Event e) {
        MapType mapType = MapManager.pixels.get(server.getSingle(e));
        if (mapType == null) {
            return null;
        }
        return new ItemStack[]{mapType.getItem()};
    }
}
