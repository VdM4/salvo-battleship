package salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);

		Game game1 = new Game();

		System.out.println(("Did you ever hear the tragedy of Darth Plagueis the wise?"));
	}


	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository,
									  GameRepository gameRepository,
									  GamePlayerRepository gamePlayerRepository,
									  ShipRepository shipRepository,
									  SalvoRepository salvoRepository,
									  ScoreRepository scoreRepository) {
		return (args) -> {


			Game game1 = new Game();
			Game game2 = new Game();
			Game game3 = new Game();
			Game game4 = new Game();

			Player player1 = new Player("j.bauer@ctu.gov", "24");
			Player player2 = new Player("c.obrian@ctu.gov", "42");
			Player player3 = new Player("kim_bauer@gmail.gov", "kb");
			Player player4 = new Player("t.almeida@ctu.gov", "mole");

			GamePlayer gamePlayer1 = new GamePlayer(player1, game1);
			GamePlayer gamePlayer2 = new GamePlayer(player2, game1);

			GamePlayer gamePlayer3 = new GamePlayer(player3, game2);
			GamePlayer gamePlayer4 = new GamePlayer(player4, game2);

			GamePlayer gamePlayer5 = new GamePlayer(player4, game3);
			GamePlayer gamePlayer6 = new GamePlayer(player1, game3);

			GamePlayer gamePlayer7 = new GamePlayer(player3, game4);
			GamePlayer gamePlayer8 = new GamePlayer(player2, game4);

			ArrayList<String> ShipLoc1 = new ArrayList<>(Arrays.asList("A1","A2","A3","A4","A5"));
			ArrayList<String> ShipLoc2 = new ArrayList<>(Arrays.asList("I3","I4","I5","I6"));
			ArrayList<String> ShipLoc3 = new ArrayList<>(Arrays.asList("C10","D10","E10"));
			ArrayList<String> ShipLoc4 = new ArrayList<>(Arrays.asList("E4","F4","G4"));
			ArrayList<String> ShipLoc5 = new ArrayList<>(Arrays.asList("F7","F8"));
			ArrayList<String> ShipLoc6 = new ArrayList<>(Arrays.asList("J7","J8","J9"));
			ArrayList<String> ShipLoc7 = new ArrayList<>(Arrays.asList("G1","H1","I1"));

			Ship b1 = new Ship("Carrier", ShipLoc1);
			Ship b2 = new Ship("Battleship", ShipLoc2);
			Ship b3 = new Ship("Submarine", ShipLoc3);
			Ship b4 = new Ship("Destroyer", ShipLoc4);
			Ship b5 = new Ship("Patrol Boat", ShipLoc5);

			Ship b6 = new Ship("Carrier", ShipLoc1);
			Ship b7 = new Ship("Battleship", ShipLoc2);
			Ship b8 = new Ship("Submarine", ShipLoc6);
			Ship b9 = new Ship("Destroyer", ShipLoc7);
			Ship b10 = new Ship("Patrol Boat", ShipLoc5);


			gamePlayer1.addShip(b3);
			gamePlayer1.addShip(b4);
			gamePlayer2.addShip(b8);
			gamePlayer2.addShip(b9);

			ArrayList<String> SalvoLoc1 = new ArrayList<>(Arrays.asList("B5","C5","F1"));
			ArrayList<String> SalvoLoc2 = new ArrayList<>(Arrays.asList("F2","E4","G1"));
			ArrayList<String> SalvoLoc3 = new ArrayList<>(Arrays.asList("B3","F5","C1"));
			ArrayList<String> SalvoLoc4 = new ArrayList<>(Arrays.asList("A2","A5","G6"));
			ArrayList<String> SalvoLoc5 = new ArrayList<>(Arrays.asList("A3","A6","D8"));
			ArrayList<String> SalvoLoc6 = new ArrayList<>(Arrays.asList("B8","C6","F8"));

			Salvo fire1 = new Salvo(1,SalvoLoc1);
			Salvo fire2 = new Salvo(1,SalvoLoc2);
			Salvo fire3 = new Salvo(2,SalvoLoc3);
			Salvo fire4 = new Salvo(2,SalvoLoc4);
			Salvo fire5 = new Salvo(3,SalvoLoc5);
			Salvo fire6 = new Salvo(3,SalvoLoc6);

			gamePlayer1.addSalvo(fire1);
			gamePlayer2.addSalvo(fire2);
			gamePlayer1.addSalvo(fire3);
			gamePlayer2.addSalvo(fire4);
			gamePlayer1.addSalvo(fire5);
			gamePlayer2.addSalvo(fire6);

			Score score1 = new Score(gamePlayer1,1);
			Score score2 = new Score(gamePlayer2,0);
			Score score3 = new Score(gamePlayer3,0.5f);
			Score score4 = new Score(gamePlayer4,0.5f);
			Score score5 = new Score(gamePlayer5, 0.5f);
			Score score6 = new Score(gamePlayer6, 0.5f);
			Score score7 = new Score(gamePlayer7, 1);
			Score score8 = new Score(gamePlayer8, 0);




			//SAVES

			playerRepository.save(player1);
			playerRepository.save(player2);
			playerRepository.save(player3);
			playerRepository.save(player4);

			gameRepository.save(game1);
			gameRepository.save(game2);
			gameRepository.save(game3);
			gameRepository.save(game4);

			gamePlayerRepository.save(gamePlayer1);
			gamePlayerRepository.save(gamePlayer2);
			gamePlayerRepository.save(gamePlayer3);
			gamePlayerRepository.save(gamePlayer4);
			gamePlayerRepository.save(gamePlayer5);
			gamePlayerRepository.save(gamePlayer6);
			gamePlayerRepository.save(gamePlayer7);
			gamePlayerRepository.save(gamePlayer8);

			shipRepository.save(b1);
			shipRepository.save(b2);
			shipRepository.save(b3);
			shipRepository.save(b4);
			shipRepository.save(b5);
			shipRepository.save(b6);
			shipRepository.save(b7);
			shipRepository.save(b8);
			shipRepository.save(b9);
			shipRepository.save(b10);

			salvoRepository.save(fire1);
			salvoRepository.save(fire2);
			salvoRepository.save(fire3);
			salvoRepository.save(fire4);
			salvoRepository.save(fire5);
			salvoRepository.save(fire6);

			scoreRepository.save(score1);
			scoreRepository.save(score2);
			scoreRepository.save(score3);
			scoreRepository.save(score4);
			scoreRepository.save(score5);
			scoreRepository.save(score6);
			scoreRepository.save(score7);
			scoreRepository.save(score8);



		};
	}

}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	PlayerRepository playerRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}

	@Bean
	UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

				Player player = playerRepository.findByUserName(username);

				if (player.getUserName() != null) {
					return new User(player.getUserName(), player.getPassword(), AuthorityUtils.createAuthorityList("USER"));
				} else {
					throw new UsernameNotFoundException("Unknown user: " + username);
				}
			}
		};
	}
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
					.antMatchers("/games.html").permitAll()
					.antMatchers("/scripts/games.js").permitAll()
					.antMatchers("/css/**").permitAll()
					.antMatchers("/assets/**").permitAll()
					.antMatchers("/api/games").permitAll()
					.antMatchers("/api/players").permitAll()
					.anyRequest().fullyAuthenticated()
					.and()
					.formLogin()

					.usernameParameter("username")
					.passwordParameter("password")
					.loginPage("/api/login")

					.and()
					.logout()
					.logoutUrl("/api/logout");

		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}
