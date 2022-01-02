package account;

import java.sql.Timestamp;
import java.util.List;

/**
 * An entity class represents an admin account, which is a child class of abstract class Account.
 * @author Group0065
 * @version 1.0.0
 */
public class Admin extends Account {
    /**
     * Creates an admin account with specific username and his/her account password.
     * @param name A string represents the username of this admin account.
     * @param password  A string represents the password of this admin account.
     */
    public Admin(String name, String password) {
        super(name, password);
    }

    /**
     * Gets the type of the account.
     * @return A String representing the type of this account.
     */
    @Override
    protected String getType() {
        return "Admin";
    }

    /**
     * Gets the collection of events that are specialized regarding to the type of the account.
     * @return A List of event id representing all the specialized events operating by this account.
     */
    @Override
    protected List<String> getSpecialList() {
        return null;
    }

    /**
     * Adds a specialized event into the collection of all specialized events operating by this account.
     * @param startTime A Timestamp representing the time of an new event that this account could operate.
     * @param endTime A Timestamp representing the time of an new event that this account could operate.
     * @param id   A String representing the unique id of that new event.
     */
    @Override
    protected void addToSpecialList(Timestamp startTime, Timestamp endTime, String id) {
    }

    /**
     * Removes a specialized event from the collection of all specialized events operating by this account.
     * @param id A String representing the unique id of that new event.
     */
    @Override
    protected void removeFromSpecialList(String id) {
    }
}
