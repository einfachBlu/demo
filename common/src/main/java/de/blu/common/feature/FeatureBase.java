package de.blu.common.feature;

import lombok.Getter;
import lombok.Setter;

public abstract class FeatureBase {

  @Getter @Setter private boolean enabled;

  /**
   * Method to be called when the feature is enabled.
   */
  public void onEnable() {
  }

  /**
   * Method to be called when the feature is disabled.
   */
  public void onDisable() {
  }
}
