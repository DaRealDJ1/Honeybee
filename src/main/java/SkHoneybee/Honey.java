package SkHoneybee;

import SkHoneybee.Events.PlayerClickEvent;
import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.entity.Player;
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
        this.getServer().getPluginManager().registerEvents(new PlayerClickEvent(), this);

        try {
            //This will register all our syntax for us. Explained below
            addon.loadClasses("SkHoneybee", "Elements");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // loop all files in the folder
        File f = new File("plugins/SkHoneybee/Maps");
        File[] matchingFiles = f.listFiles();
        try {
            for (File file : matchingFiles) {
                MapManager mapManager = new MapManager();
                try {
                    mapManager.Load(file.getName());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("No maps found");
        }
    }

    @Override
    public void onDisable() {
        HashMap<String, MapManager> maps = MapManager.maps;//
        for (String name : maps.keySet()) {
            MapManager mapManager = maps.get(name);
            mapManager.Save();
        }
    }
}
