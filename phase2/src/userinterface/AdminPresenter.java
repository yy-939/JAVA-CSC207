package userinterface;

import java.util.List;

/**
 * This is a presenter class for AdminSystem
 * @author Group0065
 * @version 1.0.1
 */
public class AdminPresenter extends UserPresenter {
    /**
     * Prints the message menu for admins
     */
    @Override
    void printMessageMenu() {
        List<String> options = super.getGeneralMessageOptionMenu();
        options.add("view all Speaker contact information");
        options.add("send messages to all Speakers");
        options.add("send messages to all Attendees");
        options.add("delete messages");
        super.printMenu(options, super.getReturnToMessage());
    }

    /**
     * Prints the event menu for admins
     */
    @Override
    void printEventMenu() {
        List<String> options = super.getGeneralEventOptionMenu();
        options.add("delete events with no attendees");
        super.printMenu(options, super.getReturnToMessage());
    }
}
