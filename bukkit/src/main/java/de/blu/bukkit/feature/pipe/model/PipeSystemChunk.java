package de.blu.bukkit.feature.pipe.model;

import de.blu.bukkit.feature.pipe.model.component.PipeComponent;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.bukkit.Chunk;

@Getter
public class PipeSystemChunk {

  private Chunk chunk;
  private List<PipeComponent> pipeComponents = new ArrayList<>();

  public PipeSystemChunk(Chunk chunk) {
    this.chunk = chunk;
  }
}
