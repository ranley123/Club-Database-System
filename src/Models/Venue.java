package Models;

public class Venue implements Model{
    String name;
    String address;

    public Venue(String name, String address){
        this.name = name;
        this.address = address;
    }

    @Override
    public String[] toStringArray() {
        return new String[]{name, address};
    }
}
