package com.JFSCapstoneBackend.JMSQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.JFSCapstoneBackend.Model.Email;

@Component
public class JMSMailReciever {
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@JmsListener(destination = "JMSQueue",containerFactory = "JMSFactory")
	public void sendMail(Email email) {
		SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email.getTo());
        msg.setSubject("Thanks for answering the Survey..!");
        msg.setText(email.getBody());
        javaMailSender.send(msg);
	}

}
