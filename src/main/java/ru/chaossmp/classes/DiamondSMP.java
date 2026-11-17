package ru.chaossmp.classes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

public final class DiamondSMP extends JavaPlugin implements CommandExecutor, TabCompleter {
    private ClassStore store;
    private ClassMenu menu;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        store = new ClassStore(this);
        menu = new ClassMenu(this);
        getServer().getPluginManager().registerEvents(new ClassListener(this), this);
        PluginCommand command = getCommand("class");
        if (command != null) {
            command.setExecutor(this);
            command.setTabCompleter(this);
        }
        getLogger().info("DiamondSMP 1.0.0 включён.");
    }

    @Override
    public void onDisable() {
        if (store != null) store.save();
    }

    public ClassStore getStore() { return store; }
    public ClassMenu getMenu() { return menu; }
    public boolean visualsEnabled() { return getConfig().getBoolean("visual.enabled", true); }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("Эту команду можно использовать только в игре.");
                return true;
            }
            menu.openMain(player);
            return true;
        }
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "info" -> {
                if (!(sender instanceof Player player)) { sender.sendMessage("Команда доступна игроку."); return true; }
                ClassType type = store.get(player.getUniqueId());
                if (type == null) {
                    player.sendMessage(ChatColor.YELLOW + "Вы ещё не выбрали класс. Используйте /class.");
                } else {
                    player.sendMessage(ChatColor.DARK_PURPLE + "✦ " + type.coloredName() + ChatColor.GRAY + " — " + type.shortDescription());
                    player.sendMessage(ChatColor.WHITE + type.abilityDescription());
                }
                return true;
            }
            case "reset" -> {
                if (!sender.hasPermission("diamondsmp.admin")) {
                    sender.sendMessage(ChatColor.RED + "Недостаточно прав.");
                    return true;
                }
                if (args.length < 2) { sender.sendMessage(ChatColor.RED + "Использование: /class reset <ник>"); return true; }
                org.bukkit.OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                if (!target.hasPlayedBefore() && !target.isOnline()) { sender.sendMessage(ChatColor.RED + "Игрок не найден: " + args[1]); return true; }
                if (store.get(target.getUniqueId()) == null) { sender.sendMessage(ChatColor.YELLOW + "У игрока нет выбранного класса."); return true; }
                store.set(target.getUniqueId(), null);
                sender.sendMessage(ChatColor.GREEN + "Класс игрока " + target.getName() + " сброшен.");
                if (target.isOnline() && target.getPlayer() != null)
                    target.getPlayer().sendMessage(ChatColor.YELLOW + "Ваш класс был сброшен администратором. Вы можете выбрать новый через /class.");
                return true;
            }
            case "reload" -> {
                if (!sender.hasPermission("diamondsmp.admin")) { sender.sendMessage(ChatColor.RED + "Недостаточно прав."); return true; }
                reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "Конфигурация DiamondSMP перезагружена.");
                return true;
            }
            default -> {
                sender.sendMessage(ChatColor.YELLOW + "Использование: /class [info|reset <ник>|reload]");
                return true;
            }
        }
    }

    @Override
    public java.util.List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) return java.util.List.of("info", "reset", "reload").stream()
                .filter(s -> s.startsWith(args[0].toLowerCase(Locale.ROOT))).toList();
        if (args.length == 2 && args[0].equalsIgnoreCase("reset") && sender.hasPermission("diamondsmp.admin"))
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(n -> n.toLowerCase(Locale.ROOT).startsWith(args[1].toLowerCase(Locale.ROOT))).toList();
        return java.util.Collections.emptyList();
    }
}
