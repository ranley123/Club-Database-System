package Model;

public class Court implements Model{
    int number;
    String venueName;

    public Court(int number, String venueName){
        this.number = number;
        this.venueName = venueName;
    }
    public String[] toStringArray(){
        String[] stringArrayFormat = {number + "", venueName};
        return stringArrayFormat;
    }
}
