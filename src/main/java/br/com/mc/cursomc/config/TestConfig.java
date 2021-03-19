package br.com.mc.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.mc.cursomc.services.DBService;
import br.com.mc.cursomc.services.EmailService;
import br.com.mc.cursomc.services.MockEMailService;
import br.com.mc.cursomc.utils.TestUtils;

@Configuration
@Profile("test")
public class TestConfig {
	
	public TestConfig() {
		TestUtils.logDestacado("TestConfig()");
	}
	
	@Autowired
	private DBService dbserv;

	@Bean
	public boolean initDatabase() throws Exception {

		TestUtils.logDestacado("TestConfig.initDatabase()");
		dbserv.initTestDB();
		
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEMailService();
	}
}
