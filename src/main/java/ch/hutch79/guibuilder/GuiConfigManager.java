package ch.hutch79.guibuilder;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class GuiConfigManager {
    private final JavaPlugin pluginInstance;
    public GuiConfigManager(JavaPlugin javaPlugin) {

        pluginInstance = javaPlugin;
    }

    public void configReader(String guiName, YamlConfiguration yamlConfiguration, String path) {



    }

}
