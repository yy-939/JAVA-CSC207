package account;

import java.sql.Timestamp;
import java.util.*;

/**
 * An entity class represents an speaker account, which is a child class of abstract class Account.
 * It stores a list of events that the speaker given in addition.
 * @author Group0065
 * @version 1.0.0
 */

public class Speaker extends Account {
    private Map<String, Timestamp[]> hostingEvents;

    /** Creates a speaker account with specific username and his/her account password.
     * @param name A string represents the username of this speaker account.
     * @param password A string represents the password of this speaker account.
     */
    public Speaker(String name, String password) {
        super(name, password);
        hostingEvents = new HashMap<>();
    }

    /** Gets the type of this speaker account.
     * @return A string that shows the type of this account is speaker.
     */
    @Override
    protected String getType() {
        return "Speaker";
    }

    /** Gets the list of events that the speaker given.
     * @return A list of Strings which represents the unique IDs of events that the speaker given.
     */
    @Override
    protected List<String> getSpecialList() {
        return new ArrayList<>(hostingEvents.keySet());
    }

    /** Adds new event to the list of events that the speaker given.
     * @param startTime A Timestamp represents the time of this event.
     * @param endTime A Timestamp represents the time of this event.
     * @param id A String represents the unique ID of this event.
     */
    @Override
    protected void addToSpecialList(Timestamp startTime, Timestamp endTime, String id) {
        Timestamp[] times = {startTime, endTime};
        hostingEvents.put(id, times);
        events.putIfAbsent(id, times);
    }

    /** Removes am event from the list of events that the speaker given.
     * @param id A String represents the unique ID of this event.
     */
    @Override
    protected void removeFromSpecialList(String id) {
        if (hostingEvents.get(id) != null) {
            hostingEvents.remove(id, hostingEvents.get(id));
            events.remove(id, events.get(id));
        }
    }

    /** Displays a description for this speaker account about the number of events he/she gives.
     * @return A string representing the number of events given by this speaker account.
     */
    @Override
    public String toString(){
        return super.toString() + "\nYou are hosting " + hostingEvents.size() + " events.";
    }

    /** Checks if this speaker is available at a given time.
     * @param startTime A Timestamp represents the time that be checked.
     * @param endTime A Timestamp represents the time that be checked.
     * @return true iff this speaker is available at this time and is not hosting an event at this time, false if
     * the speaker is not available at that time.
     */
    @Override
    protected boolean available(Timestamp startTime,Timestamp endTime){
        return super.available(startTime, endTime);
    }

}
