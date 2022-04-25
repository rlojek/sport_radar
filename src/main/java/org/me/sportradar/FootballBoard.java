package org.me.sportradar;

import java.util.function.BiFunction;
import java.util.function.Function;

public class FootballBoard {

    private GameStatus gameStatus = null;

    private BiFunction<String, String, GameStatus> startGameHandler = gameStarter;

    private BiFunction<GameStatus, GameScore, GameStatus> gameUpdateHandler = gameNotStartedUpdate;

    private Function<GameStatus, GameStatus> gameFinisHandler = finishNotStartedGame ;

    public GameStatus startGame(String homeName, String awayName) {

        this.gameStatus = startGameHandler.apply(homeName, awayName);
        this.startGameHandler = doubleGameStarter;
        this.gameUpdateHandler = scoreUpdater;
        this.gameFinisHandler = finishGameRecord;
        return this.gameStatus;
    }

    public GameStatus updateScore(GameScore scoreUpdate) {
        return gameUpdateHandler.apply(this.gameStatus, scoreUpdate);
    }

    public void finishGame(){
        this.gameStatus = gameFinisHandler.apply(this.gameStatus);
        this.startGameHandler = gameStarter;
        this.gameUpdateHandler = gameNotStartedUpdate;
        this.gameFinisHandler = finishNotStartedGame;
    }

    private static BiFunction<GameStatus, GameScore, GameStatus> scoreUpdater = GameStatus::updateScore;

    private static BiFunction<GameStatus, GameScore, GameStatus> gameNotStartedUpdate = (status, score) -> {
        throw new RuntimeException("Game record not started yet");
    };

    private static BiFunction<String, String, GameStatus> gameStarter = GameStatus::new;

    private static BiFunction<String, String, GameStatus> doubleGameStarter = (homeName, awayName) ->
    {
        throw new RuntimeException("Game record in progress");
    };

    private static Function<GameStatus, GameStatus> finishGameRecord = ga -> null;

    private static Function<GameStatus, GameStatus> finishNotStartedGame = gs ->{
        throw new RuntimeException("Game record not started yet");
    };
}
