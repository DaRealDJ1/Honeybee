package SkHoneybee.Elements.Maps.Events;

import SkHoneybee.Events.Custom;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class mapClick extends SkriptEvent {

    static {
        Skript.registerEvent("mapClick", mapClick.class, Custom.class, "Map Click");
        EventValues.registerEventValue(Custom.class, Number.class, new Getter<Number, Custom>() {
            public Number get(Custom e) {
                return e.getId();
            }

        }, 0);
    }


    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event e) {
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "MapClick Event";
    }

}