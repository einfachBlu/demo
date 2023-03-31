package de.blu.bukkit.feature.pipe.model.component;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;

public final class ChestComponent extends PipeComponent implements ContainerComponent {

  private static final List<Material> AROUND_ALLOWED_MATERIALS = List.of(Material.END_ROD);
  private static final List<BlockFace> AROUND_ALLOWED_FACES = List.of(BlockFace.UP, BlockFace.DOWN,
      BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);

  public ChestComponent(Block block) {
    super(block);
  }

  @Override
  public PipeComponentType getType() {
    return PipeComponentType.CHEST;
  }

  @Override
  public List<PipeComponent> getComponentsAround() {
    /*
    // Return Double Chest Block if exist
    Chest chest = (Chest) this.block.getBlockData();
    Type chestType = chest.getType();
    Type oppositeType = chestType.equals(Type.RIGHT) ? Type.LEFT :Type.RIGHT;

    if (chestType.equals(Type.SINGLE)) {
      // Single Chest
      return List.of();
    }

    // Search for the other Chest
    List<BlockFace> faces = List.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
    List<Block> chestsAround = this.getBlocksAround(List.of(Material.CHEST), faces);
    Block targetChest = chestsAround.stream().filter(chestBlock -> {
      Chest targetChest2 = (Chest) chestBlock.getBlockData();
      if (!targetChest2.getType().equals(oppositeType)) {
        return false;
      }

      return true;
    }).findFirst().orElse(null);

    if (targetChest == null) {
      return List.of();
    }
    PipeComponent pipeComponent = PipeComponentFactory.getInstance()
        .getPipeComponentFromBlock(targetChest);

    if (pipeComponent == null) {
      return List.of();
    }

    return List.of(pipeComponent);
    */
    return this.getComponentsAroundComponent(AROUND_ALLOWED_MATERIALS, AROUND_ALLOWED_FACES);
  }

  @Override
  public Inventory getInventory() {
    Block block = this.getBlock();
    Chest chest = (Chest) block.getState();
    return chest.getInventory();
  }
}
