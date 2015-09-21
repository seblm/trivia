package com.adaptionsoft.games.trivia.ui.players;

import com.adaptionsoft.games.trivia.query.Player;
import com.adaptionsoft.games.trivia.ui.Component;
import com.adaptionsoft.games.trivia.ui.PlayersColors;
import com.adaptionsoft.games.trivia.ui.TriviaClient;
import com.adaptionsoft.games.trivia.ui.board.Board;
import processing.core.PFont;
import processing.core.PImage;

import java.util.stream.IntStream;

import static com.adaptionsoft.games.trivia.ui.TriviaClient.*;
import static processing.core.PConstants.CENTER;

public class Players implements Component {
    private final TriviaClient parent;
    private final Board board;
    private final PImage coin;
    private final PFont fontBold;

    public Players(TriviaClient parent, Board board, PImage coin, PFont fontBold) {
        this.parent = parent;
        this.board = board;
        this.coin = coin;
        this.fontBold = fontBold;
    }

    @Override
    public void draw() {
        final int playerWidth = parent.width / MAX_NUMBER_OF_PLAYERS;
        parent.pushStyle();
        {
            parent.textAlign(CENTER);
            parent.fill(0);

            int playerNumber = 0;
            for (Player player : parent.gameState.getPlayers().values()) {
                final int xCenter = playerWidth * playerNumber + playerWidth / 2;
                parent.pushStyle();
                {
                    if (player.equals(parent.gameState.getCurrentPlayer())) {
                        parent.textFont(fontBold);
                    }
                    PlayersColors.fillFont(parent, playerNumber);
                    parent.text(player.getName(), xCenter, 30);
                    IntStream.range(0, player.getGoldCoins()).forEach(i -> parent.image(coin, xCenter - coin.width * 3 + i * coin.width, 40));
                }
                parent.popStyle();
                drawPlayerOnBoard(player, playerNumber);
                playerNumber++;
            }
        }
        parent.popStyle();
    }

    private void drawPlayerOnBoard(Player player, int playerNumber) {
        final float
                delta = ANGLE / MAX_NUMBER_OF_PLAYERS,
                angleForPlayer = (player.getLocation() * MAX_NUMBER_OF_PLAYERS + playerNumber) * delta + (delta / 2),
                radius = (board.size - board.thickness / 2) / 2;

        parent.pushStyle();
        PlayersColors.fillPiece(parent, playerNumber);
        board.boardDrawing(() -> parent.ellipse(radius * cos(angleForPlayer), radius * sin(angleForPlayer), board.thickness / 8, board.thickness / 8));
        parent.popStyle();
    }
}
