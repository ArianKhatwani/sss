package ru.chaossmp.classes;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public final class MenuHolder implements InventoryHolder {
    public enum View { MAIN, CONFIRM, INFO }
    private final View view;
    private final ClassType type;
    public MenuHolder(View view, ClassType type) { this.view = view; this.type = type; }
    public View view() { return view; }
    public ClassType type() { return type; }
    @Override public Inventory getInventory() { return null; }
}
