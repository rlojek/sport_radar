package org.me.sportradar;

public class GameStatus {
    public final String homeTeam;
    public final String awayTeam;

    public final GameScore gameScore;

    public GameStatus(String homeTeam, String awayTeam) {
        this(homeTeam, awayTeam, new GameScore());
    }

    public GameStatus(String homeTeam, String awayTeam, GameScore gameScore) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.gameScore = gameScore;
    }

    public GameStatus updateScore(GameScore freshScore) {
        return new GameStatus(homeTeam, awayTeam, freshScore);
    }
}
