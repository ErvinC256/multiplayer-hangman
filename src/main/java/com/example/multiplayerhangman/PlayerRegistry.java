package com.example.multiplayerhangman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PlayerRegistry {

    private final Logger logger = LoggerFactory.getLogger(PlayerRegistry.class);
    private final Scanner scanner = new Scanner(System.in);

    private Set<Player> players = new HashSet<>();
    private int currentPlayerIndex = 0;

    public boolean performRegistration() {

        System.out.println(
                "***Enter name for player " + (getCurrentPlayerIndex() + 1) + ", or enter -1" + " to cancel :");
        String name = scanner.nextLine();

        if (name.equals("-1")) {
            return false;
        }

        if (isPlayerRegistered(name)) {
            logger.error("Player '{}' not registered", name);
            return false;
        }

        registerPlayer(name);

        logger.info("Player '{}' registered", name);

        return false;
    }

    public boolean performDeRegistration() {

        if (getPlayers().size() == 0) {
            logger.error("No registered players");
            return false;
        }
        System.out.println("***Enter a player id to be deleted :");
        displayRegisteredPlayers();

        try {
            int playerId = Integer.parseInt(scanner.nextLine());

            if (playerId < 0 || playerId > this.currentPlayerIndex - 1) {
                logger.error("Out of bound");
                return false;
            }
            deRegisterPlayer(playerId);

            logger.info("Player '{}' de-registered", playerId);

        } catch (NumberFormatException e) {
            logger.error("Invalid input. Please enter a valid option");
            return false;
        }

        currentPlayerIndex--;
        return true;
    }

    public void displayRegisteredPlayers() {

        System.out.println("Registered players : ");

        if (players.isEmpty()) {
            System.out.print("---");
        } else {
            players.stream()
                    .sorted(Comparator.comparing(player -> player.getId()))
                    .forEach(player -> System.out
                            .print(String.valueOf(player.getId()) + ". " + player.getName() + "  "));
        }
        System.out.println();
    }

    private boolean isPlayerRegistered(String name) {

        return players.stream().anyMatch(player -> player.getName().equals(name));
    }

    private void registerPlayer(String name) {

        Player player = new Player(name);
        player.setId(this.currentPlayerIndex);
        players.add(player);

        this.currentPlayerIndex++;
    }

    private void deRegisterPlayer(int playerId) {
        Optional<Player> playerOptional = players.stream()
                .filter(player -> player.getId() == playerId)
                .findFirst();

        if (playerOptional.isPresent()) {
            players.remove(playerOptional.get());
            this.currentPlayerIndex--;
        }
    }

    // getters setters
    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }
}
