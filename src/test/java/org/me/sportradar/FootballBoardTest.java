package org.me.sportradar;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.*;

class FootballBoardTest {

    private GameStatusRepository gameStatusRepository = mock(GameStatusRepository.class);
    private SummaryGenerator summaryGenerator = mock(SummaryGenerator.class);
    private final FootballBoard footballBoard = new FootballBoard(gameStatusRepository, summaryGenerator);

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
    @DisplayName("Get Summary should reach to repo and pass records to generator - integration check")
    void shouldUseRecordsFromRepositoryToGenerateSummary(){
        Stream<GameRecord> recordsStream = Stream.empty();
        given(gameStatusRepository.getAllRecord()).willReturn(recordsStream);
        Stream<String> summaryFormatted = Stream.empty();
        given(summaryGenerator.generateSummary(recordsStream)).willReturn(summaryFormatted);

        Stream<String> summary = footballBoard.getSummary();

        verify(gameStatusRepository).getAllRecord();
        verify(summaryGenerator).generateSummary(recordsStream);
        assertThat(summary).isSameAs(summaryFormatted);
    }
}