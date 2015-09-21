package com.adaptionsoft.games.trivia.ui.deck;

import com.adaptionsoft.games.trivia.ui.Category;
import com.adaptionsoft.games.trivia.ui.Component;
import com.adaptionsoft.games.trivia.ui.TriviaClient;

import static processing.core.PApplet.max;
import static processing.core.PApplet.min;
import static processing.core.PConstants.CENTER;

public class Deck implements Component {
    
    private static final int NUMBER_OF_QUESTIONS = 50;
    
    private final TriviaClient parent;

    public Deck(TriviaClient parent) {
        this.parent = parent;
    }

    @Override
    public void draw() {
        final int cardWidth = min(parent.width, parent.height) / 20,
                cardHeight = max(parent.width, parent.height) / 20,
                deckWidthForOneCategory = NUMBER_OF_QUESTIONS * 2 + cardWidth,
                deckWidth = deckWidthForOneCategory * Category.values().length;

        parent.pushMatrix();
        {
            parent.translate((parent.width - deckWidth) / 2, parent.height - cardHeight - 2);
            for (Category category : Category.values()) {
                parent.game.questionsFor(category)
                        .sorted((questionNumber1, questionNumber2) -> questionNumber2.compareTo(questionNumber1))
                        .forEach(x -> {
                            int y = parent.game.isCurrentCard(category, x) ? -cardHeight / 2 : 0;
                            category.fill(parent);
                            parent.rect(
                                    x * 2, y,
                                    cardWidth, cardHeight);
                            parent.fill(255);
                            parent.textAlign(CENTER, CENTER);
                            parent.text(x, x * 2 + cardWidth / 2, y + cardHeight / 2);
                        });
                parent.translate(deckWidthForOneCategory, 0);
            }
        }
        parent.popMatrix();
    }
}
