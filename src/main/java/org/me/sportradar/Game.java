package org.me.sportradar;

public class Game {
    public final String homeTeam;
    public final String awayTeam;

    public final GameScore gameScore = new GameScore();

    public Game(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }
}
