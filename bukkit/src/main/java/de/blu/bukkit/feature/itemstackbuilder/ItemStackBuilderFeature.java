package de.blu.bukkit.feature.itemstackbuilder;

import de.blu.bukkit.feature.itemstackbuilder.enchantment.GlowEnchantment;
import de.blu.common.feature.FeatureBase;
import de.blu.common.feature.model.Feature;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Singleton
@Feature(name = "ItemStackBuilder")
public final class ItemStackBuilderFeature extends FeatureBase {

  @Getter private static ItemStackBuilderFeature instance;

  @Getter @Inject private JavaPlugin javaPlugin;

  @Override
  public void onEnable() {
    instance = this;
    GlowEnchantment.register(this.javaPlugin);
  }

  @Override
  public void onDisable() {
  }
}
