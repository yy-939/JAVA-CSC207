package authentication;

import account.*;

import java.util.Scanner;

/**
 * A controller class that can ask user input for username and password to log in and stores accountManager.
 * @author Group0065
 * @version 1.0.0
 */

public class ConferenceLoginSystem {
    public ConferenceLoginSystem(){

    }

    /**
     * Asks user to give input of user name and password to log in.
     * @return an array of string of username and its type, null if user request to quit.
     */
    public String[] logIn(AccountManager am){
        ConferencePresenter cp = new ConferencePresenter();
        cp.printWelcomeMessage("Login System");
        Scanner sc = new Scanner(System.in);

        while (true) {
            cp.enterUsernameWithoutRestriction();
            String input = sc.nextLine();
            String username, password, type;
            if ("r".equals(input)) {
                cp.printRedoMessage("Return to previous page");
                return null;
            } else {
                username = input;
            }
            cp.enterPasswordWithoutRestriction();
            password = sc.nextLine();
            type = validateAccount(username, password, am);
            if (type != null){
                cp.printLoginSuccessful();
                return new String[]{username, type};
            }
            cp.printIncorrectUsernameOrPassword();
        }
    }

    /* helper method */
    // This one line method exists because we can encapsulate how the password is checked and account for future changes
    private String validateAccount(String username, String password, AccountManager am){
        return am.checkPassword(username, password);
    }

}
