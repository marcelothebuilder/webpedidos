/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.util.report.impl;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import com.github.marcelothebuilder.webpedidos.util.report.EmissorRelatorio;
import com.github.marcelothebuilder.webpedidos.util.report.EmissorRelatorioWork;
import com.github.marcelothebuilder.webpedidos.util.report.ExecutorRelatorio;

/**
 * @author Marcelo Paixao Resende
 *
 */
@RequestScoped
public final class ExecutorRelatorioHibernate implements ExecutorRelatorio {
	
	private @Inject EntityManager manager;
	private EmissorRelatorio emissor;
	
	public ExecutorRelatorioHibernate() {
		
	}
	
	public ExecutorRelatorioHibernate(EmissorRelatorio emissor) {
		this.emissor = emissor;
	}

	/* (non-Javadoc)
	 * @see com.github.marcelothebuilder.webpedidos.util.report.ExecutorRelatorio#emite()
	 */
	@Override
	public void emite() {
		// we CAN'T close the resource, as it's managed by Hibernate
		@SuppressWarnings("resource")
		Session session = manager.unwrap(Session.class);
		Work work = new EmissorRelatorioWork(this.emissor);
		session.doWork(work);
	}

	@Override
	public void setEmissor(EmissorRelatorio emissor) {
		this.emissor = emissor;
	}

}
