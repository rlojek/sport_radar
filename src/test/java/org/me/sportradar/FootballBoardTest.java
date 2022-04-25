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
        GameStatus theGameStatus = cut.startGame(homeName, awayName);
        //then
        assertThat(theGameStatus).isNotNull();
    }

    @Test
    void shouldUpdateOngoingGameScoreWithGivenNumbers()  {
        //given
        FootballBoard cut = new FootballBoard();
        String homeName = "home";
        String awayName = "away";
        GameStatus theGameStatus = cut.startGame(homeName, awayName);

        GameScore aScore = new GameScore(3,1);
        //when
        theGameStatus = cut.updateScore(aScore);

        //then
        GameScore gameScore = theGameStatus.gameScore;
        assertThat(gameScore.home).isEqualTo(3);
        assertThat(gameScore.away).isEqualTo(1);
    }


}