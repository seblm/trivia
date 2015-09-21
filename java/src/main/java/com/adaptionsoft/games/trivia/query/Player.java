package com.adaptionsoft.games.trivia.query;

import java.util.Objects;

public class Player {
    private final String name;
    
    private int location;
    private int goldCoins;
    private boolean inPenaltyBox;

    public Player(String name) {
        this.name = name;
        this.location = 0;
        this.goldCoins = 0;
        this.inPenaltyBox = false;
    }

    public String getName() {
        return name;
    }

    public int getLocation() {
        return location;
    }
    
    public int getGoldCoins() {
        return goldCoins;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public Player location(int location) {
        this.location = location;
        return this;
    }

    public Player goldCoins(int goldCoins) {
        this.goldCoins = goldCoins;
        return this;
    }

    public Player inPenaltyBox(boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
        return this;
    }
    
    public Player copy() {
        Player copy = new Player(name);
        copy.location = location;
        copy.goldCoins = goldCoins;
        copy.inPenaltyBox = inPenaltyBox;
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(location, player.location) &&
                Objects.equals(goldCoins, player.goldCoins) &&
                Objects.equals(inPenaltyBox, player.inPenaltyBox) &&
                Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, location, goldCoins, inPenaltyBox);
    }

    @Override
    public String toString() {
        return "Player(" + name + " @" + location + " $" + goldCoins + (inPenaltyBox ? " #" : "  ") + ")";
    }
}
