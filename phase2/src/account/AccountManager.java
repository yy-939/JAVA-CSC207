package account;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

/**
 * A use case class that can manager Account entity
 * It stores all accounts
 * @author Group0065
 * @version 1.0.0
 */

public class AccountManager implements Serializable {

    // This maps the type of the account to another map which maps the username of a account to the account entity
    private Map<String, Map<String, Account>> allAccounts = new HashMap<>();

    /**
     * Gets the account with corresponding username.
     * @param username A string representing the username of account for finding.
     * @return An Account with username or null if there is no account with such username.
     */
    public Account findAccountByUsername(String username){
        for (String type : allAccounts.keySet()){
            Account account = (allAccounts.get(type) != null) ? allAccounts.get(type).get(username) : null;
            if (account != null) return account;
        }
        return null;
    }

    /**
     * Gets all different account types.
     * @return An Set with Strings of different account types.
     */
    public ArrayList<String> getAllAccountType(){
        AccountFactory af = new AccountFactory();
        return af.getAllType();
    }

    /**
     * Checks if this user exist.
     * @param username A string representing the username of the account for checking.
     * @return true iff the account with given username exists, false if the account does not exist.
     */
    public boolean checkUser(String username) {
        return getAccountInfo(username) != null;
    }

    /**
     * Gets all usernames of accounts basing on the type
     * @param type A string represents the type of accounts needed.
     * @return a List of Strings that represent all usernames of the given type of accounts.
     */
    public List<String> getUsernameForType(String type){
        List<String> result = new ArrayList<>();
        Map<String, Account> users = allAccounts.get(type.toLowerCase());
        if (users != null) {result.addAll(users.keySet());}
        return result;
    }

    /**
     * Check if the given password matches the account's password.
     * @param username A string represents the username of this account.
     * @param password A string represents the password that be checked.
     * @return A string represents the type of this account iff the account exists and with the password matching
     * with that account's password, null if the account does not exist or the password does not match.
     */
    public String checkPassword(String username, String password){
        Account account = findAccountByUsername(username);
        return (account == null || !account.getPassword().equals(password)) ? null : account.getType();
    }

    /**
     * Adds a new account to specific account lists basing on the type of this new account. If this account is a
     * speaker account, it is added to speakerList. ELse, it is added to allAccounts.
     * Assumes that this account does not exist
     * @param accountType A string represents the type of this account.
     * @param username A string represents the username of this account.
     * @param password A string represents the password of this account.
     */
    public void addAccount(String accountType, String username, String password) {
        AccountFactory af = new AccountFactory();
        allAccounts.computeIfAbsent(accountType.toLowerCase(), k -> new HashMap<>());
        allAccounts.get(accountType.toLowerCase()).put(username, af.getAccount(accountType, username, password));
    }

    /**
     * Checks if a given account is available at given time
     * @param startTime A Timestamp represents the time.
     * @param endTime A Timestamp represents the time.
     * @param user A string represents the username of the account.
     * @return true iff this account exists and is available at given time, false if the account does not exist
     * or is not available at that time.
     */
    public boolean freeAtTime(Timestamp startTime, Timestamp endTime, String user) {
        Account account = findAccountByUsername(user);
        return (account != null) && account.available(startTime, endTime);
    }

    /**
     * Signs up a event for an account
     * @param startTime The Timestamp represents the time of the event.
     * @param endTime The Timestamp represents the time of the event.
     * @param event A string represents the unique id of this event.
     * @param username A string represents the username of an account.
     * @return true iff this account exists and is available at the given time, false if this account does not exist
     * or is not available at that time to attend this event.
     */
    public boolean signUpEvent(Timestamp startTime, Timestamp endTime, String event, String username){
        Account curAccount = findAccountByUsername(username);
        if (curAccount != null && curAccount.available(startTime, endTime)){
            curAccount.addEvent(startTime, endTime, event);
            return true;
        }
        return false;
    }

    /**
     * Cancels a event for an account
     * @param event A string represents the unique id of this event.
     * @param username A string represents the username of this account.
     * @return true iff this account exists and the event can be removed from account's eventList successfully,
     * false if this account does not exist or the removing fails.
     */
    public boolean dropEvent(String event, String username){
        Account curAccount = findAccountByUsername(username);
        return curAccount != null && curAccount.removeEvent(event);
    }

    /**
     * Gets a list of strings representing the events that the user signed up.
     * @param username A string represents the username of the account.
     * @return A SortedMap of user current signed up events if the target account is not null.
     */
    public Map<String, Timestamp[]> viewSignedUpEvents(String username) {
        Account curAccount = findAccountByUsername(username);
        if (curAccount == null) return null;
        return curAccount.getEvents();
    }

    /**
     * Adds a new friend to the list of friends of the account.
     * @param username A string represents the username of the account.
     * @param friend A string represents the username of the friend account.
     * @return true iff the account exist, the friend account exists and does not exist in the account's friend list,
     * and add the friend successfully, false if the either accounts does not exist or the friend account already in
     * the friend list of the account.
     */
    public boolean addFriend(String username, String friend){
        Account curAccount = findAccountByUsername(username);
        if (curAccount == null || findAccountByUsername(friend) == null) return false;
        if (!curAccount.hasFriend(friend)) {
            curAccount.addFriend(friend);
            return true;
        }
        else return false;
    }

    /**
     * Removes a friend from the list of friends of the account.
     * @param username A string represents the username of the account.
     * @param friend A string represents the username of the friend account.
     * @return true iff the account exist, the friend exists and exist in the account's friend list,
     * and removes friend successfully, false if the either accounts does not exist or the friend account is not in
     * the friend list of the account.
     */
    public boolean removeFriend(String username, String friend){
        Account curAccount = findAccountByUsername(username);
        if (curAccount == null || findAccountByUsername(friend) == null) return false;
        if (curAccount.hasFriend(friend)) {
            curAccount.removeFriend(friend);
            return true;
        }
        else return false;
    }

    /**
     * Gets the account's friend list.
     * @param username A string represents the username of the account.
     * @return A list of strings represent the usernames of the account's friends if this account exists,
     * null if the account does not exist.
     */
    public List<String> getFriendList(String username){
        Account curAccount = findAccountByUsername(username);
        if (curAccount == null) return null;
        return curAccount.getFriends();
    }

    /**
     * Checks if an account can do messaging operations.
     * @param username A string represents the username of this account.
     * @return true iff this account exists and this account can do messaging operations, false if the account does
     * not exist or it exists but cannot do messaging operations.
     */
    public boolean checkMessagable(String username){
        Account curAccount = findAccountByUsername(username);
        return curAccount != null && curAccount.getMessagable();
    }

    /**
     * Gets the collection of events that are specialized regarding to the type of the account.
     * @param username A string represents the username of this account.
     * @return A list of event id represents all the specialized events operating by this account.
     */
    public List<String> getSpecialList(String username){
        Account curAccount = findAccountByUsername(username);
        if (curAccount == null) return null;
        return curAccount.getSpecialList();
    }

    /**
     * Adds a specialized event into the collection of all specialized events operating by this account.
     * @param startTime A Timestamp represents the time of the event.
     * @param endTime A Timestamp represents the time of the event.
     * @param id A string represents the id of this event.
     * @param username A string represents the username of this account.
     */
    // This method is not used as changing a speaker for a talk is not included in phase 1. Speakers need to be fixed
    public void addToSpecialList(Timestamp startTime, Timestamp endTime, String id, String username){
        Account curAccount = findAccountByUsername(username);
        if (curAccount == null) {
            return;
        }
        curAccount.addToSpecialList(startTime, endTime, id);
    }

    /** Removes an event from the list of events that the organizer organized.
     * @param id A string represents the unique ID of this event.
     */
    public void removeFromSpecialList(String id, String username) {
        Account curAccount = findAccountByUsername(username);
        if (curAccount != null) curAccount.removeFromSpecialList(id);
    }

    /**
     * Gets specific information of an account.
     * @param username A string represents the username of this account.
     * @return A string represents the detailed information of the account if this account exists.
     */
    public String getAccountInfo(String username){
        Account curAccount = findAccountByUsername(username);
        if (curAccount == null) return null;
        return curAccount.toString();
    }

    /**
     * Sets a new password for an account.
     * @param new_password A string represents the new password.
     * @param username A string represents the username of this account.
     */
    public void setPassword(String new_password, String username) {
        Account curAccount = findAccountByUsername(username);
        if (curAccount == null) return;
        curAccount.setPassword(new_password);
    }

    /**
     * Gets password of an account.
     * @param username A string represents the username of this account.
     */
    public String getPassword(String username) {
        Account account = findAccountByUsername(username);
        if (account != null) {
        return account.getPassword();}
        return null;
    }

    /**
     * Gets available time that the user is free
     * @param username Username of this user
     * @return List of available time
     */
    public List<Timestamp[]> getUnAvailableTime(String username) {
        List<Timestamp[]> unAvailableTime = new ArrayList<>();
        Map<String, Timestamp[]> allEvents = findAccountByUsername(username).getEvents();
        for (String event: allEvents.keySet()) {
            unAvailableTime.add(allEvents.get(event));
        }
        return unAvailableTime;
    }
}


