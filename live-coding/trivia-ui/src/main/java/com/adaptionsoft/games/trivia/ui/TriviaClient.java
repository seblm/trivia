package com.adaptionsoft.games.trivia.ui;

import com.adaptionsoft.games.trivia.event.*;
import com.adaptionsoft.games.trivia.query.GameState;
import com.adaptionsoft.games.trivia.ui.board.Board;
import com.adaptionsoft.games.trivia.ui.deck.Deck;
import com.adaptionsoft.games.trivia.ui.dice.Dice;
import com.adaptionsoft.games.trivia.ui.event.FileEventWalker;
import com.adaptionsoft.games.trivia.ui.event.snapshot.Restorable;
import com.adaptionsoft.games.trivia.ui.event.snapshot.Snapshots;
import com.adaptionsoft.games.trivia.ui.penaltybox.PenaltyBox;
import com.adaptionsoft.games.trivia.ui.players.Players;
import com.adaptionsoft.games.trivia.ui.progress.Progress;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import javax.swing.*;
import java.io.File;
import java.util.*;

public class TriviaClient extends PApplet implements EventsListener, Restorable {

    public static final int BACKGROUND = 200;
    public static final int NUMBER_OF_LOCATIONS = 12;
    public static final int MAX_NUMBER_OF_PLAYERS = 5;
    public static final float ANGLE = 2 * PI / NUMBER_OF_LOCATIONS;

    private static final int WIDTH = 1014;
    private static final int HEIGHT = 768;

    public GameState gameState;

    private Board board;
    private Deck deck;
    private Players players;
    private Dice dice;
    private PenaltyBox penaltyBox;
    private Progress progress;

    private FileEventWalker eventsWalker;
    private Snapshots snapshots;
    private UpdateUIWithEvents updateUIWithEvents;

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
        pixelDensity(displayDensity());
    }

    @Override
    public void setup() {
        textFont(createFont("LucidaGrande", 18, true));
        noLoop();
        smooth();

        BlockingProducerConsumer blockingProducerConsumer = new BlockingProducerConsumer();

        this.eventsWalker = new FileEventWalker(blockingProducerConsumer);
        this.snapshots = new Snapshots(this);

        this.gameState = new GameState();

        this.board = new Board(this);
        this.deck = new Deck(this);
        this.players = new Players(this, board, loadImage("coin.png"), createFont("LucidaGrande-Bold", 18, true));
        this.dice = new Dice(this, board);
        this.penaltyBox = new PenaltyBox(this, board);
        this.progress = new Progress(this, board, eventsWalker::percent);
        this.updateUIWithEvents = new UpdateUIWithEvents(blockingProducerConsumer, gameState, snapshots, this);

        new Thread(updateUIWithEvents, "EventConsumer").start();
    }

    @Override
    public void draw() {
        background(BACKGROUND);
        board.draw();
        deck.draw();
        players.draw();
        dice.draw();
        penaltyBox.draw();
        progress.draw();
    }

    @Override
    public void on(AnswerWasCorrect event) {
    }

    @Override
    public void on(PlayerWasAdded event) {
        redraw();
    }

    @Override
    public void on(CurrentPlayerWas event) {
        redraw();
    }

    @Override
    public void on(Rolled event) {
        redraw();
    }

    @Override
    public void on(LocationWas event) {
        redraw();
    }

    @Override
    public void on(CategoryWas event) {
        redraw();
    }

    @Override
    public void on(QuestionWasAsked event) {
        redraw();
    }

    @Override
    public void on(NewGoldCoinsCount event) {
        redraw();
    }

    @Override
    public void on(PlayerWasSentToPenaltyBox event) {
        redraw();
    }

    @Override
    public void on(PlayerWasGettingOutOfThePenaltyBox event) {
        redraw();
    }

    @Override
    public void on(UnknownEvent unknownEvent) {
    }

    @Override
    public void on(NewPlayerCount event) {
    }

    @Override
    public void on(PlayerWasNotGettingOutOfThePenaltyBox event) {
    }

    @Override
    public void on(AnswerWasNotCorrect event) {
    }

    @Override
    public void keyReleased(KeyEvent event) {
        if (event.getKeyCode() == 39 || event.getKeyCode() == 40) {
            // right or down
            eventsWalker.next(1);
            return;
        }

        if (event.getKeyCode() == 37 || event.getKeyCode() == 38) {
            // left or up
            int numberOfEventsToRollback = snapshots.restore(1);
            eventsWalker.back(numberOfEventsToRollback);
            return;
        }

        if (event.getKey() == 'o' || event.getKey() == 'O') {
            List<String> scenarii = FileEventWalker.referenceFiles().map(File::getName).toList();
            String scenario = (String) JOptionPane.showInputDialog(null, "Please choose your scenario:", "Scenarii", JOptionPane.PLAIN_MESSAGE, null, scenarii.toArray(), null);
            if (scenario == null) {
                return;
            }

            eventsWalker.loadEvents(scenario);
        }
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        if (event.getCount() == 0) {
            return;
        }
        if (event.getCount() > 0) {
            eventsWalker.next(event.getCount());
            return;
        }
        int numberOfEventsToRollback = snapshots.restore(1);
        eventsWalker.back(numberOfEventsToRollback);
    }

    @Override
    public void restore(GameState state) {
        gameState = state.copy();
        updateUIWithEvents.updateGameState(gameState);
        redraw();
    }

    public static void main(String[] args) {
        PApplet.main(new String[]{TriviaClient.class.getName()});
    }
}
