package com.adaptionsoft.games.trivia.ui.event;

@FunctionalInterface
public interface EventProducer {
    void produce(String event);
}
