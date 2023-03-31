package de.blu.bukkit.feature.pipe;

import de.blu.bukkit.feature.pipe.loader.PipeSystemLoader;
import de.blu.bukkit.feature.pipe.model.PipeSystemChunk;
import de.blu.bukkit.feature.pipe.model.component.PipeComponent;
import de.blu.bukkit.feature.pipe.repository.PipeSystemChunksRepository;
import de.blu.common.feature.FeatureBase;
import de.blu.common.feature.model.Feature;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.util.Vector;

@Singleton
@Feature(name = "PipeSystem", dependencies = {"ItemStackBuilder"})
public final class PipeFeature extends FeatureBase {

  public static final Vector POS1 = new Vector(-110, 100, -45);
  public static final Vector POS2 = new Vector(-75, 125, -10);

  @Inject private PipeSystemChunksRepository pipeSystemChunksRepository;
  @Inject private PipeSystemLoader pipeSystemLoader;

  @Override
  public void onEnable() {
    this.pipeSystemChunksRepository.load(Bukkit.getWorlds().get(0), this::onChunksLoaded);
  }

  private void onChunksLoaded() {
    // Get Pipe Components in Target area
    List<PipeComponent> pipeComponents = new ArrayList<>();
    for (PipeSystemChunk pipeSystemChunk : this.pipeSystemChunksRepository) {
      pipeComponents.addAll(pipeSystemChunk.getPipeComponents());
    }

    // Load PipeSystems
    this.pipeSystemLoader.loadPipeSystems(pipeComponents);
  }
}
