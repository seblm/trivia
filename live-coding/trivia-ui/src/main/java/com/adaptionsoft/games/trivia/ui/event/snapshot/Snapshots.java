package com.adaptionsoft.games.trivia.ui.event.snapshot;

import com.adaptionsoft.games.trivia.event.*;
import com.adaptionsoft.games.trivia.query.GameState;

import java.util.Deque;
import java.util.LinkedList;

public class Snapshots implements EventsListener {

    private final Deque<Snapshot> snapshots;
    private final GameState state;
    private final Restorable restorable;

    private int index;

    public Snapshots(Restorable restorable) {
        this.index = 0;
        this.snapshots = new LinkedList<>();
        this.state = new GameState(false);
        this.restorable = restorable;
    }

    @Override
    public void on(AnswerWasCorrect event) {
        state.on(event);
        newEvent(event);
    }

    @Override
    public void on(AnswerWasNotCorrect event) {
        state.on(event);
        newEvent(event);
    }

    @Override
    public void on(CategoryWas event) {
        state.on(event);
        newEvent(event);
    }

    @Override
    public void on(CurrentPlayerWas event) {
        state.on(event);
        newEvent(event);
    }

    @Override
    public void on(LocationWas event) {
        state.on(event);
        newEvent(event);
    }

    @Override
    public void on(NewGoldCoinsCount event) {
        state.on(event);
        newEvent(event);
    }

    @Override
    public void on(NewPlayerCount event) {
        state.on(event);
        newEvent(event);
    }

    @Override
    public void on(PlayerWasAdded event) {
        state.on(event);
        newEvent(event);
    }

    @Override
    public void on(PlayerWasGettingOutOfThePenaltyBox event) {
        state.on(event);
        newEvent(event);
    }

    @Override
    public void on(PlayerWasNotGettingOutOfThePenaltyBox event) {
        state.on(event);
        newEvent(event);
    }

    @Override
    public void on(PlayerWasSentToPenaltyBox event) {
        state.on(event);
        newEvent(event);
    }

    @Override
    public void on(QuestionWasAsked event) {
        state.on(event);
        newEvent(event);
    }

    @Override
    public void on(Rolled event) {
        state.on(event);
        newEvent(event);
    }

    @Override
    public void on(UnknownEvent event) {
        state.on(event);
        newEvent(event);
    }

    private void newEvent(Event event) {
        index++;
        System.out.format("index %d\t%s%n", index, event);
    }

    private void snapshot() {
        snapshots.addFirst(new Snapshot(index, state.copy()));
        System.out.format("     put snapshot at %d%n", index);
    }

    public int restore(int numberOfRollbacks) {
        if (index <= 0 || numberOfRollbacks <= 0 || this.snapshots.isEmpty()) {
            return 0;
        }

        return restoreRecursive(numberOfRollbacks);
    }

    private int restoreRecursive(int numberOfRollbacks) {
        if (numberOfRollbacks == 0) {
            restorable.restore(snapshots.getFirst().state.copy());
            return 0;
        }

        int newIndex;
        if (snapshots.getFirst().eventIndex == index) {
            Snapshot snapshotToRestore = snapshots.removeFirst();
            newIndex = snapshotToRestore.eventIndex - 10;
            System.out.format("  remove snapshot at %d%n", snapshotToRestore.eventIndex);
        } else {
            Snapshot snapshotToRestore = snapshots.getFirst();
            newIndex = snapshotToRestore.eventIndex;
            System.out.format("     get snapshot at %d%n", snapshotToRestore.eventIndex);
        }
        int numberOfEvents = index - newIndex;
        index = newIndex;

        return numberOfEvents + restoreRecursive(numberOfRollbacks - 1);
    }

}
