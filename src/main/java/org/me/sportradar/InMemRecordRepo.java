package org.me.sportradar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class InMemRecordRepo implements GameStatusRepository{

    //not thread safe by design ;)
    private final List<GameRecord> repo = new ArrayList();

    //should be external sequence generator - I need some sleep tonight :D
    private static AtomicLong sequnece = new AtomicLong( 0L);

    @Override
    public long addGameStatus(GameStatus gameStatus) {
        long id = sequnece.getAndIncrement();
        repo.add(new GameRecord(id, gameStatus));
        return id;
    }

    @Override
    public Stream<GameRecord> getAllRecord() {
        return repo.stream();
    }
}
