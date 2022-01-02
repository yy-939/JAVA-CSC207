package account;


import authentication.ConferenceRegisterSystem;
import authentication.SimpleValidationPassword;
import Input.InputStrategy;
import Input.UserInput;

import java.sql.Timestamp;
import java.util.*;

/**
 * An upper controller of account.
 * It stores AccountManager, EventManager, SimpleValidationPassword, AccountPresenter and ConferenceRegisterSystem.
 * It gets input from user and give corresponding moves by calling methods from AccountManager, EventManager,
 * SimpleValidationPassword, AccountPresenter and ConferenceRegisterSystem. To do kinds of operation to user's account.
 * @author Group0065
 * @version 1.0.6
 */

public class AccountSystem {
    private AccountManager accounts;
    private SimpleValidationPassword validator;
    private AccountPresenter presenter;
    private ConferenceRegisterSystem register;
    private InputStrategy sc;

    /**
     * Creates an account system with specific account manager and event manager.
     *
     * @param accounts An AccountManager represents the user's account manager.
     */
    public AccountSystem(AccountManager accounts) {
        this.accounts = accounts;
        this.validator = new SimpleValidationPassword();
        this.presenter = new AccountPresenter();
        this.register = new ConferenceRegisterSystem();
        this.sc = new UserInput();
    }

    public AccountManager getAccounts() {
        return accounts;
    }

    /**
     * Gets info of current account.
     * Prints type, username, number of event it has singed up and number of friends it has.
     */
    public void displayCurrentAccount(String username) {
        presenter.accountInfo(accounts.getAccountInfo(username));
    }

    /**
     * Prints a string that contains all speakers.
     */
    public void getSpeakerList() {
        System.out.print("Speaker List: ");
        presenter.getAllSpeakers(accounts.getUsernameForType("Speaker"));
    }

    // helper
    private boolean speakerExist(String speakerName) {
        return accounts.getUsernameForType("Speaker").contains(speakerName);
    }

    /**
     * Prints strings to guide the user change password if the new password is valid and the result about if the
     * operation is successful.
     *
     * @param username The username of current user.
     */
    public void changePassword(String username) {
        presenter.askNewPassword();
        String newPassword = sc.inputString();
        if(newPassword == null){
            return;
        }
        if (accounts.getPassword(username).equals(newPassword)) {
            presenter.samePassword();
            return;
        }
        boolean create = validator.validate(newPassword);
        if (create) {
            accounts.setPassword(newPassword, username);
            presenter.changePasswordResult();
        } else {
            System.out.println(validator.getDescription());
        }
    }

    /**
     * Prints a string of the user has.
     *
     * @param username The username of current user.
     */
    public void viewFriendList(String username) {
        presenter.getFriendList(accounts.getFriendList(username));
    }

    /**
     * Prints a string of the events the user signed up.
     *
     * @param username The username of current user.
     */
    public void viewSignEvents(String username) {
        presenter.getSignedEvents(accounts.viewSignedUpEvents(username));
    }

    public List<String> getSignedEvents(String username) {
        Map<String, Timestamp[]> allEvents = this.accounts.viewSignedUpEvents(username);
        return new ArrayList<>(allEvents.keySet());
    }

    /**
     * Prints a string reflecting the result of adding a friend to friendList.
     *
     * @param username The username of current user.
     */
    public void addFriend(String username) {
        presenter.askUsername();
        String userToAdd = sc.inputString();
        if(userToAdd == null){
            return;
        }
        presenter.addFriendResult(accounts.addFriend(username, userToAdd));
    }

    /**
     * Prints a string reflecting the result of removing a friend from friendList.
     *
     * @param username The username of current user.
     */
    public void removeFriend(String username) {
        presenter.askUsername();
        String userToDelete = sc.inputString();
        if(userToDelete == null){
            return;
        }
        presenter.deleteFriendResult(accounts.removeFriend(username, userToDelete));
    }

    /**
     * Prints a string reflecting the result of sign up for an event.
     *
     * @param username The username of current user.
     * @return
    //        presenter.askEventId();
    //        String eventId = sc.nextLine();
    //        if (events.checkEvent(eventId)) {
     */
    public String signUpEvent(String username, Timestamp starTime, Timestamp endTime, String eventId) {
        if (starTime == null || eventId == null || endTime == null){
            return null;
        }
        presenter.singUpEventResult(accounts.signUpEvent(starTime, endTime, eventId, username));
        return username;}

    /**
     * Prints a string reflecting the result of cancel the enrollment of an event.
     *
     * @param username The username of current user.
     */
    public String dropEvent(String username, Timestamp myTime, String eventId){
        if (myTime == null || eventId == null || username == null){
            return null;
        }else if (!accounts.viewSignedUpEvents(username).containsKey(eventId)) {
            return null;
        }
        presenter.cancelEventResult(accounts.dropEvent(eventId, username));
        return username;}

    /**
     * Remove signed up events for attendee(s)
     * @param usernames the string represents the username(s) of attendee(s)
     * @param myTime the event time
     * @param eventId the event id
     */
    public void dropEventsForAttendee(List<String> usernames, Timestamp myTime, String eventId){
        if (myTime == null || eventId == null || usernames == null){
            return;
        }
        for(String username : usernames){
            accounts.dropEvent(eventId, username);
        }
    }

    /**
     * Requests input of speaker name from user and print a string reflecting all events that are given
     * by a same speaker.
     */
    public String getSpeakerEvents() {
        presenter.askSpeakerName();
        String speakerName = sc.inputString();
        if(speakerName == null){
            return null;
        }
        if (speakerExist(speakerName)) {
            return speakerName;
        } else {
            presenter.speakerNotExist();
            return null;
        }
    }

    /**
     * Prints a list of events that is created by a organizer.
     *
     * @param organizerName The username of user need to be checked.
     */
    public void getOrganizedEvents(String organizerName) {
        presenter.eventOrganized(accounts.getSpecialList(organizerName));
    }

    /**
     * Prints a list of events that is given by this speaker.
     *
     * @param speakerName The username of user need to be checked.
     */
    public void getSpeakerEvents(String speakerName) {
        presenter.getSpeakerEvents(accounts.getSpecialList(speakerName));
    }

    /**
     * Sets username, password to create an account (account type given by user) and prints a string reflecting
     * the result of this operation.
     */
    public void addAccount() {
        ArrayList<String> typeList = accounts.getAllAccountType();
        presenter.createAccount(typeList);
        Integer input = sc.inputInteger();
        if (input == null) {
            return;
        } else {
            if (input < typeList.size()) {
                register.createAccount(typeList.get(input), accounts);
            } else {
                presenter.noAccountType(input.toString());
            }
        }
    }

    /**
     * Returns the all usernames according to the user type
     * @param type A string represents teh user type
     * @return A list of string representing all the username of the account type
     */
    public List<String> getUsernameForType(String type) {
        if (this.accounts.getUsernameForType(type).isEmpty()) {
            presenter.noAccountType(type);
            return null;
        } else {
            return this.accounts.getUsernameForType(type);
        }
    }

    /**
     * Returns a receiver list from the sender's favorite list for sending message if the sender is messageable.
     * @param currUser A string representing the username of the sender.
     * @return A list of strings representing all username of valid receivers.
     */
    public List<String> checkUserList(String currUser){
        if(!accounts.checkMessagable(currUser) || !accounts.checkUser(currUser)){
            return null;
        }
        List<String> result = new ArrayList<>();
        presenter.askReceivers();
        String receiver = sc.inputString();
        if(receiver == null){
            return null;
        }
        while(!receiver.equalsIgnoreCase("s")){
            if(accounts.getFriendList(currUser).contains(receiver)){
                result.add(receiver);
                presenter.addReceiverSuccessfully(receiver);
            }
            else{
                presenter.invalidReceiver(receiver);
            }
            receiver = sc.inputString();
        }
        return result;
    }

    /**
     * Returns the username if the user is free during a certain time period.
     * @param startTime A timestamp representing the start time.
     * @param endTime A timestamp representing the end time.
     * @param user A string representing the username of the user.
     * @return A string representing the user who is checked that has time during the period.
     */
    public String freeAtTime(Timestamp startTime, Timestamp endTime, String user) {
        if (accounts.freeAtTime(startTime, endTime, user)) {
            return user;
        } else {
            presenter.notAvailable(startTime, endTime, user);
            return null;
        }
    }

    /**
     * Returns a host list for a event.
     * @param startTime A timestamp representing the start time of the event.
     * @param endTime A timestamp representing the end time of the event.
     * @param eventId A string representing the event id.
     * @return a list of strings representing the name of host speakers for the event.
     */
    public List<String> addHostEvents(Timestamp startTime, Timestamp endTime, String eventId,
                                      List<String> oriSpeakers) {
        List<String> result = new ArrayList<>();
        presenter.askSpeakerName();
        String speakerName = sc.inputString();
        if(speakerName == null){
            return null;
        }
        while (!(speakerName.equalsIgnoreCase("s"))) {
            if(speakerExist(speakerName) && !(oriSpeakers.contains(speakerName))
                    && !(freeAtTime(startTime, endTime, speakerName) == null)){
            accounts.addToSpecialList(startTime, endTime, eventId, speakerName);
            result.add(speakerName);
            oriSpeakers.add(speakerName);
            presenter.successfullySchedule(speakerName);
            } else if (oriSpeakers.contains(speakerName)){
                presenter.existedHost();
            } else{presenter.failAddHostEvents();}
            presenter.askNextSpeaker();
            speakerName = sc.inputString();
            if(speakerName == null){
                return null;
            }
        }
        return result;
    }

    // helper
    private boolean checkOrganizer(String organizerName) {
        return accounts.getUsernameForType("Organizer").contains(organizerName);
    }

    /**
     * Adds the event into the organizing list of an organizer.
     * @param startTime A timestamp representing the start time of the event.
     * @param endTime A timestamp representing the end time of the event.
     * @param eventId A string representing the event id.
     * @param username A string representing the organizer name.
     */
    public void addOrganizeEvents(Timestamp startTime, Timestamp endTime, String eventId, String username) {
        if (checkOrganizer(username)) {
            accounts.addToSpecialList(startTime, endTime, eventId,username);
        } else {
            presenter.organizerNotExist();

        }
    }

    /**
     * Returns the event id which is cancelled from the special list of all host speakers.
     * @param time A timestamp representing the start time of the event.
     * @param eventId A string representing the event id.
     * @param usernames A list of strings representing all the username of host speakers.
     * @return A string representing the event id that is removed from all speakers' list.
     */
    public String cancelEventsFromSpecialList(Timestamp time, String eventId, List<String> usernames) {
        if (time == null || eventId == null||usernames == null){
            return null;
        }
        for (String username : usernames){
            accounts.removeFromSpecialList(eventId, username);
            accounts.dropEvent(eventId, username);
        }
        return eventId;
    }

    /**
     * Returns a time list for unavailable time of the user.
     * @param username A string representing the username of user.
     * @return A list of timestamp list representing the unavailable time periods.
     */
    public List<Timestamp[]> getUnAvailableTime(String username) {
        return this.accounts.getUnAvailableTime(username);
        // if some are not available, presenter will tell user "xxx1 is not available" "xxx2 is not available" etc.
    }

    /**
     * Returns a list of speakers who are available at the certain time period.
     * @param startTime A timestamp representing the start time.
     * @param endTime A timestamp representing the end time.
     * @return A list of strings representing the username of available speakers.
     */
    public List<String> checkSpeaker(Timestamp startTime, Timestamp endTime){
        List<String> result = new ArrayList<>();
        presenter.askSpeakerName();
        String userName = sc.inputString();
        if(userName == null){return null;}
        while(!userName.equals("s")){
            if(speakerExist(userName) && !(freeAtTime(startTime, endTime, userName) == null)){
                result.add(userName);
                presenter.successfullySchedule(userName);
                presenter.askNextSpeaker();
            }
            else{
                presenter.invalidSpeakerAskNext(userName);
            }
            userName = sc.inputString();
            if(userName==null){return null;}
        }
        return result;

    }

    /**
     * Prints out the summary of all existed accounts statistic information.
     */
    public void getAllAccountsInformation(){
        List<String> allType = accounts.getAllAccountType();
        Map<String, Integer> allStats = new HashMap<>();
        String maxPeople = "";
        int currTypeNumber = 0;
        int acc = 0;
        for (String eachType : allType){
            List<String> allUsers = accounts.getUsernameForType(eachType);
            allStats.put(eachType, allUsers.size());
            if (allUsers.size() > currTypeNumber){
                currTypeNumber = allUsers.size();
                maxPeople = eachType;
            }
            acc += allUsers.size();
        } double maxPercentage = (currTypeNumber * 1.0) / (acc * 1.0);
        if (allStats.isEmpty() || maxPeople.equals("") || acc == 0){
            presenter.noUsersExist();
            return;}
        presenter.printAccountsStatsInfo(allStats, maxPeople, maxPercentage, acc);
    }

    /**
     * Schedules the speaker in certain time period for an event
     * @param startTime A timestamp representing the start time.
     * @param endTime A timestamp representing the end time.
     * @param eventId A string representing the event id.
     * @param speakerNames A list of strings representing the username of available speakers.
     */
    public void scheduleEventForSpeaker(Timestamp startTime, Timestamp endTime, String eventId, List<String> speakerNames) {
        if(startTime == null || endTime == null || eventId == null || speakerNames == null || speakerNames.isEmpty()){
            return;}
        for (String each : speakerNames){
            if (!(freeAtTime(startTime, endTime, each) == null)){
                accounts.addToSpecialList(startTime, endTime, eventId, each);
            }else{presenter.failAddHostEvents();
            return;}}
    }
}

