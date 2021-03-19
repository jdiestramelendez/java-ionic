package br.com.mc.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.mc.cursomc.domain.Pedido;

public interface EmailService {
	
	String getSender();
	
	JavaMailSender getJMS();

	default void sendOrderConfirmationMessage(Pedido pedido) {
		SimpleMailMessage sm = prepareEMailFromPedido(pedido);
		sendMail(sm);
	}
	
	default SimpleMailMessage prepareEMailFromPedido(Pedido pedido) {
		String sender = getSender();
		System.out.println("Sender: " + sender);
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(pedido.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Recebemos seu pedido: " + pedido.getId());
		sm.setSentDate(new Date());
		sm.setText(pedido.toString());
		return sm;
	}
	
	default String htmlFromTemplatePedido(Pedido pedido) {
		Context ctx = new Context();
		ctx.setVariable("pedido", pedido);
		TemplateEngine te = getTemplateEngine();
		return te.process("email/ConfirmacaoPedido", ctx);
	}
	
	TemplateEngine getTemplateEngine();

	default void sendOrderConfirmationHtmlEmail(Pedido pedido) {
		MimeMessage mm;
		try {
			mm = prepareMimeMessage(pedido);
			sendHtmlEmail(mm);
		} catch (Exception e) {
			sendOrderConfirmationMessage(pedido);
		}
	}

	default MimeMessage prepareMimeMessage(Pedido pedido) throws MessagingException {
		String sender = getSender();
		System.out.println("Sender: " + sender);
		JavaMailSender jms = getJMS();
		MimeMessage mm = jms.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mm, true);
		mmh.setTo(pedido.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Recebemos seu pedido: " + pedido.getId());
		mmh.setSentDate(new Date());
		mmh.setText(htmlFromTemplatePedido(pedido));
		return mm;
	}

	void sendMail(SimpleMailMessage msg);
	
	void sendHtmlEmail(MimeMessage msg) throws Exception;
}
