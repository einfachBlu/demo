package de.blu.bukkit.feature.pipe.factory;

import de.blu.bukkit.feature.pipe.model.component.ChestComponent;
import de.blu.bukkit.feature.pipe.model.component.EndRodComponent;
import de.blu.bukkit.feature.pipe.model.component.FurnaceComponent;
import de.blu.bukkit.feature.pipe.model.component.LightningRodComponent;
import de.blu.bukkit.feature.pipe.model.component.PipeComponent;
import de.blu.bukkit.feature.pipe.model.component.RedstoneWireComponent;
import javax.inject.Singleton;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.Block;

@Singleton
public final class PipeComponentFactory {

  @Getter public static PipeComponentFactory instance;

  public PipeComponentFactory() {
    instance = this;
  }

  public PipeComponent getPipeComponentFromBlock(Block block) {
    if (block.getType().equals(Material.FURNACE)) {
      return new FurnaceComponent(block);
    } else if (block.getType().equals(Material.CHEST)) {
      return new ChestComponent(block);
    } else if (block.getType().equals(Material.LIGHTNING_ROD)) {
      return new LightningRodComponent(block);
    } else if (block.getType().equals(Material.REDSTONE_WIRE)) {
      return new RedstoneWireComponent(block);
    } else if (block.getType().equals(Material.END_ROD)) {
      return new EndRodComponent(block);
    }

    return null;
  }
}
