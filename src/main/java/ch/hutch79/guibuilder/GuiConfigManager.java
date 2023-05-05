package ch.hutch79.guibuilder;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class GuiConfigManager{

    private static FileConfiguration customConfig;
    private static JavaPlugin mainClass;
    protected GuiConfigManager(JavaPlugin main) {
        mainClass = main;
    }

    private static HashMap<String, FileConfiguration> configs = new HashMap<String, FileConfiguration>();

    protected FileConfiguration getConfig(String name) {
        return configs.get(name);
    }

    protected void createConfig(String name, Boolean replace) {
        File customConfigFile = new File(mainClass.getDataFolder(), name);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            mainClass.saveResource(name, replace);
        }

        // YamlConfiguration customConfig = new YamlConfiguration();

        YamlConfiguration customConfig = YamlConfiguration.loadConfiguration(customConfigFile);

        configs.put(name, customConfig);

    }
}
