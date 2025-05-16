package dev.llombardi.comparator.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to define a friendly display name for DTO fields.
 * Used by the comparator to provide more readable information about
 * changes.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DisplayName {
	String value();
}
