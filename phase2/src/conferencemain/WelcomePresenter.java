package conferencemain;


import java.util.Arrays;
import java.util.List;

/**
 * An presenter class of WelcomeSystem and displays the message to the user about what selections can be performed.
 * @author Group0065
 * @version 1.0.3
 */
public class WelcomePresenter extends MainPresenter {

    /**
     * Provides a welcome message to the user when he/she first start the program
     * @param systemName a String represents the Conference system's name
     */
    void printWelcomeMessage(String systemName) {
        super.printWelcomeMessage(systemName, "Please enter 'q' to quit " + systemName);
    }

    /**
     * Prints messages represents the request of user
     * @param action a string represents the action required to execute
     */
    void requestTo(String action) {
        System.out.println("Request to " + action);
    }

    /**
     * Displays the main menu to user
     */
    void printMenu() {
        List<String> lst = Arrays.asList("request to login", "request to create an account");
        printMenu(lst, null);
    }


    /**
     * Displays the exit menu to user
     */
    void exit(){
        System.out.println("Do you wish to exit?");
        System.out.println("y: Yes");
        System.out.println("n: No");
        getInput();
    }
}
