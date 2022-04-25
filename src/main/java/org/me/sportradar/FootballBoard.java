package org.me.sportradar;

import java.util.function.BiFunction;
import java.util.function.Function;

public class FootballBoard {

    private GameStatus gameStatus = null;

    private  BiFunction<String , String, GameStatus> startGameHandler = gameStarter;

    private BiFunction<GameStatus, GameScore, GameStatus> gameUpdateHandler = (status, score) -> null;

    public GameStatus startGame(String homeName, String awayName) {

        startGameHandler = (x,y) -> null;
        this.gameStatus = gameStarter.apply(homeName, awayName);
        this.gameUpdateHandler = scoreUpdater;
        return this.gameStatus;
    }

    public GameStatus updateScore(GameScore scoreUpdate) {
        return gameUpdateHandler.apply(this.gameStatus, scoreUpdate);
    }


    private static BiFunction<GameStatus, GameScore, GameStatus> scoreUpdater = (status, score) -> {
        return  status.updateScore(score);
    };

    private static BiFunction<String , String, GameStatus> gameStarter = (homeName, awayName) ->{
        GameStatus gameStatus = new GameStatus(homeName, awayName);
        return gameStatus;
    };


}
