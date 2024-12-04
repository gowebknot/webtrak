package com.webknot.webtrak.enums;

import java.util.stream.Stream;

public enum TemplateEnum {

  VALUE_A("VALUE_A"),
  VALUE_B("VALUE_B"),
  VALUE_C("VALUE_C");

  private final String value;

  TemplateEnum(String value) {
    this.value = value;
  }

  public static TemplateEnum of(String value) {
    return Stream.of(TemplateEnum.values()).filter(v -> v.getValue().equals(value)).findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }

  public String getValue() {
    return value;
  }

}
