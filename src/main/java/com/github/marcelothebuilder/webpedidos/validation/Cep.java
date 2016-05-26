/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

/**
 * @author Marcelo Paixao Resende
 *
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Pattern(regexp = "\\d{5}-?\\d{3}")
public @interface Cep {
	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "{com.github.marcelothebuilder.constraints.cep.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
