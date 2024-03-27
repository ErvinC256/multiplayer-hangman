package com.example.multiplayerhangman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PlayerRegistry {

    private final Logger log = LoggerFactory.getLogger(PlayerRegistry.class);

    private List<Player> players = new ArrayList<>();
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

        players.forEach(player -> System.out.println(String.valueOf(player.getId()) + ". " + player.getName() + " "));
    }

    private boolean isPlayerRegistered(String name) {

        return players.stream().anyMatch(player -> player.getName().equals(name));
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }
}

