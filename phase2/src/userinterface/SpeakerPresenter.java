package userinterface;

import java.util.List;

/**
 * This class is an presenter class of SpeakerSystem. It extends the abstract class UserPresenter.
 * This class displays the message to the speakerSystem about what selections can be performed.
 * @author Group0065
 * @version 1.0.0
 */
public class SpeakerPresenter extends UserPresenter {

    /**
     * Provides an additional message for additional message options in SpeakerSystem.
     */
    @Override
    void printMessageMenu() {
        List<String> options = super.getGeneralMessageOptionMenu();
        options.add("send messages to all attendees for one or more events");
        super.printMenu(options, super.getReturnToMessage());
    }

    /**
     *  Provides an additional message for additional event options in SpeakerSystem.
     *  It provides a description of case "8"
     */
    @Override
    void printEventMenu() {
        List<String> options = super.getGeneralEventOptionMenu();
        options.add("view a list of events giving by you");
        super.printMenu(options, super.getReturnToMessage());
    }
}
