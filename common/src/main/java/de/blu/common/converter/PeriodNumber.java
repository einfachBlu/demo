package de.blu.common.converter;

import lombok.AllArgsConstructor;

import java.text.NumberFormat;
import java.util.Locale;

@AllArgsConstructor
public final class PeriodNumber {

  private Object value;

  @Override
  public String toString() {
    if (this.value instanceof Float value) {
      return new DecimalNumber(value, 2, true).toString();
    }

    if (this.value instanceof Double value) {
      return new DecimalNumber(value, 2, true).toString();
    }

    return NumberFormat.getIntegerInstance(Locale.GERMANY).format(this.value);
  }
}
