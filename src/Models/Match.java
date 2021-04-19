package Models;

import java.sql.Date;

public class Match implements Model{
    int id;
    String p1Name;
    String p2Name;
    int p1GamesWon;
    int p2GamesWon;
    Date datePlayed;
    int courtNumber;
    String venueName;

    public Match(int id, String p1Name, String p2Name, int p1GamesWon, int p2GamesWon,
                 Date datePlayed, int courtNumber, String venueName){
        this.id = id;
        this.p1Name = p1Name;
        this.p2Name = p2Name;
        this.p1GamesWon = p1GamesWon;
        this.p2GamesWon = p2GamesWon;
        this.datePlayed = datePlayed;
        this.courtNumber = courtNumber;
        this.venueName = venueName;
    }

    @Override
    public String[] toStringArray() {
        return new String[]{"" + id, p1Name, p2Name, "" + p1GamesWon,
                "" + p2GamesWon, datePlayed.toString(), courtNumber + "", venueName};
    }
}
