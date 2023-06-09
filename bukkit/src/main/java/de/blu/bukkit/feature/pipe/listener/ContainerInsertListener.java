package de.blu.bukkit.feature.pipe.listener;

import com.google.inject.Injector;
import de.blu.bukkit.feature.pipe.model.ChipType;
import de.blu.bukkit.feature.pipe.model.PipeSystem;
import de.blu.bukkit.feature.pipe.model.chip.Chip;
import de.blu.bukkit.feature.pipe.model.chip.SenderChip;
import de.blu.bukkit.feature.pipe.model.component.ContainerComponent;
import de.blu.bukkit.feature.pipe.model.component.EndRodComponent;
import de.blu.bukkit.feature.pipe.model.component.PipeComponent;
import de.blu.bukkit.feature.pipe.repository.PipeSystemRepository;
import de.blu.common.annotation.AutoRegisterListener;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

@Singleton
@AutoRegisterListener
public final class ContainerInsertListener implements Listener {

  @Inject private PipeSystemRepository pipeSystemRepository;
  @Inject private Injector injector;
  @Inject private JavaPlugin javaPlugin;

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onTrySendItems(InventoryClickEvent e) {
    Bukkit.getScheduler().runTask(this.javaPlugin, () -> this.trySendItems(e));
  }

  private void trySendItems(InventoryClickEvent e) {
    Player player = (Player) e.getWhoClicked();
    int slot = e.getSlot();
    int hotbarButton = e.getHotbarButton();
    InventoryView view = player.getOpenInventory();
    Inventory topInventory = view.getTopInventory();
    Inventory bottomInventory = view.getBottomInventory();

    Location containerLocation = topInventory.getLocation();

    if (containerLocation == null) {
      return;
    }

    Block containerBlock = containerLocation.getBlock();
    Inventory inventory = null;
    if (containerBlock.getType().equals(Material.CHEST)) {
      inventory = ((Chest) containerBlock.getState()).getInventory();
    } else if (containerBlock.getType().equals(Material.FURNACE)) {
      inventory = ((Furnace) containerBlock.getState()).getInventory();
    }

    if (inventory == null) {
      return;
    }

    // Check if its part of a PipeSystem
    PipeSystem pipeSystem = this.pipeSystemRepository.getByBlock(containerBlock);

    if (pipeSystem == null) {
      // TODO: Fail here in case the second double chest block is not registered as Pipe Component
      return;
    }

    PipeComponent containerComponent = pipeSystem.getComponent(containerBlock);
    if (containerComponent == null) {
      return;
    }

    // Get the adjacent endrod and check for the Sender Chip
    List<EndRodComponent> endRodComponents = containerComponent.getComponentsAround().stream()
        .filter(x -> x instanceof EndRodComponent)
        .map(pipeComponent -> (EndRodComponent) pipeComponent).toList();

    SenderChip senderChip = null;
    for (EndRodComponent endRodComponent : endRodComponents) {
      if (!endRodComponent.getChip().getType().equals(ChipType.SENDER)) {
        continue;
      }

      senderChip = (SenderChip) endRodComponent.getChip();
    }

    if (senderChip == null) {
      return;
    }

    // Check if there are Items to send
    if (inventory.isEmpty()) {
      return;
    }

    // Get other EndRods in the System with receiverChips
    List<EndRodComponent> receiverEndRodComponents = pipeSystem.getPipeComponents().stream().filter(pipeComponent -> {
      if (!(pipeComponent instanceof EndRodComponent endRodComponent)) {
        return false;
      }

      if (!endRodComponent.getChip().getType().equals(ChipType.RECEIVER)) {
        return false;
      }

      return true;
    }).map(pipeComponent -> (EndRodComponent) pipeComponent).toList();

    List<ItemStack> itemStacks = new ArrayList<>();
    for (int i = 0; i < inventory.getSize(); i++) {
      ItemStack itemStack = inventory.getItem(i);
      itemStacks.add(itemStack);
    }

    for (EndRodComponent receiverEndRodComponent : receiverEndRodComponents) {
      // Move
      ContainerComponent targetContainer = (ContainerComponent) receiverEndRodComponent.getNearbyContainer();
      slot = -1;
      for (ItemStack itemStack : itemStacks) {
        slot++;

        if (itemStack == null || itemStack.getType().equals(Material.AIR)) {
          continue;
        }

        int addedAmount = targetContainer.insertItem(itemStack.clone());

        if (addedAmount == 0) {
          // Nothing added
          continue;
        }

        if (addedAmount == itemStack.getAmount()) {
          // Stored the Item
          itemStack.setType(Material.AIR);
          inventory.setItem(slot, itemStack);
          itemStacks.set(slot, itemStack);
          continue;
        }

        // Return missing Items
        itemStack.setAmount(itemStack.getAmount() - addedAmount);
        inventory.setItem(slot, itemStack);
        itemStacks.set(slot, itemStack);
      }
    }
  }

  @EventHandler
  public void onSmelt(FurnaceSmeltEvent e){
    CookingRecipe<?> recipe = e.getRecipe();
    Block block = e.getBlock();
    ItemStack source = e.getSource();
    ItemStack result = e.getResult();
  }
}
