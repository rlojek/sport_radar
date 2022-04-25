package org.me.sportradar;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FootballBoardTest {

    @Test
    void startGameShouldCreateEmptyScore() {
        //given
        FootballBoard cut = new FootballBoard();
        //when
        String homeName = "home";
        String awayName = "away";
        Game theGame = cut.startGame(homeName, awayName);
        //then
        assertThat(theGame).isNotNull();
    }

}