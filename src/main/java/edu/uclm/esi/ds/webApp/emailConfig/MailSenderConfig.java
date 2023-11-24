package edu.uclm.esi.ds.webApp.emailConfig;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailSenderConfig {

	
	@Bean("JavaMailSender")
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.gmail.com");
        sender.setPort(587);
        sender.setUsername("servicios.muevetic@gmail.com");
        sender.setPassword("proyectoISO23");

        Properties props = sender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); 
        props.put("mail.debug", "true");
        
		return sender;
		
	}
}
