package com.wj;

import com.wj.dao.entities.AppRole;
import com.wj.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Stream;

@SpringBootApplication
public class SecurityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityServiceApplication.class, args);
	}


	/*
		Au démarrage Spring va executer cette méthode et le placer dans son contexte
		Ainsi il pourra être injecter avec Autowired
	 */
	@Bean
	public BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CommandLineRunner start(AccountService accountService){
		return args -> {
			
			//Ajout des rôles
			accountService.saveRole(new AppRole(null, "USER"));
			accountService.saveRole(new AppRole(null, "ADMIN"));
			
			//Ajout des utilisateurs
			Stream.of("woodson", "louis", "matheo", "flora").forEach(u -> {
				accountService.saveUser(u, "1234", "1234");
			});
			
			/*
				Par defaut on ajoute le role  USER aux utilisateurs, qui n'ont pas de role ici:
				 voir classe AccountServiceImpl
			 */
			accountService.addRoleToUser("woodson", "ADMIN");
		};
	}

}

