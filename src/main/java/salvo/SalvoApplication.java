package salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.yaml.snakeyaml.events.Event;

import javax.persistence.Id;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);

		Game game1 = new Game();

		System.out.println(game1.toString());
	}


	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository,
									  GameRepository gameRepository,
									  GamePlayerRepository gamePlayerRepository) {
		return (args) -> {


			Game game1 = new Game();
			Game game2 = new Game();


			Player player1 = new Player("vincent.chase@mail.com");
			Player player2 = new Player("gerard.sputnik@mail.com");
			Player player3 = new Player("vdm@mail.com");
			Player player4 = new Player("albert.crazyman@mail.com");

			GamePlayer gamePlayer1 = new GamePlayer(player1, game1);
			GamePlayer gamePlayer2 = new GamePlayer(player2, game1);
			GamePlayer gamePlayer3 = new GamePlayer(player3, game2);
			GamePlayer gamePlayer4 = new GamePlayer(player4, game2);

			playerRepository.save(player1);
			playerRepository.save(player2);
			playerRepository.save(player3);
			playerRepository.save(player4);

			gameRepository.save(game1);
			gameRepository.save(game2);

			gamePlayerRepository.save(gamePlayer1);
			gamePlayerRepository.save(gamePlayer2);
			gamePlayerRepository.save(gamePlayer3);
			gamePlayerRepository.save(gamePlayer4);


			/*
			playerRepository.save(new Player("thev2d2@gmail.com"));
			playerRepository.save(new Player("vince.clapes@gmail.com"));
			playerRepository.save(new Player("gerard.deharo@gmail.com"));
			playerRepository.save(new Player("albert.elloco@gmail.com"));
			playerRepository.save(new Player("bryn.elcolgado@gmail.com"));


			gameRepository.save(new Game());
			gameRepository.save(new Game());
			gameRepository.save(new Game());
			gameRepository.save(new Game());
			gameRepository.save(new Game());


			gamePlayerRepository.save(new GamePlayer());
			gamePlayerRepository.save(new GamePlayer());
			*/


		};
	}





}
