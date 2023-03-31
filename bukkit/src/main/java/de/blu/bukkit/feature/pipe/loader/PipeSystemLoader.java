package de.blu.bukkit.feature.pipe.loader;

import de.blu.bukkit.feature.pipe.factory.PipeComponentFactory;
import de.blu.bukkit.feature.pipe.model.PipeSystem;
import de.blu.bukkit.feature.pipe.model.component.ContainerComponent;
import de.blu.bukkit.feature.pipe.model.component.EndRodComponent;
import de.blu.bukkit.feature.pipe.model.component.PipeComponent;
import de.blu.bukkit.feature.pipe.model.component.PipeComponentType;
import de.blu.bukkit.feature.pipe.repository.PipeSystemRepository;
import de.blu.bukkit.util.BlockHighlightUtil;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.Color;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

@Singleton
public final class PipeSystemLoader {

  @Inject private PipeSystemRepository pipeSystemRepository;
  @Inject private PipeComponentFactory pipeComponentFactory;
  @Inject private JavaPlugin javaPlugin;

  public void loadPipeSystems(List<PipeComponent> pipeComponents) {
    List<Color> colors = new ArrayList<>(
        List.of(Color.RED, Color.YELLOW, Color.LIME, Color.FUCHSIA, Color.AQUA, Color.WHITE,
            Color.SILVER, Color.GRAY, Color.BLACK, Color.MAROON, Color.OLIVE, Color.GREEN,
            Color.TEAL, Color.BLUE, Color.NAVY, Color.PURPLE, Color.ORANGE));

    int i = 0;
    ArrayList<PipeComponent> endRodComponents = new ArrayList<>(
        pipeComponents.stream().filter(x -> x instanceof EndRodComponent).toList());
    for (PipeComponent endRodComponent : endRodComponents) {
      if (!pipeComponents.contains(endRodComponent)) {
        // Probably removed in the loadPipeSystem Iteration
        continue;
      }

      PipeSystem pipeSystem = this.loadPipeSystem(endRodComponent, pipeComponents);
      if (pipeSystem != null) {
        for (PipeComponent pipeComponent : pipeSystem.getPipeComponents()) {
          BlockHighlightUtil.highlightBlock(pipeComponent.getBlock(), colors.get(i));
        }
        i++;
        this.pipeSystemRepository.add(pipeSystem);
      }
    }
  }

  private PipeSystem loadPipeSystem(PipeComponent endRodStartComponent,
      List<PipeComponent> pipeComponents) {
    PipeSystem pipeSystem = new PipeSystem();
    this.loadPipeSystemComponentsRecursive(pipeSystem, endRodStartComponent, pipeComponents);

    if (pipeSystem.getPipeComponents().stream().filter(x -> x instanceof ContainerComponent).count()
        < 2) {
      // Need to have at least 2 containers in the system
      System.out.println("PipeSystem has not enough containers");
      return null;
    }

    return pipeSystem;
  }

  private void loadPipeSystemComponentsRecursive(PipeSystem pipeSystem, PipeComponent pipeComponent,
      List<PipeComponent> pipeComponents) {
    List<PipeComponent> componentsAround = pipeComponent.getComponentsAround();
    if (componentsAround.size() > 0) {
      componentsAround.removeIf(x -> pipeComponents.stream().noneMatch(x::equals));
    }

    // Remove from List of Components if it's not a reusable Container
    if (!(pipeComponent instanceof ContainerComponent)) {
      pipeComponents.remove(pipeComponent);
    }

    // Add to PipeSystem
    pipeSystem.getPipeComponents().add(pipeComponent);

    if (pipeComponent.getType().equals(PipeComponentType.CHEST)) {
      Block block = pipeComponent.getBlock();
    }

    // Add components around
    if (componentsAround.isEmpty()) {
      return;
    }

    for (PipeComponent componentAround : componentsAround) {
      this.loadPipeSystemComponentsRecursive(pipeSystem, componentAround, pipeComponents);
    }
  }
}
