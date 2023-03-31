package de.blu.bukkit.feature.pipe.model.component;

import de.blu.bukkit.feature.pipe.factory.PipeComponentFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

@Getter
public abstract class PipeComponent {

  protected Block block;

  public PipeComponent(Block block) {
    this.block = block;
  }

  public abstract PipeComponentType getType();

  public abstract List<PipeComponent> getComponentsAround();


  protected List<Block> getBlocksAround(List<Material> materials,
      List<BlockFace> faces) {
    List<Block> adjacentBlocks = new ArrayList<>();
    for (BlockFace face : faces) {
      Block adjacent = this.block.getRelative(face);
      if (materials.contains(adjacent.getType())) {
        adjacentBlocks.add(adjacent);
      }
    }

    return adjacentBlocks;
  }

  protected List<PipeComponent> getComponentsAroundComponent(List<Material> materials,
      List<BlockFace> faces) {
    List<Block> adjacentBlocks = this.getBlocksAround(materials, faces);

    List<PipeComponent> pipeComponents = new ArrayList<>();

    for (Block adjacentBlock : adjacentBlocks) {
      PipeComponent pipeComponent = PipeComponentFactory.getInstance()
          .getPipeComponentFromBlock(adjacentBlock);
      if (pipeComponent != null) {
        pipeComponents.add(pipeComponent);
      }
    }

    return pipeComponents;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof PipeComponent)) {
      return false;
    }

    return this.block.equals(((PipeComponent) other).block);
    //return this.block.getLocation().equals(((PipeComponent) other).block.getLocation());
  }
}
