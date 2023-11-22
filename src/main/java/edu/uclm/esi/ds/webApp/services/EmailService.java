package edu.uclm.esi.ds.webApp.services;

import java.util.Map;

import org.springframework.mail.SimpleMailMessage;
import edu.uclm.esi.ds.webapp.email.config.MailSenderConfig;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	private JavaMailSender mailsender;
	String from ="servicios.muevetic@gmail.com";
	String subject ="recuperacion de contraseña";
	String content = "Has solicitado un cambio de contraseña , para recuperarla , haz clic en el siguiente enlace: \n http://localhost:4200/modificar-contraseña/";
	
	
	public void sendRecover(Map<String, Object> info) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(info.get("email").toString());
		String messagecontent = content+""+org.apache.commons.codec.digest.DigestUtils.sha512Hex(info.get("email").toString());
		message.setText(messagecontent);
		message.setSubject(subject);
		mailsender.send(message);
	}

}
