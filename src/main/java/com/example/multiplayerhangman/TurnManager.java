package com.example.multiplayerhangman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TurnManager {

    private final Logger logger = LoggerFactory.getLogger(PlayerRegistry.class);
    private final Scanner scanner = new Scanner(System.in);

    private Queue<Player> playerQueue = new LinkedList<>();

    public void performPlayerAssignment(Set<Player> players) {
        int numPlayersLeft = players.size();

        while (numPlayersLeft > 0) {

            int[] playerIds = players.stream()
                                        .mapToInt(player -> (int) player.getId())
                                        .toArray();

            try {
                System.out.print("**Enter a player id, or enter -1 to cancel : ");
                int playerId = Integer.parseInt(scanner.nextLine());

                if (playerId == -1) {
                    break;
                }

                if (playerId < 0 || !Arrays.stream(playerIds).anyMatch(id -> id == playerId)) {
                    continue;
                }

                for (Player player : players) {
                    if (player.getId() == playerId) {
                        addPlayerToQueue(player);
                        logger.info("Player '{}' queued ", player.getId());

                        players.remove(player);
                        break;
                    }
                }

                numPlayersLeft--;

            } catch (NumberFormatException e) {
                logger.error("Invalid input");
            }

        }
    }

    public void displayPlayersInQueue() {
        System.out.print("Players currently in queue : ");

        if (playerQueue.isEmpty()) {
            System.out.print("---");
        } else {
            playerQueue.stream()
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
