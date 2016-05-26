package com.github.marcelothebuilder.webpedidos.util.cdi;

import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;

public class CDIServiceLocator {

	private static BeanManager getBeanManager() {
		return CDI.current().getBeanManager();
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		BeanManager bm = getBeanManager();
		Set<Bean<?>> beans = bm.getBeans(clazz);

		if (beans == null || beans.isEmpty()) {
			return null;
		}

		Bean<T> bean = (Bean<T>) beans.iterator().next();

		CreationalContext<T> ctx = bm.createCreationalContext(bean);
		T o = (T) bm.getReference(bean, clazz, ctx);

		return o;
	}

}