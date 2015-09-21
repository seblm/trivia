package com.adaptionsoft.games.trivia.query;

import com.adaptionsoft.games.trivia.Category;
import com.adaptionsoft.games.trivia.event.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

import static java.util.Arrays.stream;
import static java.util.Optional.empty;

public class GameState implements EventsListener {
    private static final String PROMPT = "GameState> ";

    final Players players;
    final Questions questions;

    Optional<Category> currentCategory;
    Optional<Player> currentPlayer;
    Optional<Integer> dice;

    private final ObjectMapper mapper;
    private final Optional<Path> file;

    public GameState() {
        this(true);
    }

    public GameState(boolean haveToWrite) {
        this(new Players(), new Questions(), empty(), empty(), empty(), haveToWrite);
    }

    private GameState(Players players, Questions questions, Optional<Category> currentCategory, Optional<Player> currentPlayer, Optional<Integer> dice, boolean haveToWrite) {
        this.players = players;
        this.questions = questions;
        this.currentCategory = currentCategory;
        this.currentPlayer = currentPlayer;
        this.dice = dice;
        mapper = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .enable(SerializationFeature.INDENT_OUTPUT);
        file = haveToWrite ? Optional.of(Paths.get("target", "game-state.json")) : empty();
        write();
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
        currentCategory = Optional.of(event.category);
        write();
    }

    @Override
    public void on(CurrentPlayerWas event) {
        currentPlayer = players.get(event.player);
        write();
    }

    @Override
    public void on(LocationWas event) {
        players.get(event.player).ifPresent(player -> player.location(event.newLocation));
        write();
    }

    @Override
    public void on(NewGoldCoinsCount event) {
        players.get(event.player).ifPresent(player -> player.goldCoins(event.newGoldCoinsCount));
        write();
    }

    @Override
    public void on(NewPlayerCount event) {
    }

    @Override
    public void on(PlayerWasAdded event) {
        players.add(event.name);
        write();
    }

    @Override
    public void on(PlayerWasGettingOutOfThePenaltyBox event) {
        players.get(event.player).ifPresent(player -> player.inPenaltyBox(false));
        write();
    }

    @Override
    public void on(PlayerWasNotGettingOutOfThePenaltyBox event) {
    }

    @Override
    public void on(PlayerWasSentToPenaltyBox event) {
        players.get(event.player).ifPresent(player -> player.inPenaltyBox(true));
        write();
    }

    @Override
    public void on(QuestionWasAsked event) {
        questions.remove(event.category, event.questionNumber);
        write();
    }

    @Override
    public void on(Rolled event) {
        dice = Optional.of(event.roll);
        write();
    }

    @Override
    public void on(UnknownEvent event) {
    }

    private void write() {
        file.map(Path::toFile).ifPresent(fileToWrite -> {
            try {
                mapper.writeValue(fileToWrite, this);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }

    public static void main(String[] args) {
        GameState gameState = new GameState();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print(PROMPT);
            in.lines().forEach(line -> {
                if ("exit".equalsIgnoreCase(line.trim())) {
                    System.exit(0);
                }
                Event event = EventsListener.dispatch(line, gameState);
                System.out.format("%s%n%n%s", event, PROMPT);
            });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
