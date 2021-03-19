package br.com.mc.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

public class MockEMailService implements EmailService {
	private static final Logger log = LoggerFactory.getLogger(MockEMailService.class);

	@Value("${email.default.sender}")
	private String sender;

	@Autowired
	private JavaMailSender jms;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Override
	public void sendMail(SimpleMailMessage msg) {
		log.info("Enviando email");
		log.info(msg.toString());
		log.info("Email enviado");
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) throws Exception {
		log.info("Enviando email HTML");
		log.info(new String(msg.getInputStream().readAllBytes()));
		log.info("Email enviado HTML");
	}

	@Override
	public String getSender() {
		return sender;
	}

	@Override
	public JavaMailSender getJMS() {
		return jms;
	}

	@Override
	public TemplateEngine getTemplateEngine() {
		return templateEngine;
	}
}
