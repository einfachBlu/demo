package de.blu.common.feature;

import com.google.inject.Injector;
import de.blu.common.feature.model.Feature;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.Getter;
import org.reflections.Reflections;

@Singleton
public final class FeatureManager {

  @Inject private Injector injector;

  private Set<Class<? extends FeatureBase>> featuresClasses;
  @Getter private Set<FeatureBase> features = new HashSet<>();

  public void init(String packageName) {
    // Load Features
    this.loadFeatures(packageName);

    // Enable Features
    this.enableFeatures();
  }

  private void loadFeatures(String packageName) {
    // Find Classes
    this.featuresClasses = this.findFeatures(packageName);

    // Instantiate them with Guice
    for (Class<? extends FeatureBase> featureClass : this.featuresClasses) {
      FeatureBase feature = this.injector.getInstance(featureClass);
      this.features.add(feature);
    }
  }

  private void enableFeatures() {
    long enableStartTime = System.currentTimeMillis();
    List<FeatureBase> remainingFeatures = new ArrayList<>(
        this.features.stream().filter(x -> !x.isEnabled()).toList());
    var lastSize = 0;
    int i = 0;
    outerLoop:
    while (remainingFeatures.size() > 0) {
      if (lastSize == remainingFeatures.size()) {
        // Break because it seems that some depends on each other
        System.out.println("Unable to resolve Feature dependencies: " + remainingFeatures);
        break;
      }

      FeatureBase feature = remainingFeatures.get(i);
      for (String dependency : feature.getClass().getAnnotation(Feature.class).dependencies()) {
        if (!this.isFeatureEnabled(dependency)) {
          //System.out.println("Skip " + feature.getClass().getAnnotation(Feature.class).name() + " because dependency is not enabled yet: " + dependency);
          i++;
          continue outerLoop;
        }
      }

      i = 0;
      lastSize = remainingFeatures.size();
      long startTime = System.currentTimeMillis();
      feature.onEnable();
      feature.setEnabled(true);
      System.out.println(
          "[Feature Manager] Enabled Feature " + feature.getClass().getAnnotation(Feature.class)
              .name() + " (Took " + (System.currentTimeMillis() - startTime) + "ms)");
      remainingFeatures.remove(feature);
    }

    System.out.println("[Feature Manager] Took " + (System.currentTimeMillis() - enableStartTime)
        + "ms to enable all Features.");
  }

  public void disableFeatures() {
    // TODO: Check Order
    for (FeatureBase feature : this.features) {
      feature.onDisable();
      feature.setEnabled(false);
      System.out.println(
          "[Feature Manager] Disabled Feature " + feature.getClass().getAnnotation(Feature.class)
              .name());
    }
  }

  private Set<Class<? extends FeatureBase>> findFeatures(String packageName) {
    Set<Class<? extends FeatureBase>> featureClasses = new HashSet<>();
    Reflections reflections = new Reflections(packageName);
    Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Feature.class, true);
    for (Class<?> featureClass : classes) {
      boolean correctParent = FeatureBase.class.isAssignableFrom(featureClass);
      if (!correctParent) {
        System.out.println(
            "[Feature Manager] Class " + featureClass.getName() + " does not extend FeatureBase!");
        continue;
      }

      featureClasses.add(featureClass.asSubclass(FeatureBase.class));
    }

    return featureClasses;
  }

  private boolean isFeatureEnabled(String featureName) {
    return this.features.stream().anyMatch(
        x -> x.getClass().getAnnotation(Feature.class).name().equalsIgnoreCase(featureName)
            && x.isEnabled());
  }
}
