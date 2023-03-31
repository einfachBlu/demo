package de.blu.bukkit.feature.pipe.menu;

import de.blu.bukkit.feature.itemstackbuilder.builder.DefaultsItemStackBuilder;
import de.blu.bukkit.feature.itemstackbuilder.builder.ItemStackBuilder;
import de.blu.bukkit.feature.pipe.model.ChipType;
import de.blu.bukkit.feature.pipe.model.chip.Chip;
import de.blu.bukkit.feature.pipe.model.chip.NoChip;
import de.blu.bukkit.feature.pipe.model.chip.ReceiverChip;
import de.blu.bukkit.feature.pipe.model.chip.SenderChip;
import de.blu.bukkit.feature.pipe.model.component.EndRodComponent;
import de.blu.bukkit.feature.pipe.model.component.PipeComponent;
import de.blu.common.util.CooldownHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public final class PipeFilterMenu implements Listener {

  private final CooldownHelper cooldownHelper = new CooldownHelper();
  @Inject private JavaPlugin javaPlugin;
  private Player player;
  private PipeComponent pipeComponent;
  private Inventory inventory;
  private Chip previousChip = new NoChip();
  private Chip chip = new NoChip();
  private final Map<Integer, Runnable> clickCallbacks = new HashMap<>();

  public void open(Player player, PipeComponent pipeComponent) {
    this.player = player;
    this.pipeComponent = pipeComponent;
    this.load();

    this.updateContent();
    this.registerListener();
    player.openInventory(this.inventory);
  }

  private void save() {
    Block block = this.pipeComponent.getBlock();
    this.previousChip.clear(block, this.javaPlugin);
    block.setMetadata("chip-type",
        new FixedMetadataValue(this.javaPlugin, this.chip.getType().name()));
    this.chip.save(block, this.javaPlugin);
    if (this.pipeComponent instanceof EndRodComponent endRodComponent) {
      endRodComponent.setChip(this.chip);
    }
  }

  private void load() {
    Block block = this.pipeComponent.getBlock();
    this.previousChip = Chip.instantiateFromBlock(block);
    this.previousChip.load(block);
    this.chip = Chip.instantiateFromBlock(block);
    this.chip.load(block);
  }

  private void updateContent() {
    if (this.inventory == null) {
      this.inventory = Bukkit.createInventory(null, 9 * 5, Component.text("Pipe Interface"));
    }

    ItemStack placeHolderGlass = DefaultsItemStackBuilder.defaults()
        .placeHolderGlass(Material.BLACK_STAINED_GLASS_PANE).build();
    for (int i = 0; i < this.inventory.getSize(); i++) {
      this.inventory.setItem(i, placeHolderGlass);
    }

    this.clickCallbacks.clear();
    this.updateHeader();
    this.updateChipContent();
  }

  private void updateHeader() {
    List<Chip> chips = List.of(new NoChip(), new SenderChip(), new ReceiverChip());

    int slot = 2;
    for (Chip chip : chips) {
      boolean selected = this.chip.equals(chip);
      List<String> lore = new ArrayList<>(chip.getDisplay().getLoreDescription());
      lore.add(" ");
      lore.add(selected ? ("§aAUSGEWÄHLT") : ("§eKlicken zum Auswählen"));
      ItemStackBuilder builder = ItemStackBuilder.normal(chip.getDisplay().getMaterial())
          .displayName(chip.getDisplay().getDisplayName())
          .lore(lore.stream().map(Component::text).collect(Collectors.toList()));
      if (selected) {
        builder.glow();
      }

      this.inventory.setItem(slot, builder.build());
      this.clickCallbacks.put(slot, () -> {
        this.chip = chip;

        this.player.playSound(this.player.getEyeLocation(), Sound.ENTITY_ITEM_PICKUP, 10, 1);
        this.updateContent();
      });
      slot++;
    }

    // Chip Selection
    /*
    this.inventory.setItem(5,
        ItemStackBuilder.normal(Material.RED_WOOL).displayName("§aRequester Chip").build());
    this.inventory.setItem(6,
        ItemStackBuilder.normal(Material.LIGHT_BLUE_WOOL).displayName("§aRequest Interface Chip")
            .build());
    */

    /**
     * Chips:
     * - Sender (Send items to every receiver Chip with options to set when items should be sent)
     * - Receiver (Can receive Items and optionally filter them and option to set priority)
     * - Requester (Can request items anywhere from the System - used in furnace inputs to auto smelt ores that are in the storage)
     * - Request Interface (Transform the Chest in a Request Interface to request items when shift clicking on the chest. normal click on chest open the items to grab them)
     */
  }

  private void updateChipContent() {
  }

  private void registerListener() {
    Bukkit.getPluginManager().registerEvents(this, this.javaPlugin);
  }

  private void unregisterListener() {
    HandlerList.unregisterAll(this);
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent e) {
    Inventory clickedInventory = e.getClickedInventory();
    if (clickedInventory == null) {
      return;
    }

    if (!e.getView().getTopInventory().equals(this.inventory)) {
      return;
    }

    e.setCancelled(true);

    if (!clickedInventory.equals(this.inventory)) {
      // Clicked in the own inventory while the menu is opened
      return;
    }

    if (this.cooldownHelper.isOnCooldown(String.valueOf(this.hashCode()))) {
      return;
    }

    int slot = e.getSlot();
    if (this.clickCallbacks.containsKey(slot)) {
      this.clickCallbacks.get(slot).run();
      this.cooldownHelper.setCooldown(String.valueOf(this.hashCode()), 250);
    }
  }

  @EventHandler
  public void onInventoryClose(InventoryCloseEvent e) {
    Player player = (Player) e.getPlayer();
    Inventory inventory = e.getInventory();

    if (!player.getUniqueId().equals(this.player.getUniqueId())) {
      return;
    }

    if (!inventory.equals(this.inventory)) {
      return;
    }

    this.inventory.clear();
    this.unregisterListener();
    this.save();
  }
}
