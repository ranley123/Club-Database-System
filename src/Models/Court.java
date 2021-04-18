package Models;

public class Court implements Model{
    int number;
    String venueName;
    String venueAddress;

    public Court(int number, String venueName, String venueAddress){
        this.number = number;
        this.venueName = venueName;
        this.venueAddress = venueAddress;
    }

    @Override
    public String toString(){
        return venueName + " " + number;
    }


    public String[] toStringArray(){
        String[] stringArrayFormat = {number + "", venueName};
        return stringArrayFormat;
    }
}
