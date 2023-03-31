package de.blu.common.converter;

import lombok.AllArgsConstructor;

import java.text.NumberFormat;
import java.util.Locale;

@AllArgsConstructor
public final class PeriodShortNumber {

  private Object value;

  @Override
  public String toString() {
    if (this.value instanceof Float value) {
      return new DecimalNumber(value, 2, true).toString();
    }

    if (this.value instanceof Double value) {
      return new DecimalNumber(value, 2, true).toString();
    }

    long value = (long) this.value;
    String valueString = "" + value;
    if (value >= 1000000000) {
      valueString = new DecimalNumber((float)value / 1000000000, 1, true) + "B";
    } else if (value >= 1000000) {
      valueString = new DecimalNumber((float)value / 1000000, 1, true) + "M";
    } else if (value >= 10000) {
      valueString = new DecimalNumber((float)value / 1000, 1, true) + "k";
    }

    //NumberFormat.getIntegerInstance(Locale.GERMANY).format(valueString);
    return valueString;
  }
}
