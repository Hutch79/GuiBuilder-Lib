package ch.hutch79.guibuilder;

import org.bukkit.inventory.ItemStack;


public class StoreGui {
    private String name;
    private final int size;
    private final ItemStack[] items;

    StoreGui(String name, int size, ItemStack[] items) {
        this.name = name;
        this.size = size;
        this.items = items;
    }

    protected String getName() {
        return this.name;
    }

    protected Integer getSize() {
        return this.size;
    }

    protected ItemStack[] getItems() {
        return this.items;
    }

    protected void setName (String newName) {
        this.name = newName;
    }
}
