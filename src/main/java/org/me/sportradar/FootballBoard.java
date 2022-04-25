package org.me.sportradar;

public class FootballBoard {
    public Game startGame(String homeName, String awayName) {

        return new Game(homeName, awayName);
    }
}
