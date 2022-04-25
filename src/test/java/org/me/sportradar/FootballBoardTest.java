package org.me.sportradar;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

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
        cut.startGame(homeName, awayName);

        GameScore aScore = new GameScore(3,1);
        //when
        GameStatus theGameStatus = cut.updateScore(aScore);

        //then
        GameScore gameScore = theGameStatus.gameScore;
        assertThat(gameScore.home).isEqualTo(3);
        assertThat(gameScore.away).isEqualTo(1);
    }

    @Test
    void shouldFailToUpdateNotStartedGame()  {
        FootballBoard cut = new FootballBoard();
        Throwable ex = catchThrowable(() -> cut.updateScore(new GameScore()));
        assertThat(ex).hasMessage("Game record not started yet")
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldFailToStartGameWhileOneIsOngoing() {
        FootballBoard cut = new FootballBoard();
        cut.startGame("home", "away");
        Throwable ex = catchThrowable(() -> cut.startGame("home", "away"));
        assertThat(ex).hasMessage("Game record in progress")
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldFailToFinishNotStartedGame() {
        FootballBoard cut = new FootballBoard();
        Throwable ex = catchThrowable(() -> cut.finishGame());
        assertThat(ex).hasMessage("Game record not started yet")
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldRestartBoardAfterFinish(){
        //given
        FootballBoard cut = new FootballBoard();
        cut.startGame("home", "away");
        cut.updateScore(new GameScore(11, 22));

        //when
        cut.finishGame();
        String freshHome = "fresh home";
        String freshAway = "fresh away";
        GameStatus gameStatus = cut.startGame(freshHome, freshAway);
        //then
        assertThat(gameStatus.homeTeam).isEqualTo(freshHome);
        assertThat(gameStatus.awayTeam).isEqualTo(freshAway);
        assertThat(gameStatus.gameScore.home).isEqualTo(0);
        assertThat(gameStatus.gameScore.away).isEqualTo(0);
    }
}