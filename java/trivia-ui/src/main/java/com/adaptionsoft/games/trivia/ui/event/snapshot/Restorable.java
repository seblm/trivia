package com.adaptionsoft.games.trivia.ui.event.snapshot;

import com.adaptionsoft.games.trivia.query.GameState;

public interface Restorable {
    void restore(GameState state);
}
