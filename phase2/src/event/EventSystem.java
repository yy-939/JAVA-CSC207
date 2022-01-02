package event;

import Input.UserInput;

import java.sql.Timestamp;
import java.util.*;

/**
 * An upper controller of event.
 * Stores EventManager and EventPresenter.
 * Gets input from user and gives corresponding moves by calling methods from EventManager.
 * @author Group0065
 * @version 1.0.0
 */
public class EventSystem {
    private EventManager events;
    private EventPresenter presenter;
    private UserInput sc = new UserInput();


    /**
     * constructs an event system
     * @param events is the event manager that the system uses
     */
    public EventSystem(EventManager events) {
        this.events = events;
        this.presenter = new EventPresenter();
    }

    /**
     * gives the event manager the event system uses
     * @return the event manager
     */
    public EventManager getEvents() {
        return events;
    }

    /**
     * gives the event manager the event system uses
     * @return the event manager
     */
    public boolean canAddHost(String eventId) {
        String type = events.getEventType(eventId);
        if (type != null){
            if (type.equals("Talk") && events.getHosts(eventId).size() == 0){
                return true;
            }else if(type.equals("PanelDiscussion")){
                return true;
            }else {presenter.hostMax();
                return false;}
        }
        return false;
    }

    /**
     * Checks if event exists by given an event id.
     * @param eventId the id of event we want to check if exists
     * @return true if exists, else false
     */
    private boolean checkEvent(String eventId) {
        return events.checkEvent(eventId);
    }

    /**
     * deletes the event, if there exists event that has no attendee and ask the user which event is wanted
     * to be deleted
     */
    public void deleteEmptyEvent(){
        List<String[]> deletableEvents = events.getEmptyEvents();
        if (deletableEvents.size() == 0){
            presenter.printNoEmpty();
            return;
        } else {
            presenter.printEventList(deletableEvents, "Events with no attendees:");
        }
        presenter.askEventIdToDelete();
        String id = sc.inputString();
        boolean contain = false;
        int i = 0;
        while (!contain && i < deletableEvents.size()){
            contain = Arrays.asList(deletableEvents.get(i)).contains(id);
        }
        if (contain) {
            cancelEvent(id);
        } else {
            presenter.eventNotEmpty();
        }
    }

    /**
     * Prints out a list of information of all events to user.
     */
    public void printEventList(){
        List<String[]> allEvents = events.getEventSchedule();
        if (allEvents.size() == 0) {
            presenter.printNoEvent();
        } else {
            presenter.printEventList(allEvents, "Event List:");
        }
    }

    //private helper
    private String inputEventType() {
        presenter.askEventType();
        String type = sc.inputString();
        if(type==null){return null;}
        if(!(type.equalsIgnoreCase("Talk") || type.equalsIgnoreCase("Party") || type.equalsIgnoreCase("PanelDiscussion"))) {
            presenter.eventTypeMessage();
            return null;
        }
        return type;
    }

    /**
     * Receives the input from user, and create an event by given information.
     * Ask for information step by step, requires user to give information following text prompt.
     * Gives feedback to user by print out strings, gives feedback when this event is successfully created,
     * or this event is not able to be created.
     * @param location username of the organizer that create event.
     * @param capacity An int representing the event capacity.
     * @param startTime A timestamp representing the start time of event
     * @param endTime A timestamp representing the end time of event
     * @param speakers A list of usernames of speakers who host the event.
     * @return An event id if the event is created successfully.
     */
    public String createEvent(String location, int capacity, Timestamp startTime, Timestamp endTime,
                              List<String> speakers) {
        if (location == null | capacity < 0  | startTime == null | endTime == null){return null;}
        String type = inputEventType();
        if (type == null) {
            return null;
        }else if((type.equals("Talk") && speakers.size() != 1) || (type.equals("Party") && speakers.size() != 0)) {
            presenter.hostNotMatch();
            return null;}
        presenter.askEventName();
        String name = sc.inputString();
        if(name == null){
            return null;
        }
        presenter.askEventDescription();
        String description = sc.inputString();
        if(description==null){return null;}
        String eventID = events.createEvent(type, name, startTime, endTime, location, description, capacity, speakers);
        presenter.eventCreateMessage(eventID != null, eventID);
        return eventID;
    }

    /**
     * This method is used to add a user with given username to an event given by user input.
     * This method gives prompt to user to enter event id.
     * If the event id is invalid, will prints out feedback to user.
     * This method prints out feedback to user when the user is added successfully or not.
     * @param eventId the event to be add
     * @param username the username of user add as an attendee into an event
     */
    public void addAttendee(String eventId, String username) {
        if (eventId == null){return;}
        if(events.checkEvent(eventId)) {presenter.addAttendeeMessage(events.addAttendee(username, eventId));}else{
            presenter.eventNotExist();
        }
    }

    /**
     * Shows the description of an event which the event id is given by user input.
     * Gives prompt to user to enter event id.
     * Prints out the toString description of given id, or gives feedback when this event doesn't exist.
     */
    public void checkDescription() {
        presenter.askEventId();
        String eventId = sc.inputString();
        if(eventId == null){
            return;
        }
        if(this.checkEvent(eventId)) {
            presenter.descriptionMessage(events.provideDescription(eventId));
        } else {
            presenter.eventNotExist();
        }
    }

    /**
     * Gets all events in given location provided by user input.
     * Gives prompt to user to enter location, which is room name.
     * Prints out toString description of all events in the given room, or feedback if this room doesn't
     * exists.
     */
    public void getEventByLocation(String location) {
        List<String> eventList = events.getEventByLocation(location);
        if(eventList == null || eventList.isEmpty()){
            presenter.noEventAtLocation(location);
        }
        else {
            presenter.locationMessage(location);
            for (String event : eventList) {
                presenter.getEventByLocationMessage(event, events.getName(event),
                        events.getStartTime(event).toString(), events.getEndTime(event).toString(), location,
                        events.getHosts(event));
            }
        }
    }

    /**
     * Gets all event with time given time period provided by user input.
     * Gives prompt to user to enter start time and end time, which has to be in a correct format.
     * If the input time is not in a correct format, then give feedback to user to let user correct the input format.
     * Ask for the start of the time period first, if get the correct format, goes to end of the time period.
     * Gets all event starts during the time period.
     * When get the correct format, return the description of all corresponding events to user.
     */
    public void getEventByTime () {

        presenter.askStartTime();
        Timestamp startTime = sc.inputTime();
        if(startTime == null){
            return;
        }
        presenter.askEndTime();
        Timestamp endTime = sc.inputTime();
        if(endTime == null){
            return;
        }
        if (endTime.before(startTime)) {
            presenter.endTimeBeforeStartTime();
            return;
        }
        List<String[]> information = new ArrayList<>();
        List<String> available = events.getEventsByTime(startTime, endTime);
        for (String event: available) {
            String[] strList = {events.getStartTime(event).toString().substring(0, events.getStartTime(event).toString().length() - 3), event, events.getName(event)};
            information.add(strList);
        }
        presenter.getEventByTimeMessage(information);
    }


    /**
     * Prints out all attendable events in the format [time, id, event name] by given the times.
     * @param unavailableTimeLists the list of times unavailable for attendee.
     */
    public void viewEventsAttendable(List<Timestamp[]> unavailableTimeLists) {
        if (unavailableTimeLists == null){return;}
        List<String[]> allEvents = events.getEventsAttendable(unavailableTimeLists);
        if (allEvents.size() == 0) {
            presenter.printNotAvailable();
        } else {
            presenter.printEventList(allEvents, "Here are all events you are able to attend:");
        }
    }

    /**
     * Gets the event start time by given an existed event ID from the user input.
     * @param eventID A string representing the ID of an existed event.
     * @return A string representing event ID iff the event exists.
     */
    public Timestamp getEventStartTimeByID(String eventID){
        if (eventID == null){ return null;}
        return events.getStartTime(eventID);
    }

    /**
     * Gets the event end time by given an existed event ID from the user input.
     * @param eventID A string representing the ID of an existed event.
     * @return A string representing event ID iff the event exists.
     */
    public Timestamp getEventEndTimeByID(String eventID){
        if (eventID == null){ return null;}
        return events.getEndTime(eventID);
    }

    /**
     * Schedules a list of speakers for an event by given event ID and names of speakers.
     * If the event id or speaker username is invalid, there will be feedback for user prints out.
     * If both valid, then prints out feedback to user that this speaker is scheduled successfully.
     */
    public void scheduleSpeaker(String eventID, List<String> speakers) {
        assert speakers != null;
        if (eventID == null | speakers.isEmpty()){return;}
        List<Boolean> results = new ArrayList<>();
        for (String speaker : speakers){
            results.add(events.scheduleSpeaker(eventID, speaker));
        } if (results.contains(false)){presenter.scheduleSpeakerMessage(false);
        } presenter.scheduleSpeakerMessage(true);
    }

    /**
     * Rescheduling event, which is change the time of the event.
     * Event id and new start time are given by user input with prompt given to user to enter event id and new start time.
     * Asks for event id first, if id is invalid, give user feedback that id is invalid.
     * Then ask for new start time, give corresponding feedback if time is invalid or not in correct format.
     * If all valid, then reschedule this event with given time, return feedback to user that the event is rescheduled successfully.
     * @param eventID A string representing the ID of the event meant to reschedule
     */
    public void rescheduleEvent(String eventID) {
        Timestamp originalStartTime = events.getStartTime(eventID);
        Timestamp originalEndTime = events.getEndTime(eventID);
        presenter.askStartTime();
        Timestamp newStartTime = sc.inputTime();
        presenter.askEndTime();
        Timestamp newEndTime = sc.inputTime();
        int loopCount = 0;
        if (originalStartTime.equals(newStartTime)&& originalEndTime.equals(newEndTime)){
            presenter.repeatedTime();
            String s = sc.inputString();
            if (!(s.equalsIgnoreCase("s"))) {
                rescheduleEvent(eventID);
                loopCount++;
            }
            else{
                presenter.remainsAndExit();
            }
        }
        if(loopCount == 1){
            presenter.rescheduleMessage(events.rescheduleEvent(eventID, newStartTime,newEndTime));
        }
    }

    /**
     * Cancelling an event only by Organizer. The event id is given by user input.
     * If the event id is invalid, print out feedback to user.
     * If the event id is valid, then cancel this event and print out the feedback that the event is removed in event
     * system successfully.
     * @param eventId A string representing the ID of a valid and existed event.
     * @return A string represents the ID of the cancelled event if it's already cancelled.
     */
    public String cancelEvent(String eventId) {
        events.cancelEvent(eventId);
        presenter.cancelEvent();
        return eventId;
    }

    /**
     * Gets a list of speakers by given the event id.
     * @param eventId the id of event we want to check for speaker
     * @return list of username of speakers host the given event
     */
    public List<String> getHosts(String eventId) {
        if (eventId == null){return null;}
        return events.getHosts(eventId);
    }

    /**
     * Gets a list of usernames of all attendees by one given event id.
     * @param eventID A string representing the ID of event.
     * @return a list of username of attendees of the given event.
     */
    public List<String> getAttendees(String eventID) {
        if (eventID == null){return null;}
        return events.getAttendees(eventID);
    }


    /**
     * Print a list of usernames of speakers who are hosting the signed events.
     @param allEventsID A list of strings representing the IDs of signed events.
     */
    public List<String> getSpeakersForSignedEvents(List<String> allEventsID){
        if (allEventsID == null){return null;}
        if(allEventsID.isEmpty()){
            presenter.noEventAttended();
        }
        List<String> allSpeakers = new ArrayList<>();
        for (String event : allEventsID){
            allSpeakers.addAll(events.getHosts(event));
        }
        presenter.getSpeakersForSignedEvents(allSpeakers);
        return allSpeakers;
    }

    /**
     * Print a message if the attendee has been removed from an event successfully.
     @param attendeeName A string representing the username of attendee for remove from the enrollment of that event.
     @return A string representing the event dropped.
     */
    public String removeAttendee(String attendeeName){
        if (attendeeName == null){return null;}
        presenter.askEventId();
        String eventId = sc.inputString();
        if(eventId == null){
            return null;
        }
        if (!this.checkEvent(eventId)){
            presenter.eventNotExist();
            return null;}
        if(!events.getAttendees(eventId).contains(attendeeName)) {
            presenter.printErrorMessage(attendeeName + "is not enrolled in the event.");
        } else {
            events.removeAttendee(eventId, attendeeName);
        } return attendeeName;
    }

    /**
     * Checks if there exists an event with id given from user input.
     * @return given id if exists, null elsewise
     */
    public String checkExistence(){
        presenter.askEventId();
        String eventId = sc.inputString();
        if(eventId == null){
            return null;
        }
        if(this.checkEvent(eventId)) {
            presenter.descriptionMessage(events.provideDescription(eventId));
            return eventId;
        } else {
            presenter.eventNotExist();
            return null;
        }
    }

    /**
     * Checks if the host of event with given id is held by given speaker
     * @param speaker username of speaker
     * @param eventID id of event we want to check
     * @return true if the given event is held by given user, false otherwise
     */
    public boolean checkHostEvent(String speaker, String eventID){
        if (speaker == null | eventID == null){return false;}
        if(!getHosts(eventID).contains(speaker)) {
            presenter.printErrorMessage("This event is not held by you, " +
                    "you cannot get the attendee list for this event.");
            return false;
        } return true;
    }

    /**
     * Prints out toString of all events on the date given from user input.
     */
    public void getEventByDate(){
        presenter.askForDateInput();
        List<Timestamp> timeList = sc.inputDate();
        if(timeList == null){
            return;
        }
        Timestamp time1 = timeList.get(0);
        Timestamp time2 = timeList.get(1);
        List<String> eventList = events.getEventsByTime(time1, time2);
        if(eventList == null || eventList.isEmpty()) {
            presenter.noEventDate(time1.toString().substring(0,10));
        }
        else{
            presenter.printEventOnDate(time1.toString().substring(0,10));
            for(String event: eventList){
                presenter.dateMessage(event, events.getName(event), events.getStartTime(event).toString(),
                        events.getEndTime(event).toString(), events.getRoom(event), events.getHosts(event));
                }
            }
        }


    /**
     * Prints toString of all events held by given speaker.
     * @param speaker username of speaker we want to check
     */
    public void getEventBySpeaker(String speaker){
        List<String> eventList = events.getEventBySpeaker(speaker);
        if(eventList == null || eventList.isEmpty()){
            presenter.speakHostNoEvent(speaker);
        }
        else{
            presenter.printEventHosts(speaker);
            for(String event: eventList){
                presenter.speakerMessage(event, events.getName(event), events.getStartTime(event).toString(),
                        events.getEndTime(event).toString(), events.getRoom(event), events.getHosts(event));
            }
        }
    }

    /**
     * Prints toString all events assigned by that user.
     * @param eventList list of toString of all assigned event of user
     */
    //input is from accountSystem.getSignedEvents()
    public void getAssignedEvent(List<String> eventList){
        if(eventList == null || eventList.isEmpty()){
            presenter.noEventAttended();
        }else{
            presenter.printAttendedEvents();
            for(String event: eventList){
                presenter.userAttended(event, events.getName(event), events.getStartTime(event).toString(),
                        events.getEndTime(event).toString(), events.getRoom(event),events.getHosts(event));
            }
        }
    }

    /**
     * Gets the room which holds event with given id.
     * @param eventId id of event want to check
     * @return room id holds given event
     */
    public String findRoomByEvent(String eventId){
        String room = events.getRoom(eventId);
        if (eventId  == null && room == null){
            presenter.failToFindRoom();
            return null;
        }else{
        return events.getRoom(eventId);}
    }

    /**
     * Gets the capacity of event of given id
     * @param eventID id of event want to check
     * @return capacity of given event
     */
    public int getEventCapacity(String eventID){
        return events.getCapacity(eventID);
    }

    /**
     * Changes the event capacity of given event to given capacity.
     * @param eventId id of event want to operate
     * @param capacity new capacity
     */
    public void changeEventCapacity(String eventId, int capacity){
        if(capacity > 0){
            this.events.setCapacity(eventId, capacity);
            presenter.changeCapacity();
        }else{
            presenter.failToChangeCapacity();
        }
    }

    /**
     * prints the information of top 5 popular events in the system
     */
    public void getAllEventsSummary(){
        List<String[]> allEvents = events.getEventSchedule(); // time, id, event name
        if (allEvents.size() == 0) {
            presenter.printErrorMessage("The event statistics information is not available due to no event here");
            return;
        }
        int totalNumber = allEvents.size();
        Map<String, Integer> enrollSum = new HashMap<>();
        for (String[] each : allEvents){
            String eventID = each[2];
            List<String> attendees = events.getAttendees(eventID);
            String event = eventID;
            event = event + " " + each[3];
            enrollSum.put(event, attendees.size());
        } presenter.printAllEventsSummary(totalNumber, enrollSum);
    }

    /**
     * prints most popular five events of the system
     */
    public void getEventsTopF(){
        Map<Double, String[]> allRate = events.getAttendRate();
        Map<Double, String[]> topFive = new HashMap<>();
        List<Double> rates = new ArrayList<>(allRate.keySet());
        List<String[]> events = new ArrayList<>(allRate.values());
        int i = 0;
        if (allRate.size() == 0) {presenter.printNoEvent();
            return;}
        while (i < allRate.keySet().size() && i <= 5){
            topFive.put(rates.get(i), events.get(i));
            i += 1;
        }
        presenter.printTopEvents(topFive);
    }
}
