package com.adaptionsoft.games.trivia.ui.dice;

import com.adaptionsoft.games.trivia.ui.Component;
import com.adaptionsoft.games.trivia.ui.TriviaClient;
import com.adaptionsoft.games.trivia.ui.board.Board;

import static processing.core.PConstants.CENTER;

public class Dice implements Component {

    private final TriviaClient parent;
    private final Board board;

    public Dice(TriviaClient parent, Board board) {
        this.parent = parent;
        this.board = board;
    }

    @Override
    public void draw() {
        Integer dice = parent.gameState.getDice();

        if (dice == null) {
            return;
        }

        final int
                size = board.size / 3,
                xCenter = parent.width / 3 / 2,
                yCenter = parent.height / 2,
                radius = size / 6,
                left = xCenter - size / 3,
                right = xCenter + size / 3,
                top = yCenter - size / 3,
                bottom = yCenter + size / 3;

        parent.pushStyle();
        {
            parent.fill(255);
            parent.rectMode(CENTER);
            parent.rect(
                    xCenter, yCenter,
                    size, size,
                    board.size / 20);
            parent.fill(0);
            if (dice == 1 || dice == 3 || dice == 5) {
                parent.ellipse(xCenter, yCenter, radius, radius);
            }
            if (dice == 2 || dice == 3 || dice == 4 || dice == 5 || dice == 6) {
                parent.ellipse(left, top, radius, radius);
                parent.ellipse(right, bottom, radius, radius);
            }
            if (dice == 4 || dice == 5 || dice == 6) {
                parent.ellipse(right, top, radius, radius);
                parent.ellipse(left, bottom, radius, radius);
            }
            if (dice == 6) {
                parent.ellipse(left, yCenter, radius, radius);
                parent.ellipse(right, yCenter, radius, radius);
            }
        }
        parent.popStyle();
    }
}
