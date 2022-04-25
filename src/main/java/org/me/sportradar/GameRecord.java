package org.me.sportradar;

public class GameRecord {

    public final Long id;
    public final GameStatus gameStatus;

    public GameRecord(Long id, GameStatus gameStatus) {
        this.id = id;
        this.gameStatus = gameStatus;
    }
}
