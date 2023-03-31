package de.blu.common.util;

import java.util.HashMap;
import javax.inject.Singleton;


/**
 * The CooldownHelper class provides a mechanism for tracking the cooldown period for a given
 * reference. The class is implemented as a subclass of HashMap, with the reference being used as
 * the key and the end time of the cooldown period being used as the value. The class offers methods
 * for setting the last usage time for a given reference, using a specified number of milliseconds
 * as the cooldown period, and for checking whether a given reference is currently on cooldown.
 */
@Singleton
public final class CooldownHelper extends HashMap<String, Long> {

  /**
   * Sets the current time as the last usage time for the given reference, using the specified
   * number of milliseconds as the cooldown period.
   *
   * @param reference    the reference to set the last usage time for
   * @param cooldownTime the number of milliseconds to use as the cooldown period
   */
  public void setCooldown(String reference, long cooldownTime) {
    long endTime = System.currentTimeMillis() + cooldownTime;
    this.put(reference, endTime);
  }

  /**
   * Returns true if the given reference is currently on cooldown, false otherwise.
   *
   * @param reference the reference to check for cooldown
   * @return true if the reference is currently on cooldown, false otherwise
   */
  public boolean isOnCooldown(String reference) {
    Long endTime = this.get(reference);
    if (endTime == null) {
      return false;
    }

    long currentTime = System.currentTimeMillis();
    return currentTime < endTime;
  }
}
