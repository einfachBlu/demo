package de.blu.bukkit;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.blu.bukkit.module.ModuleSettings;
import de.blu.bukkit.util.BlockHighlightUtil;
import de.blu.common.annotation.AutoRegisterListener;
import de.blu.common.feature.FeatureManager;
import de.blu.common.feature.model.Feature;
import java.io.File;
import java.lang.reflect.Constructor;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

@Singleton
public final class DemoPlugin extends JavaPlugin {

  @Inject private FeatureManager featureManager;
  @Inject private ExecutorService executorService;
  @Inject private Injector injector;

  @Override
  public void onEnable() {
    Injector injector = Guice.createInjector(new ModuleSettings(this));
    injector.injectMembers(this);

    this.startup();
  }

  private void startup() {
    BlockHighlightUtil.removeAllHighlights();

    // Init Feature Manager
    this.featureManager.init("de.blu.bukkit.feature");

    this.registerListeners("de.blu.bukkit");

    this.getCommand("features").setExecutor((sender, command, label, args) -> {
      Bukkit.getServer().dispatchCommand(sender, "bukkit:plugins");
      this.displayFeatures(sender);
      return true;
    });

    this.getServer().getPluginManager().registerEvents(new Listener() {
      @EventHandler
      public void onCmd(PlayerCommandPreprocessEvent e) {
        String message = e.getMessage();
        Player player = e.getPlayer();
        String command = message.substring(1).replaceAll(" ", "");

        if (command.equalsIgnoreCase("pl") || command.equalsIgnoreCase("plugins")) {
          e.setCancelled(true);
          Bukkit.getServer().dispatchCommand(player, "features");
        }
      }
    }, this);

    // Watcher
    this.executorService.submit(this::initFileWatcher);
  }

  private void initFileWatcher() {
    try {
      File file = new File(new File(this.getServer().getWorldContainer(), "plugins"),
          "bukkit-demo.jar");
      WatchService watchService = FileSystems.getDefault().newWatchService();
      Paths.get(file.getParentFile().getAbsolutePath())
          .register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

      // Schleife zum Überwachen der Änderungen
      while (true) {
        WatchKey key;
        try {
          // Warten auf das Auftreten von Änderungen
          key = watchService.take();
        } catch (InterruptedException ex) {
          return;
        }

        for (WatchEvent<?> event : key.pollEvents()) {
          WatchEvent.Kind<?> kind = event.kind();

          // Überprüfen, ob die Art der Änderung "ENTRY_MODIFY" ist
          if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
            WatchEvent<Path> ev = (WatchEvent<Path>) event;
            Path filename = ev.context();

            if (!file.getName().equalsIgnoreCase(filename.toString())) {
              continue;
            }

            // Reload
            Bukkit.getScheduler().runTask(this, () -> {
              Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "rl");
              System.out.println("New Version detected");
            });
          }
        }

        boolean valid = key.reset();
        if (!valid) {
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void displayFeatures(CommandSender sender) {
    sender.sendMessage(" ");
    sender.sendMessage("§eFeatures (" + this.featureManager.getFeatures().size() + "):");
    String message = String.join("§r, ", this.featureManager.getFeatures().stream().map(
        feature -> (feature.isEnabled() ? "§a" : "§c") + feature.getClass()
            .getAnnotation(Feature.class).name()).toList());
    sender.sendMessage(message);
  }

  @Override
  public void onDisable() {
    this.featureManager.disableFeatures();
    this.executorService.shutdown();
  }

  private void registerListeners(String packageName) {
    Reflections reflections = new Reflections(
        new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage(packageName)).filterInputsBy(
            (input) -> !input.contains("$") && !input.endsWith("Listener") && !input.contains(
                "Test")));
    Set<Class<? extends Listener>> listenerClasses = reflections.getSubTypesOf(Listener.class);
    for (Class<? extends Listener> clazz : listenerClasses) {
      if (clazz.getAnnotation(AutoRegisterListener.class) == null) {
        continue;
      }

      try {
        Constructor<? extends Listener> constructor = clazz.getDeclaredConstructor();
        Listener listener = constructor.newInstance();
        this.injector.injectMembers(listener);
        Bukkit.getServer().getPluginManager().registerEvents(listener, this);
        System.out.println("Registered Listener " + clazz.getName());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
