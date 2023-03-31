package de.blu.common.feature.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Feature {

  /**
   * Name of this Feature
   */
  String name();

  /**
   * Optional List of Dependencies that should be loaded before this Feature
   */
  String[] dependencies() default {};
}
