package Model;

import java.sql.Date;

public class Player implements Model{
    String email;
    String forename;
    String middlename;
    String surname;
    Date dateOfBirth;

    public Player(String email, String forename, String middlename, String surname, Date dateOfBirth){
        this.email = email;
        this.forename = forename;
        this.middlename = middlename;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String[] toStringArray() {
        return new String[]{email, forename, middlename, surname, dateOfBirth.toString()};
    }
}
