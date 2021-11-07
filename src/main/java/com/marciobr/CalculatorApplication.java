package com.marciobr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.marciobr.config.FileStorageConfig;

@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties({
	FileStorageConfig.class
})
@ComponentScan
public class CalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculatorApplication.class, args);
		
		//Use isso pra gerar o hash da senha
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(16);
		
		//Escolha a sua senha. admin123 é só um exemplo.
		String result = bCryptPasswordEncoder.encode("admin123");
		
		// Pegue esse resultado e grave na coluna de senha do usuário no BD
		System.out.println("My hash " + result);
	}

}
