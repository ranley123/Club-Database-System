package Model;

public class PlayerPhone implements Model{
    String email;
    String phoneNumber;
    String phoneType;

    public PlayerPhone(String email, String phoneNumber, String phoneType){
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
    }

    @Override
    public String[] toStringArray() {
        return new String[]{email, phoneNumber, phoneType};
    }
}
