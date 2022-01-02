package userinterface;

import account.AccountSystem;
import event.EventSystem;
import message.MessageSystem;
import room.RoomSystem;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

/**
 * This class is an controller class of organizer.
 * This class stores the username, his/her own message, event, room systems about the organizer.
 * The methods in OrganizerSystem interact with presenter, display the specific menu for the organizer to operate
 * his/her message, event, room, account systems.
 * @author Group0065
 * @version 1.0.0
 */
public class OrganizerSystem extends UserSystem {
    protected OrganizerPresenter organizerPresenter;

    /** Creates an organizer system with the specified username
     * @param username The username of this organizer.
     */
    public OrganizerSystem(String username) {
        super(username);
        this.organizerPresenter = new OrganizerPresenter();
    }

    /** Displays the message menu options for organizer account. It includes the common message options of all types
     * of users. Also, it extends more options that are:
     * view a list of Speaker contact information,
     * send messages to all Speakers,
     * send messages to all Attendees,
     * end this menu.
     * @param accountSystem accountSystem
     */
    @Override
    protected void messageOption(AccountSystem accountSystem) {
        Scanner c = new Scanner(System.in);
        String input;
        for(;;) {
            organizerPresenter.printMessageMenu();
            input = c.nextLine();
            if (!super.generalMessageOption(input, accountSystem)) {
                switch(input) {
                    case "10":
                        accountSystem.getSpeakerList();
                        break;
                    case "11":
                        messageSystem.sendMessageToList(this.username, accountSystem.getUsernameForType("Speaker"));
                        break;
                    case "12":
                        messageSystem.sendMessageToList(this.username, accountSystem.getUsernameForType("Attendee"));
                        break;
                    case "r":
                        // End menu
                        return;
                    default:
                        organizerPresenter.printInvalidInput();
                }
            }
        }
    }

    /** Displays the event menu options for organizer account. It inherits the common event options of all types
     * of users. Also, it extends more options that are:
     * view a list of events created,
     * schedule a Speaker,
     * cancel an event,
     * reschedule an event,
     * create an event,
     * view all rooms,
     * add a room,
     * change the capacity of an event
     * view the statistic information of all events in this conference
     * end this menu.
     * @param accountSystem accountSystem
     */
    @Override
    protected void eventOption(AccountSystem accountSystem) {
        Scanner c = new Scanner(System.in);
        String input;
        for(;;) {
            organizerPresenter.printEventMenu();
            input = c.nextLine();
            if (!super.generalEventOption(input, accountSystem)) {
                switch(input) {
                    case "11":
                        accountSystem.getOrganizedEvents(this.username);
                        break;
                    case "12":
                        String eventID = eventSystem.checkExistence();
                        if(eventID==null){
                            break;
                        }
                        if (!eventSystem.canAddHost(eventID)){
                            break;
                        }
                        Timestamp startTime = eventSystem.getEventStartTimeByID(eventID);
                        Timestamp endTime = eventSystem.getEventEndTimeByID(eventID);
                        List<String> eventL = new ArrayList<>();
                        eventL.add(eventID);
                        List<String> oriSpeakers = eventSystem.getSpeakersForSignedEvents(eventL);
                        List<String> newSpeakers = accountSystem.addHostEvents(startTime, endTime, eventID, oriSpeakers);
                        eventSystem.scheduleSpeaker(eventID, newSpeakers);
                        break;
                    case "13":
                        String eventId = eventSystem.checkExistence();
                        if(eventId==null){break;}
                        Timestamp time = eventSystem.getEventStartTimeByID(eventId);
                        String event = eventSystem.cancelEvent(eventId);
                        List<String> speaker = eventSystem.getHosts(event);
                        List<String> attendees = eventSystem.getAttendees(event);
                        List<String> organizer = new ArrayList<>();
                        organizer.add(this.username);
                        accountSystem.cancelEventsFromSpecialList(time, event, organizer);
                        accountSystem.cancelEventsFromSpecialList(time, event, speaker);
                        accountSystem.dropEventsForAttendee(attendees, time, event);
                        roomSystem.removeEventFromRoom(event);
                        break;
                    case "14":
                        String eventToBeCancelled = eventSystem.checkExistence();
                        if(eventToBeCancelled == null){break;}
                        eventSystem.rescheduleEvent(eventToBeCancelled);
                        break;
                    case "15":
                        String location = roomSystem.askForRoomName();
                        if(location ==null){break;}
                        Integer capacity = roomSystem.inputEventCapacity(location);
                        if(capacity == null){
                            break;
                        }
                        Timestamp start = roomSystem.askForStartTime();
                        if(start==null){break;}
                        Timestamp end = roomSystem.askForEndTime();
                        if(end==null){break;}
                        if (roomSystem.CheckroomAvailability(location,start,end)){
                            List<String> speakerList = accountSystem.checkSpeaker(start, end);
                            if(speakerList==null){break;}
                            String newEvent = eventSystem.createEvent(location, capacity, start, end, speakerList);
                            if(newEvent==null){break;}
                            roomSystem.addEventToRoom(location, newEvent, start, end);
                            accountSystem.scheduleEventForSpeaker(start, end, newEvent, speakerList);
                            accountSystem.addOrganizeEvents(start, end, newEvent, this.username);
                        }
                        break;
                    case "16":
                        roomSystem.getRoomsInfo();
                        break;
                    case "17":
                        roomSystem.addRoom();
                        break;
                    case "18":
                        String eventid = eventSystem.checkExistence();
                        if(eventid==null){
                            break;
                        }
                        String room = eventSystem.findRoomByEvent(eventid);
                        if(room==null){break;}
                        Integer eventCapacity = eventSystem.getEventCapacity(eventid);
                        int changedCapacity = roomSystem.changeCapacity(room, eventCapacity);
                        eventSystem.changeEventCapacity(eventid, changedCapacity);
                        break;
                    case "19":
                        eventSystem.getAllEventsSummary();
                        break;
                    case "20":
                        eventSystem.getEventsTopF();
                        break;
                    case "r":
                        return;
                    default:
                        organizerPresenter.printInvalidInput();
                }
            }
        }

    }

    /** Displays the account menu options for organizer account. It inherits the common account options of all types
     * of users. Also, it extends more options that are:
     * create a Speaker account,
     * view the statistic information of all accounts in this conference
     * end this menu.
     * @param accountSystem accountSystem
     */
    @Override
    protected void accountOption(AccountSystem accountSystem) {
        Scanner c = new Scanner(System.in);
        String input;
        for(;;) {
            organizerPresenter.printAccountMenu();
            input = c.nextLine();
            if (!super.generalAccountOption(input, accountSystem)) {
                switch(input) {
                    case "5":
                        accountSystem.addAccount();
                        break;
                    case "6":
                        accountSystem.getAllAccountsInformation();
                        break;
                    case "r":
                        return;
                    default:
                        organizerPresenter.printInvalidInput();
                }

            }
        }
    }

}
