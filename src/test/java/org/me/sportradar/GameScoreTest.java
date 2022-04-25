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

}