package com.adaptionsoft.games.trivia.query;

import com.adaptionsoft.games.trivia.Category;
import com.adaptionsoft.games.trivia.event.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

import static java.util.Arrays.stream;
import static java.util.Optional.empty;

public class GameState implements EventsListener {
    final Players players;
    final Questions questions;

    Optional<Category> currentCategory;
    Optional<Player> currentPlayer;
    Optional<Integer> dice;

    public GameState() {
        this.players = new Players();
        this.questions = new Questions();

        this.currentCategory = empty();
        this.currentPlayer = empty();
        this.dice = empty();
    }

    public Category getCurrentCategory() {
        return currentCategory.orElse(null);
    }

    public Player getCurrentPlayer() {
        return currentPlayer.orElse(null);
    }

    public Map<Category, Integer> getQuestions() {
        SortedMap<Category, Integer> questions = new TreeMap<>();
        stream(Category.values()).forEach(category -> questions.put(category, this.questions.questions.get(category).size()));
        return questions;
    }

    public Map<String, Player> getPlayers() {
        return players.getPlayers();
    }

    public Integer getDice() {
        return dice.orElse(null);
    }

    @Override
    public void on(AnswerWasCorrect event) {
    }

    @Override
    public void on(AnswerWasNotCorrect event) {
    }

    @Override
    public void on(CategoryWas event) {
    }

    @Override
    public void on(CurrentPlayerWas event) {
    }

    @Override
    public void on(LocationWas event) {
    }

    @Override
    public void on(NewGoldCoinsCount event) {
    }

    @Override
    public void on(NewPlayerCount event) {
    }

    @Override
    public void on(PlayerWasAdded event) {
    }

    @Override
    public void on(PlayerWasGettingOutOfThePenaltyBox event) {
    }

    @Override
    public void on(PlayerWasNotGettingOutOfThePenaltyBox event) {
    }

    @Override
    public void on(PlayerWasSentToPenaltyBox event) {
    }

    @Override
    public void on(QuestionWasAsked event) {
    }

    @Override
    public void on(Rolled event) {
    }

    @Override
    public void on(UnknownEvent event) {
    }

    public static void main(String[] args) {
        GameState gameState = new GameState();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            in.lines().forEach(line -> EventsListener.dispatch(line, gameState));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
