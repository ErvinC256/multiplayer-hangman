package com.example.multiplayerhangman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class MultiplayerHangmanApplication implements CommandLineRunner {

	private final Logger logger = LoggerFactory.getLogger(MultiplayerHangmanApplication.class);
	private final Scanner scanner = new Scanner(System.in);

	private final PlayerRegistry playerRegistry = new PlayerRegistry();
	private static final int ANSWER = 52;
//	private boolean someoneHasGuessedIt = false;
	private final TurnManager turnManager = new TurnManager();

	public static void main(String[] args) {
		SpringApplication.run(MultiplayerHangmanApplication.class, args);
	}

	private void displayMainMenu() {

		int option = -1;

		do {
			try {
				System.out.println("-----------------------------");
				System.out.println("Welcome to the Multi-player Hangman Game!");
				System.out.println("-----------------------------");
				System.out.println("1. Register/Un-Register Player");
				System.out.println("2. Start Game");
				System.out.println("3. Exit");

				option = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				logger.error("Invalid input. Please enter a valid option.");
				continue;
			}

			switch (option) {
				case 1:
					displayRegistrationMenu();
					break;
				case 2:
					if (playerRegistry.getPlayers().size() < 2) {
						logger.error("You need at least a player to start the game");
						option = -1;
					} else {
						startGame();
					}
					break;
				case 3:
					System.out.println("Exiting game...");
					System.exit(0);
					break;
				default:
					break;
			}
		} while (option < 1 || option > 3);
	}

	private void displayRegistrationMenu() {

		int option = -1;

		do {
			try {
				System.out.println("-----------------------------");
				System.out.println("Player Registration");
				System.out.println("1. Register Player");
				System.out.println("2. Un-Register Player");
				System.out.println("3. View Registered Players");
				System.out.println("4. Back");

				// Parse user input and handle potential NumberFormatException
				option = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				logger.error("Invalid input. Please enter a valid option.");
				continue;
			}

			switch (option) {
				case 1:
					System.out.println(String.format("***Enter name for player %s, or enter -1 to cancel :  ", playerRegistry.getPlayerIndex()));
					String name = scanner.nextLine();

					if (name.equals("-1")) {
						break;
					}
					boolean registered = playerRegistry.registerPlayer(name);

					if (registered) {
						logger.info("Player '{}' registered successfully with registry.", name);
					} else {
						logger.info("Player '{}' not registered.", name);
					}
					option = -1;
					break;
				case 2:
					System.out.println("***Enter a player id to be deleted:");
					playerRegistry.displayPlayerNames();

					try {
						int playerId = Integer.parseInt(scanner.nextLine());
						boolean unregistered = playerRegistry.unregisterPlayer(playerId);

						if (unregistered) {
							logger.info("Player '{}' un-registered successfully with registry.", playerId);
						} else {
							logger.info("Player '{}' not un-registered.", playerId);
						}
						option = -1;
						break;
					} catch (NumberFormatException e) {
						logger.error("Invalid input. Please enter a valid option.");
						option = -1;
						break;
					}
				case 3:
					playerRegistry.displayPlayerNames();
					option = -1;
					break;
				case 4:
					displayMainMenu();
					break;
				default:
					break;

			}

		} while (option < 1 || option > 3);

	}

	private void startGame() {

		System.out.println("Guessing game has started : ");
		System.out.println("Let's begin...");

		assignPlayers();

		Player firstPlayer = turnManager.getPlayerQueue().poll();
		System.out.println(String.format("Please suggest a number, %s : ", firstPlayer.getName()));
		int number = getValidInput();

		boolean someoneHasGuessedIt = false;
		int numRemainingPlayers = turnManager.getPlayerQueue().size();

		for (int i = 0; i < numRemainingPlayers; i++) {
			Player currentPlayer = turnManager.getPlayerQueue().poll();
			System.out.println(String.format("Please guess a number, %s : ", currentPlayer.getName()));
			int guess = getValidInput();

			if (guess == number) {
				someoneHasGuessedIt = true;
				break;
			}
		}

		if (someoneHasGuessedIt) {
			System.out.println("Someone has guessed it!");
		}
	}

	private void assignPlayers() {
		Queue<Player> playerQueue = new LinkedList<>();
		Set<Player> playersToBeAssigned = playerRegistry.getPlayers();
		int numPlayersToBeAssigned = playersToBeAssigned.size();

		for (int i = 0; i < numPlayersToBeAssigned; i++) {
			turnManager.displayPlayersInQueue();

			System.out.print("Players currently not in queue : ");
			for (Player player : playersToBeAssigned) {
				System.out.print(player.getId() + ". " + player.getName() + "  ");
			}
			System.out.println();

			System.out.print("Choose a player index : ");
			int id = Integer.parseInt(scanner.nextLine());

			Player player = playerRegistry.getPlayer(id);
			playersToBeAssigned.remove(player);
			turnManager.addPlayerToQueue(player);

		}

		System.out.println("Players after assignment :");
		turnManager.displayPlayersInQueue();
	}

	private void playRound(Player player) {

		int guess = -1;

		try {
			System.out.println("Enter your guess, " + player.getName() + " : ");
			guess = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			logger.error("Invalid input. Please enter a valid option.");
		}

		if (guess == ANSWER) {
			player.incrementCorrectCount();
			displayScoreBoard();
			System.exit(0);
		} else {
			player.incrementWrongCount();
			System.out.println("Incorrect guess. Try again next round.");
		}
	}

	private void displayScoreBoard() {
		System.out.println("***ScoreBoard***");

		playerRegistry.getPlayers().forEach(player -> {
			System.out.println(player.getName() + " has correct count of " + player.getCorrectCount()
					+ " and wrong count of " + player.getWrongCount());
		});
	}

	private int getValidInput() {
		int input = Integer.MIN_VALUE;
		try {
			input = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			logger.error("Invalid input. Please enter a valid number.");
		}
		return input;
	}

	@Override
	public void run(String... args) throws Exception {

		displayMainMenu();
	}

}
