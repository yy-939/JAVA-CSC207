package userinterface;

import account.AccountManager;
import account.AccountSystem;
import data.DataSaver;
import event.EventManager;
import event.EventSystem;
import message.MessageSystem;
import message.MessagingManager;
import room.RoomManager;
import room.RoomSystem;
import data.DataReader;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This controller class represents an abstract user system
 * @author Group0065
 * @version 1.0.0
 */

public abstract class UserSystem {

    protected String username;
    protected MessageSystem messageSystem;
    protected EventSystem eventSystem;
    protected RoomSystem roomSystem;

    /**
     * Creates a UserSystem with specific account's username
     * @param username A string represents the username of this user account
     */
    UserSystem(String username){
        this.username = username;

    }

    /**
     * Sets this UserSystem's messageSystem with the given MessageSystem
     * @param messageSystem A MessageSystem associated with this conference
     */
    public void setMessageSystem(MessageSystem messageSystem){
        this.messageSystem = messageSystem;
    }

    /**
     * Sets this UserSystem's eventSystem with the given EventSystem
     * @param eventSystem A EventSystem associated with this conference
     */
    public void setEventSystem(EventSystem eventSystem){
        this.eventSystem = eventSystem;
    }

    /**
     * Sets this UserSystem's roomSystem with the given RoomSystem
     * @param roomSystem A RoomSystem associated with this conference
     */
    public void setRoomSystem(RoomSystem roomSystem){
        this.roomSystem = roomSystem;
    }

    /**
     * Provides three options and a logout option:
     * if user choose "0", the accountOption would be selected
     * if user choose "1", the messageOption would be selected
     * if user choose "2", the eventOption would be selected
     * if user choose "r" which if the logout option, additional options would be presented to confirm the logout
     * operation and data saving operation.
     * @param accountSystem accountSystem
     * @return true if saved, false if not
     */
    public boolean selectOption(AccountSystem accountSystem){
        Scanner sc = new Scanner(System.in);
        UserPresenter up = new UserPresenter();
        while (true) {
            up.optionMenu(this.username);
            switch (sc.nextLine()){
                case "0":
                    this.accountOption(accountSystem);
                    break;
                case "1":
                    this.messageOption(accountSystem);
                    break;
                case "2":
                    this.eventOption(accountSystem);
                    break;
                case "r":
                    // this will result in logout
                    up.logOut();
                    switch (sc.nextLine()) {
                        case "y":
                            aa:
                            for(;;) {
                                up.saveDataMenu();
                                switch (sc.nextLine()) {
                                    case "0":
                                        saveData(accountSystem);
                                        up.printActionMessage("Save the data");
                                        up.logOutSuccess();
                                        return true;
                                    case "1":
                                        up.printActionMessage("Un-save the data");
                                        up.logOutSuccess();
                                        return false;
                                    case "r":
                                        break aa;
                                    default:
                                        up.printInvalidInput();
                                }
                            }
                            break;
                        case "n":
                            break;
                        default:
                            up.printInvalidInput();
                    }
                    break;
                default:
                    up.printInvalidInput();
            }
        }
    }

    /**
     * Provides options for messaging operations and will be implemented
     * in the subclasses.
     * @param accountSystem accountSystem
     */
    abstract protected void messageOption(AccountSystem accountSystem);

    /**
     * Provides options for event related operations and will be implemented
     * in the subclasses.
     * @param accountSystem accountSystem
     */
    abstract protected void eventOption(AccountSystem accountSystem);

    /**
     * Provides options for account related operations and will be implemented
     * in the subclasses.
     * @param accountSystem accountSystem
     */
    abstract protected void accountOption(AccountSystem accountSystem);


    /**
     * Provides options of messaging operations for user to choose
     * @param input A string represents the decision made by user
     * @param accountSystem accountSystem
     * @return
     * if user choose "0", user can view messages received
     * if user choose "1", user can view messages sent
     * if user choose "2", user can view his/her friend list
     * if uer choose "3", user can delete messages received ( in his/her message box)
     * if uer choose "4", user can delete messages sent ( in his/her message box)
     * if user choose "5", user can view received messages by message ID order ( time order)
     * if user choose "6", user can view sent messages by message ID order ( time order)
     * if user choose "7", user can mark message as unread
     * if user choose "8", user can mark message as unread
     * if user choose "9", user can send messages to users in the friendlist
     * if user does not choose and options above, will return false
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    protected boolean generalMessageOption(String input, AccountSystem accountSystem) {
        switch(input) {
            case "0":
                messageSystem.getMassageReceive(this.username);
                return true;
            case "1":
                messageSystem.getMassageSent(this.username);
                return true;
            case "2":
                accountSystem.viewFriendList(this.username);
                return true;
            case "3":
                messageSystem.deleteReceivedMessage(this.username);
                return true;
            case "4":
                messageSystem.deleteSentMessage(this.username);
                return true;
            case "5":
                messageSystem.getReceivedMessageWithGivenOrder(this.username);
                return true;
            case "6":
                messageSystem.getSentMessageWithGivenOrder(this.username);
                return true;
            case "7":
                messageSystem.viewUnreadMessage(this.username);
                return true;
            case "8":
                messageSystem.markAsUnread(this.username);
                return true;
            case "9":
                messageSystem.sendMessageToList(this.username,
                        accountSystem.checkUserList(this.username));
                return true;
            default:
                return false;
        }

    }

    /**
     * Provides options of event related operations for user to choose
     * @param input A string represents the decision made by user
     * @param accountSystem accountSystem
     * @return
     * if user choose "0", user can view a schedule of events and return true
     * if user choose "1", user can view signed up events and return true
     * if user choose "2", user can view a schedule of events that available to them and return true
     * if user choose "3", user can view schedule of events at a given time and return true
     * if user choose "4", user can view schedule of events with a given room id
     * if user choose "5", user can view schedule of events with a given Speaker username
     * if user choose "6", user can view schedule of events on a given date
     * if user choose "7", user can view schedule of events user have enrolled
     * if user choose "8", user can sign up an event
     * if user choose "9", user can cancel an enrollment of event
     * if user choose "10" user can get the description of a event
     * if user does not choose and options above, will return false
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    protected boolean generalEventOption(String input, AccountSystem accountSystem) {
        switch(input) {
            case "0":
                eventSystem.printEventList();
                return true;
            case "1":
                accountSystem.viewSignEvents(this.username);
                return true;
            case "2":
                List<Timestamp[]> unAvailableList = accountSystem.getUnAvailableTime(this.username);
                eventSystem.viewEventsAttendable(unAvailableList);
                return true;
            case "3":
                eventSystem.getEventByTime();
                return true;
            case "4":
                String room = roomSystem.checkRoomExistence();
                if(room != null) {
                    eventSystem.getEventByLocation(room);
                }
                return true;
            case "5":
                String speaker = accountSystem.getSpeakerEvents();
                if(speaker != null){
                    eventSystem.getEventBySpeaker(speaker);
                }
                return true;
            case "6":
                eventSystem.getEventByDate();
                return true;
            case "7":
                eventSystem.getAssignedEvent(accountSystem.getSignedEvents(username));
                return true;
            case "8":
                String eventID = eventSystem.checkExistence();
                if(eventID == null){
                    return true;
                }
                Timestamp start = eventSystem.getEventStartTimeByID(eventID);
                Timestamp end = eventSystem.getEventEndTimeByID(eventID);
                String username = accountSystem.signUpEvent(this.username, start, end, eventID);
                eventSystem.addAttendee(eventID, username);
                return true;
            case "9":
                String eventId = eventSystem.checkExistence();
                if(eventId == null){
                    return true;
                }
                Timestamp eventTime = eventSystem.getEventStartTimeByID(eventId);
                String attendee = accountSystem.dropEvent(this.username, eventTime, eventId);
                eventSystem.removeAttendee(attendee);
                return true;
            case "10":
                eventSystem.checkDescription();
                return true;
            default:
                return false;
        }
    }

    /**
     * Provides options of account related operations for user to choose
     * @param input A string represents the decision made by user
     * @param accountSystem accountSystem
     * @return
     * if user choose "0", user can view account information and return true
     * if user choose "1", user can change password and return true
     * if user choose "2", user can view friend list and return true
     * if user choose "3", user can add an account to friend list and return true
     * if user choose "4", user can delete an account from friend list and return true
     * if user does not choose and options above, will return false
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    protected boolean generalAccountOption(String input, AccountSystem accountSystem) {
        switch(input) {
            case "0":
                accountSystem.displayCurrentAccount(this.username);
                return true;
            case "1":
                accountSystem.changePassword(this.username);
               return true;
            case "2":
                accountSystem.viewFriendList(this.username);
                return true;
            case "3":
                accountSystem.addFriend(this.username);
                return true;
            case "4":
                accountSystem.removeFriend(this.username);
                return true;
            default:
                return false;
        }
    }

    /**
     * Saves different data
     * @param accountSystem accountSystem
     */
    public void saveData(AccountSystem accountSystem){
        AccountManager accountManager = accountSystem.getAccounts();
        DataSaver<AccountManager> accountSaver = new DataSaver<>();
        accountSaver.saveToFile(accountSaver.getSrcPath("UserInfoDataBase.ser"), accountManager);

        EventManager eventManager = eventSystem.getEvents();
        DataSaver<EventManager> eventSaver = new DataSaver<>();
        eventSaver.saveToFile(eventSaver.getSrcPath("EventDataBase.ser"), eventManager);

        RoomManager roomManager = roomSystem.getRooms();
        DataSaver<RoomManager> roomSaver = new DataSaver<>();
        roomSaver.saveToFile(roomSaver.getSrcPath("RoomDataBase.ser"), roomManager);

        MessagingManager messagingManager = messageSystem.getMessages();
        DataSaver<MessagingManager> messageSaver = new DataSaver<>();
        messageSaver.saveToFile(messageSaver.getSrcPath("MessageDataBase.ser"), messagingManager);
    }


}
