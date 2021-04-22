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

    public int getNumber(){return number;}

    public String getVenueName(){return venueName;}

    public String getVenueAddress(){return venueAddress;};

    @Override
    public String toString(){
        return venueName + " " + number + " " + venueAddress;
    }


    public Object[] toObjectArray(){
        return new Object[]{number, venueName, venueAddress};
    }
}
