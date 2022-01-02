package userinterface;

import conferencemain.MainPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is an presenter class of userSystem.
 * This class displays the message to the user about what selections can be performed.
 * @author Group0065
 * @version 1.0.0
 */
public class UserPresenter extends MainPresenter {


    /**
     * Provides an optionMenu for the user so that the user can understand the options based
     * on the information given by this method
     * @param username A string represents the username of this account
     */
    void optionMenu(String username){
        System.out.println("Welcome: " + username);
        List<String> lst = Arrays.asList("manage my account", "see my messages", "manage my events");
        super.printMenu(lst, "log out");
    }

    /**
     * Provides messages to confirm whether the user wants to logout
     */
    void logOut(){
        System.out.println("Do you wish to log out?");
        System.out.println("y: Yes");
        System.out.println("n: No");
        getInput();
    }

    /**
     * Provides messages to confirm whether the user wants to save data
     */
    void saveDataMenu() {
        List<String> lst = Arrays.asList("save data and logout", "un-save data and logout");
        super.printMenu(lst, "redo action");
    }

    /**
     * Provides messages to tell user he/she has successfully log out
     */
    void logOutSuccess() {
        super.printActionMessage("You've successfully log out.");
    }

    /**
     * Returns a collections of of possible options of general message option menu
     * @return a collection of options
     */
    List<String> getGeneralMessageOptionMenu() {
        System.out.println("Welcome to my message:");
        return new ArrayList<> (Arrays.asList(
                "view message received",
                "view message sent",
                "view friendlist contact information",
                "delete my messages received",
                "delete my messages sent",
                "view received messages in message ID order",
                "view sent messages in message ID order",
                "view unread message",
                "mark message as unread",
                "send messages to friends"
                ));
    }

    /**
     * Returns a message of returning to System option page
     * @return a String representation of message
     */
    String getReturnToMessage() {
         return "return to System option page";
     }

    /**
     * Returns a collections of of possible options of general event option menu
     * @return a collection of options
     */
     List<String> getGeneralEventOptionMenu() {
         System.out.println("Welcome to my event");
         return new ArrayList<>(Arrays.asList(
                "view a schedule of events",
                "view signed up events",
                "view a schedule of events that available to you",
                "view schedule of events on a given time",
                "view schedule of events with a given room",
                "view schedule of events by a specific Speaker",
                "view schedule of events by a given date",
                "view schedule of events you have enrolled",
                "sign up for an event",
                "cancel an enrollment of event",
                 "view the description of a event"
         ));
     }

    /**
     * Returns a collections of of possible options of general account option menu
     * @return a collection of options
     */
    List<String> getGeneralAccountOptionMenu() {
        System.out.println("Welcome to my account:");
        return new ArrayList<> (Arrays.asList(
                "view account info",
                "change password",
                "view friendlist",
                "add account to friendlist",
                "delete account from friendlist"
        ));
    }

    /**
     * Prints the general message menu
     */
    void printMessageMenu() {
        super.printMenu(getGeneralMessageOptionMenu(), getReturnToMessage());
    }

    /**
     * Prints the general event menu
     */
    void printEventMenu() {
        super.printMenu(getGeneralEventOptionMenu(), getReturnToMessage());
    }

    /**
     * Prints the general account menu
     */
    void printAccountMenu() {
        super.printMenu(getGeneralAccountOptionMenu(), getReturnToMessage());
    }
}