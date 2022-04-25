package org.me.sportradar;

public class GameScore {
    public final int home;
    public final int away;

    public GameScore(){
        this(0,0);
    }

    public GameScore(int home, int away) {
        this.home = home;
        this.away = away;
    }

}
