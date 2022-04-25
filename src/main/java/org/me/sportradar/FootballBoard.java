package org.me.sportradar;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

public class FootballBoard {

    private final GameStatusRepository gameStatusRepository;
    private final Function<GameStatus, String> formatGameStatus = gs ->{
        GameScore score = gs.gameScore;
        return gs.homeTeam + " - " + gs.awayTeam + ": " + score.home + " - " + score.away;
    };

    private GameStatus gameStatus = null;

    private BiFunction<String, String, GameStatus> startGameHandler = gameStarter;

    private BiFunction<GameStatus, GameScore, GameStatus> gameUpdateHandler = gameNotStartedUpdate;

    private Function<GameStatus, Long> gameFinisHandler = finishNotStartedGame ;

    public FootballBoard(GameStatusRepository gameStatusRepository) {
        this.gameStatusRepository = gameStatusRepository;
    }

    public GameStatus startGame(String homeName, String awayName) {

        this.gameStatus = startGameHandler.apply(homeName, awayName);
        this.startGameHandler = doubleGameStarter;
        this.gameUpdateHandler = scoreUpdater;
        this.gameFinisHandler = gameStatusRepository::addGameStatus;
        return this.gameStatus;
    }

    public GameStatus updateScore(GameScore scoreUpdate) {
        GameStatus freshStatus = gameUpdateHandler.apply(this.gameStatus, scoreUpdate);
        this.gameStatus = freshStatus;
        return freshStatus;
    }

    public Long finishGame(){
        Long recordId = gameFinisHandler.apply(this.gameStatus);
        this.startGameHandler = gameStarter;
        this.gameUpdateHandler = gameNotStartedUpdate;
        this.gameFinisHandler = finishNotStartedGame;
        this.gameStatus = null;
        return recordId;
    }
    public Stream<String> getSummary() {
        Comparator<GameRecord> gameScore = comparing(gr -> gr.gameStatus.gameScore.home + gr.gameStatus.gameScore.away);
        return gameStatusRepository.getAllRecord()
                .sorted(gameScore.reversed().thenComparing(gr -> gr.id))
                .map(r -> r.gameStatus)
                .map(formatGameStatus);
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

    private static Function<GameStatus, Long> finishNotStartedGame = gs ->{
        throw new RuntimeException("Game record not started yet");
    };

}
