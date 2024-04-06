package com.example.multiplayerhangman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PlayerRegistry {

    private final Logger log = LoggerFactory.getLogger(PlayerRegistry.class);

    private Set<Player> players = new HashSet<>();
    private int playerIndex = 0;

    public boolean registerPlayer(String name) {

        if (isPlayerRegistered(name)) {

            return false;
        }

        Player player = new Player(name);
        player.setId(this.playerIndex);
        players.add(player);

        playerIndex++;
        return true;
    }

    public boolean unregisterPlayer(int playerId) {

        Optional<Player> playerOptional = players.stream()
                                                .filter(player -> player.getId() == playerId)
                                                .findFirst();
        if (playerOptional.isPresent()) {
            players.remove(playerOptional.get());
            return true;
        }

        return false;
    }

    public void displayPlayerNames() {

        System.out.print("Registered Players: ");
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

