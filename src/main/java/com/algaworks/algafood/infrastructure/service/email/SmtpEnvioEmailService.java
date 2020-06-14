package com.algaworks.algafood.infrastructure.service.email;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class SmtpEnvioEmailService implements EnvioEmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Autowired
	private Configuration freemarkerConfig;
	
	@Override
	public void enviar(Mensagem mensagem) {
		try {
			var mimeMessage = criarMimeMessage(mensagem);
			
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new EmailException("Não foi possível enviar e-mail", e);
		}
	}
	
	protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
		var mimeMessage = mailSender.createMimeMessage();
		var corpo = processarTemplate(mensagem);
		
		var helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.toString());
		helper.setFrom(emailProperties.getRemetente());
		helper.setTo(mensagem.getDestinatarios().toArray(new String[mensagem.getDestinatarios().size()]));
		helper.setSubject(mensagem.getAssunto());
		helper.setText(corpo, true);
		
		return mimeMessage;
	}

	protected String processarTemplate(Mensagem mensagem) {
		try {
			Template template = freemarkerConfig.getTemplate(mensagem.getCorpo());
			
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
		} catch (Exception e) {
			throw new EmailException("Não foi possível montar o template do e-mail", e);
		}
	}
}
