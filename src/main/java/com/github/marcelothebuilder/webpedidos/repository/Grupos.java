/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.repository;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.github.marcelothebuilder.webpedidos.model.usuario.Grupo;

/**
 * @author Marcelo Paixao Resende
 *
 */
public class Grupos implements Serializable {
	private static final long serialVersionUID = 1L;

	private @Inject EntityManager manager;

	public Set<Grupo> todos() {
		Set<Grupo> grupos = null;

		TypedQuery<Grupo> query = manager.createQuery("from Grupo", Grupo.class);
		grupos = new HashSet<>(query.getResultList());

		return grupos;
	}

	public Grupo porCodigo(Integer codigo) {
		return manager.find(Grupo.class, codigo);
	}

}
