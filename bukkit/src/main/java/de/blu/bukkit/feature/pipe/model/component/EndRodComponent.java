package de.blu.bukkit.feature.pipe.model.component;

import de.blu.bukkit.feature.pipe.model.chip.Chip;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public final class EndRodComponent extends PipeComponent {

  private static final List<Material> AROUND_ALLOWED_MATERIALS = List.of(Material.REDSTONE_WIRE,
      Material.LIGHTNING_ROD, Material.CHEST, Material.FURNACE);
  private static final List<BlockFace> AROUND_ALLOWED_FACES = List.of(BlockFace.UP, BlockFace.DOWN,
      BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);

  @Setter @Getter private Chip chip;
  @Getter private PipeComponent nearbyContainer;

  public EndRodComponent(Block block) {
    super(block);

    this.chip = Chip.instantiateFromBlock(block);
    this.chip.load(block);

    for (PipeComponent pipeComponent : this.getComponentsAround()) {
      if (!(pipeComponent instanceof ContainerComponent)) {
        continue;
      }

      this.nearbyContainer = pipeComponent;
    }
  }

  @Override
  public PipeComponentType getType() {
    return PipeComponentType.END_ROD;
  }

  @Override
  public List<PipeComponent> getComponentsAround() {
    return this.getComponentsAroundComponent(AROUND_ALLOWED_MATERIALS, AROUND_ALLOWED_FACES);
  }
}
