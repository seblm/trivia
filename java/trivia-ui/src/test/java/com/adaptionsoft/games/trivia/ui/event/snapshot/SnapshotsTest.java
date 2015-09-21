package com.adaptionsoft.games.trivia.ui.event.snapshot;

import com.adaptionsoft.games.trivia.event.EventsListener;
import com.adaptionsoft.games.trivia.query.GameState;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class SnapshotsTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private Restorable restorable;

    private Snapshots snapshots;

    private static List<String> events = new ArrayList<>();

    @BeforeClass
    public static void createEvents() {
        events.add("Chet was added");
        events.add("They are player number 1");
        events.add("Pat was added");
        events.add("They are player number 2");
        events.add("Sue was added");
        events.add("They are player number 3");
        events.add("Chet is the current player");
        events.add("They have rolled a 6");
        events.add("Chet's new location is 6");
        events.add("The category is Sports");
        events.add("Sports Question 0");
        events.add("Answer was corrent!!!!");
        events.add("Chet now has 1 Gold Coins.");
    }

    @Before
    public void createSnapshots() {
        this.snapshots = new Snapshots(restorable);
    }

    @Test
    public void should_not_restore_if_nothing_was_happening() {
        int nbOfRestoredEvents = snapshots.restore(1);

        assertThat(nbOfRestoredEvents).isZero();
        verify(restorable, never()).restore(any());
    }

    @Test
    public void should_not_restore_if_number_of_rollback_is_zero() {
        runEvents(1);

        int nbOfRestoredEvents = snapshots.restore(0);

        assertThat(nbOfRestoredEvents).isZero();
        verify(restorable, never()).restore(any());
    }

    @Test
    public void should_restore_to_initial_state() {
        runEvents(1);

        int nbOfRestoredEvents = snapshots.restore(1);

        assertThat(nbOfRestoredEvents).isEqualTo(1);
        ArgumentCaptor<GameState> actualGameState = ArgumentCaptor.forClass(GameState.class);
        verify(restorable).restore(actualGameState.capture());
        assertThat(actualGameState.getValue().getPlayers()).isEmpty();
    }

    @Test
    public void should_restore_to_initial_state_with_ten_events() {
        runEvents(10);

        int nbOfRestoredEvents = snapshots.restore(1);

        assertThat(nbOfRestoredEvents).isEqualTo(10);
        ArgumentCaptor<GameState> actualGameState = ArgumentCaptor.forClass(GameState.class);
        verify(restorable).restore(actualGameState.capture());
        assertThat(actualGameState.getValue().getPlayers()).isEmpty();
    }

    @Test
    public void should_restore_to_10_with_13_events() {
        runEvents(13);

        int nbOfRestoredEvents = snapshots.restore(1);

        assertThat(nbOfRestoredEvents).isEqualTo(3);
        ArgumentCaptor<GameState> actualGameState = ArgumentCaptor.forClass(GameState.class);
        verify(restorable).restore(actualGameState.capture());
        assertThat(actualGameState.getValue().getPlayers().get("Chet").getGoldCoins()).isZero();
    }

    private void runEvents(int count) {
        events.stream().limit(count).forEach(event -> EventsListener.dispatch(event, snapshots));
    }

}