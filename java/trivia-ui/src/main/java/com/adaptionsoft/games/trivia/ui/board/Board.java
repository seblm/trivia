package com.adaptionsoft.games.trivia.ui.board;

import com.adaptionsoft.games.trivia.ui.Category;
import com.adaptionsoft.games.trivia.ui.Component;
import com.adaptionsoft.games.trivia.ui.TriviaClient;

import static com.adaptionsoft.games.trivia.ui.TriviaClient.*;

public class Board implements Component {

    public final int size;

    private final TriviaClient parent;
    public final int thickness;

    public Board(TriviaClient parent) {
        this.parent = parent;
        this.size = max(parent.width / 3, parent.height / 3);
        this.thickness = size / 3;
    }

    @Override
    public void draw() {
        boardDrawing(this::drawOnBoardReferential);
    }

    public void boardDrawing(Component component) {
        final int
                xCenter = parent.width / 2,
                yCenter = parent.height / 2;

        parent.pushMatrix();
        {
            parent.translate(xCenter, yCenter);
            startLocationAtNorth();
            startLocationCentered();

            component.draw();
        }
        parent.popMatrix();
    }

    private void drawOnBoardReferential() {
        for (int factor = 0; factor < NUMBER_OF_LOCATIONS; factor++) {
            Category category = Category.fromIndex(factor);
            category.fill(parent);
            parent.arc(
                    0, 0,
                    size, size,
                    factor * ANGLE, (factor + 1) * ANGLE,
                    TriviaClient.PIE);
        }

        parent.fill(BACKGROUND);
        parent.ellipse(0, 0, size - thickness, size - thickness);
    }

    private void startLocationAtNorth() {
        parent.rotate(-PI / 2);
    }

    private void startLocationCentered() {
        parent.rotate(-ANGLE / 2);
    }

}
