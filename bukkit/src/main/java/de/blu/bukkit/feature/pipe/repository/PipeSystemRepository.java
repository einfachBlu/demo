package de.blu.bukkit.feature.pipe.repository;

import de.blu.bukkit.feature.pipe.model.PipeSystem;
import java.util.ArrayList;
import javax.inject.Singleton;
import org.bukkit.block.Block;

@Singleton
public final class PipeSystemRepository extends ArrayList<PipeSystem> {

  public PipeSystem getByBlock(Block block){
    for (PipeSystem pipeSystem : this) {
      if (pipeSystem.getPipeComponents().stream().anyMatch(x -> x.getBlock().equals(block))) {
        return pipeSystem;
      }
    }

    return null;
  }
}
