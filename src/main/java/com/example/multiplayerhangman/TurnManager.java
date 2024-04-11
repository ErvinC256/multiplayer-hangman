package com.example.multiplayerhangman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TurnManager {

    private final Logger logger = LoggerFactory.getLogger(PlayerRegistry.class);
    private final Scanner scanner = new Scanner(System.in);

    private Queue<Player> playerQueue = new LinkedList<>();

    public boolean performPlayerAssignment(Set<Player> players) {
        int numPlayersToBeAssigned = players.size();

        for (int i = 0; i < numPlayersToBeAssigned; ) {
            try {
                System.out.print("**Enter a player id: ");
                int playerId = Integer.parseInt(scanner.nextLine());

                if (playerId < 0 || playerId > players.size()) {
                    logger.error("Out of bound");
                    continue;
                }
                Optional<Player> playerOptional = players.stream().filter(player -> player.getId() == playerId).findFirst();

                if (playerOptional.isPresent()) {
                    Player player = playerOptional.get();
                    addPlayerToQueue(player);
                }
            } catch (NumberFormatException e) {
                logger.error("Invalid input");
            }
            i++;
        }
        return true;
    }

    public void displayPlayersInQueue() {
        System.out.print("Players currently in queue: ");

        if (playerQueue.isEmpty()) {
            System.out.print("---");
        } else {
            playerQueue.stream()
                    .sorted(Comparator.comparing(player -> player.getId()))
                    .forEach(player -> System.out.print(player.getId() + ". " + player.getName() + "  "));
        }

        System.out.println();
    }

    private boolean addPlayerToQueue(Player player) {

        if (playerQueue.stream().anyMatch(p -> p.getId() == player.getId())) {
            return false;
        } else {
            return playerQueue.offer(player);
        }
    }

    private void resetQueue() {
        playerQueue.clear();
    }

    // getters setters
    public Queue<Player> getPlayerQueue() {
        return playerQueue;
    }

    public void setPlayerQueue(Queue<Player> playerQueue) {
        this.playerQueue = playerQueue;
    }
}
