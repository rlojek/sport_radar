package org.me.sportradar;

import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;


public class SummaryGenerator {

    private final Function<GameStatus, String> formatGameStatus;

    public SummaryGenerator(Function<GameStatus, String> formatGameStatus) {
        this.formatGameStatus = formatGameStatus;
    }

    public Stream<String> generateSummary(Stream<GameRecord> gameRecords){
        Comparator<GameRecord> gameScore = comparing(gr -> gr.gameStatus.gameScore.home + gr.gameStatus.gameScore.away);
        Stream<String> formattedSummery = gameRecords
                .sorted(gameScore.thenComparing(gr -> gr.id).reversed())
                .map(r -> r.gameStatus)
                .map(formatGameStatus);
        return formattedSummery;
    }

}
