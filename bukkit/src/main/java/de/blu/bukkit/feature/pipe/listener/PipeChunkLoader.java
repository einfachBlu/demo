package de.blu.bukkit.feature.pipe.listener;

import de.blu.bukkit.feature.pipe.repository.PipeSystemChunksRepository;
import de.blu.common.annotation.AutoRegisterListener;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

@Singleton
@AutoRegisterListener
public final class PipeChunkLoader implements Listener {

  @Inject private PipeSystemChunksRepository pipeSystemChunksRepository;

  @EventHandler
  public void onChunkLoad(ChunkLoadEvent e) {
    World world = e.getWorld();
    Chunk chunk = e.getChunk();

    this.pipeSystemChunksRepository.load(chunk);
  }
}
