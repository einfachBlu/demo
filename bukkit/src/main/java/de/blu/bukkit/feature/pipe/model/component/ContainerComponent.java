package de.blu.bukkit.feature.pipe.model.component;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface ContainerComponent {

  Inventory getInventory();

  /**
   * Add Item to the Inventory
   *
   * @param itemStack itemStack to add
   * @return amount of items that were added
   */
  int insertItem(ItemStack itemStack);
}
