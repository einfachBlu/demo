package de.blu.bukkit.feature.itemstackbuilder.builder;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public final class DefaultsItemStackBuilder extends ItemStackBuilder {

  public DefaultsItemStackBuilder placeHolderGlass(Material glassMaterial) {
    this.type(glassMaterial);
    this.displayName(ChatColor.GRAY + " ");
    return this;
  }

  public DefaultsItemStackBuilder invisibleItem() {
    this.type(Material.MAP);
    this.displayName(ChatColor.GRAY + " ");
    this.customModelData(1337);
    return this;
  }
}
