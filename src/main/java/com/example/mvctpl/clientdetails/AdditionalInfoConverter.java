package com.example.mvctpl.clientdetails;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Converter
public class AdditionalInfoConverter implements AttributeConverter<Map<String, Object>, String> {
  private static final String ELEMENTS_DELIMITER = ",";
  private static final String KEY_VALUE_DELIMITER = "<>";

  @Override
  public String convertToDatabaseColumn(Map<String, Object> stringObjectMap) {
    return stringObjectMap.entrySet().stream()
       .map(stringObjectEntry ->
          stringObjectEntry.getKey()
             .concat(KEY_VALUE_DELIMITER)
             .concat(stringObjectEntry.getValue().toString()))
       .collect(Collectors.joining(ELEMENTS_DELIMITER));
  }

  @Override
  public Map<String, Object> convertToEntityAttribute(String s) {
    final Map<String, Object> result = new HashMap<>();
    Optional.ofNullable(s).ifPresent(s1 -> {
      if(!s1.equals("")) {
        String[] mapElement = s1.split(ELEMENTS_DELIMITER);
        for (String element: mapElement) {
          String[] keyValue = element.split(KEY_VALUE_DELIMITER);
          result.put(keyValue[0], keyValue[1]);
        }
      }
    });
    return result;
  }
}
