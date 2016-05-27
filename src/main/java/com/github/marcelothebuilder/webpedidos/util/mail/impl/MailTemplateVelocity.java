/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.util.mail.impl;

import java.io.StringWriter;
import java.util.Locale;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.tools.generic.NumberTool;

import com.github.marcelothebuilder.webpedidos.util.mail.Mail;
import com.github.marcelothebuilder.webpedidos.util.mail.MailTemplate;

/**
 * @author Marcelo Paixao Resende
 *
 */
public final class MailTemplateVelocity implements MailTemplate {
	private String htmlContent;

	public MailTemplateVelocity(Mail mail) throws ResourceNotFoundException {
		String arquivoTemplate = mail.getTemplate();

		VelocityEngine engine = new VelocityEngine();
		engine.addProperty("resource.loader", "class");
		engine.addProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		engine.init();

		Template template = engine.getTemplate(arquivoTemplate);

		NumberTool numberTool = new NumberTool();

		assert (numberTool != null);
		
		VelocityContext context = new VelocityContext(mail.getParametros());
		context.put("numberTool", numberTool);
		context.put("locale", Locale.getDefault());

		StringWriter writer = new StringWriter();

		template.merge(context, writer);

		htmlContent = writer.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.marcelothebuilder.webpedidos.util.mail.MailTemplate#getHtml()
	 */
	@Override
	public String getHtml() {
		return this.htmlContent;
	}

}
