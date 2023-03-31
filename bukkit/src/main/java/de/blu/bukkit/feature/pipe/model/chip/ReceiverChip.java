package de.blu.bukkit.feature.pipe.model.chip;

import de.blu.bukkit.feature.pipe.model.ChipType;
import de.blu.bukkit.feature.pipe.model.chip.display.ChipDisplay;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

public final class ReceiverChip extends Chip {

  @Override
  public ChipDisplay getDisplay() {
    return new ChipDisplay(Material.RED_WOOL, "§aReceiver Chip",
        List.of("§7Dieser Chip empfängt Items von", "§eSender Chips"));
  }

  @Override
  public ChipType getType() {
    return ChipType.RECEIVER;
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
