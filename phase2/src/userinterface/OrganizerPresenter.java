package userinterface;

import java.util.List;

/**
 * OrganizerPresenter is an presenter class of organizer.
 * OrganizerPresenter displays the message to the organizer users about what selections they can perform.
 * @author Group0065
 * @version 1.0.0
 */
public class OrganizerPresenter extends UserPresenter {

    /**
     * Displays the description of additional message options for organizer.
     */
    @Override
    void printMessageMenu() {
        List<String> options = super.getGeneralMessageOptionMenu();
        options.add("view all Speaker contact information");
        options.add("send messages to all Speakers");
        options.add("send messages to all Attendees");
        super.printMenu(options, super.getReturnToMessage());
    }

    /**
     * Displays the description of additional event options for organizer.
     */
    @Override
    void printEventMenu() {
        List<String> options = super.getGeneralEventOptionMenu();
        options.add("view all events created by you");
        options.add("schedule a Speaker");
        options.add("cancel an event");
        options.add("reschedule an event");
        options.add("create an event");
        options.add("view all rooms");
        options.add("add a room");
        options.add("change the capacity of an event");
        options.add("view the statistic information of all events in this conference");
        options.add("view top 5 events with highest attend rate");
        super.printMenu(options, super.getReturnToMessage());
    }

    /**
     * Displays the description of additional account options for organizer.
     */
    @Override
    void printAccountMenu() {
        List<String> options = super.getGeneralAccountOptionMenu();
        options.add("create an account");
        options.add("view the statistic information of all the accounts");
        super.printMenu(options, super.getReturnToMessage());
    }
}
