package de.blu.bukkit.feature.itemstackbuilder.builder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public final class LeatherArmorItemStackBuilder extends ItemStackBuilder {

  public LeatherArmorItemStackBuilder() {
    super();
  }

  public static LeatherArmorItemStackBuilder of(ItemStackBuilder itemStackBuilder) {
    itemStackBuilder.build();
    LeatherArmorItemStackBuilder leatherArmorItemStackBuilder = new LeatherArmorItemStackBuilder();
    leatherArmorItemStackBuilder.itemStack = itemStackBuilder.itemStack;
    leatherArmorItemStackBuilder.itemMeta = itemStackBuilder.itemMeta;
    return leatherArmorItemStackBuilder;
  }

  public LeatherArmorItemStackBuilder dye(Color color) {
    if (this.getItemStack().getType() != Material.LEATHER_HELMET
        && this.getItemStack().getType() != Material.LEATHER_CHESTPLATE
        && this.getItemStack().getType() != Material.LEATHER_LEGGINGS
        && this.getItemStack().getType() != Material.LEATHER_BOOTS) {
      return this;
    }

    LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) this.getItemMeta();
    leatherArmorMeta.setColor(color);
    return this;
  }

  @Getter
  @AllArgsConstructor
  public enum ArmorElement {
    HELMET(Material.LEATHER_HELMET), CHESTPLATE(Material.LEATHER_CHESTPLATE), LEGGINGS(
        Material.LEATHER_LEGGINGS), BOOTS(Material.LEATHER_BOOTS);

    private Material material;
  }
}
