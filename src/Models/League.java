package Models;

public class League implements Model{
    String name;
    int year;
    float prizeMoney;
    String winnerEmail;

    public League(String name, int year, float prizeMoney, String winnerEmail){
        this.name = name;
        this.year = year;
        this.prizeMoney = prizeMoney;
        this.winnerEmail = winnerEmail;
    }

    public String getName(){return name;}

    public int getYear(){return year;}

    @Override
    public String[] toStringArray() {
        return new String[] {name, year + "", prizeMoney + "", winnerEmail};
    }
}
