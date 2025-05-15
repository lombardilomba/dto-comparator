package com.study.comparator.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Anotação para definir um nome de exibição amigável para campos de DTOs.
 * Utilizada pelo comparador para fornecer informações mais legíveis sobre
 * alterações.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DisplayName {
	String value();
}
