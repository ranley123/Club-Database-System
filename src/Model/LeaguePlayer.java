package Model;

public class LeaguePlayer implements Model{
    String email;
    String leagueName;
    int leagueYear;

    public LeaguePlayer(String email, String leagueName, int leagueYear){
        this.email = email;
        this.leagueName = leagueName;
        this.leagueYear = leagueYear;
    }

    @Override
    public String[] toStringArray() {
        return new String[]{email, leagueName, leagueYear + ""};
    }
}
