package org.me.sportradar;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SoftAssertionsExtension.class)
class GameScoreTest {

    @Test
    void shouldCreateFreshGameScore(SoftAssertions softly) {
        GameScore cut = new GameScore();
        assertThat(cut).isNotNull();
        softly.assertThat(cut.home).isEqualTo(0);
        softly.assertThat(cut.away).isEqualTo(0);
    }

    @Test
    void whenScoreUpdateIsGivenUpdateShouldBeApplied(SoftAssertions softly){
        GameScore cut = new GameScore();
        int homeUpdate = 1;
        int awayUpdate = 10;
        GameScore gameScoreUpdated = cut.updateScore(homeUpdate, awayUpdate);
        softly.assertThat(gameScoreUpdated.home).isEqualTo(homeUpdate);
        softly.assertThat(gameScoreUpdated.away).isEqualTo(awayUpdate);
    }

}