package com.example.multiplayerhangman;

import java.util.LinkedList;
import java.util.Queue;

public class TurnManager {

    private Queue<Player> playerQueue = new LinkedList<>();

    public boolean addPlayerToQueue(Player player) {

        if (playerQueue.stream().anyMatch(p -> p.getId() == player.getId())) {
            return false;
        } else {
            return playerQueue.offer(player);
        }
    }

    public void displayPlayersInQueue() {

        System.out.print("Players currently in queue : ");

        if (playerQueue.isEmpty()) {
            System.out.print("---");
        } else {
            this.playerQueue.forEach(player -> System.out.print(player.getId() + ". " + player.getName() + "  "));
        }

        System.out.println();
    }

    public void resetQueue() {
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
