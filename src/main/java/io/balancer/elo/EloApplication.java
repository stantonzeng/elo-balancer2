package io.balancer.elo;

import io.balancer.elo.model.Player;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EloApplication{

	public static void main(String[] args) {
		SpringApplication.run(EloApplication.class, args);
	}

	@Bean
	CommandLineRunner run(PlayerRepository playerRepo){
		return args -> {
			playerRepo.save(new Player(null, 900.0, "bterabtragdsa"));
			playerRepo.save(new Player(null, 1250.0, "My Cat Says Meow"));
			playerRepo.save(new Player(null, 1300.0, "bubblysoju"));
			playerRepo.save(new Player(null, 1600.0, "pagchompu"));
			playerRepo.save(new Player(null, 750.0, "Shamusa"));
			playerRepo.save(new Player(null, 750.0, "asdgartbaretb"));
			playerRepo.save(new Player(null, 1800.0, "ShadowStriker"));
			playerRepo.save(new Player(null, 1100.0, "Mitsuki Moon"));
			playerRepo.save(new Player(null, 1450.0, "Ally of Women"));
			playerRepo.save(new Player(null, 600.0, "Lionblaze219"));
		};
	}
}
