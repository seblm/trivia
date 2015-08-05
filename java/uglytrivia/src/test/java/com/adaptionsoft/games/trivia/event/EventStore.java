package com.adaptionsoft.games.trivia.event;

import org.junit.rules.ExternalResource;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class EventStore extends ExternalResource {

    private final Function<String, Event> toEvent;
    private final List<Event> events;

    private final PrintStream console;
    private final List<Consumer<String>> preProcessors;
    private final List<Consumer<Event>> postProcessors;
    
    private StringBuilder currentLine;

    public EventStore(Function<String, Event> toEvent) {
        this.toEvent = toEvent;
        this.events = new ArrayList<>();
        this.console = System.out;
        this.preProcessors = new ArrayList<>();
        this.postProcessors = new ArrayList<>();
        
        this.currentLine = new StringBuilder();
    }

    @Override
    protected void before() throws Throwable {
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                if ((char) b == '\n') {
                    String line = currentLine.toString();
                    preProcessors.forEach(preProcessor -> preProcessor.accept(line));
                    Event event = toEvent.apply(line);
                    postProcessors.forEach(postProcessor -> postProcessor.accept(event));
                    events.add(event);
                    currentLine = new StringBuilder();
                    return;
                }
                currentLine.append((char) b);
            }
        }));
    }

    @Override
    protected void after() {
        System.setOut(console);
    }

    public EventStore start() {
        events.clear();
        return this;
    }

    public EventStore debug() {
        preProcessors.add(line -> console.format("%25s -> ", line));
        postProcessors.add(console::println);
        return this;
    }

    public List<Event> events() {
        return events;
    }

    public List<Event> events(Class<? extends Event> onlyThesesEvents) {
        return events.stream().filter(event -> onlyThesesEvents.equals(event.getClass())).collect(toList());
    }

}
