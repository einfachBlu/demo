package de.blu.bukkit.feature.itemstackbuilder.builder;

import de.blu.bukkit.feature.itemstackbuilder.ItemStackBuilderFeature;
import de.blu.bukkit.feature.itemstackbuilder.enchantment.GlowEnchantment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.persistence.PersistentDataType;

@Getter
@Setter
public class ItemStackBuilder {

  protected NamespacedKey namespacedKey = new NamespacedKey(
      ItemStackBuilderFeature.getInstance().getJavaPlugin(), "ItemStackBuilder");
  protected ItemStack itemStack;
  protected ItemMeta itemMeta;

  public ItemStackBuilder() {
    this.itemStack = new ItemStack(Material.AIR);
    this.itemMeta = this.getItemStack().getItemMeta();
  }

  public ItemStackBuilder(ItemStack itemStack) {
    this.itemStack = itemStack;
    this.itemMeta = itemStack.getItemMeta();
  }

  public static ItemStackBuilder air() {
    return new ItemStackBuilder().type(Material.AIR);
  }

  public static BookItemStackBuilder book() {
    return new BookItemStackBuilder();
  }

  public static BookItemStackBuilder book(boolean writeable) {
    return new BookItemStackBuilder().writeable(writeable);
  }

  public static PotionItemStackBuilder potion() {
    return new PotionItemStackBuilder();
  }

  public static DefaultsItemStackBuilder defaults() {
    return new DefaultsItemStackBuilder();
  }

  public static LeatherArmorItemStackBuilder armor() {
    return new LeatherArmorItemStackBuilder();
  }

  public static LeatherArmorItemStackBuilder armor(
      LeatherArmorItemStackBuilder.ArmorElement armorElement) {
    return (LeatherArmorItemStackBuilder) new LeatherArmorItemStackBuilder().type(
        armorElement.getMaterial());
  }

  public static SkullItemStackBuilder skull() {
    return new SkullItemStackBuilder();
  }

  public static SkullItemStackBuilder skull(Material material) {
    return (SkullItemStackBuilder) new SkullItemStackBuilder().type(material);
  }

  public static ItemStackBuilder normal(Material material) {
    return new ItemStackBuilder().type(material);
  }

  public static ItemStackBuilder normal(Material material, int amount) {
    return new ItemStackBuilder().type(material).amount(amount);
  }

  public static ItemStackBuilder normal(Material material, int amount, short durability) {
    return new ItemStackBuilder().type(material).amount(amount).durability(durability);
  }

  public static ItemStackBuilder wrap(ItemStack itemStack) {
    if (itemStack.getType().equals(Material.SKELETON_SKULL) || itemStack.getType()
        .equals(Material.WITHER_SKELETON_SKULL) || itemStack.getType().equals(Material.PLAYER_HEAD)
        || itemStack.getType().equals(Material.CREEPER_HEAD) || itemStack.getType()
        .equals(Material.DRAGON_HEAD) || itemStack.getType().equals(Material.ZOMBIE_HEAD)) {
      SkullItemStackBuilder skullItemStackBuilder = new SkullItemStackBuilder();
      skullItemStackBuilder.setItemStack(itemStack);
      skullItemStackBuilder.setItemMeta(itemStack.getItemMeta());
      return skullItemStackBuilder;
    }

    ItemStackBuilder builder = new ItemStackBuilder();
    builder.setItemStack(itemStack);
    builder.setItemMeta(itemStack.getItemMeta());
    return builder;
  }

  public static ItemStackBuilder copy(ItemStack itemStack) {
    return ItemStackBuilder.wrap(itemStack.clone());
  }

  public ItemStack build() {
    this.getItemStack().setItemMeta(this.getItemMeta());

    if (!(this.itemStack instanceof CraftItemStack)) {
      this.itemStack = CraftItemStack.asCraftCopy(this.itemStack);
    }

    return this.getItemStack();
  }

  public Material type() {
    return this.getItemStack().getType();
  }

  public ItemStackBuilder type(Material material) {
    this.getItemStack().setType(material);
    this.setItemMeta(this.getItemStack().getItemMeta());
    return this;
  }

  public int amount() {
    return this.getItemStack().getAmount();
  }

  public ItemStackBuilder amount(int amount) {
    this.getItemStack().setAmount(amount);
    return this;
  }

  public short durability() {
    return this.getItemStack().getDurability();
  }

  public ItemStackBuilder durability(short durability) {
    this.getItemStack().setDurability(durability);
    return this;
  }

  public MaterialData data() {
    return this.getItemStack().getData();
  }

  public ItemStackBuilder data(MaterialData data) {
    this.getItemStack().setData(data);
    return this;
  }

  public int customModelData() {
    if (this.getItemMeta() == null) {
      return 0;
    }

    return this.getItemMeta().getCustomModelData();
  }

  public ItemStackBuilder customModelData(int customModelData) {
    if (this.getItemMeta() == null) {
      return this;
    }

    this.getItemMeta().setCustomModelData(customModelData);
    this.build();
    return this;
  }

  public ItemStackBuilder displayName(String displayName) {
    if (this.getItemMeta() == null) {
      return this;
    }

    this.getItemMeta().setDisplayName(displayName);
    this.build();
    return this;
  }

  public String displayName() {
    return this.getItemMeta().getDisplayName();
  }

  public Component loreEntry(int index) {
    if (this.getItemMeta() == null) {
      return Component.empty();
    }

    List<Component> lore = this.getItemMeta().lore();
    if (lore == null) {
      return Component.empty();
    }

    if (lore.size() <= index) {
      return Component.empty();
    }

    return lore.get(index);
  }

  public ItemStackBuilder loreEntry(int index, Component line) {
    if (this.getItemMeta() == null) {
      return this;
    }

    if (index < 0) {
      return this;
    }

    List<Component> lore = this.getItemMeta().lore();
    if (lore == null) {
      lore = new ArrayList<>();
    }

    if (lore.size() <= index) {
      for (int i = 0; i < index + 1; i++) {
        if (lore.size() <= i) {
          lore.add(Component.empty());
        }
      }
    }

    lore.set(index, line);
    this.getItemMeta().lore(lore);
    this.build();

    return this;
  }

  public ItemStackBuilder clearLore() {
    if (this.getItemMeta() == null) {
      return this;
    }

    this.getItemMeta().lore(null);
    return this;
  }

  public List<Component> lore() {
    if (this.getItemMeta() == null) {
      return new ArrayList<>();
    }

    return this.getItemMeta().lore();
  }

  public ItemStackBuilder lore(Component... lines) {
    this.lore(Arrays.asList(lines));
    return this;
  }

  public ItemStackBuilder lore(String... lines) {
    this.lore(Arrays.stream(lines).map(Component::text).collect(Collectors.toList()));
    return this;
  }

  public ItemStackBuilder lore(List<Component> lines) {
    if (this.getItemMeta() == null) {
      return this;
    }

    this.getItemMeta().lore(lines);
    return this;
  }

  public ItemStackBuilder addItemFlags(ItemFlag... itemFlags) {
    this.getItemMeta().addItemFlags(itemFlags);
    this.build();
    return this;
  }

  public ItemStackBuilder removeItemFlags(ItemFlag... itemFlags) {
    this.getItemMeta().removeItemFlags(itemFlags);
    this.build();
    return this;
  }

  public ItemStackBuilder addEnchantment(Enchantment enchantment, int level) {
    this.getItemMeta().addEnchant(enchantment, level, true);
    this.build();
    return this;
  }

  public ItemStackBuilder removeEnchantment(Enchantment enchantment) {
    this.getItemMeta().removeEnchant(enchantment);
    this.build();
    return this;
  }

  public boolean isGlow() {
    NamespacedKey key = this.namespacedKey;
    GlowEnchantment glowEnchantment = new GlowEnchantment(key);

    return this.getItemMeta().hasEnchant(glowEnchantment);
  }

  public ItemStackBuilder glow() {
    NamespacedKey key = this.namespacedKey;
    GlowEnchantment glowEnchantment = new GlowEnchantment(key);
    this.getItemMeta().addEnchant(glowEnchantment, 1, true);
    this.build();
    return this;
  }

  public ItemStackBuilder unglow() {
    NamespacedKey key = this.namespacedKey;
    GlowEnchantment glowEnchantment = new GlowEnchantment(key);
    this.getItemMeta().removeEnchant(glowEnchantment);
    this.build();
    return this;
  }

  public boolean unbreakable() {
    return this.getItemMeta().isUnbreakable();
  }

  public ItemStackBuilder unbreakable(boolean value) {
    this.getItemMeta().setUnbreakable(value);
    this.build();
    return this;
  }

  public ItemStackBuilder storeNBTBoolean(String key, boolean value) {
    this.getItemMeta().getPersistentDataContainer()
        .set(new NamespacedKey(ItemStackBuilderFeature.getInstance().getJavaPlugin(), key),
            PersistentDataType.BYTE, (byte) (value ? 1 : 0));
    return this;
  }

  public boolean getNBTBoolean(String key) {
    var data = this.getItemMeta().getPersistentDataContainer()
        .get(new NamespacedKey(ItemStackBuilderFeature.getInstance().getJavaPlugin(), key),
            PersistentDataType.BYTE);
    if (data == null) {
      return false;
    }

    return data == (byte) 1;
  }

  public ItemStackBuilder storeNBTInt(String key, int value) {
    this.getItemMeta().getPersistentDataContainer()
        .set(new NamespacedKey(ItemStackBuilderFeature.getInstance().getJavaPlugin(), key),
            PersistentDataType.INTEGER, value);
    return this;
  }

  public int getNBTInt(String key) {
    var data = this.getItemMeta().getPersistentDataContainer()
        .get(new NamespacedKey(ItemStackBuilderFeature.getInstance().getJavaPlugin(), key),
            PersistentDataType.INTEGER);
    if (data == null) {
      return -1;
    }

    return data;
  }

  public ItemStackBuilder storeNBTDouble(String key, double value) {
    this.getItemMeta().getPersistentDataContainer()
        .set(new NamespacedKey(ItemStackBuilderFeature.getInstance().getJavaPlugin(), key),
            PersistentDataType.DOUBLE, value);
    return this;
  }

  public double getNBTDouble(String key) {
    var data = this.getItemMeta().getPersistentDataContainer()
        .get(new NamespacedKey(ItemStackBuilderFeature.getInstance().getJavaPlugin(), key),
            PersistentDataType.DOUBLE);
    if (data == null) {
      return -1;
    }

    return data;
  }

  public ItemStackBuilder storeNBTString(String key, String value) {
    this.getItemMeta().getPersistentDataContainer()
        .set(new NamespacedKey(ItemStackBuilderFeature.getInstance().getJavaPlugin(), key),
            PersistentDataType.STRING, value);
    return this;
  }

  public String getNBTString(String key) {
    var data = this.getItemMeta().getPersistentDataContainer()
        .get(new NamespacedKey(ItemStackBuilderFeature.getInstance().getJavaPlugin(), key),
            PersistentDataType.STRING);
    if (data == null) {
      return "";
    }

    return data;
  }

  public ItemStackBuilder storeNBTList(String key, List<String> value) {
    // Size
    this.getItemMeta().getPersistentDataContainer()
        .set(new NamespacedKey(ItemStackBuilderFeature.getInstance().getJavaPlugin(), key),
            PersistentDataType.INTEGER, value.size());

    // Set entries
    for (int i = 0; i < value.size(); i++) {
      this.getItemMeta().getPersistentDataContainer().set(
          new NamespacedKey(ItemStackBuilderFeature.getInstance().getJavaPlugin(), key + "." + i),
          PersistentDataType.STRING, value.get(i));
    }

    return this;
  }

  public List<String> getNBTList(String key) {
    var size = this.getItemMeta().getPersistentDataContainer()
        .get(new NamespacedKey(ItemStackBuilderFeature.getInstance().getJavaPlugin(), key),
            PersistentDataType.INTEGER);
    if (size == null) {
      return Collections.emptyList();
    }

    List<String> list = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      var data = this.getItemMeta().getPersistentDataContainer().get(
          new NamespacedKey(ItemStackBuilderFeature.getInstance().getJavaPlugin(), key + "." + i),
          PersistentDataType.STRING);
      if (data != null) {
        list.add(data);
      }
    }

    return list;
  }

  public ItemStackBuilder removeNBTValue(String key) {
    var size = this.getItemMeta().getPersistentDataContainer()
        .get(new NamespacedKey(ItemStackBuilderFeature.getInstance().getJavaPlugin(), key),
            PersistentDataType.INTEGER);
    this.getItemMeta().getPersistentDataContainer()
        .remove(new NamespacedKey(ItemStackBuilderFeature.getInstance().getJavaPlugin(), key));

    if (size == null) {
      return this;
    }

    if (!this.getItemMeta().getPersistentDataContainer()
        .has(new NamespacedKey(ItemStackBuilderFeature.getInstance().getJavaPlugin(), key + ".0"),
            PersistentDataType.STRING)) {
      return this;
    }

    for (int i = 0; i < size; i++) {
      this.getItemMeta().getPersistentDataContainer().remove(
          new NamespacedKey(ItemStackBuilderFeature.getInstance().getJavaPlugin(), key + "." + i));
    }
    return this;
  }
}
