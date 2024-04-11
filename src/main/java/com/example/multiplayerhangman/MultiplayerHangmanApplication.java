package com.example.multiplayerhangman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class MultiplayerHangmanApplication implements CommandLineRunner {

	private final Logger logger = LoggerFactory.getLogger(MultiplayerHangmanApplication.class);
	private final Scanner scanner = new Scanner(System.in);

	private final PlayerRegistry playerRegistry = new PlayerRegistry();
	private final TurnManager turnManager = new TurnManager();

	public static void main(String[] args) {
		SpringApplication.run(MultiplayerHangmanApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		displayMainMenu();
	}

	private void displayMainMenu() {
		do {
			printMainMenu();
			try {
				int option = Integer.parseInt(scanner.nextLine());
				switch (option) {
					case 1:
						displayRegistrationMenu();
						break;
					case 2:
						if (playerRegistry.getPlayers().size() < 2) {
							logger.error("You need at least a player to start the game");
						} else {
							startGame();
						}
						break;
					case 3:
						System.out.println("Exiting game...");
						System.exit(0);
						break;
					default:
						logger.error("Out of bound");
						break;
				}
			} catch (NumberFormatException e) {
				logger.error("Invalid input");
			}
		} while (true);
	}

	private void printMainMenu() {
		System.out.println("-----------------------------------------");
		System.out.println("Welcome to the Multi-player Hangman Game!");
		System.out.println("-----------------------------------------");
		System.out.println("1. Register/De-Register Player");
		System.out.println("2. Start Game");
		System.out.println("3. Exit");
	}

	private void displayRegistrationMenu() {
		do {
			printRegistrationMenu();
			try {
				int option = Integer.parseInt(scanner.nextLine());
				switch (option) {
					case 1:
						playerRegistry.performRegistration();
						break;
					case 2:
						playerRegistry.performDeRegistration();
						break;
					case 3:
						playerRegistry.displayRegisteredPlayers();
						break;
					case 4:
						displayMainMenu();
						break;
					default:
						logger.error("Out of bound");
						break;
				}
			} catch (NumberFormatException e) {
				logger.error("Invalid input");
			}
		} while (true);
	}

	private void printRegistrationMenu() {
		System.out.println("-------------------");
		System.out.println("Player Registration");
		System.out.println("1. Register Player");
		System.out.println("2. Un-Register Player");
		System.out.println("3. View Registered Players");
		System.out.println("4. Back");
	}

	private void startGame() {

		System.out.println("-------------------------------");
		System.out.println("---Guessing game has started---");
		System.out.println("-------------------------------");

		playerRegistry.displayRegisteredPlayers();
		turnManager.performPlayerAssignment(playerRegistry.getPlayers());
		turnManager.displayPlayersInQueue();

		System.out.println("-------------------------------");

		int numOfPlayerInQueue = turnManager.getPlayerQueue().size();

		for (int i = 0; i < numOfPlayerInQueue; i++) {
			Player firstPlayer = turnManager.getPlayerQueue().poll();
			Queue<Player> guessPlayerQueue = new LinkedList<>(turnManager.getPlayerQueue().stream()
					.filter(player -> player.getId() != firstPlayer.getId())
					.collect(Collectors.toList()));

			System.out.println(String.format("**Please suggest a word, %s : ", firstPlayer.getName()));
			String word = scanner.nextLine();

			boolean wordHasBeenRevealed = false;

			while (!wordHasBeenRevealed) {
				Player currentPlayer = guessPlayerQueue.poll();
				System.out.println(String.format("**Please guess a number, %s : ", currentPlayer.getName()));
				char character = scanner.nextLine().charAt(0);

				if (word.indexOf(character) != -1) {
					word = word.chars()
							.mapToObj(c -> (char) c == character ? '#' : (char) c)
							.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
							.toString();
					currentPlayer.incrementCorrectCount();
				} else {
					currentPlayer.incrementWrongCount();
				}

				System.out.println(word);

				long numOfHash = word.chars().filter(c -> c == '#').count();
				wordHasBeenRevealed = numOfHash == word.length();

				guessPlayerQueue.offer(currentPlayer);
			}
			System.out.println("The word has been revealed!");

			turnManager.getPlayerQueue().offer(firstPlayer);
		}

		Player[] playerArray = turnManager.getPlayerQueue().toArray(new Player[0]);
		displayScoreBoard(playerArray);
	}

	private void displayScoreBoard(Player[] arr) {
		System.out.println("***ScoreBoard***");

		Player[] sortedArr = selectionSortPlayerByDescCorrectCount(arr);
	}

	private Player[] selectionSortPlayerByDescCorrectCount(Player[] arr) {
		int n = arr.length;

		for (int i = 0; i < n - 1; i++) {
			int maxIndex = i;
			int maxCorrectCount = arr[i].getCorrectCount();
			int maxWrongCount = arr[i].getWrongCount();

			for (int j = i + 1; j < n; j++) {
				int currentCorrectCount = arr[j].getCorrectCount();
				int currentWrongCount = arr[j].getWrongCount();

				if (currentCorrectCount > maxCorrectCount ||
						(currentCorrectCount == maxCorrectCount && currentWrongCount < maxWrongCount)) {
					maxIndex = j;
				}
			}

			if (!maxIndex == i) {
				Player temp = arr[i];
				arr[i] = arr[maxIndex];
				arr[maxIndex] = temp;
			}
		}

		return arr;
	}

}
