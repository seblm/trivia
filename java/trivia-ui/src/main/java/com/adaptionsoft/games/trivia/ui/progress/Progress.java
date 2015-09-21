package com.adaptionsoft.games.trivia.ui.progress;

import com.adaptionsoft.games.trivia.ui.Component;
import com.adaptionsoft.games.trivia.ui.board.Board;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.function.IntSupplier;

public class Progress implements Component {
    private final PApplet parent;
    private final Board board;
    private final IntSupplier percent;

    public Progress(PApplet parent, Board board, IntSupplier percent) {
        this.parent = parent;
        this.board = board;
        this.percent = percent;
    }

    @Override
    public void draw() {
        board.boardDrawing(this::drawOnBoardReferential);
    }

    private void drawOnBoardReferential() {
        int radius = board.size / 3;
        if (progressIsDone()) {
            parent.ellipse(0, 0, radius, radius);
        } else {
            parent.arc(0, 0, radius, radius, 0, 2 * PConstants.PI * percent.getAsInt() / 100, PConstants.PIE);
        }
    }

    private boolean progressIsDone() {
        return percent.getAsInt() >= 100;
    }
}
