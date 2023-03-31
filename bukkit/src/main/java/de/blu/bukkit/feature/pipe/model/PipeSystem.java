package de.blu.bukkit.feature.pipe.model;

import de.blu.bukkit.feature.pipe.model.component.PipeComponent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import org.bukkit.block.Block;

@Getter
public final class PipeSystem {

  private final UUID id = UUID.randomUUID();
  private final List<PipeComponent> pipeComponents = new ArrayList<>();

  public <T extends PipeComponent> T getComponent(Block block){
    for (PipeComponent pipeComponent : this.pipeComponents) {
      if (!pipeComponent.getBlock().equals(block)) {
        continue;
      }

      return (T) pipeComponent;
    }

    return null;
  }
}
