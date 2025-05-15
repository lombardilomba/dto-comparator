package com.study.comparator.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Anotação para definir que a Classe faz parte do fluxo de um fluxo Critico.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DtoObserver {
}
