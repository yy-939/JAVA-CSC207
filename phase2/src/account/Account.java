package account;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;
/** An abstract class represents all type of accounts.
 * An account can store this user account's username, password, friends list, associated events，and whether
 * this user account can do messaging operations.
 * @author Group0065
 * @version 1.0.0
 */
public abstract class Account implements Serializable {
    protected Map<String, Timestamp[]> events;
    private final String username; // No getters as the username are already stored in use case
    private String password;
    @SuppressWarnings("FieldMayBeFinal")
    private List<String> friends;
    private boolean messagable;

    /** Creates an account with the specified username and password.
     * @param name The username of this account.
     * @param password The password to log in this account.
     */
    public Account(String name, String password) {
        this.events = new HashMap<>();
        this.username = name;
        this.password = password;
        this.friends = new ArrayList<>();
        this.messagable = true;
    }

    /** Gets the password of this account.
     * @return A String representing the account’s password.
     */
    protected String getPassword(){
        return password;
    }

    /** Sets the password of this account.
     * @param password A String containing the new password for this account.
     */
    protected void setPassword(String password){
        this.password = password;
    }

    /** Checks if the account has time to attend the event or not.
     * @param startTime A Timestamp representing the time of an event.
     * @param endTime A Timestamp representing the time of an event.
     * @return true iff the account is available to join an event on the given time, false if this account cannot.
     */
    protected boolean available(Timestamp startTime, Timestamp endTime){
        for (String event: events.keySet()){
            Timestamp[] times = events.get(event);
            if (!(times[0].after(endTime) || times[1].before(startTime))) {
                return false;
            }
        }
        return true;
    }

    /** Adds an event into the collection of all signed events of this account.
     * @param startTime A Timestamp representing the time of the new event.
     * @param endTime A Timestamp representing the time of the new event.
     * @param id A String representing the id of the new event.
     */
    protected void addEvent(Timestamp startTime, Timestamp endTime, String id){
        Timestamp[] times = {startTime, endTime};
        events.put(id, times);
    }

    /** Checks if this account can cancel the event or not by given event time and ID.
     * @param id A String representing the id of event want to be canceled.
     * @return true iff this account canceled the given event successfully, false if the cancellation fails.
     */
    protected boolean removeEvent(String id) {
        if (events.get(id) != null) {
            events.remove(id, events.get(id));
            return true;
        }
        return false;
    }

    /** Gets all the signed events of this account.
     * @return A List of event id representing all the signed events along with their time in ascending order.
     */
    protected Map<String, Timestamp[]> getEvents(){
        return this.events;
    }

    /** Checks if this account is able to be messaged.
     * @return true iff the account can be messaged, false if this account cannot.
     */
    protected boolean getMessagable(){
        return messagable;
    }

    /** Gets all the friends of this account.
     * @return A List of usernames representing all the friends of this account.
     */
    protected List<String> getFriends(){
        return new ArrayList<>(friends);
    }

    /** Gets the type of the account.
     * @return A String representing the type of this account.
     */
    protected abstract String getType();

    /** Gets the collection of events that are specialized regarding to the type of the account.
     * @return A List of event id representing all the specialized events operating by this account.
     */
    protected abstract List<String> getSpecialList();

    /** Adds a specialized event into the collection of all specialized events operating by this account.
     * @param startTime A Timestamp representing the time of an new event that this account could operate.
     * @param endTime A Timestamp representing the time of an new event that this account could operate.
     * @param id A String representing the unique id of that new event.
     */
    protected abstract void addToSpecialList(Timestamp startTime, Timestamp endTime, String id);

    /** Removes a specialized event from the collection of all specialized events operating by this account.
     * @param id A String representing the unique id of that new event.
     */
    protected abstract void removeFromSpecialList(String id);

    /** Displays a description for this account including general information in type, username, signed events
     * and friends.
     * @return A String representing the information including type, username, signed events list
     * and friends list.
     */
    public String toString(){
        return "Type: " + this.getType() + "\nUsername: " + username + "\nYou have signed up for " +
                events.size() + " events" + "\nYou have " + friends.size() + " user(s) in your favourite user list.";
    }

    /** Checks if this account has a friend with username given
     * @param username A String representing the username of the friend account going to be checked.
     * @return true iff this account has a friend of the given username, false otherwise.
     */
    protected boolean hasFriend(String username) { return this.friends.contains(username); }

    /** Adds this account has a friend with username given
     * @param username A String representing the username of the friend account going to be added.
     */
    protected void addFriend(String username) { friends.add(username); }


    /** Removes the given friend from this account's friend list
     * @param username A String representing the username of the friend account going to be removed.
     */
    protected void removeFriend(String username) {
        friends.remove(username);
    }

}
