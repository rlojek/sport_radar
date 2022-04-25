package org.me.sportradar;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GameScoreTest {

    @Test
    void shouldCreateFreshGameScore() {
        GameScore cut = new GameScore();
        assertThat(cut).isNotNull();
        assertThat(cut.home).isEqualTo(0);
        assertThat(cut.away).isEqualTo(0);
    }

    @Test
    void whenScoreUpdateIsGivenUpdateShouldBeApplied(){
        GameScore cut = new GameScore();
        int homeUpdate = 1;
        int awayUpdate = 10;
        GameScore gameScoreUpdated = cut.updateScore(homeUpdate, awayUpdate);
        assertThat(gameScoreUpdated.home).isEqualTo(1);
        assertThat(gameScoreUpdated.away).isEqualTo(10);
    }

}