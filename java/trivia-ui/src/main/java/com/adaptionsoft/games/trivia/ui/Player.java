package com.adaptionsoft.games.trivia.ui;

import java.util.Objects;
import java.util.OptionalInt;

import static java.lang.String.format;
import static java.util.OptionalInt.empty;

public class Player {
    private com.adaptionsoft.games.trivia.query.Player player;
    
    public final int[][] colors;

    private boolean currentPlayer;

    Player(String name, int[][] colors) {
        this(new com.adaptionsoft.games.trivia.query.Player(name), colors, false);
    }

    Player(com.adaptionsoft.games.trivia.query.Player player, int[][] colors, boolean isCurrentPlayer) {
        this.player = player.copy();
        this.colors = colors;
        this.currentPlayer = isCurrentPlayer;
    }

    void notCurrentPlayerAnymore() {
        currentPlayer = false;
    }

    void currentPlayer() {
        currentPlayer = true;
    }

    public boolean isCurrentPlayer() {
        return currentPlayer;
    }

    void locationWas(int location) {
        player.location(location);
    }

    void goldCoinsEarned(int number) {
        player.goldCoins(number);
    }

    void sentToPenaltyBox() {
        player.inPenaltyBox(true);
    }

    void gotOutOfPenaltyBox() {
        player.inPenaltyBox(false);
    }

    public OptionalInt location() {
        if (player.isInPenaltyBox()) {
            return empty();
        }

        return OptionalInt.of(player.getLocation());
    }

    public int goldCoins() {
        return player.getGoldCoins();
    }

    public boolean isOnPenaltyBox() {
        return player.isInPenaltyBox();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(currentPlayer, player.currentPlayer) &&
                Objects.equals(this.player, player.player) &&
                Objects.equals(colors, player.colors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player.getName());
    }

    @Override
    public String toString() {
        return format("%s @%s with %d gold coins%s%s",
                player.getName(),
                player.getLocation(),
                player.getGoldCoins(),
                currentPlayer ? " is current player" : "",
                player.isInPenaltyBox() ? " but on penalty box" : "");
    }

    public String getName() {
        return player.getName();
    }
}
