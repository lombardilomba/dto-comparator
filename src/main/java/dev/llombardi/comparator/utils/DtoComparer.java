package dev.llombardi.comparator.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dev.llombardi.comparator.annotation.DisplayName;
import dev.llombardi.comparator.comparison.ChangedField;

public class DtoComparer {

    private static final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    /**
     * Compares a current DTO object with a previous version stored as JSON.
     *
     * @param newDto         current DTO object
     * @param oldJson        JSON representing the previous version of the DTO
     * @param monitoredPaths list of field paths to be specially monitored
     * @return list of changed fields with their details
     */
    public static List<ChangedField> compare(Object newDto, String oldJson, List<String> monitoredPaths) {
        List<ChangedField> result = new ArrayList<>();
        try {
            Object oldDto = objectMapper.readValue(oldJson, newDto.getClass());
            compareAllRecursively("", newDto, oldDto, result, monitoredPaths);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Recursive method to compare all fields of two objects.
     *
     * @param path           current path in the object tree
     * @param current        new object
     * @param previous       old object
     * @param result         list to store changed fields
     * @param monitoredPaths list of paths to monitor
     * @throws Exception if reflection error occurs
     */
    private static void compareAllRecursively(String path,
                                              Object current,
                                              Object previous,
                                              List<ChangedField> result,
                                              List<String> monitoredPaths) throws Exception {
        if (current == null && previous == null) return;

        Class<?> clazz = (current != null) ? current.getClass() : previous.getClass();
        if (isSimpleType(clazz)) {
            if ((current != null && !current.equals(previous)) || (current == null && previous != null)) {
                boolean monitored = isMonitored(path, monitoredPaths);
                result.add(new ChangedField(path, previous, current, monitored, null));
            }
            return;
        }

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object newValue = (current != null) ? field.get(current) : null;
            Object oldValue = (previous != null) ? field.get(previous) : null;

            boolean isList = List.class.isAssignableFrom(field.getType());
            String fieldName = field.getName();
            String currentPath = path.isEmpty() ? fieldName : path + "." + fieldName;
            String displayName = getDisplayName(field);

            if (isList) {
                List<?> newList = newValue != null ? (List<?>) newValue : List.of();
                List<?> oldList = oldValue != null ? (List<?>) oldValue : List.of();
                int maxSize = Math.max(newList.size(), oldList.size());

                for (int i = 0; i < maxSize; i++) {
                    Object newItem = i < newList.size() ? newList.get(i) : null;
                    Object oldItem = i < oldList.size() ? oldList.get(i) : null;
                    String itemPath = currentPath + "[" + i + "]";
                    compareAllRecursively(itemPath, newItem, oldItem, result, monitoredPaths);
                }
            } else {
                compareAllRecursively(currentPath, newValue, oldValue, result, monitoredPaths);
            }

            // Propagate display names to subfields
            for (ChangedField change : result) {
                if (change.getPath().startsWith(currentPath)
                        && change.getDisplayName() == null
                        && displayName != null) {
                    change.setDisplayName(displayName);
                }
            }
        }
    }

    /**
     * Checks if a class is a primitive, wrapper, or other simple type.
     *
     * @param clazz class to check
     * @return true if it's a simple type, false otherwise
     */
    private static boolean isSimpleType(Class<?> clazz) {
        if (clazz == null) return false;
        return clazz.isPrimitive()
                || clazz.isEnum()
                || clazz == String.class
                || clazz == Integer.class
                || clazz == Long.class
                || clazz == Double.class
                || clazz == Boolean.class
                || clazz == BigDecimal.class
                || clazz == LocalDate.class
                || clazz == LocalDateTime.class;
    }

    /**
     * Checks if a path is in the list of monitored paths.
     *
     * @param path           field path
     * @param monitoredPaths list of monitored patterns
     * @return true if monitored, false otherwise
     */
    private static boolean isMonitored(String path, List<String> monitoredPaths) {
        if (monitoredPaths == null || monitoredPaths.isEmpty()) {
            return false;
        }
        if (monitoredPaths.contains(path)) {
            return true;
        }
        for (String pattern : monitoredPaths) {
            if (pattern.endsWith(".*")) {
                String prefix = pattern.substring(0, pattern.length() - 2);
                if (path.startsWith(prefix + ".")) {
                    return true;
                }
            }
            if (path.matches(pattern + "\\[\\d+\\].*")) {
                return true;
            }
        }
        return false;
    }

    private static String getDisplayName(Field field) {
        DisplayName annotation = field.getAnnotation(DisplayName.class);
        return (annotation != null) ? annotation.value() : null;
    }
}
