package Exceptions;

public class PhoneTypeOutOfDomainException extends Exception{
    public PhoneTypeOutOfDomainException(String s){
        System.out.println("Phone Type should be home, mobile, and work," +
                "Your is: " + s);
    }
}
