package account;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * An entity class represents an organizer account, which is a child class of abstract class Account.
 * It stores a list of events that the organizer organized in addition, no matter the event is removed or not.
 * @author Group0065
 * @version 1.0.0
 */

public class Organizer extends Account {
    private List<String> organizedEvents;

    /** Creates an organizer account with specific username and his/her account password.
     * @param name A string represents the username of this organizer account.
     * @param password A string represents the password of this organizer account.
     */
    public Organizer(String name, String password) {
        super(name, password);
        organizedEvents = new ArrayList<>();
    }

    /** Gets a string represents the type of this organizer account.
     * @return A string that shows the type of this account is organizer.
     */
    @Override
    protected String getType() {
        return "Organizer";
    }

    /** Gets the list of events that the organizer organized.
     * @return A list of strings which represents the unique id of each event that the organizer organized.
     */
    @Override
    protected List<String> getSpecialList() {
        return new ArrayList<>(organizedEvents);
    }

    /** Adds a new event to the list of events that the organizer organized.
     * @param startTime A Timestamp represents the time of this event.
     * @param endTime A Timestamp represents the time of this event.
     * @param id A String represents the unique ID of this event.
     */
    @Override
    protected void addToSpecialList(Timestamp startTime, Timestamp endTime, String id) {
        organizedEvents.add(id);
    }

    /** Removes an event from the list of events that the organizer organized.
     * @param id A String represents the unique ID of this event.
     */
    @Override
    protected void removeFromSpecialList(String id) {
        organizedEvents.remove(id);
    }

    /** Displays a description for this organizer account about the number of events he/she is organizing.
     * @return A string representing the number of events organized by this organizer account in addition.
     */
    @Override
    public String toString(){
        return super.toString() + "\nYou have organized " + organizedEvents.size() + " events.";
    }

}
