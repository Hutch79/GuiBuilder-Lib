package ch.hutch79.guibuilder;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class GuiBuilder {

    private static JavaPlugin main;

    private static HashMap<Player, Inventory> playerGuis = new HashMap<>();
    private static HashMap<String, StoreGui> GUIs = new HashMap<>();
    private GuiConfigManager configManager;

    /**
     * @param mainClass Main Plugin Instance
     */
    public void GuiBuilderInit(JavaPlugin mainClass) {
        main = mainClass;
        configManager = new GuiConfigManager(main);
        Bukkit.getConsoleSender().sendMessage(String.valueOf(main));
    }

    /**
     * @param name Name of the Config file with a file extension
     * @param replace Copy Default config on first create?
     */
    public void createConfig(String name, boolean replace) {
        configManager.createConfig(name, replace);
    }

    /**
     * @param name Name of the Config file with a file extension
     * @return List of all found GUIs in given config file
     */
    public List<String> readConfig(String name) {
        FileConfiguration guiConfig = configManager.getConfig(name);
        Bukkit.getConsoleSender().sendMessage("GuiBuilder!!!!!");

        Set<String> guiSet = guiConfig.getConfigurationSection("layout").getKeys(false);

        List<String> guiIDList = new ArrayList<>(guiSet.size());
        guiIDList.addAll(guiSet);

        List<Inventory> guiList = null;

        for (int i = 0; i < guiIDList.size(); i++) { // Going through all GUIs

            int guiSize = guiConfig.getInt("layout." + guiIDList.get(i) + ".rowCount");
            guiSize = guiSize * 9;
            String guiName = guiConfig.getString("layout." + guiIDList.get(i) + ".name");

            Set<String> itemSet = guiConfig.getConfigurationSection("layout." + guiIDList.get(i) + ".slots").getKeys(false);
            List<String> itemList = new ArrayList<>(itemSet.size());
            itemList.addAll(itemSet);

            ItemStack[] itemStack = new ItemStack[guiSize];

            for (int j = 0; j < itemList.size(); j++) { // Going through all configured Items

                itemSet = guiConfig.getConfigurationSection("layout." + guiIDList.get(i) + ".slots." + itemList.get(j)).getKeys(true);
                List<String> itemList2 = new ArrayList<>(itemSet.size());
                itemList2.addAll(itemSet);
                Bukkit.getConsoleSender().sendMessage("Hololulu");
                Bukkit.getConsoleSender().sendMessage(String.valueOf(itemList2));



                ItemStack item = new ItemStack(Material.STONE);
                ItemMeta itemMeta = item.getItemMeta();

                for (String s : itemList2) {

                    Bukkit.getConsoleSender().sendMessage(s);
                    Bukkit.getConsoleSender().sendMessage(guiConfig.getString("layout." + guiIDList.get(i) + ".slots." + itemList.get(j) + "." + s));



                    if (Objects.equals(s, "item")) {item.setType(Material.valueOf(guiConfig.getString("layout." + guiIDList.get(i) + ".slots." + itemList.get(j) + "." + s)));}
                    if (Objects.equals(s, "name")) {itemMeta.setDisplayName(guiConfig.getString("layout." + guiIDList.get(i) + ".slots." + itemList.get(j) + "." + s));}


                }

                item.setItemMeta(itemMeta);
                itemStack[Integer.parseInt(itemList.get(j))] = item;


            }
            GUIs.put(guiIDList.get(i), new StoreGui(guiName, guiSize, itemStack));
        }


        return guiIDList;
    }

    /**
     * @param player Player which should see the GUI
     * @param gui Name of the GUI inside the config file [layout.XXX]
     */
    public void openGUI(Player player, String gui) {
        if (playerGuis.containsKey(player)) {
            player.sendMessage("if");
            player.sendMessage("playerGuis.get(player)");
            player.openInventory(playerGuis.get(player));
        }
        else {

            Inventory inv = Bukkit.createInventory(player, GUIs.get("main").getSize(), GUIs.get("main").getName());
            inv.setContents(GUIs.get(gui).getItems());
            playerGuis.put(player, inv);

            player.openInventory(inv);
        }
    }
}
