package br.com.amanda.mailsender;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MailRunner implements CommandLineRunner {

	@Autowired
	private MailSenderService service;

	@Value("${user.home}")
	private String userHome;

	private static final String ATTACHMENT_ROOT = "/attachments";

	@Override
	public void run(String... args) throws Exception {
		try (Stream<Path> walk = Files.walk(Paths.get(this.userHome + File.separator + ATTACHMENT_ROOT))) {

			List<String> result = walk.filter(Files::isRegularFile).map(Path::toString).collect(Collectors.toList());

			for (String f : result) {
				this.service.sendMessageWithAttachment("client@client.com", "Subject", "Mail text", f);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
