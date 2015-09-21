package com.adaptionsoft.games.trivia.ui;

import com.adaptionsoft.games.trivia.ui.event.EventConsumer;
import com.adaptionsoft.games.trivia.event.EventsListener;

class UpdateUIWithEvents implements Runnable {

    private final EventConsumer consumer;
    private final EventsListener[] eventsListeners;

    public UpdateUIWithEvents(EventConsumer consumer, EventsListener... eventsListeners) {
        this.consumer = consumer;
        this.eventsListeners = eventsListeners;
    }

    @Override
    public void run() {
        while (true) {
            String event = consumer.consume();
            EventsListener.dispatch(event, eventsListeners);
        }
    }
}
