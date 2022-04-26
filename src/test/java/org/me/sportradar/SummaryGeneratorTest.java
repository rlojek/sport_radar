package org.me.sportradar;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SummaryGeneratorTest {

    private SummaryGenerator cut = new SummaryGenerator(SummaryFormatter::formatSummary);

    @Test
    void shouldReturnSummaryOfGameRecords() {
        //given
        GameRecord record = new GameRecord(1000L,
                new GameStatus("home", "away", new GameScore()));
        //when
        Stream<String> summary = cut.generateSummary(Stream.of(record));
        //then
        assertThat(summary)
                .containsExactly("home 0 - away 0");
    }

    @Test
    void shouldOrderSummaryByScore(){
        //given
        GameRecord recordLow = new GameRecord(1000L,
                new GameStatus("home Low", "away Low", new GameScore(1,3)));
        GameRecord recordHigh = new GameRecord(1000L,
                new GameStatus("home", "away", new GameScore(10,12)));
        //when
        Stream<String> summary = cut.generateSummary(Stream.of(recordLow, recordHigh));

        //then
        assertThat(summary)
                .containsExactly("home 10 - away 12", "home Low 1 - away Low 3");
    }

    @Test
    void shouldOrderSummaryWithTheSameScoreByRecordOrder(){
        //given
        GameRecord recordFirst = new GameRecord(1000L,
                new GameStatus("home first", "away first", new GameScore(1,3)));
        GameRecord recordLast = new GameRecord(2000L,
                new GameStatus("home", "away", new GameScore(2,2)));
        //when
        Stream<String> summary = cut.generateSummary(Stream.of(recordLast, recordFirst));

        //then
        assertThat(summary)
                .containsExactly("home 2 - away 2", "home first 1 - away first 3");
    }

}