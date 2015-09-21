package com.adaptionsoft.games.trivia.ui;

public class PlayersColors {
    public static void fillPiece(TriviaClient parent, int index) {
        switch (index % 5) {
            case 0:
                parent.fill(204, 102, 255);
                break;
            case 1:
                parent.fill(255, 111, 207);
                break;
            case 2:
                parent.fill(255, 204, 102);
                break;
            case 3:
                parent.fill(102, 204, 255);
                break;
            case 4:
            default:
                parent.fill(204, 255, 102);
                break;
        }
    }

    public static void fillFont(TriviaClient parent, int index) {
        switch (index % 5) {
            case 0:
                parent.fill(128, 0, 255);
                break;
            case 1:
                parent.fill(255, 0, 128);
                break;
            case 2:
                parent.fill(255, 128, 0);
                break;
            case 3:
                parent.fill(0, 128, 255);
                break;
            case 4:
            default:
                parent.fill(128, 255, 0);
                break;
        }
    }
}
