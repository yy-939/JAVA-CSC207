package userinterface;

import account.AccountSystem;
import event.EventSystem;
import message.MessageSystem;
import room.RoomSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This controller class represents a speaker system, which is a child class of abstract class UserSystem.
 * It also stores a SpeakerPresenter class which can provide information for SpeakerSystem
 * @author Group0065
 * @version 1.0.0
 */

public class SpeakerSystem extends UserSystem {
    protected SpeakerPresenter speakerPresenter;

    /**
     * Creates a speakerSystem with specific speaker account's username
     * @param username A string represents the username of this speaker account
     */
    public SpeakerSystem(String username) {
        super(username);
        speakerPresenter = new SpeakerPresenter();
    }

    /**
     * Provides additional options for user to choose the messaging operations
     * that the speaker wants to do:
     * the first additional option is to send messages to all attendees for one or multiple events
     * the second additional option is to end the selecting menu
     * @param accountSystem accountSystem
     */
    @Override
    protected void messageOption(AccountSystem accountSystem) {
        Scanner c = new Scanner(System.in);
        String input;
        for (;;) {
            speakerPresenter.printMessageMenu();
            input = c.nextLine();
            if (!super.generalMessageOption(input, accountSystem)) {
                switch(input) {
                    case "10":
                        String event = eventSystem.checkExistence();
                        if (!(event == null)){
                            if(eventSystem.checkHostEvent(this.username, event)){
                                messageSystem.sendMessageToList(this.username,eventSystem.getAttendees(event));
                            }
                        }
                        break;
                    case "r":
                        return;
                    default:
                        speakerPresenter.printInvalidInput();
                }
            }
        }

    }

    /**
     * Provides additional options for user to choose the event related operations
     * that the speaker wants to do:
     * the first additional option is to view a list of given events
     * the second additional option is to end the selecting menu
     * @param accountSystem accountSystem
     */
    @Override
    protected void eventOption(AccountSystem accountSystem) {
        Scanner c = new Scanner(System.in);
        String input;
        for (;;) {
            speakerPresenter.printEventMenu();
            input = c.nextLine();
            if (!super.generalEventOption(input, accountSystem)) {
                switch(input) {
                    case "11":
                        accountSystem.getSpeakerEvents(this.username);
                        break;
                    case "r":
                        return;
                    default:
                        speakerPresenter.printInvalidInput();
                }
            }
        }
    }

    /**
     * Provides additional options for user to choose the account related operations
     * that the speaker wants to do:
     * to end the selecting menu
     * @param accountSystem accountSystem
     */
    @Override
    protected void accountOption(AccountSystem accountSystem) {
        Scanner c = new Scanner(System.in);
        String input;
        for (;;) {
            speakerPresenter.printAccountMenu();
            input = c.nextLine();
            if (!super.generalAccountOption(input, accountSystem)) {
                switch(input) {
                    case "r":
                        return;
                    default:
                        speakerPresenter.printInvalidInput();
                }
            }
        }

    }
}
