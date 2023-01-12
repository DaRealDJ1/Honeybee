package SkHoneybee.Elements.Discord.Effects;

import SkHoneybee.BotManager;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class sendMessage extends Effect {
    static {
        Skript.registerEffect(sendMessage.class, "Send [message] %string% to [channel] %string% with [bot] %string%");
    }

    private Expression<String> message;
    private Expression<String> channel;
    private Expression<String> bot;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        this.message = (Expression<String>) expressions[0];
        this.channel = (Expression<String>) expressions[1];
        this.bot = (Expression<String>) expressions[2];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "";//return "Kick player effect with expression player: " + player.toString(event, debug) + " and string expression: " + reason.toString(event, debug);
    }

    @Override
    protected void execute(Event event) {
        BotManager botManager = BotManager.bots.get(bot.getSingle(event));
        botManager.sendMessage(message.getSingle(event), channel.getSingle(event));
    }
}
