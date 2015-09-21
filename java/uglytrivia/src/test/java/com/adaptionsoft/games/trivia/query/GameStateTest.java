package com.adaptionsoft.games.trivia.query;

import com.adaptionsoft.games.trivia.Category;
import com.adaptionsoft.games.trivia.event.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

import static com.adaptionsoft.games.trivia.Category.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.StrictAssertions.fail;
import static org.assertj.core.data.MapEntry.entry;

public class GameStateTest {

    private Path file;

    @Before
    public void initializeFile() {
        this.file = Paths.get("target", "game-state", "game-state.json");
    }
    
    @After
    public void deleteFile() {
        this.file.toFile().delete();
        this.file.getParent().toFile().delete();
    }

    @Test
    public void should_not_have_current_category_at_startup() {
        GameState gameState = new GameState();

        assertThat(gameState.getCurrentCategory()).isNull();
    }
    
    @Test
    public void should_have_current_category() {
        GameState gameState = new GameState();
        
        gameState.on(new CategoryWas(Pop));

        assertThat(gameState.getCurrentCategory()).isEqualTo(Pop);
    }
    
    @Test
    public void should_not_have_any_player_at_startup() {
        GameState gameState = new GameState();
        
        assertThat(gameState.getCurrentPlayer()).isNull();
    }
    
    @Test
    public void should_not_have_current_player_if_it_wasn_t_added() {
        GameState gameState = new GameState();

        gameState.on(new CurrentPlayerWas("player"));
        
        assertThat(gameState.getCurrentPlayer()).isNull();
    }
    
    @Test
    public void should_have_current_player() {
        GameState gameState = new GameState();

        gameState.on(new PlayerWasAdded("player"));
        gameState.on(new CurrentPlayerWas("player"));
        
        assertThat(gameState.getCurrentPlayer()).isEqualTo(new Player("player"));
    }
    
    @Test
    public void should_update_location_of_player() {
        GameState gameState = new GameState();
        gameState.on(new PlayerWasAdded("player"));
        
        gameState.on(new LocationWas("player", 3));

        gameState.on(new CurrentPlayerWas("player"));
        assertThat(gameState.getCurrentPlayer()).isEqualTo(new Player("player").location(3));
    }
    
    @Test
    public void should_update_gold_coins_of_player() {
        GameState gameState = new GameState();
        gameState.on(new PlayerWasAdded("player"));
        
        gameState.on(new NewGoldCoinsCount("player", 4));

        gameState.on(new CurrentPlayerWas("player"));
        assertThat(gameState.getCurrentPlayer()).isEqualTo(new Player("player").goldCoins(4));
    }
    
    @Test
    public void should_getting_out_of_the_penalty_box() {
        GameState gameState = new GameState();
        gameState.on(new PlayerWasAdded("player"));
        gameState.on(new PlayerWasSentToPenaltyBox("player"));
        
        gameState.on(new PlayerWasGettingOutOfThePenaltyBox("player"));

        gameState.on(new CurrentPlayerWas("player"));
        assertThat(gameState.getCurrentPlayer()).isEqualTo(new Player("player").inPenaltyBox(false));
    }
    
    @Test
    public void should_send_player_to_penalty_box() {
        GameState gameState = new GameState();
        gameState.on(new PlayerWasAdded("player"));
        
        gameState.on(new PlayerWasSentToPenaltyBox("player"));

        gameState.on(new CurrentPlayerWas("player"));
        assertThat(gameState.getCurrentPlayer()).isEqualTo(new Player("player").inPenaltyBox(true));
    }

    @Test
    public void should_have_questions_and_categories_at_startup() {
        GameState gameState = new GameState();

        Map<Category, Integer> questions = gameState.getQuestions();
        
        assertThat(questions).containsExactly(
                entry(Sports, 50),
                entry(Rock, 50),
                entry(Pop, 50),
                entry(Science, 50)
        );
    }
    
    @Test
    public void should_decrement_questions_once_asked() {
        GameState gameState = new GameState();
        
        gameState.on(new QuestionWasAsked(Science, 0));

        assertThat(gameState.getQuestions().get(Science)).isEqualTo(49);
    }

    @Test
    public void should_not_have_any_dice_at_startup() {
        GameState gameState = new GameState();
        
        assertThat(gameState.getDice()).isNull();
    }
    
    @Test
    public void should_have_dice() {
        GameState gameState = new GameState();
        
        gameState.on(new Rolled(5));

        assertThat(gameState.getDice()).isEqualTo(5);
    }

    @Test
    public void should_write_json_file() {
        int firstLocation = 1;
        int firstGoldCoins = 2;
        int secondLocation = 3;
        int secondGoldCoins = 4;
        Category currentCategory = Science;
        int dice = 2;
        ObjectMapper mapper = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .enable(SerializationFeature.INDENT_OUTPUT);
        GameState expectedState = new GameState();
        Player currentPlayer = expectedState.players.add("first").location(firstLocation).goldCoins(firstGoldCoins).inPenaltyBox(false);
        expectedState.players.add("second").location(secondLocation).goldCoins(secondGoldCoins).inPenaltyBox(true);
        expectedState.questions.remove(Pop, 0).remove(Pop, 1).remove(Pop, 2).remove(Rock, 0).remove(Rock, 1).remove(Science, 0);
        expectedState.currentCategory = Optional.of(currentCategory);
        expectedState.currentPlayer = Optional.of(currentPlayer);
        expectedState.dice = Optional.of(2);
        GameState gameState = new GameState();

        gameState.on(new PlayerWasAdded("first"));
        gameState.on(new LocationWas("first", firstLocation));
        gameState.on(new NewGoldCoinsCount("first", firstGoldCoins));
        gameState.on(new PlayerWasAdded("second"));
        gameState.on(new LocationWas("second", secondLocation));
        gameState.on(new NewGoldCoinsCount("second", secondGoldCoins));
        gameState.on(new PlayerWasSentToPenaltyBox("second"));
        gameState.on(new QuestionWasAsked(Pop, 0));
        gameState.on(new QuestionWasAsked(Pop, 1));
        gameState.on(new QuestionWasAsked(Pop, 2));
        gameState.on(new QuestionWasAsked(Rock, 0));
        gameState.on(new QuestionWasAsked(Rock, 1));
        gameState.on(new QuestionWasAsked(Science, 0));
        gameState.on(new CategoryWas(currentCategory));
        gameState.on(new CurrentPlayerWas("first"));
        gameState.on(new Rolled(dice));

        try {
            assertThat(file).exists().hasContent(mapper.writeValueAsString(expectedState));
        } catch (JsonProcessingException e) {
            fail("Error during serializing expected state", e);
        }
    }
}