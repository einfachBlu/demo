package de.blu.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSpan {

  /** The time in Millis when the TimeSpan is started */
  private long from = System.currentTimeMillis();

  /** The time in Millis when the TimeSpan ended. Is -1 if no ending (lifetime) */
  private long to = -1;

  /**
   * Get the Duration in milliseconds
   *
   * @return milliseconds between the from and to of this TimeSpan. Also returns -1 if no ending set
   */
  public long duration() {
    if (this.to == -1) {
      return -1;
    }

    return this.to - this.from;
  }

  /**
   * Check if the TimeSpan is expired
   *
   * @return true if the end time is over and false if the end time is not over or has no end time
   */
  public boolean isExpired() {
    if (this.to == -1) {
      return false;
    }

    return this.to <= System.currentTimeMillis();
  }
}
