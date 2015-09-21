package com.adaptionsoft.games.trivia.ui;

import com.adaptionsoft.games.trivia.ui.event.EventConsumer;
import com.adaptionsoft.games.trivia.ui.event.EventProducer;

import java.util.concurrent.LinkedBlockingQueue;

public class BlockingProducerConsumer implements EventProducer, EventConsumer {
    private final LinkedBlockingQueue<String> events = new LinkedBlockingQueue<>();

    @Override
    public String consume() {
        try {
            return events.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public void produce(String event) {
        try {
            events.put(event);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
