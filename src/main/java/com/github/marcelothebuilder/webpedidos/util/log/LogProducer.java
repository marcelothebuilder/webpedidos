/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.util.log;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe produtora de objetos Log
 * 
 * @author Marcelo Paixao Resende
 *
 */
public final class LogProducer {
	@Produces
	public Log getLog(InjectionPoint injectionPoint) {
		Class<?> injectedClass = injectionPoint.getMember().getDeclaringClass();
		return LogFactory.getLog(injectedClass);
	}
}

