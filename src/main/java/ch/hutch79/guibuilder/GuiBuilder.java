package ch.hutch79.guibuilder;


import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class GuiBuilder {

    private static JavaPlugin pluginInstance;
    private static NamespacedKey key;

    public GuiBuilder(JavaPlugin javaPlugin) {
        pluginInstance = javaPlugin;
        key = new NamespacedKey(pluginInstance, "tag");
    }


    private static final HashMap<UUID, HashMap<String, Inventory>> playerInventorys = new HashMap<>();
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
     * Adds an Item in a given GUI on a give Slot with a tag.
     * @param gui Name of the GUI
     * @param slot slot where the item should be placed in
     * @param item Item to add on slot
     * @param tag Tag to clearly identify the Item trough PersistentDataContainers
     */
    public void setItem(String gui, int slot, ItemStack item, String tag) {
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, tag);
        item.setItemMeta(itemMeta);

        guis.get(gui).getItems()[slot] = item;
    }

    /**
     * Adds an Item in a given GUI on a give Slot with a tag.
     * @param gui Name of the GUI
     * @param slot slot where the item should be placed in
     * @param item Item to add on slot
     * @param tags Tags to clearly identify the Item trough PersistentDataContainers
     */
    public void setItem(String gui, int slot, ItemStack item, ArrayList<String> tags) {
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        NamespacedKey listKey;
        int i = 0;
        for (String s : tags) {
            i++;
            listKey = new NamespacedKey(pluginInstance, "tag" + i);
            itemMeta.getPersistentDataContainer().set(listKey, PersistentDataType.STRING, s);
        }

        item.setItemMeta(itemMeta);

        guis.get(gui).getItems()[slot] = item;
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
        if (playerInventorys.containsKey(uuid) && playerInventorys.get(uuid).containsKey(gui)) {
            player.openInventory(playerInventorys.get(uuid).get(gui));
            return;
        }

        Inventory inv = Bukkit.createInventory(player, guis.get(gui).getSize(), guis.get(gui).getName());
        inv.setContents(guis.get(gui).getItems());

        HashMap<String, Inventory> temp = new HashMap<>();
        temp.put(gui, inv);

        player.openInventory(inv);
        playerInventorys.put(uuid, temp);
    }

    /**
     * @param itemStack ItemStack from a GUI
     * @return Tag or empty ArrayList if no tag was found
     */
    public ArrayList<String> getTag(ItemStack itemStack) {
        PersistentDataContainer container = Objects.requireNonNull(itemStack.getItemMeta()).getPersistentDataContainer();
        ArrayList<String> tags = new ArrayList<>();
        if (container.has(key, PersistentDataType.STRING)) {
            tags.add(container.get(key, PersistentDataType.STRING));
            return tags;
        }

        NamespacedKey tagsKey;
        for (int i = 1; i <= 1000; i++) {
            tagsKey = new NamespacedKey(pluginInstance, "tag" + i);

            if (!container.has(tagsKey, PersistentDataType.STRING)) {
                return tags;
            }

            tags.add(container.get(tagsKey, PersistentDataType.STRING));
        }
        return tags;
    }

    /**
     * @return NamespaceKey for PersistentDataContainer
     */
    public NamespacedKey getNamespacedKey() {
        return key;
    }
}
