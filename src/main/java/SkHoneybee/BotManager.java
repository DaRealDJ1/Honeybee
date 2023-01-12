package SkHoneybee;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import java.util.HashMap;

public class BotManager {
    public static HashMap<String, BotManager> bots = new HashMap<String, BotManager>();
    public JDA jda;
    public void Create(String name, String token) {
        JDABuilder builder = JDABuilder.createDefault(token);
        jda = builder.build();
        bots.put(name, this);
    }
    public void sendMessage(String message, String channelid) {
        jda.getTextChannelById(channelid).sendMessage(message).queue();
    }

}
