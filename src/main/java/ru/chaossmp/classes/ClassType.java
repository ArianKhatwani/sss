package ru.chaossmp.classes;

import org.bukkit.Material;
import org.bukkit.ChatColor;

public enum ClassType {
    BERSERK("Берсерк", ChatColor.RED, Material.IRON_AXE,
            "Чем меньше здоровья — тем опаснее.",
            "Урон растёт при потере здоровья; на низком здоровье появляется красная аура. Удары сопровождаются огнём, убийство даёт ускорение."),
    GUARDIAN("Страж", ChatColor.GOLD, Material.SHIELD,
            "Стойкость и накопленная защита.",
            "Постоянно снижает входящий урон. После периода без полученного урона заряжает щит: следующий удар дополнительно поглощается золотой вспышкой."),
    SHADOW("Тень", ChatColor.DARK_PURPLE, Material.ENDER_PEARL,
            "Уклонение и атаки из тени.",
            "Может автоматически уклониться от удара, наносит дополнительный урон атакующему со спины и получает краткую невидимость после убийства."),
    THUNDER("Громовержец", ChatColor.AQUA, Material.LIGHTNING_ROD,
            "Каждый третий удар вызывает разряд.",
            "Каждый третий успешный удар сопровождается электрическим разрядом, частицами и звуком. Во время бега вокруг игрока искрится энергия."),
    RANGER("Следопыт", ChatColor.GREEN, Material.BOW,
            "Точность на больших дистанциях.",
            "Выстрелы из лука наносят дополнительный урон на дальней дистанции. Поражённая цель ненадолго помечается свечением; убийства сопровождаются природными эффектами."),
    ALCHEMIST("Алхимик", ChatColor.DARK_GREEN, Material.BREWING_STAND,
            "Случайные эликсиры за победу.",
            "После убийства получает случайное положительное усиление: скорость, сила, регенерация, прыгучесть или сопротивление. Усиление сопровождается зелёными частицами и звуком."),
    RIFT("Разлом", ChatColor.LIGHT_PURPLE, Material.END_CRYSTAL,
            "Искажение пространства вокруг владельца.",
            "Может автоматически уклониться от урона. Прыжки оставляют портальный след, а убийства создают визуальную мини-чёрную дыру.");

    private final String displayName;
    private final ChatColor color;
    private final Material icon;
    private final String shortDescription;
    private final String abilityDescription;

    ClassType(String displayName, ChatColor color, Material icon, String shortDescription, String abilityDescription) {
        this.displayName = displayName;
        this.color = color;
        this.icon = icon;
        this.shortDescription = shortDescription;
        this.abilityDescription = abilityDescription;
    }

    public String displayName() { return displayName; }
    public ChatColor color() { return color; }
    public Material icon() { return icon; }
    public String shortDescription() { return shortDescription; }
    public String abilityDescription() { return abilityDescription; }
    public String coloredName() { return color + displayName; }

    public static ClassType fromId(String id) {
        if (id == null) return null;
        try { return valueOf(id.toUpperCase(java.util.Locale.ROOT)); }
        catch (IllegalArgumentException ex) { return null; }
    }
}
