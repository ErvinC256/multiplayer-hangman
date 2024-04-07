package com.example.multiplayerhangman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PlayerRegistry {

    private final Logger logger = LoggerFactory.getLogger(PlayerRegistry.class);
    private final Scanner scanner = new Scanner(System.in);

    private Set<Player> players = new HashSet<>();
    private int playerIndex = 0;

    public boolean performRegistration() {

        boolean registered;

        System.out.println("***Enter name for player " + (getPlayerIndex() + 1) + ", or enter -1" + " to cancel :");
        String name = scanner.nextLine();

        if (name.equals("-1") || isPlayerRegistered(name)) {
            logger.error("Player '{}' not registered.", name);
            return registered = false;
        }

        registerPlayer(name);

        logger.info("Player '{}' registered successfully with registry.", name);

        return registered = true;
    }

    public boolean performDeRegistration() {

        boolean deRegistered;

        if (getPlayers().size() == 0) {
            System.out.println("No registered players.");
            return deRegistered = false;
        }
        System.out.println("***Enter a player id to be deleted :");
        displayPlayerNames();

        try {
            int playerId = Integer.parseInt(scanner.nextLine());
            deRegisterPlayer(playerId);

            logger.info("Player '{}' de-registered successfully with registry.", playerId);

        } catch (NumberFormatException e) {
            logger.error("Invalid input. Please enter a valid option.");
            return deRegistered = false;
        }

        return deRegistered = true;
    }

    public void displayPlayerNames() {

        System.out.println("Registered players : ");

        if (players.isEmpty()) {
            System.out.print("---");
        } else {
            players.stream()
                    .sorted(Comparator.comparing(player -> player.getId()))
                    .forEach(player -> System.out.print(String.valueOf(player.getId()) + ". " + player.getName() + "  "));
        }
        System.out.println();
    }

    public boolean isPlayerRegistered(String name) {

        return players.stream().anyMatch(player -> player.getName().equals(name));
    }

    public Player getPlayer(int playerId) {

        return players.stream()
                .filter(player -> player.getId() == playerId)
                .findFirst()
                .orElse(null);
    }

    private void registerPlayer(String name) {

        Player player = new Player(name);
        player.setId(this.playerIndex);
        players.add(player);

        this.playerIndex++;
    }

    private void deRegisterPlayer(int playerId) {
        Optional<Player> playerOptional = players.stream()
                .filter(player -> player.getId() == playerId)
                .findFirst();

        if (playerOptional.isPresent()) {
            players.remove(playerOptional.get());
            this.playerIndex--;
        }
    }

    // getters setters
    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}

