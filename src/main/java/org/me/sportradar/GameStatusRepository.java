package org.me.sportradar;

import java.util.stream.Stream;

interface GameStatusRepository {

    long addGameStatus(GameStatus gameStatus);
    Stream<GameRecord> getAllRecord();
}
