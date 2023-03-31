package de.blu.bukkit.module;

import com.google.inject.AbstractModule;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.bukkit.plugin.java.JavaPlugin;

public final class ModuleSettings extends AbstractModule {

  private final JavaPlugin javaPlugin;

  public ModuleSettings(JavaPlugin javaPlugin) {
    this.javaPlugin = javaPlugin;
  }

  @Override
  protected void configure() {
    bind(JavaPlugin.class).toInstance(this.javaPlugin);
    bind(ExecutorService.class).toInstance(Executors.newCachedThreadPool());
  }
}
