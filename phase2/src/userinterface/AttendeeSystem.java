package userinterface;

import account.AccountSystem;
import event.EventSystem;
import message.MessageSystem;
import room.RoomSystem;

import java.util.List;
import java.util.Scanner;

/**
 * AttendeeSystem is an controller class of attendees.
 * AttendeeSystem stores the username, his/her own message, event, room systems about the attendee.
 * The methods in AttendeeSystem interact with presenter, display the menu for the attendee to operate his/her
 * message, event, room, account systems.
 * @author Group0065
 * @version 1.0.0
 */
public class AttendeeSystem extends UserSystem {
    protected AttendeePresenter attendeePresenter;


    /** Creates an attendee system with the specified username
     * @param username The username of this attendee.
     */
    public AttendeeSystem(String username){
        super(username);
        attendeePresenter = new AttendeePresenter();
    }

    /** Displays the message menu options for attendee account. It includes the common message options of all types
     * of users. Also, it extends that:
     * to view the speakers contact information for their signed-up events,
     * send messages to speakers of their signed-up events
     * end this menu.
     * @param accountSystem
     */
    @Override
    protected void messageOption(AccountSystem accountSystem) {
        Scanner c = new Scanner(System.in);
        String input;
        for(;;) {
            attendeePresenter.printMessageMenu();
            input =  c.nextLine();
            if (!super.generalMessageOption(input, accountSystem)) {
                switch(input) {
                    case "10":
                        List<String> signedEvents = accountSystem.getSignedEvents(this.username);
                        eventSystem.getSpeakersForSignedEvents(signedEvents);
                        break;
                    case "11":
                        List<String> signedUpEvents = accountSystem.getSignedEvents(this.username);
                        List<String> speakers = eventSystem.getSpeakersForSignedEvents(signedUpEvents);
                        messageSystem.sendMessageToList(this.username,speakers);
                        break;

                    case "r":
                        return;
                    default:
                        attendeePresenter.printInvalidInput();
                }
            }
        }
    }

    /** Displays the event menu options for attendee account. It includes the common event options of all types
     * of users. Also, it extends one more option to end the menu.
     * @param accountSystem
     */
    @Override
    protected void eventOption(AccountSystem accountSystem) {
        Scanner c = new Scanner(System.in);
        String input;
        for(;;) {
            attendeePresenter.printEventMenu();
            input = c.nextLine();
           if (!super.generalEventOption(input, accountSystem)) {
               switch(input) {
                   case "r":
                       return;
                   default:
                       attendeePresenter.printInvalidInput();
               }
           }
        }
    }

    /** Displays the account menu options for attendee account. It includes the common account options of all types
     * of users. Also, it extends one more option to end the menu.
     * @param accountSystem
     */
    @Override
    protected void accountOption(AccountSystem accountSystem) {
        Scanner c = new Scanner(System.in);
        String input;
        for(;;) {
            attendeePresenter.printAccountMenu();
            input = c.nextLine();
            if (!super.generalAccountOption(input, accountSystem)) {
                switch (input) {
                    case "r":
                        return;
                    default:
                        attendeePresenter.printInvalidInput();
                }
            }
        }
    }
}