package de.blu.common.util;

public final class RandomUtils {

  public static int rangeInt(int min, int max) {
    return (int) ((Math.random() * (max + 1 - min)) + min);
  }

  public static boolean bool() {
    return RandomUtils.rangeInt(0, 1) == 1;
  }

  public static double rangeDouble(double min, double max) {
    return ((Math.random() * (max - min)) + min);
  }
}
