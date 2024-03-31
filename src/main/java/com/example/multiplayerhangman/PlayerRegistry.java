package com.example.multiplayerhangman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerRegistry {

    private final Logger log = LoggerFactory.getLogger(PlayerRegistry.class);

    private Set<Player> players = new HashSet<>();
    private int playerIndex = 0;

    public boolean registerPlayer(String name) {

        if (isPlayerRegistered(name)) {
            log.error("Player '{}' is already registered with registry.", name);
            return false;
        }

        Player player = new Player(name);
        player.setId(this.playerIndex);
        players.add(player);
        log.info("Player '{}' registered successfully with registry.", name);

        playerIndex++;
        return true;
    }

    public void unregisterPlayer(int playerIndex) {

        players.remove(playerIndex);
        log.info("Player '{}' un-registered successfully with registry.", playerIndex);
    }

    public void displayPlayerNames() {

        System.out.print("Registered Players: ");
        players.forEach(player -> System.out.print(String.valueOf(player.getId()) + ". " + player.getName() + "  "));
        System.out.println();
    }

    private boolean isPlayerRegistered(String name) {

        return players.stream().anyMatch(player -> player.getName().equals(name));
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

