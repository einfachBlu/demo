package de.blu.bukkit.util;

import java.nio.Buffer;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.entity.Display.Brightness;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Transformation;

public final class BlockHighlightUtil {

  public static void removeHighlight(Block block) {
    Bukkit.getWorlds().get(0).getEntities().stream().filter(x -> {
          if (!(x instanceof BlockDisplay)) {
            return false;
          }

          return x.getLocation().getBlock().equals(block);
        })
        .forEach(Entity::remove);
  }

  public static void removeAllHighlights() {
    Bukkit.getWorlds().get(0).getEntities().stream().filter(x -> x instanceof BlockDisplay)
        .forEach(Entity::remove);
  }

  public static void highlightBlock(Block block, Color color) {
    Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugin("DemoPlugin"), () -> {
      // TODO: Double Chests / Stretch?

      removeHighlight(block);

      World world = block.getWorld();
      Location location = block.getLocation();
      float scale = 1.001f;
      BlockDisplay entity = (BlockDisplay) world.spawnEntity(location, EntityType.BLOCK_DISPLAY);
      entity.teleport(block.getLocation());

      BlockData blockData = block.getBlockData();
      entity.setBlock(blockData);
      entity.setBillboard(Billboard.FIXED);

      // Glow & Brightness to highlight
      entity.setGlowing(true);
      entity.setGlowColorOverride(color);
      entity.setBrightness(new Brightness(15, 15));

      // Adjust Scale & relative Position so it does not overlap
      entity.setTransformation(new Transformation(
          entity.getTransformation().getTranslation().add(scale - 1, scale - 1, scale - 1),
          entity.getTransformation().getLeftRotation(),
          entity.getTransformation().getScale().mul(scale, scale, scale),
          entity.getTransformation().getRightRotation()));
    });
  }
}
