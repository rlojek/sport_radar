package org.me.sportradar;


import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class BoardAcceptanceTest {

    private GameStatusRepository repo = new InMemRecordRepo();
    private SummaryGenerator summaryGenerator = new SummaryGenerator(SummaryFormatter::formatSummary);
    FootballBoard theBoard = new FootballBoard(repo, summaryGenerator);

    @Test
    void acceptanceTest() {
        //given
        Stream<String> inputScores = Stream.of(
                "Mexico - Canada: 0 - 5",
                "Spain - Brazil: 10 - 2",
                "Germany - France: 2 - 2",
                "Uruguay - Italy: 6 - 6",
                "Argentina - Australia: 3 - 1");
        Stream<GameScoreInput> gameScoreInputStream = generateInputs(inputScores);
        //when
        gameScoreInputStream.forEach(this::driveScoreBoard);
        Stream<String> summary = theBoard.getSummary();
        //then
        assertThat(summary).containsExactly(
                "Uruguay 6 - Italy 6",
                "Spain 10 - Brazil 2",
                "Mexico 0 - Canada 5",
                "Argentina 3 - Australia 1",
                "Germany 2 - France 2");
    }

    private void driveScoreBoard(GameScoreInput gsi) {
        theBoard.startGame(gsi.home, gsi.away);
        theBoard.updateScore(new GameScore(gsi.homeScore, gsi.awayScore));
        theBoard.finishGame();
    }

    private Stream<GameScoreInput> generateInputs(Stream<String> gameScores) {
        Function<String, GameScoreInput> map = s -> {
            String[] teamScore = s.split(": ");
            String[] teams = teamScore[0].split(" - ");
            String[] scores = teamScore[1].split(" - ");
            return GameScoreInput.of(teams[0], teams[1], scores[0], scores[1]);
        };
        return gameScores.map(map);
    }

    private static class GameScoreInput {
        final String home;
        final String away;
        final int homeScore;
        final int awayScore;

        private GameScoreInput(String home, String away, int homeScore, int awayScore) {
            this.home = home;
            this.away = away;
            this.homeScore = homeScore;
            this.awayScore = awayScore;
        }

        public static GameScoreInput of(String home, String away, String homeScore, String awayScore) {
            return new GameScoreInput(home, away, Integer.valueOf(homeScore), Integer.valueOf(awayScore));
        }
    }
}
