package com.adaptionsoft.games.trivia.ui.event;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.max;
import static java.util.Arrays.stream;

public class FileEventWalker implements EventsWalker {

    private final List<String> eventsToPlay = new ArrayList<>();
    private final EventProducer producer;

    private int index;

    public FileEventWalker(EventProducer producer) {
        this.producer = producer;
    }

    public static Stream<File> referenceFiles() {
        try {
            return stream(new File(FileEventWalker.class.getResource("/reference").toURI()).listFiles(File::isFile));
        } catch (URISyntaxException e) {
            return Stream.empty();
        }
    }

    public void loadEvents(String scenario) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/reference/" + scenario)))) {
            eventsToPlay.clear();
            index = 0;
            String currentEvent;
            while ((currentEvent = in.readLine()) != null) {
                eventsToPlay.add(currentEvent);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void next(int numberOfEvents) {
        if (eventsToPlay.isEmpty()) {
            return;
        }
        int newIndex = Math.min(index + numberOfEvents, eventsToPlay.size());
        IntStream.range(index, newIndex)
                .mapToObj(eventsToPlay::get)
                .forEach(producer::produce);
        index = newIndex;
    }

    @Override
    public int percent() {
        if (eventsToPlay.isEmpty()) {
            return 0;
        }
        
        return index * 100 / eventsToPlay.size();
    }

    public void back(int numberOfEventsToRollback) {
        index -= max(0, numberOfEventsToRollback);
    }
}
