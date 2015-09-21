package com.adaptionsoft.games.trivia.query;

import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

import static java.util.Collections.unmodifiableSortedMap;

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
}
