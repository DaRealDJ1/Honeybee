package SkHoneybee;

import SkHoneybee.Events.mapEvent;
import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public final class Honey extends JavaPlugin {
    Honey instance;
    SkriptAddon addon;
    @Override
    public void onEnable() {
        instance = this;
        addon = Skript.registerAddon(this);
        // Plugin startup logic
        getLogger().info("Honeybee is now enabled!");
        getServer().getPluginManager().registerEvents(new mapEvent(), this);

        try {
            //This will register all our syntax for us. Explained below
            addon.loadClasses("SkHoneybee", "Elements");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // loop all files in the folder
        File f = new File("plugins/SkHoneybee/Maps");
        File[] matchingFiles = f.listFiles();
        for (File file : matchingFiles) {
            Manager manager = new Manager();
            try {
                manager.Load(file.getName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onDisable() {
        HashMap<String, Manager> maps = Manager.maps;
        for (String name : maps.keySet()) {
            Manager manager = maps.get(name);
            manager.Save();
        }
    }
}
