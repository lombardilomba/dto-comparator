package dev.llombardi.comparator.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to indicate that the class is part of a critical flow.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DtoObserver {
}
