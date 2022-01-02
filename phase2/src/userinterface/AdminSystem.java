package userinterface;

import account.AccountSystem;

import java.util.Scanner;

/**
 * This class is a subclass of UserSystem, which contains menu for admin user.
 * @author Group0065
 * @version 1.0.2
 */
public class AdminSystem extends UserSystem {
    protected AdminPresenter adminPresenter;

    /**
     * Creates a UserSystem with specific account username
     * @param username A string represents the username of this user account
     */
    public AdminSystem(String username){
        super(username);
        adminPresenter = new AdminPresenter();
    }

    /** Displays the message menu options for admin account. It includes the common message options of all types
     * of users. Also, it extends that:
     * view all Speaker contact information
     * send messages to all Speakers
     * send messages to all Attendees
     * delete messages
     * end this menu.
     * @param accountSystem AccountSystem
     */
    @Override
    protected void messageOption(AccountSystem accountSystem) {
        Scanner c = new Scanner(System.in);
        String input;
        for(;;) {
            adminPresenter.printMessageMenu();
            input =  c.nextLine();
            if (!super.generalMessageOption(input, accountSystem)) {
                switch(input) {
                    // case 8-10 new added
                    case "10":
                        accountSystem.getSpeakerList();
                        break;
                    case "11":
                        messageSystem.sendMessageToList(this.username, accountSystem.getUsernameForType("Speaker"));
                        break;
                    case "12":
                        messageSystem.sendMessageToList(this.username, accountSystem.getUsernameForType("Attendee"));
                        break;
                    case "13":
                        // delete messages
                        messageSystem.adminDeleteMessage();
                        break;
                    case "r":
                        return;
                    default:
                        adminPresenter.printInvalidInput();
                }
            }
        }
    }

    /** Displays the event menu options for admin account. It includes the common event options of all types
     * of users. Also, it extends:
     * delete events with no attendees
     * to end the menu.
     * @param accountSystem AccountSystem
     */
    @Override
    protected void eventOption(AccountSystem accountSystem) {
        Scanner c = new Scanner(System.in);
        String input;
        for(;;) {
            adminPresenter.printEventMenu();
            input = c.nextLine();
            if (!super.generalEventOption(input, accountSystem)) {
                switch(input) {
                    case "11":
                        // delete events with no attendees
                        eventSystem.deleteEmptyEvent();
                        break;
                    case "r":
                        return;
                    default:
                        adminPresenter.printInvalidInput();
                }
            }
        }
    }

    /** Displays the account menu options for admin account. It includes the common event options of all types
     * of users. Also, it extends another option to end the menu.
     * @param accountSystem AccountSystem
     */
    @Override
    protected void accountOption(AccountSystem accountSystem) {
        Scanner c = new Scanner(System.in);
        String input;
        for(;;) {
            adminPresenter.printAccountMenu();
            input = c.nextLine();
            if (!super.generalAccountOption(input, accountSystem)) {
                switch (input) {
                    case "r":
                        return;
                    default:
                        adminPresenter.printInvalidInput();
                }
            }
        }
    }
}
