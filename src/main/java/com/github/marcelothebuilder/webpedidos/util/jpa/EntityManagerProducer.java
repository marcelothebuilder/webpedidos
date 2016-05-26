package com.github.marcelothebuilder.webpedidos.util.jpa;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@ApplicationScoped
public class EntityManagerProducer implements Serializable {
	private static final long serialVersionUID = -4626099643783264680L;

	private @Inject EntityManagerFactory factory;

	public EntityManagerProducer() {
	}

	@Produces
	@RequestScoped
	public EntityManager createEntityManager() {
		System.out.println("-> starting entity manager");
		return factory.createEntityManager();
	}

	public void closeEntityManager(@Disposes EntityManager manager) {
		System.out.println("-> closing entity manager");
		manager.close();
	}
}
