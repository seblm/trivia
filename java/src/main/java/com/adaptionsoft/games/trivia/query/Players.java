package com.adaptionsoft.games.trivia.query;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableSortedMap;
import static java.util.stream.Collectors.joining;

public class Players {
    private final SortedMap<String, Player> players;
    
    public Players() {
        this.players = new TreeMap<>();
    }

    public Player add(String name) {
        Player newPlayer = new Player(name);
        players.put(name, newPlayer);
        return newPlayer;
    }

    public Optional<Player> get(String playerName) {
        return Optional.ofNullable(players.get(playerName));
    }
    
    public Map<String, Player> getPlayers() {
        return unmodifiableSortedMap(players);
    }
    
    public Players copy() {
        Players copy = new Players();
        players.forEach((name, player) -> copy.players.put(name, player.copy()));
        return copy;
    }

    @Override
    public String toString() {
        return players.values().stream().map(Player::toString).collect(joining(", "));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Players players1 = (Players) o;
        return Objects.equals(players, players1.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(players);
    }
}
