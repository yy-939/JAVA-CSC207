package account;

import java.util.ArrayList;

/**
 * A factory class that can construct a new Attdenee, Speaker or Organizer account.
 * It gives account type, username and password for a new account as parameter.
 * @author Group0065
 * @version 1.0.2
 */
public class AccountFactory {

    /**
     * Creates a new account with given type, username and password.
     * @param accountType A string representing the type of this new account.
     * @param username A string representing the username of this new account.
     * @param password A string representing the password of this new account.
     * @return An Account with these given information.
     */
    public Account getAccount(String accountType, String username, String password) {
        if (accountType == null) {
            return null;
        } else if (accountType.equalsIgnoreCase("ATTENDEE")) {
            return new Attendee(username, password);
        } else if (accountType.equalsIgnoreCase("SPEAKER")) {
            return new Speaker(username, password);
        } else if (accountType.equalsIgnoreCase("ORGANIZER")) {
            return new Organizer(username, password);
        } else if (accountType.equalsIgnoreCase("ADMIN")) {
            return new Admin(username, password);
        } else {
            return null;
        }
    }

    /**
     * Gives all types of user now we can have
     * @return a list of String of different kinds of users
     */
    public ArrayList<String> getAllType(){
        ArrayList<String> result = new ArrayList<String>();
        result.add("ATTENDEE");
        result.add("SPEAKER");
        result.add("ORGANIZER");
        result.add("ADMIN");
        return result;
    }
}

