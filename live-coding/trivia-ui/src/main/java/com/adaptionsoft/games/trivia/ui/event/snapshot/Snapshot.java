package com.adaptionsoft.games.trivia.ui.event.snapshot;

import com.adaptionsoft.games.trivia.query.GameState;

public class Snapshot {
    final int eventIndex;
    public final GameState state;

    Snapshot(int eventIndex, GameState state) {
        this.eventIndex = eventIndex;
        this.state = state;
    }
}
