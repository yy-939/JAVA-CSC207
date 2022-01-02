package userinterface;

import java.util.List;

/**
 * AttendeePresenter is an presenter class of attendee.
 * AttendeePresenter displays the message to the attendee users about what selections they can perform.
 * @author Group0065
 * @version 1.0.0
 */
public class AttendeePresenter extends UserPresenter {

    /**
     * Displays the description of additional message options for attendees.
     */
    @Override
    void printMessageMenu() {
        List<String> options = super.getGeneralMessageOptionMenu();
        options.add("view Speaker contact information of signed up events");
        options.add("send messages to speaker of signed up events");
        super.printMenu(options, super.getReturnToMessage());
    }
}
