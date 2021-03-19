package br.com.mc.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.mc.cursomc.services.DBService;
import br.com.mc.cursomc.services.EmailService;
import br.com.mc.cursomc.services.SmtpEmailService;
import br.com.mc.cursomc.utils.TestUtils;

@Configuration
@Profile("dev")
public class DevConfig {
	
	public DevConfig() {
		TestUtils.logDestacado("DevConfig()");
	}
	
	@Autowired
	private DBService dbserv;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean initDatabase() throws Exception {
		TestUtils.logDestacado("DevConfig.initDatabase(): strategy=" + strategy);

		if (strategy != null && strategy.startsWith("create")) {
			dbserv.initTestDB();
		}
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
