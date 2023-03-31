package de.blu.bukkit.feature.pipe.model.component;

import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.Inventory;

public final class FurnaceComponent extends PipeComponent implements ContainerComponent {

  public FurnaceComponent(Block block) {
    super(block);
  }

  @Override
  public PipeComponentType getType() {
    return PipeComponentType.FURNACE;
  }

  @Override
  public List<PipeComponent> getComponentsAround() {
    // End
    return List.of();
  }

  @Override
  public Inventory getInventory() {
    Block block = this.getBlock();
    Furnace furnace = (Furnace) block.getState();
    return furnace.getInventory();
  }
}
