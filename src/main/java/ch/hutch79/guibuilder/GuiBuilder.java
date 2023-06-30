package ch.hutch79.guibuilder;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class GuiBuilder {


    private static final HashMap<UUID, HashMap<String, Inventory>> playerGuis = new HashMap<>();
    private static final HashMap<String, StoreGui> guis = new HashMap<>();

    public void createGui(String name, int guiSize) {
        if (guis.containsKey(name)) {
            Bukkit.getLogger().warning("A GUI called '" + name + "' does already exist!");
            return;
        }
        ItemStack[] itemStack = new ItemStack[guiSize];
        guis.put(name, new StoreGui(name, guiSize, itemStack));
    }

    /**
     * Adds an Item in a given GUI on a give Slot.
     * @param gui Name of the GUI
     * @param slot slot where the item should be placed in
     * @param item Item to add on slot
     */
    public void setItem(String gui, int slot, ItemStack item) {
        guis.get(gui).getItems()[slot] = item;
    }

    /**
     * Set the Title which is seen on the very top of your GUI.
     * @param gui Name of GUI
     * @param title GUI Title (Visible in-game)
     */
    public void setTitle(String gui, String title) {
        guis.get(gui).setName(title);
    }


    /**
     * Opens a GUI for player.
     * If player already had the GUI opened, the same GUI will be loaded again
     * @param player Player to open the GUI
     * @param gui Which GUI to open
     */
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
