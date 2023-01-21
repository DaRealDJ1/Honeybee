package SkHoneybee.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class Custom extends Event {
    public static Double eventX = 0D;
    public static Double eventY = 0D;
    public static Double eventID = 0D;
    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private Number x;
    private Number y;
    private String name;
    private Number id;

    public Custom(Player player, Number x, Number y, String name, Number id) {
        this.player = player;
        this.x = x;
        this.y = y;
        this.name = name;
        this.id = id;
        eventX = x.doubleValue() * 128;
        eventY = y.doubleValue() * 128;
        eventID = id.doubleValue() * 128;
    }


    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
    public Player getPlayer() {
        return player;
    }
    public Number getX() {
        return x;
    }
    public Number getY() {
        return y;
    }
    public String getName() {
        return name;
    }
    public Number getId() {
        return id;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
