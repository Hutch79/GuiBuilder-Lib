package ch.hutch79.guibuilder.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.Objects;

public class Placeholders {
    private static boolean isPlaceholderApiInstalled = false;

    public boolean isPlaceholderAPI() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            isPlaceholderApiInstalled = true;
        }
        return isPlaceholderApiInstalled;
    }

    protected String stringReplacePlaceholders(Player player, String input) {
        if (isPlaceholderApiInstalled) {
            return me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, input);
        }
        return input;
    }

    public Inventory replacePlaceholdersInInventory(Player player, Inventory inv) {

        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getContents()[i] == null) {
                continue;
            }

            if (!Objects.equals(inv.getContents()[i].getType(), Material.PLAYER_HEAD)) {
                ItemMeta itemMeta = inv.getContents()[i].getItemMeta();

                assert itemMeta != null;
                inv.getContents()[i].setItemMeta(itemMetaReplacePlaceholder(player, itemMeta));
                continue;
            }
            SkullMeta itemMeta = (SkullMeta) inv.getContents()[i].getItemMeta();

            assert itemMeta != null;
            inv.getContents()[i].setItemMeta(itemMetaReplacePlaceholder(player, itemMeta));
        }
        return inv;
    }

    public ItemMeta itemMetaReplacePlaceholder(Player player, ItemMeta itemMeta) {
        assert itemMeta != null;
        itemMeta.setDisplayName(stringReplacePlaceholders(player, itemMeta.getDisplayName()));
        List<String> itemLore = itemMeta.getLore();
        if (itemLore != null) {
            itemLore.replaceAll(input -> stringReplacePlaceholders(player, input));
        }
        return itemMeta;
    }
}
