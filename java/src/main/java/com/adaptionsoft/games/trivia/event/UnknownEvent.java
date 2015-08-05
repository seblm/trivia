package com.adaptionsoft.games.trivia.event;

@EventAnnotation(unknownEvent = true)
public record UnknownEvent(String unknownEvent) implements Event {
}
