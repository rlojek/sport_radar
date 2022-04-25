package org.me.sportradar;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameStatusTest {

    @Test
    void shouldCreateGameStatusWithTeamsNamesAndFreshScore() {
        String homeName = "From Home";
        String awayName = "Away coming";
        GameStatus cut = new GameStatus(homeName, awayName);
        assertThat(cut.homeTeam).isEqualTo(homeName);
        assertThat(cut.awayTeam).isEqualTo(awayName);
        assertThat(cut.gameScore.home).isEqualTo(0);
        assertThat(cut.gameScore.away).isEqualTo(0);
    }

    @Test
    void shouldAssignNewScore() {
        String homeName = "From Home";
        String awayName = "Away coming";
        GameStatus cut = new GameStatus(homeName, awayName);
        GameScore freshScore = new GameScore();
        GameStatus updated = cut.updateScore(freshScore);
    }

}