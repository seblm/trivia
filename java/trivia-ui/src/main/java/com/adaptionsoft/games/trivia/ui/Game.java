package com.adaptionsoft.games.trivia.ui;

import com.adaptionsoft.games.trivia.event.CategoryWas;
import com.adaptionsoft.games.trivia.event.CurrentPlayerWas;
import com.adaptionsoft.games.trivia.event.PlayerWasAdded;
import com.adaptionsoft.games.trivia.event.QuestionWasAsked;
import com.adaptionsoft.games.trivia.query.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.adaptionsoft.games.trivia.ui.TriviaClient.MAX_NUMBER_OF_PLAYERS;
import static java.util.Collections.unmodifiableList;

public class Game {

    private final List<Player> players;

    private GameState state;

    Game() {
        this.state = new GameState(false);
        this.players = new ArrayList<>();
    }

    void restore(GameState state) {
        this.state = state;
        this.players.clear();
        state.getPlayers().values().forEach(player ->
                players.add(new Player(player, colors(players.size()), player.getName().equals(Optional.ofNullable(state.getCurrentPlayer()).map(com.adaptionsoft.games.trivia.query.Player::getName).orElse("")))));
    }

    void playerAdded(String name) {
        if (players.size() < MAX_NUMBER_OF_PLAYERS) {
            players.add(new Player(name, colors(players.size())));
            state.on(new PlayerWasAdded(name));
        }
    }

    private int[][] colors(int index) {
        return new int[][]{fontColor(index), pieceColor(index)};
    }

    public List<Player> players() {
        return unmodifiableList(players);
    }

    void currentPlayerChanged(String name) {
        getCurrentPlayer().ifPresent(Player::notCurrentPlayerAnymore);
        state.on(new CurrentPlayerWas(name));
        getCurrentPlayer().ifPresent(Player::currentPlayer);
    }

    private Optional<Player> getCurrentPlayer() {
        return findPlayerByName(Optional.ofNullable(state.getCurrentPlayer()).map(com.adaptionsoft.games.trivia.query.Player::getName).orElse(""));
    }

    void locationChanged(String playerName, int location) {
        findPlayerByName(playerName).ifPresent(player -> player.locationWas(location));
    }

    void categoryChanged(Category category) {
        state.on(new CategoryWas(com.adaptionsoft.games.trivia.Category.valueOf(camelCase(category.name()))));
    }

    private String camelCase(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
    }

    void questionAsked(Category identifiedCategory, int number) {
        categoryChanged(identifiedCategory);
        state.on(new QuestionWasAsked(com.adaptionsoft.games.trivia.Category.valueOf(camelCase(state.getCurrentCategory().name())), number));
    }

    void goldCoinsEarned(String playerName, int number) {
        findPlayerByName(playerName).ifPresent(player -> player.goldCoinsEarned(number));
    }

    void playerSentToPenaltyBox(String playerName) {
        findPlayerByName(playerName).ifPresent(Player::sentToPenaltyBox);
    }

    void playerGotOutOfPenaltyBox(String playerName) {
        findPlayerByName(playerName).ifPresent(Player::gotOutOfPenaltyBox);
    }

    public Stream<Integer> questionsFor(Category category) {
        int start = 50 - state.getQuestions().get(com.adaptionsoft.games.trivia.Category.valueOf(camelCase(category.name())));
        return IntStream.range(start, 50).mapToObj(i -> i);
    }

    public boolean isCurrentCard(Category category, Integer question) {
        return Optional.ofNullable(state.getCurrentCategory())
                .filter(currentCategory -> currentCategory.name().equalsIgnoreCase(category.name()))
                .map(currentCategory -> 50 - state.getQuestions().get(currentCategory))
                .filter(questionForCurrentCategory -> questionForCurrentCategory.equals(question))
                .isPresent();
    }

    private Optional<Player> findPlayerByName(String name) {
        return players.stream().filter(player -> player.getName().equals(name)).findFirst();
    }

    private static int[] pieceColor(int index) {
        switch (index % 5) {
            case 0:
                return new int[]{204, 102, 255};
            case 1:
                return new int[]{255, 111, 207};
            case 2:
                return new int[]{255, 204, 102};
            case 3:
                return new int[]{102, 204, 255};
            case 4:
            default:
                return new int[]{204, 255, 102};
        }
    }

    private static int[] fontColor(int index) {
        switch (index % 5) {
            case 0:
                return new int[]{128, 0, 255};
            case 1:
                return new int[]{255, 0, 128};
            case 2:
                return new int[]{255, 128, 0};
            case 3:
                return new int[]{0, 128, 255};
            case 4:
            default:
                return new int[]{128, 255, 0};
        }
    }
}
