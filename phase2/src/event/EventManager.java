package event;

import java.io.Serializable;
import java.sql.Array;
import java.sql.Timestamp;
import java.util.*;


/**
 * An use case class of event.
 * Stores all event in a map with event id of event map to the corresponding event. Also a sorted map with
 * time map to a list of toString of events start at that time.
 * Also stores total number of events.
 * Has methods to construct new events, get event with given information, and change the information of
 * event.
 * @author Group0065
 * @version 1.0.0
 */
public class EventManager implements Serializable {
    private Map<String, Map<String, Event>> eventList;
    private Map<String, Timestamp[]> eventSchedule;
    private int numTalk;
    private int numParty;
    private int numDiscuss;

    /**
     * constructs an new event manager
     */
    public EventManager() {
        eventList = new HashMap<>();
        eventSchedule = new TreeMap<>();
        numTalk = 0;
        numParty = 0;
        numDiscuss = 0;
    }

    // a helper function to find event by id
    private Event findEvent(String id) {
        for (Map<String, Event> events: eventList.values()) {
            if (events != null && events.get(id) != null) {
                return events.get(id);
            }
        }
        return null;
    }


    //private helper
    private int totalNumberOfEvents(){
        int sum = 0;
        for (Map<String, Event> events: eventList.values()){
            if (events != null) sum += events.size();
        }
        return sum;
    }

    private String[] getEventScheduleValue(Timestamp sTime, Timestamp eTime, String eventId) {
        String time = sTime.toString();
        time = time.substring(0, time.length() - 3);
        String time2 = eTime.toString();
        time2 = time2.substring(0, time2.length() - 3);
        return new String[]{time, time2, eventId, Objects.requireNonNull(findEvent(eventId)).getName()};
    }

    /**
     * Gets Schedule of all events
     * @return A list of arrays where in the format [time, id, event name]
     */
    protected List<String[]> getEventSchedule() {
        List<String[]> lst = new ArrayList<>();
        for (String eventId : eventSchedule.keySet()) {
            Timestamp[] value = eventSchedule.get(eventId);
            lst.add(getEventScheduleValue(value[0], value[1], eventId));
            }
        return lst;
    }

    /**
     * gets the list of the events that have no attendee
     * @return the list of the event that have no attendee
     */
    protected List<String[]> getEmptyEvents() {
        List<String[]> eventLst = new ArrayList<>();
        for (String event: eventSchedule.keySet()) {
            if (getAttendees(event).size() == 0) {
                Timestamp[] value = eventSchedule.get(event);
                String[] inform = getEventScheduleValue(value[0], value[1], event);
                inform[3] = getName(event);
                eventLst.add(inform);
            }
        }
        return eventLst;
    }

    /**
     * Gets the event that is attendable for the user
     * @param unavailableTime List of list of timestamp that user is unavailable
     * @return A list of arrays where in the format [time, id, event name]
     */
    protected List<String[]> getEventsAttendable(List<Timestamp[]> unavailableTime) {
        List<String> events = new ArrayList<>(eventSchedule.keySet());
        for (Timestamp[] times: unavailableTime) {
            if (!getEventsByTime(times[0], times[1]).isEmpty()){
                for (String removeEvent: getEventsByTime(times[0], times[1])) {
                    events.remove(removeEvent);}
            }
        }
        List<String[]> complete = new ArrayList<>();
        for (String event: events) {
            Timestamp start = Objects.requireNonNull(findEvent(event)).getStartTime();
            Timestamp end = Objects.requireNonNull(findEvent(event)).getEndTime();
            String[] information = {start.toString().substring(0, start.toString().length() - 3), end.toString().substring(0, end.toString().length() - 3), event, Objects.requireNonNull(findEvent(event)).getName()};
            complete.add(information);
        }
        return complete;
    }

    /**
     * Checks if an event exist.
     * @param eventID id of the new event
     * @return true if event exists
     */
    public boolean checkEvent(String eventID) {
        for (Map<String, Event> events: eventList.values()) {
            if (events != null && events.get(eventID) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a new event with given information, and then add the new constructed event into
     * eventList and eventSchedule.
     * Creates an event based on input: if there is only one host, it will be a talk.
     * Other events to be added in phase 2. (party if there is no speaker, panel discussion if multiple speaker)
     * Assumes that it has been checked to make sure there is only one event in a given room.
     * Assumes that it has been checked to make sure the capacity does not exceed the room capacity.
     * For now, assume that host.size() == 1
     * @param name name of the new event
     * @param startTime start time of the new event start
     * @param endTime end time of the new event start
     * @param location room name of the new event held in
     * @param description description of the new event
     * @param capacity the max number of people can participate in the new event
     * @param host the host of the new event
     * @return return event id if create successfully, otherwise return null.
     */
    public String createEvent(String type, String name, Timestamp startTime, Timestamp endTime,
                              String location, String description, int capacity,
                               List<String> host) {
        EventFactory ef = new EventFactory(numTalk, numParty, numDiscuss);
        Event newEvent = ef.createEvent(type, name, startTime, endTime,location,description,capacity, host);
        if (newEvent != null) {
            this.eventList.computeIfAbsent(type, k -> new HashMap<>());
            this.eventList.get(type).put(newEvent.getId(), newEvent);
            this.eventSchedule.put(newEvent.getId(), new Timestamp[]{startTime, endTime});
            numTalk = ef.updateEvent().get(0);
            numParty = ef.updateEvent().get(1);
            numDiscuss = ef.updateEvent().get(2);
            return newEvent.getId();
        }
        return null;
    }

    /**
     * Gets all the events starts during the given period of time.
     * @param startTime start of the time period
     * @param endTime end of the time period
     * @return a map contains all events start during the given period of time with start time map to them
     */
    protected List<String> getEventsByTime(Timestamp startTime, Timestamp endTime) {
        List<String> eventInTime = new ArrayList<>();
        for (String events : eventSchedule.keySet()) {
            Timestamp[] times = eventSchedule.get(events);
            if (times[0].after(startTime) && times[1].before(endTime)) {
                eventInTime.add(events);
            }
        }
        return startTimeSort(eventInTime);
    }

    /**
     * Gets all the events are held in a given room.
     * @param location the name of given room
     * @return a map contains all events are held in the given room by event id mapped to event name
     */
    protected List<String> getEventByLocation(String location){
        List<String> result = new ArrayList<>();
        for(Map<String, Event> events: eventList.values()){
            if(events != null){
                for (Event event : events.values()){
                    if (event != null && event.getLocation().equalsIgnoreCase(location)) result.add(
                            event.getId());
                }
            }
        }

        return startTimeSort(result);
    }

    /**
     * Adds a given user as attendee into the event with given id.
     * @param userName the username of user we want to add into event as attendee
     * @param id id of the event we want to add the user in
     * @return true of added successfully, else false
     */
    public boolean addAttendee(String userName, String id){
        Event event = findEvent(id);
        if (event == null || event.isInEvent(userName)) return false;
        else{
            return event.addAttendee(userName);
        }
    }

    /**
     * Removes a given user from attendee of the event with given id.
     * @param userName the username of user we want to remove from attendee of event
     * @param eventID id of the event we want to add the user in
     * @return true of removed successfully, else false
     */
    public boolean removeAttendee(String userName, String eventID){
        Event event = findEvent(eventID);
        return event != null && event.removeAttendee(userName);
    }

    //schedule a speaker
    /**
     * Schedules a speaker into a given event.
     * @param eventID id of the event we want to schedule the speaker to
     * @param username the user we want to schedule as the speaker of the event
     * @return true if scheduled successfully. else false
     */
    protected boolean scheduleSpeaker (String eventID, String username) {
        Event event = findEvent(eventID);
        if (event != null){
            return event.changeHost(username);
        }
        return false;
    }

    //reschedule an event

    /**
     * Reschedules an event, change its start time to the given time.
     * @param eventID id of the event we want to change time
     * @param startTime the new time we want the event start at
     * @param endTime the new time we want the event end at
     * @return true if rescheduled successfully, else false
     */
    protected boolean rescheduleEvent(String eventID, Timestamp startTime, Timestamp endTime) {
        Event event = findEvent(eventID);
        if (event != null && startTime != event.getStartTime() && endTime != event.getEndTime()) {
            Timestamp[] times = {startTime, endTime};
            eventSchedule.remove(eventID);
            eventSchedule.put(eventID, times);
            event.setStartTime(startTime);
            event.setEndTime(endTime);
            return true;
        }
        return false;
    }

    /**
     * Cancels an event.
     * @param eventID id of the event we want to cancel
     * @return true if rescheduled successfully, else false
     */
    protected boolean cancelEvent(String eventID) {
        Event event = findEvent(eventID);
        if (event == null) return false;
        eventSchedule.remove(eventID);
        for (Map<String, Event> eventMap : eventList.values()) {
            eventMap.remove(eventID);
        }
        return true;
    }

    /**
     * Provides the description of event with given id.
     * The description is toString of the event.
     * @param ID id of the event we want to get description
     * @return the toString of event with given id if it exists
     */
    public String provideDescription(String ID){
        Event event = findEvent(ID);
        return (event == null) ? null : event.toString();
    }

    /**
     * Returns the hosts of a given event.
     * @param eventID id of the event we want to work on
     * @return the list of hosts for this event, or null if this event does not exist
     */
    public List<String> getHosts(String eventID){
        Event event = findEvent(eventID);
        return (event == null) ? null : event.getHosts();
    }

    /**
     * Returns all attendees of a given event.
     * @param eventID id of the event we want to work on
     * @return the list of hosts for this event, or null if this event does not exist
     */
    public List<String> getAttendees(String eventID){
        Event event = findEvent(eventID);
        return (event == null) ? null : event.getAttendees();
    }


    /**
     * Returns the start time of a given event.
     * @param eventID id of the event we want to work on
     * @return start time of the event, or null if this event does not exist
     */
    public Timestamp getStartTime(String eventID){
        Event event = findEvent(eventID);
        return (event == null) ? null : event.getStartTime();
    }

    /**
     * Returns the end time of a given event.
     * @param eventID id of the event we want to work on
     * @return end time of the event, or null if this event does not exist
     */
    public Timestamp getEndTime(String eventID){
        Event event = findEvent(eventID);
        return (event == null) ? null : event.getEndTime();
    }

    /**
     * Returns type of a given event.
     * @param eventID id of the event we want to work on
     * @return type of the event, or null if this event does not exist
     */
    public String getEventType(String eventID){
        for (String types: eventList.keySet()) {
            if (eventList.get(types).containsKey(eventID));
            return types;
        }
        return null;
    }

    /**
     * Returns the name of a given event.
     * @param eventID id of the event we want to work on
     * @return name of the event, or null if this event does not exist
     */
    public String getName(String eventID){
        Event event = findEvent(eventID);
        return (event == null) ? null : event.getName();
    }

    /**
     * Returns room of a given event.
     * @param eventID id of the event we want to work on
     * @return room where the event located
     */
    protected String getRoom(String eventID){
        Event event = findEvent(eventID);
        return (event == null) ? null : event.getLocation();
    }

    /**
     * Returns capacity of a given event.
     * @param eventID id of the event we want to work on
     * @return an int represents the capacity of the event
     */
    protected int getCapacity(String eventID){
        Event event = findEvent(eventID);
        return (event == null) ? null : event.getCapacity();
    }

    /**
     * Change the capacity of the event
     * @param eventID a string represents the id of this event
     * @param newcapacity an int represents the new capacity
     * @return true if capacity has been successfully changed
     */
    protected boolean setCapacity(String eventID, int newcapacity){
        Event event = findEvent(eventID);
        if (event == null){
            return false;
        }
        event.setCapacity(newcapacity);
        return true;
    }

    /**
     * Checks of there are still space in the event
     * @return true if and only if the event is not full
     */
    protected boolean canSignup(String eventID){
        Event event = findEvent(eventID);
        return event != null && event.canSignup();
    }

    /**
     * gives a list of the event that are held by the specific speaker
     * @param speaker the name of the speaker
     * @return the list of event hold by the speaker b
     */
    protected List<String> getEventBySpeaker(String speaker){
        List<String> result = new ArrayList<>();
        for(Map<String, Event> eventMap: eventList.values()){
            for(Event event: eventMap.values()){
                if(!(event.getHosts() == null) && event.getHosts().contains(speaker)){
                    result.add(event.getId());
                } }
        }
        return startTimeSort(result);
    }
    /**
     * get number of attendees of an event.
     * @return true if and only if the event is not full
     */
    protected Map<Double, String[]> getAttendRate(){
        Map<Double, String[]> unSortedMap = new HashMap<>();
        for(Map<String, Event> eventMap: eventList.values()){
            for(Event event: eventMap.values()){
                int total = event.getAttendees().size();
                double attendRate = (total * 1.0) / (event.getCapacity() * 1.0);
                String[] eventInformation= {event.getId(), event.getName()};
                unSortedMap.put(attendRate, eventInformation);
                }
        }
        Map<Double, String[]> SortedMap = new TreeMap<>(Collections.reverseOrder());
        SortedMap.putAll(unSortedMap);
        return SortedMap;
    }

    //helper to sort list of events by time.
    private List<String> startTimeSort(List<String> events){
        if(events == null || events.isEmpty()){
            return events;
        }
        List<String> result = new ArrayList<>();
        while(!events.isEmpty()){
            String pivot = events.get(0);
            Timestamp time = findEvent(pivot).getStartTime();
            for(String event: events){
                if(findEvent(event).getStartTime().before(time)){
                    time = findEvent(event).getStartTime();
                    pivot = event;
                }
                else if(findEvent(event).getStartTime().equals(time)){
                    if(pivot.compareTo(event) < 0){
                        pivot = event;
                    }
                }
            }
            result.add(pivot);
            events.remove(pivot);
        }
        return result;
    }

}

