package com.algaworks.algafood.infrastructure.service.email;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.algaworks.algafood.core.email.EmailProperties;

public class SandboxEnvioEmailService extends SmtpEnvioEmailService {

	@Autowired
	private EmailProperties emailProperties;
	
	@Override
	protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
		var mimeMessage = super.criarMimeMessage(mensagem);
		
		var helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.toString());
		helper.setTo(emailProperties.getSandbox().getDestinatario());
		
		return mimeMessage;
	}
	
}