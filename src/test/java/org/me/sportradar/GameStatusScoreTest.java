package org.me.sportradar;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SoftAssertionsExtension.class)
class GameStatusScoreTest {

    @Test
    void shouldCreateFreshGameScore(SoftAssertions softly) {
        assertThat(new GameScore()).isNotNull();
        softly.assertThat(new GameScore().home).isEqualTo(0);
        softly.assertThat(new GameScore().away).isEqualTo(0);
    }

    @Test
    void whenScoreUpdateIsGivenUpdateShouldBeApplied(SoftAssertions softly){
        int homeUpdate = 1;
        int awayUpdate = 10;
        GameScore gameScoreUpdated = new GameScore(homeUpdate, awayUpdate);
        softly.assertThat(gameScoreUpdated.home).isEqualTo(homeUpdate);
        softly.assertThat(gameScoreUpdated.away).isEqualTo(awayUpdate);
    }
}