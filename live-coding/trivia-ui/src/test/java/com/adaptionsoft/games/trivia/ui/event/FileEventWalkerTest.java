package com.adaptionsoft.games.trivia.ui.event;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FileEventWalkerTest {

    @Test
    public void should_limit_index_to_max_size_of_events_even_if_we_try_to_advance_one_time_more_than_size_of_events() {
        FileEventWalker fileEventWalker = new FileEventWalker(event -> {
        });
        fileEventWalker.loadEvents("3events.txt");

        fileEventWalker.next(4);

        assertThat(fileEventWalker.percent()).isEqualTo(100);
    }

    @Test
    public void should_limit_index_to_max_size_of_events_even_if_we_try_to_advance_two_times_with_second_more_than_size_of_events() {
        FileEventWalker fileEventWalker = new FileEventWalker(event -> {
        });
        fileEventWalker.loadEvents("3events.txt");
        fileEventWalker.next(1);

        fileEventWalker.next(4);

        assertThat(fileEventWalker.percent()).isEqualTo(100);
    }

}
