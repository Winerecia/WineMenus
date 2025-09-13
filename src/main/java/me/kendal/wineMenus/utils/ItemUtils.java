package me.kendal.wineMenus.utils;

import me.kendal.wineMenus.objects.Item;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

public class ItemUtils {
    private static final Map<Material, Item> cachedBackground = new EnumMap<>(Material.class);

    public static Item getBackground(Material material) {
        // достаём из кэша или создаём и кладём
        return cachedBackground.computeIfAbsent(material, mat -> {
            Item item = new Item(material);
            item.setName(Component.text(" ")); // пустое имя
            item.setLore(new ArrayList<>());   // пустая лора
            item.clearFlags();  // скрыть всё лишнее
            return item;
        }).clone(); // ⚠️ всегда отдаём КЛОН чтобы фон никто не испортил
    }

    public static Component mini(String string) {
        return MiniMessage.miniMessage().deserialize(string);
    }
}
