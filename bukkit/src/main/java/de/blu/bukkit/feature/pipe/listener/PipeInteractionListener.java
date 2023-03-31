package de.blu.bukkit.feature.pipe.listener;

import com.google.inject.Injector;
import de.blu.bukkit.feature.itemstackbuilder.builder.ItemStackBuilder;
import de.blu.bukkit.feature.pipe.menu.PipeFilterMenu;
import de.blu.bukkit.feature.pipe.model.PipeSystem;
import de.blu.bukkit.feature.pipe.model.component.ContainerComponent;
import de.blu.bukkit.feature.pipe.model.component.EndRodComponent;
import de.blu.bukkit.feature.pipe.model.component.PipeComponent;
import de.blu.bukkit.feature.pipe.repository.PipeSystemChunksRepository;
import de.blu.bukkit.feature.pipe.repository.PipeSystemRepository;
import de.blu.common.annotation.AutoRegisterListener;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.EquipmentSlot;

@Singleton
@AutoRegisterListener
public final class PipeInteractionListener implements Listener {

  @Inject private PipeSystemRepository pipeSystemRepository;
  @Inject private Injector injector;

  @EventHandler
  public void onInteract(PlayerInteractEvent e){
    Player player = e.getPlayer();
    Block clickedBlock = e.getClickedBlock();

    if (clickedBlock == null) {
      return;
    }

    if (e.getHand() == null || !e.getHand().equals(EquipmentSlot.HAND)) {
      return;
    }

    if (!clickedBlock.getType().equals(Material.END_ROD)) {
      return;
    }

    PipeSystem pipeSystem = this.pipeSystemRepository.getByBlock(clickedBlock);
    if (pipeSystem == null) {
      return;
    }

    if (!player.isSneaking()) {
      //return;
    }

    EndRodComponent component = pipeSystem.getComponent(clickedBlock);
    if (component == null) {
      return;
    }

    player.sendMessage("§aPipe ID: " + pipeSystem.getId().toString());

    /*
    PipeComponent nearbyContainer = component.getNearbyContainer();
    if (nearbyContainer != null) {
      Block containerBlock = nearbyContainer.getBlock();
      if (containerBlock.getState() instanceof Container container) {
        player.sendMessage("§aChest nearby: " + container.getInventory().getSize());

        if (container.getInventory().getSize() == 3) {
          // Furnace test
          container.getInventory().setItem(0, ItemStackBuilder.normal(Material.DIRT).displayName("§aSlot 0").build());
          container.getInventory().setItem(1, ItemStackBuilder.normal(Material.DIRT).displayName("§aSlot 1").build());
          container.getInventory().setItem(2, ItemStackBuilder.normal(Material.DIRT).displayName("§aSlot 2").build());
        }
      }
    }
    */

    // Open Options Menu
    PipeFilterMenu menu = this.injector.getInstance(PipeFilterMenu.class);
    menu.open(player, component);
  }
}
