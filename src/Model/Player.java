package Model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

public class Player implements Model{
    String email;
    String forename;
    String middlename;
    String surname;
    Date dateOfBirth;
    ArrayList<String> phoneNumbers;

    public Player(String email, String forename, String middlename, String surname, Date dateOfBirth, String phoneNumber){
        this.email = email;
        this.forename = forename;
        this.middlename = middlename;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        phoneNumbers = new ArrayList<>();
        phoneNumbers.add(phoneNumber);
    }

    @Override
    public String toString() {
        return forename;
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
    public String[] toStringArray() {
        return new String[]{email, forename, middlename, surname, dateOfBirth.toString()};
    }
}
