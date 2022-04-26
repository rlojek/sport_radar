package org.me.sportradar;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class FootballBoardTest {

    private GameStatusRepository gameStatusRepository = mock(GameStatusRepository.class);
    private final FootballBoard footballBoard = new FootballBoard(gameStatusRepository);

    @Test
    void startGameShouldCreateEmptyScore() {
        //when
        String homeName = "home";
        String awayName = "away";
        GameStatus theGameStatus = footballBoard.startGame(homeName, awayName);
        //then
        assertThat(theGameStatus).isNotNull();
    }

    @Test
    void shouldUpdateOngoingGameScoreWithGivenNumbers()  {
        //given
        String homeName = "home";
        String awayName = "away";
        footballBoard.startGame(homeName, awayName);

        GameScore aScore = new GameScore(3,1);
        //when
        GameStatus theGameStatus = footballBoard.updateScore(aScore);

        //then
        GameScore gameScore = theGameStatus.gameScore;
        assertThat(gameScore.home).isEqualTo(3);
        assertThat(gameScore.away).isEqualTo(1);
    }

    @Test
    void shouldFailToUpdateNotStartedGame()  {
        Throwable ex = catchThrowable(() -> footballBoard.updateScore(new GameScore()));
        assertThat(ex).hasMessage("Game record not started yet")
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldFailToStartGameWhileOneIsOngoing() {
        footballBoard.startGame("home", "away");
        Throwable ex = catchThrowable(() -> footballBoard.startGame("home", "away"));
        assertThat(ex).hasMessage("Game record in progress")
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldFailToFinishNotStartedGame() {
        Throwable ex = catchThrowable(() -> footballBoard.finishGame());
        assertThat(ex).hasMessage("Game record not started yet")
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldRestartBoardAfterFinish(){
        //given
        footballBoard.startGame("home", "away");
        footballBoard.updateScore(new GameScore(11, 22));

        //when
        footballBoard.finishGame();
        String freshHome = "fresh home";
        String freshAway = "fresh away";
        GameStatus gameStatus = footballBoard.startGame(freshHome, freshAway);
        //then
        assertThat(gameStatus.homeTeam).isEqualTo(freshHome);
        assertThat(gameStatus.awayTeam).isEqualTo(freshAway);
        assertThat(gameStatus.gameScore.home).isEqualTo(0);
        assertThat(gameStatus.gameScore.away).isEqualTo(0);
    }

    @Test
    @DisplayName("Game Status should be sored in repo when game is finished")
    void shouldAddGameStatusToRepositoryWhenFinished() {
        //given
        footballBoard.startGame("home", "away");
        GameStatus gameStatus = footballBoard.updateScore(new GameScore(11, 22));
        given(gameStatusRepository.addGameStatus(any())).willReturn(1L);
        //when
        Long id = footballBoard.finishGame();
        //then
        assertThat(id).isEqualTo(1L);
        verify(gameStatusRepository).addGameStatus(gameStatus);
    }

    @Test
    void shouldReturnSummaryOfGameRecords() {
        //given
        GameRecord record = new GameRecord(1000L,
                new GameStatus("home", "away", new GameScore()));
        given(gameStatusRepository.getAllRecord()).willReturn(Stream.of(record));
        //when
        Stream<String> summary = footballBoard.getSummary();
        //then
        verify(gameStatusRepository).getAllRecord();
        assertThat(summary)
                .containsExactly("home 0 - away 0");
    }

    @Test
    void shouldOrderSummaryByScore() throws Exception {
        //given
        GameRecord recordLow = new GameRecord(1000L,
                new GameStatus("home Low", "away Low", new GameScore(1,3)));
        GameRecord recordHigh = new GameRecord(1000L,
                new GameStatus("home", "away", new GameScore(10,12)));
        given(gameStatusRepository.getAllRecord()).willReturn(Stream.of(recordLow, recordHigh));
        //when
        Stream<String> summary = footballBoard.getSummary();

        //then
        verify(gameStatusRepository).getAllRecord();
        assertThat(summary)
                .containsExactly("home 10 - away 12", "home Low 1 - away Low 3");
    }

    @Test
    void shouldOrderSummaryWithTheSameScoreByRecordOrder() throws Exception {
        //given
        GameRecord recordFirst = new GameRecord(1000L,
                new GameStatus("home first", "away first", new GameScore(1,3)));
        GameRecord recordLast = new GameRecord(2000L,
                new GameStatus("home", "away", new GameScore(2,2)));
        given(gameStatusRepository.getAllRecord()).willReturn(Stream.of(recordLast, recordFirst));
        //when
        Stream<String> summary = footballBoard.getSummary();

        //then
        verify(gameStatusRepository).getAllRecord();
        assertThat(summary)
                .containsExactly("home 2 - away 2", "home first 1 - away first 3");
    }
}