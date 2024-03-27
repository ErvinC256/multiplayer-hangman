package com.example.multiplayerhangman;

public class Player {
    private long id;
    private String name;
    private int wrongCount = 0;
    private int correctCount = 0;

    public Player(String name) {
        this.name = name;
    }

    public void incrementWrongCount() {
        this.wrongCount++;
    }

    public void incrementCorrectCount() {
        this.correctCount++;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWrongCount() {
        return wrongCount;
    }

    public void setWrongCount(int wrongCount) {
        this.wrongCount = wrongCount;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }
}
