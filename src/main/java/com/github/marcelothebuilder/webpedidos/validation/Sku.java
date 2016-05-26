package com.github.marcelothebuilder.webpedidos.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Pattern(regexp = "(([A-Za-z]{3})\\d{3,17})?")
public @interface Sku {
	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "{com.github.marcelothebuilder.constraints.sku.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
