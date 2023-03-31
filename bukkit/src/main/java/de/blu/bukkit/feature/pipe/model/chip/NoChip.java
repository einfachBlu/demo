package de.blu.bukkit.feature.pipe.model.chip;

import de.blu.bukkit.feature.pipe.model.ChipType;
import de.blu.bukkit.feature.pipe.model.chip.display.ChipDisplay;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

public final class NoChip extends Chip {

  @Override
  public ChipDisplay getDisplay() {
    return new ChipDisplay(Material.WHITE_WOOL, "§fKein Chip", List.of());
  }

  @Override
  public ChipType getType() {
    return ChipType.NONE;
  }

  @Override
  public void save(Block block, JavaPlugin javaPlugin) {

  }

  @Override
  public void load(Block block) {

  }

  @Override
  public void clear(Block block, JavaPlugin javaPlugin) {

  }
}
