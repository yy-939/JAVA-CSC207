package account;

import java.sql.Timestamp;
import java.util.List;

/**
 * An entity class represents an attendee account, which is a child class of abstract class Account.
 * @author Group0065
 * @version 1.0.0
 */

class Attendee extends Account {
    /**
     * Creates an attendee account with specific username and his/her account password.
     * @param name A string represents the username of this attendee account.
     * @param password  A string represents the password of this attendee account.
     */
    public Attendee(String name, String password) {
        super(name, password);
    }

    /** Gets the type of this attendee account.
     * @return A string that shows the type of this account is attendee.
     */
    @Override
    protected String getType() {
        return "Attendee";
    }

    /** Gets the list of events specialized for this account (unavailable for attendee right now)
     * @return A list of string of the events specialized to Attendee.
     */
    @Override
    protected List<String> getSpecialList() {
        return null;
    }

    /** Adds a event into the specialized list for this account (unavailable for attendee right now)
     * @param startTime A Timestamp represents the time of this event.
     * @param endTime A Timestamp represents the time of this event.
     * @param id A String represents the unique ID of this event.
     */
    @Override
    protected void addToSpecialList(Timestamp startTime, Timestamp endTime, String id) {
    }

    /** Removes a event from the specialized list for this account (unavailable for attendee right now)
     * @param id A String represents the unique ID of this event.
     */
    @Override
    protected void removeFromSpecialList(String id) {
    }

}
