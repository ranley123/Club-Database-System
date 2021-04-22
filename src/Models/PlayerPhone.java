package Models;

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
    public Object[] toObjectArray() {
        return new Object[]{email, phoneNumber, phoneType};
    }
}
