package br.com.amanda.mailsender;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

	@Autowired
	public JavaMailSender emailSender;

	public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) throws MessagingException {
		MimeMessage message = this.emailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(text);

		FileSystemResource file
				= new FileSystemResource(new File(pathToAttachment));
		helper.addAttachment(file.getFilename(), file);

		this.emailSender.send(message);
	}

}
