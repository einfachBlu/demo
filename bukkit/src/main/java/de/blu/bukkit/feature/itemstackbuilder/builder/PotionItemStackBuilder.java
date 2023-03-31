package de.blu.bukkit.feature.itemstackbuilder.builder;

import org.bukkit.Material;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public final class PotionItemStackBuilder extends ItemStackBuilder {

  public PotionItemStackBuilder() {
    super();
    this.type(Material.POTION);
  }

  public static PotionItemStackBuilder of(ItemStackBuilder itemStackBuilder) {
    itemStackBuilder.build();
    PotionItemStackBuilder potionItemStackBuilder = new PotionItemStackBuilder();
    potionItemStackBuilder.itemStack = itemStackBuilder.itemStack;
    potionItemStackBuilder.itemMeta = itemStackBuilder.itemMeta;
    return potionItemStackBuilder;
  }

  public PotionItemStackBuilder effect(PotionType potionType, int level, boolean splash) {
    Potion potion = new Potion(potionType, level);
    potion.setSplash(splash);
    this.setItemStack(potion.toItemStack(this.getItemStack().getAmount()));
    return this;
  }
}
