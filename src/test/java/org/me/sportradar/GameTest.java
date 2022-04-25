package org.me.sportradar;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameTest {

    @Test
    void shouldCreateGameStatusWithTeamsNamesAndFreshScore() {
        String homeName = "From Home";
        String awayName = "Away coming";
        Game cut = new Game(homeName, awayName);
        assertThat(cut.homeTeam).isEqualTo(homeName);
        assertThat(cut.awayTeam).isEqualTo(awayName);
        assertThat(cut.gameScore.home).isEqualTo(0);
        assertThat(cut.gameScore.away).isEqualTo(0);
    }

}