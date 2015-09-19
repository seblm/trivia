package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.trivia.event.*;
import com.adaptionsoft.games.trivia.event.EventStore;
import com.adaptionsoft.games.uglytrivia.Game;
import org.assertj.core.api.AbstractAssert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;
import com.adaptionsoft.games.trivia.event.EventsListener;

import static com.adaptionsoft.games.trivia.CategoriesTest.Assertions.assertThat;

public class CategoriesTest {

    @Rule
    public EventStore events = new EventStore(EventsListener::toEvent);

    private Game game;

    @Before
    public void createGame() {
        game = new Game();
        game.add("player 1");
        game.add("player 2");
    }

    @Test
    public void should_have_science_category_for_location_1() {
        game.roll(1);

        assertThat(events).locationIs(1).categoryIs(Category.Science);
    }

    @Test
    public void should_have_sports_category_for_location_2() {
        game.roll(2);

        assertThat(events).locationIs(2).categoryIs(Category.Sports);
    }

    @Test
    public void should_have_rock_category_for_location_3() {
        game.roll(3);

        assertThat(events).locationIs(3).categoryIs(Category.Rock);
    }

    @Test
    public void should_have_pop_category_for_location_4() {
        game.roll(4);

        assertThat(events).locationIs(4).categoryIs(Category.Pop);
    }

    @Test
    public void should_have_science_category_for_location_5() {
        game.roll(5);

        assertThat(events).locationIs(5).categoryIs(Category.Science);
    }

    @Test
    public void should_have_sports_category_for_location_6() {
        game.roll(6);

        assertThat(events).locationIs(6).categoryIs(Category.Sports);
    }

    @Test
    public void should_have_rock_category_for_location_7() {
        game.roll(6);
        game.wasCorrectlyAnswered();
        game.roll(1);
        game.wasCorrectlyAnswered();

        events.start();
        game.roll(1);

        assertThat(events).locationIs(7).categoryIs(Category.Rock);
    }

    @Test
    public void should_have_pop_category_for_location_8() {
        game.roll(6);
        game.wasCorrectlyAnswered();
        game.roll(1);
        game.wasCorrectlyAnswered();

        events.start();
        game.roll(2);

        assertThat(events).locationIs(8).categoryIs(Category.Pop);
    }

    @Test
    public void should_have_science_category_for_location_9() {
        game.roll(6);
        game.wasCorrectlyAnswered();
        game.roll(1);
        game.wasCorrectlyAnswered();

        events.start();
        game.roll(3);

        assertThat(events).locationIs(9).categoryIs(Category.Science);
    }

    @Test
    public void should_have_sports_category_for_location_10() {
        game.roll(6);
        game.wasCorrectlyAnswered();
        game.roll(1);
        game.wasCorrectlyAnswered();

        events.start();
        game.roll(4);

        assertThat(events).locationIs(10).categoryIs(Category.Sports);
    }

    @Test
    public void should_have_rock_category_for_location_11() {
        game.roll(6);
        game.wasCorrectlyAnswered();
        game.roll(1);
        game.wasCorrectlyAnswered();

        events.start();
        game.roll(5);

        assertThat(events).locationIs(11).categoryIs(Category.Rock);
    }

    @Test
    public void should_have_pop_category_for_location_0() {
        game.roll(6);
        game.wasCorrectlyAnswered();
        game.roll(1);
        game.wasCorrectlyAnswered();

        events.start();
        game.roll(6);

        assertThat(events).locationIs(0).categoryIs(Category.Pop);
    }

    private static class CategoryAssert extends AbstractAssert<CategoryAssert, EventStore> {

        protected CategoryAssert(EventStore actual) {
            super(actual, CategoryAssert.class);
        }

        private CategoryAssert locationIs(int actualLocation) {
            List<Event> locations = actual.events(LocationWas.class);
            assertThat(locations).isNotEmpty();
            assertThat(locations.get(locations.size() - 1)).isEqualTo(new LocationWas("player 1", actualLocation));
            return this;
        }

        private CategoryAssert categoryIs(Category actualCategory) {
            List<Event> categories = actual.events(CategoryWas.class);
            assertThat(categories).isNotEmpty();
            assertThat(categories.get(categories.size() - 1)).isEqualTo(new CategoryWas(actualCategory));
            return this;
        }

    }

    static class Assertions extends org.assertj.core.api.Assertions {

        static CategoryAssert assertThat(EventStore actual) {
            return new CategoryAssert(actual);
        }

    }

}
