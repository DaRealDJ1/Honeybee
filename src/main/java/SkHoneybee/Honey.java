package SkHoneybee;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class Honey extends JavaPlugin {
    Honey instance;
    SkriptAddon addon;
    @Override
    public void onEnable() {
        instance = this;
        addon = Skript.registerAddon(this);
        // Plugin startup logic
        getLogger().info("Honeybee is now enabled!");

        try {
            //This will register all our syntax for us. Explained below
            addon.loadClasses("SkHoneybee", "Elements");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 1 second bukkit schedular
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                MapManager.render();
            }
        }, 0L, 1L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
