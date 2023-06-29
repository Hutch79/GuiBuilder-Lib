package ch.hutch79.guibuilder;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class GuiBuilder {

    private static JavaPlugin main;

    private static HashMap<UUID, HashMap<String, Inventory>> playerGuis = new HashMap<UUID, HashMap<String, Inventory>>();
    private static HashMap<String, StoreGui> guis = new HashMap<>();

    /**
     * @param mainClass Main Plugin Instance
     */
    public void GuiBuilderInit(JavaPlugin mainClass) {
        main = mainClass;
        Bukkit.getConsoleSender().sendMessage(String.valueOf(main));
    }

    public void createGui(String name, int guiSize) {
        ItemStack[] itemStack = new ItemStack[guiSize];
        guis.put(name, new StoreGui(name, guiSize, itemStack));
    }

    public void addItem(String gui, int slot, ItemStack item) {
        guis.get(gui).getItems()[slot] = item;
    }

    public void setName(String gui, String name) {
        guis.get(gui).setName(name);
    }


    public void openGui(Player player, String gui) {
        UUID uuid = player.getUniqueId();
        if (playerGuis.containsKey(uuid) && playerGuis.get(uuid).containsKey(gui)) {
            player.openInventory(playerGuis.get(uuid).get(gui));
            return;
        }

        Inventory inv = Bukkit.createInventory(player, guis.get(gui).getSize(), guis.get(gui).getName());
        inv.setContents(guis.get(gui).getItems());

        HashMap<String, Inventory> temp = new HashMap<>();
        temp.put(gui, inv);

        player.openInventory(inv);
        playerGuis.put(uuid, temp);
    }
}
