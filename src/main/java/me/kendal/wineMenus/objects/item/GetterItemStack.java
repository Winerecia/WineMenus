package me.kendal.wineMenus.objects.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Logger;

public class GetterItemStack extends ItemStack {

    private static final Logger log = Logger.getLogger("GetterItem");

    public GetterItemStack(ItemStack original) {
        super(original); // копируем состояние
    }

    private void warn(String method) {
        log.warning("[GetterItem] Попытка изменить ItemStack через " + method);
        Thread.dumpStack(); // покажет стек вызова, кто лезет
    }

    @Override
    public void setType(Material type) {
        warn("setType");
        // ничего не делаем
    }

    @Override
    public void setAmount(int amount) {
        warn("setAmount");
        // ничего не делаем
    }

    @Override
    public void addUnsafeEnchantment(org.bukkit.enchantments.Enchantment ench, int level) {
        warn("addUnsafeEnchantment");
        // ничего не делаем
    }

    @Override
    public int removeEnchantment(Enchantment ench) {
        warn("removeEnchantment");
        // ничего не делаем
        return 0;
    }

    // ...и так далее для всех сеттеров
}
