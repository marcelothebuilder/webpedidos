/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.util.jpa;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Marcelo Paixao Resende
 *
 */

@ApplicationScoped
class EntityManagerFactoryProducer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Produces
	@ApplicationScoped
	public EntityManagerFactory createEntityManagerFactory() {
		return Persistence.createEntityManagerFactory("webpedidospu");
	}

	public void closeEntityManagerFactory(@Disposes EntityManagerFactory factory) {
		factory.close();
	}
}
