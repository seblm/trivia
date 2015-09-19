package com.adaptionsoft.games.trivia.event;

import java.util.Objects;

@EventAnnotation(unknownEvent = true)
public class UnknownEvent implements Event {
    private final String unknownEvent;

    public UnknownEvent(String unknownEvent) {
        this.unknownEvent = unknownEvent;
        System.err.println(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnknownEvent that = (UnknownEvent) o;
        return Objects.equals(unknownEvent, that.unknownEvent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unknownEvent);
    }

    @Override
    public String toString() {
        return "UnknownEvent(" + unknownEvent + ')';
    }
}
