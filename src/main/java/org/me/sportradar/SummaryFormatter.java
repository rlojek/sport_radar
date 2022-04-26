package org.me.sportradar;

public interface SummaryFormatter {

    static String formatSummary(GameStatus gameStatus) {
            GameScore score = gameStatus.gameScore;
            return gameStatus.homeTeam + " " + score.home + " - " + gameStatus.awayTeam + " " + score.away;
    }
}
