package de.blu.bukkit.feature.pipe.model.chip;

import de.blu.bukkit.feature.pipe.model.ChipType;
import de.blu.bukkit.feature.pipe.model.chip.display.ChipDisplay;
import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Chip {

  public static Chip instantiateFromBlock(Block block) {
    List<MetadataValue> metadata = block.getMetadata("chip-type");
    if (metadata.size() != 0) {
      ChipType type = ChipType.valueOf(metadata.get(0).asString());
      return instantiateFromType(type);
    }

    return new NoChip();
  }

  public static Chip instantiateFromType(ChipType chipType) {
    switch (chipType) {
      case NONE -> {
        return new NoChip();
      }
      case SENDER -> {
        return new SenderChip();
      }
      case RECEIVER -> {
        return new ReceiverChip();
      }
      case REQUESTER -> {
        return new NoChip();
      }
      case INTERFACE_REQUESTER -> {
        return new NoChip();
      }
    }

    return new NoChip();
  }

  public abstract ChipDisplay getDisplay();

  public abstract ChipType getType();

  public abstract void save(Block block, JavaPlugin javaPlugin);

  public abstract void load(Block block);

  public abstract void clear(Block block, JavaPlugin javaPlugin);

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Chip)) {
      return false;
    }

    return this.getType().equals(((Chip) obj).getType());
  }
}
