package Models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

public class Player implements Model{
    String email;
    String fullname;
    Date dateOfBirth;
    String phoneNumbers;

    public String getEmail(){return email;}


    public Player(String email, String fullname, Date dateOfBirth, String phoneNumber){
        this.email = email;
        this.fullname = fullname;
        this.dateOfBirth = dateOfBirth;
        phoneNumbers = phoneNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Player player = (Player) o;
        return Objects.equals(email, player.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }


    @Override
    public Object[] toObjectArray() {
        return new Object[]{fullname, email, phoneNumbers};
    }
}
