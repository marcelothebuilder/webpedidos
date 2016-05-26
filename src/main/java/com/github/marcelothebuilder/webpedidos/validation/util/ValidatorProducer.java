/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.validation.util;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author Marcelo Paixao Resende
 *
 */
public class ValidatorProducer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Produces
	@ApplicationScoped
	public Validator createValidator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		return validator;
	}

}
