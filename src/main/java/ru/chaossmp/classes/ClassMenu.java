package ru.chaossmp.classes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public final class ClassMenu {
    private final DiamondSMP plugin;

    public ClassMenu(DiamondSMP plugin) { this.plugin = plugin; }

    public void openMain(Player player) {
        if (plugin.getStore().get(player.getUniqueId()) != null) {
            openInfo(player);
            return;
        }
        Inventory inv = Bukkit.createInventory(new MenuHolder(MenuHolder.View.MAIN, null), 54,
                ChatColor.DARK_GRAY + "✦ " + ChatColor.LIGHT_PURPLE + "Выбор класса");
        fill(inv);
        int[] slots = {10, 12, 14, 16, 28, 30, 32};
        ClassType[] types = ClassType.values();
        for (int i = 0; i < types.length; i++) {
            ClassType type = types[i];
            inv.setItem(slots[i], item(type.icon(), type.color() + "✦ " + type.displayName(),
                    List.of(ChatColor.GRAY + type.shortDescription(), "", ChatColor.YELLOW + "Нажмите, чтобы посмотреть и выбрать")));
        }
        inv.setItem(49, item(Material.BOOK, ChatColor.YELLOW + "Как это работает",
                List.of(ChatColor.GRAY + "Выбор постоянный и сохраняется навсегда.", ChatColor.GRAY + "Сменить или сбросить класс может только администратор.")));
        player.openInventory(inv);
    }

    public void openConfirm(Player player, ClassType type) {
        Inventory inv = Bukkit.createInventory(new MenuHolder(MenuHolder.View.CONFIRM, type), 27,
                ChatColor.DARK_RED + "Подтверждение выбора");
        fill(inv);
        inv.setItem(4, item(type.icon(), type.coloredName(), List.of(ChatColor.GRAY + type.shortDescription(), "", ChatColor.WHITE + type.abilityDescription())));
        inv.setItem(11, item(Material.LIME_CONCRETE, ChatColor.GREEN + "✔ Подтвердить выбор",
                List.of(ChatColor.GRAY + "Класс будет закреплён за вами.")));
        inv.setItem(15, item(Material.RED_CONCRETE, ChatColor.RED + "✖ Отменить",
                List.of(ChatColor.GRAY + "Вернуться к списку классов.")));
        inv.setItem(22, item(Material.BARRIER, ChatColor.DARK_RED + "" + ChatColor.BOLD + "ВНИМАНИЕ! ВЫБОР НАВСЕГДА!",
                List.of(ChatColor.RED + "Выбранный класс нельзя будет самостоятельно изменить или сбросить.",
                        ChatColor.RED + "Сменить класс сможет только администратор сервера.")));
        player.openInventory(inv);
    }

    public void openInfo(Player player) {
        ClassType type = plugin.getStore().get(player.getUniqueId());
        if (type == null) { openMain(player); return; }
        Inventory inv = Bukkit.createInventory(new MenuHolder(MenuHolder.View.INFO, type), 27,
                ChatColor.DARK_GRAY + "Ваш класс");
        fill(inv);
        inv.setItem(13, item(type.icon(), type.coloredName(), List.of(ChatColor.GRAY + type.shortDescription(), "",
                ChatColor.WHITE + type.abilityDescription(), "", ChatColor.GREEN + "Ваш выбор сохранён навсегда.")));
        player.openInventory(inv);
    }

    private void fill(Inventory inv) {
        ItemStack pane = item(Material.GRAY_STAINED_GLASS_PANE, " ", List.of());
        for (int i = 0; i < inv.getSize(); i++) if (i < 9 || i >= inv.getSize() - 9 || i % 9 == 0 || i % 9 == 8) inv.setItem(i, pane);
    }

    private ItemStack item(Material material, String name, List<String> lore) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }
}
