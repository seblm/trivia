package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.trivia.event.*;
import com.adaptionsoft.games.uglytrivia.Game;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.NoSuchElementException;
import com.adaptionsoft.games.trivia.event.EventsListener;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.StrictAssertions.catchThrowable;

public class GameTest {

    @Rule
    public EventStore eventStore = new EventStore(EventsListener::toEvent);

    private Game game;

    @Before
    public void createGame() {
        game = new Game();
    }

    @Test
    public void should_not_be_playable_without_any_players() {
        boolean isPlayable = game.isPlayable();

        assertThat(isPlayable).isFalse();
    }

    @Test
    public void should_not_be_playable_with_one_player() {
        game.add("player");
        boolean isPlayable = game.isPlayable();

        assertThat(isPlayable).isFalse();
    }

    @Test
    public void should_be_playable_with_two_players() {
        game.add("player 1");
        game.add("player 2");
        boolean isPlayable = game.isPlayable();

        assertThat(isPlayable).isTrue();
    }

    @Test
    public void should_add_one_player() {
        boolean playerAdded = game.add("player");

        assertThat(playerAdded).isTrue();
        assertThat(eventStore.events()).containsExactly(
                new PlayerWasAdded("player"),
                new NewPlayerCount(1));
    }

    @Test
    public void should_add_two_players_with_same_name() {
        game.add("player");
        game.add("player");

        assertThat(eventStore.events(PlayerWasAdded.class)).containsExactly(
                new PlayerWasAdded("player"),
                new PlayerWasAdded("player"));
    }

    @Test
    public void should_not_add_more_than_five_players() {
        game.add("player 1");
        game.add("player 2");
        game.add("player 3");
        game.add("player 4");
        game.add("player 5");

        Throwable throwable = catchThrowable(() -> game.add("player 6"));

        assertThat(throwable).isInstanceOf(ArrayIndexOutOfBoundsException.class).hasMessage("6");
    }

    @Test
    public void should_respect_subscription_order_when_playing() {
        game.add("player 1");
        game.add("player 2");

        game.roll(1);

        assertThat(eventStore.events(CurrentPlayerWas.class)).containsExactly(new CurrentPlayerWas("player 1"));
    }

    @Test
    public void should_go_to_next_player() {
        game.add("player 1");
        game.add("player 2");
        game.roll(1);
        game.wasCorrectlyAnswered();

        eventStore.start();
        game.roll(2);

        assertThat(eventStore.events(CurrentPlayerWas.class)).containsExactly(new CurrentPlayerWas("player 2"));
    }

    @Test
    public void should_win_gold_coins() {
        game.add("player 1");
        game.add("player 2");
        game.roll(1);

        game.wasCorrectlyAnswered();

        assertThat(eventStore.events(NewGoldCoinsCount.class)).containsExactly(new NewGoldCoinsCount("player 1", 1));
    }

    @Test
    public void should_roll_negatively() {
        game.add("player 1");
        game.add("player 2");

        game.roll(-3);

        assertThat(eventStore.events(LocationWas.class)).containsExactly(new LocationWas("player 1", -3));
    }

    @Test
    public void should_change_location_according_to_roll() {
        game.add("player 1");
        game.add("player 2");

        game.roll(2);

        assertThat(eventStore.events(LocationWas.class)).containsExactly(new LocationWas("player 1", 2));
    }

    @Test
    public void should_roll_more_than_6() {
        game.add("player 1");
        game.add("player 2");

        game.roll(7);

        assertThat(eventStore.events(LocationWas.class)).containsExactly(new LocationWas("player 1", 7));
    }

    @Test
    public void should_roll_0() {
        game.add("player 1");
        game.add("player 2");

        game.roll(0);

        assertThat(eventStore.events(LocationWas.class)).containsExactly(new LocationWas("player 1", 0));
    }

    @Test
    public void should_ask_question_0_first() {
        game.add("player 1");
        game.add("player 2");

        game.roll(1);

        assertThat(eventStore.events(QuestionWasAsked.class)).containsExactly(new QuestionWasAsked(Category.Science, 0));
    }

    @Test
    public void should_have_50_questions() {
        game.add("player 1");
        game.add("player 2");
        for (int i = 0; i < 50 / 2; i++) {
            game.roll(1);
            game.wrongAnswer();
            game.roll(1);
            game.wrongAnswer();
            game.roll(3);
            game.wrongAnswer();
            if (i == 50 / 2 - 1) {
                eventStore.start();
            }
            game.roll(3);
            game.wrongAnswer();
        }

        Throwable throwable = catchThrowable(() -> game.roll(1));

        assertThat(eventStore.events(QuestionWasAsked.class)).containsExactly(new QuestionWasAsked(Category.Pop, 49));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void should_go_to_penalty_box_if_not_answered_correctly() {
        game.add("player 1");
        game.add("player 2");
        game.roll(1);

        game.wrongAnswer();

        assertThat(eventStore.events(PlayerWasSentToPenaltyBox.class)).containsExactly(new PlayerWasSentToPenaltyBox("player 1"));
    }

    @Test
    public void should_not_escape_from_penalty_box_if_next_roll_is_even() {
        game.add("player 1");
        game.add("player 2");
        game.roll(1);
        game.wrongAnswer();
        player2Turn();

        eventStore.start();
        game.roll(2);

        assertThat(eventStore.events(PlayerWasNotGettingOutOfThePenaltyBox.class)).containsExactly(new PlayerWasNotGettingOutOfThePenaltyBox("player 1"));
    }

    @Test
    public void should_escape_from_penalty_box_if_roll_is_odd() {
        game.add("player 1");
        game.add("player 2");
        game.roll(1);
        game.wrongAnswer();
        player2Turn();

        eventStore.start();
        game.roll(1);

        assertThat(eventStore.events(PlayerWasGettingOutOfThePenaltyBox.class)).containsExactly(new PlayerWasGettingOutOfThePenaltyBox("player 1"));
    }

    @Test
    public void should_not_win_gold_coins_if_answered_correctly_but_in_penalty_box() {
        game.add("player 1");
        game.add("player 2");
        game.roll(1);
        game.wrongAnswer();
        player2Turn();
        game.roll(2);

        eventStore.start();
        game.wasCorrectlyAnswered();

        assertThat(eventStore.events(NewGoldCoinsCount.class)).isEmpty();
    }

    @Test
    public void should_escape_from_penalty_box_as_soon_as_player_has_answered_wrong_at_any_question() {
        game.add("player 1");
        game.add("player 2");
        game.roll(1);
        game.wrongAnswer();
        player2Turn();
        game.roll(1);
        game.wasCorrectlyAnswered();
        player2Turn();

        eventStore.start();
        game.roll(2);

        assertThat(eventStore.events(PlayerWasNotGettingOutOfThePenaltyBox.class)).containsExactly(new PlayerWasNotGettingOutOfThePenaltyBox("player 1"));
    }

    @Test
    public void should_end_the_game() {
        game.add("player 1");
        game.add("player 2");
        for (int i = 0; i < 5; i++) {
            game.roll(1);
            game.wasCorrectlyAnswered();
            player2Turn();
        }
        game.roll(1);

        boolean notAWinner = game.wasCorrectlyAnswered();

        assertThat(notAWinner).isFalse();
    }

    private void player2Turn() {
        game.roll(1);
        game.wasCorrectlyAnswered();
    }

}
