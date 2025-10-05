package me.kendal.wineMenus.utils;

import com.google.gson.Gson;
import me.kendal.wineMenus.objects.Item;
import me.kendal.wineMenus.objects.SkinData;
import me.kendal.wineMenus.objects.item.GetterItemStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;

public class ItemUtils {
    private static final Map<Material, Item> cachedBackground = new EnumMap<>(Material.class);

    public static Item getBackground(Material material) {
        return cachedBackground.computeIfAbsent(material, mat -> {
            // Создаём защищённый ItemStack один раз
            ItemStack stack = new ItemStack(mat);
            ItemMeta meta = stack.getItemMeta();
            if (meta != null) {
                meta.displayName(Component.text(" "));
                meta.lore(Collections.emptyList());
                meta.addItemFlags(ItemFlag.values()); // скрыть всё
                stack.setItemMeta(meta);
            }

            // Оборачиваем в твой GetterItemStack — он защищает от изменений
            return new Item(new GetterItemStack(stack));
        });
    }
    public static Component mini(String string) {
        return MiniMessage.miniMessage().deserialize(string);
    }

    public static void toBG(Item item) {
            item.setName(mini(""));
    }

    private static SkinData decodeBase64(String url) {
        String json = new String(Base64.getDecoder().decode(url));

        Gson gson = new Gson();

        return gson.fromJson(json, SkinData.class);
    }

    public static ItemStack skullFromURL(String url) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();
        SkinData data = decodeBase64(url);
        try {
            URL skinUrl = URI.create(data.getTextures().getSkin().getUrl()).toURL();
            textures.setSkin(skinUrl);
        } catch (MalformedURLException e) {
            Bukkit.getLogger().severe("[WineSeller] Произошла ошибка при обработке текстуры головы.");
            Bukkit.getLogger().severe(data.getTextures().getSkin().getUrl());
            return item;
        }
        profile.setTextures(textures);
        meta.setOwnerProfile(profile);
        item.setItemMeta(meta);

        return item;
    }
}
