package de.blu.bukkit.feature.pipe.model.chip;

import de.blu.bukkit.feature.pipe.model.ChipType;
import de.blu.bukkit.feature.pipe.model.chip.display.ChipDisplay;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

public final class SenderChip extends Chip {

  @Override
  public ChipDisplay getDisplay() {
    return new ChipDisplay(Material.GREEN_WOOL, "§aSender Chip", List.of("§7Dieser Chip sendet Items zu §eContainern", "§7an denen ein §eReceiver Chip", "§7gesetzt ist."));
  }

  @Override
  public ChipType getType() {
    return ChipType.SENDER;
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
