package com.example.multiplayerhangman;

import java.util.LinkedList;
import java.util.Queue;

public class TurnManager {

    private Queue<Player> playerQueue = new LinkedList<>();

    public boolean addPlayerToQueue(Player player) {
        return playerQueue.offer(player);
    }

    public void displayPlayersInQueue() {

        System.out.print("Players currently in queue : ");
        this.playerQueue.forEach(player -> System.out.print(player.getId() + ". " + player.getName() + "  "));

        System.out.println();
    }
}
