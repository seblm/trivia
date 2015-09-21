package com.adaptionsoft.games.trivia.ui.penaltybox;

import com.adaptionsoft.games.trivia.ui.Component;
import com.adaptionsoft.games.trivia.ui.Player;
import com.adaptionsoft.games.trivia.ui.TriviaClient;
import com.adaptionsoft.games.trivia.ui.board.Board;

import java.util.Iterator;

import static com.adaptionsoft.games.trivia.ui.TriviaClient.*;
import static java.util.Arrays.asList;
import static processing.core.PConstants.CENTER;

public class PenaltyBox implements Component {
    private final TriviaClient parent;
    private final Board board;

    public PenaltyBox(TriviaClient parent, Board board) {
        this.parent = parent;
        this.board = board;
    }

    @Override
    public void draw() {
        final int
                size = board.size / 3,
                xCenter = parent.width - parent.width / 3 / 2,
                yCenter = parent.height / 2,
                left = xCenter - size / 3,
                right = xCenter + size / 3,
                top = yCenter - size / 3,
                bottom = yCenter + size / 3;
        final Iterator<int[]> playerCoordinates = asList(
                new int[]{xCenter, yCenter},
                new int[]{left, top},
                new int[]{right, bottom},
                new int[]{right, top},
                new int[]{left, bottom}
        ).iterator();

        parent.pushStyle();
        {
            parent.fill(BACKGROUND);
            parent.strokeWeight(4);
            parent.rectMode(CENTER);
            parent.rect(xCenter, yCenter, size, size);

            parent.strokeWeight(1);
            parent.game.players().stream().filter(Player::isOnPenaltyBox).forEach(player -> {
                int[] coordinates = playerCoordinates.next();
                parent.fill(player.colors[1][0], player.colors[1][1], player.colors[1][2]);
                parent.ellipse(coordinates[0], coordinates[1], board.thickness / 8, board.thickness / 8);
            });

            parent.strokeWeight(2);
            for (int x = xCenter - size / 2; x < xCenter + size / 2; x += size / 8) {
                parent.line(x, yCenter - size / 2, x, yCenter + size / 2);
            }
        }
        parent.popStyle();
    }
}
