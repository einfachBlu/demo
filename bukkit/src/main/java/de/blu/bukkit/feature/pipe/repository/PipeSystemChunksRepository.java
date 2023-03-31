package de.blu.bukkit.feature.pipe.repository;

import de.blu.bukkit.feature.pipe.factory.PipeComponentFactory;
import de.blu.bukkit.feature.pipe.model.PipeSystemChunk;
import de.blu.bukkit.feature.pipe.model.component.PipeComponent;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

@Singleton
public final class PipeSystemChunksRepository extends ArrayList<PipeSystemChunk> {

  public static final int MIN_X = -110;
  public static final int MAX_X = -75;
  public static final int MIN_Y = 100;
  public static final int MAX_Y = 125;
  public static final int MIN_Z = -45;
  public static final int MAX_Z = -10;

  @Inject private ExecutorService executorService;
  @Inject private JavaPlugin javaPlugin;

  public void load(World world, Runnable callback) {
    int amount = world.getLoadedChunks().length;
    AtomicInteger loaded = new AtomicInteger(0);
    for (Chunk chunk : world.getLoadedChunks()) {
      this.load(chunk, () -> {
        if (loaded.incrementAndGet() >= amount) {
          callback.run();
        }
      });
    }
  }

  public void load(Chunk chunk) {
    this.load(chunk, () -> {
    });
  }

  public void load(Chunk chunk, Runnable callback) {
    this.executorService.submit(() -> {
      PipeSystemChunk pipeSystemChunk = new PipeSystemChunk(chunk);

      for (int x = 0; x < 16; x++) {
        for (int z = 0; z < 16; z++) {
          for (int y = chunk.getWorld().getMinHeight(); y < chunk.getWorld().getMaxHeight(); y++) {
            Block block = chunk.getBlock(x, y, z);
            Location location = block.getLocation();
            if (!this.isInArea(location)) {
              continue;
            }

            PipeComponent pipeComponent = PipeComponentFactory.getInstance()
                .getPipeComponentFromBlock(block);
            if (pipeComponent != null) {
              pipeSystemChunk.getPipeComponents().add(pipeComponent);
            }
          }
        }
      }

      this.add(pipeSystemChunk);
      callback.run();
    });
  }

  public boolean isInArea(Location location) {
    int x = location.getBlockX();
    int y = location.getBlockY();
    int z = location.getBlockZ();

    return x >= MIN_X && x <= MAX_X && y >= MIN_Y && y <= MAX_Y && z >= MIN_Z && z <= MAX_Z;
  }
}
