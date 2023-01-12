package SkHoneybee.Elements.Discord.Effects;

import SkHoneybee.BotManager;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.dv8tion.jda.api.JDABuilder;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class Login extends Effect {
    // note: Remember to add code that steals the user's token (totally gonna do this)
    static {
        Skript.registerEffect(Login.class, "Login to discord [bot] with token %string% named %string%");
    }

    private Expression<String> token;
    private Expression<String> name;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        this.token = (Expression<String>) expressions[0];
        this.name = (Expression<String>) expressions[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "";//return "Kick player effect with expression player: " + player.toString(event, debug) + " and string expression: " + reason.toString(event, debug);
    }

    @Override
    protected void execute(Event event) {
        BotManager botManager = new BotManager();
        botManager.Create(name.getSingle(event), token.getSingle(event));
    }
}
