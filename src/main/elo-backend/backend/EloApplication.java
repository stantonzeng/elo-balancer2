package backend;

import backend.model.Player;
import backend.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EloApplication{

	public static void main(String[] args) {
		SpringApplication.run(EloApplication.class, args);
	}

//	@Bean
//	CommandLineRunner run(PlayerRepository playerRepo, UserRepository userRepo){
//		return args -> {
//
//			playerRepo.save(new Player(null, 1558.0, "bterabtragdsa", 0));
//			playerRepo.save(new Player(null, 1160.0, "My Cat Says Meow", 0));
//			playerRepo.save(new Player(null, 1893.0, "bubblysoju", 0));
//			playerRepo.save(new Player(null, 1524.0, "pagchompu", 0));
//			playerRepo.save(new Player(null, 1001.0, "Shamusa", 0));
//			playerRepo.save(new Player(null, 1636.0, "asdgartbaretb", 0));
//			playerRepo.save(new Player(null, 1222.0, "ShadowStriker", 0));
//			playerRepo.save(new Player(null, 1279.0, "Mitsuki Moon",	 0));
//			playerRepo.save(new Player(null, 1554.0, "Ally of Women", 0));
//			playerRepo.save(new Player(null, 1618.0, "Lionblaze219", 0));
//
//			userRepo.save(new User(null, "test123=", ""));
//			User u = userRepo.findByuserName("test123=").get(0);
//			u.addToList(String.valueOf(4));
//			u.addToList(String.valueOf(7));
//			u.addToList(String.valueOf(9));
//			userRepo.save(u);
//
//		};
//	}
}
