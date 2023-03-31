package de.blu.bukkit.feature.itemstackbuilder.enchantment;

import io.papermc.paper.enchantments.EnchantmentRarity;
import java.lang.reflect.Field;
import java.util.Set;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class GlowEnchantment extends Enchantment {

  public GlowEnchantment(NamespacedKey i) {
    super(i);
    // TODO Auto-generated constructor stub
  }

  public static void register(JavaPlugin javaPlugin) {
    try {
      Field f = Enchantment.class.getDeclaredField("acceptingNew");
      f.setAccessible(true);
      f.set(null, true);
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      NamespacedKey key = new NamespacedKey(javaPlugin, "ItemStackBuilder");

      GlowEnchantment glowEnchantment = new GlowEnchantment(key);
      Enchantment.registerEnchantment(glowEnchantment);
    } catch (IllegalArgumentException ignored) {
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean canEnchantItem(ItemStack arg0) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public @NotNull Component displayName(int level) {
    return Component.text("glowEnchantment");
  }

  @Override
  public boolean isTradeable() {
    return false;
  }

  @Override
  public boolean isDiscoverable() {
    return false;
  }

  @Override
  public @NotNull EnchantmentRarity getRarity() {
    return EnchantmentRarity.RARE;
  }

  @Override
  public float getDamageIncrease(int level, @NotNull EntityCategory entityCategory) {
    return 0;
  }

  @Override
  public @NotNull Set<EquipmentSlot> getActiveSlots() {
    return null;
  }

  @Override
  public boolean conflictsWith(Enchantment arg0) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public EnchantmentTarget getItemTarget() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int getMaxLevel() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public @NotNull String getName() {
    // TODO Auto-generated method stub
    return "glowEnchantment";
  }

  @Override
  public int getStartLevel() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean isCursed() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isTreasure() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public @NotNull String translationKey() {
    return "glowEnchantment";
  }
}
