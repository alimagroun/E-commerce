package com.magroun.Ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class EmailService {
	
    @Autowired
    private JavaMailSender javaMailSender;
    
  

    void sendEmail(String email,String password) {
    	
    	
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setSubject("Password");
        msg.setText("Your password is: "+password );

        javaMailSender.send(msg);
    }
void sendMsg(Contact contact) {
	
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setTo(contact.getEmail());

    msg.setSubject("Auto-Reply");
    
    msg.setText("Hi "+contact.getName()+","+ "\n" + "\n" + "Your message has been received and is currently being reviewed by our team, this ticket id is #"+contact.getContact_id()+".");
    javaMailSender.send(msg);
	
	
}    
}
