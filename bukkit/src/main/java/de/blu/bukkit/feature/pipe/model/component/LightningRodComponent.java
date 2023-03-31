package de.blu.bukkit.feature.pipe.model.component;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public final class LightningRodComponent extends PipeComponent {

  private static final List<Material> AROUND_ALLOWED_MATERIALS = List.of(Material.REDSTONE_WIRE, Material.LIGHTNING_ROD, Material.END_ROD);
  private static final List<BlockFace> AROUND_ALLOWED_FACES = List.of(BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST,
      BlockFace.SOUTH, BlockFace.WEST);

  public LightningRodComponent(Block block) {
    super(block);
  }

  @Override
  public PipeComponentType getType() {
    return PipeComponentType.LIGHTNING_ROD;
  }

  @Override
  public List<PipeComponent> getComponentsAround() {
    return this.getComponentsAroundComponent(AROUND_ALLOWED_MATERIALS, AROUND_ALLOWED_FACES);
  }
}
