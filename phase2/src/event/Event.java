package event;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.List;

/**
 * An abstract entity class of events, stores name, ID, start time, end time, location, description, capacity,
 * and a list of attendees of the event.
 * Abstract because there are different kind of events.
 * Methods in this class are some getter and setter for attributes in this class.
 * @author Group0065
 * @version 1.0.0
 */
@SuppressWarnings("FieldMayBeFinal")
public abstract class Event implements Serializable {
    private String name;
    private final String ID;
    private Timestamp startTime;
    private Timestamp endTime;
    private String location;
    private String description;
    private int capacity;
    private List<String> attendee;

    /**
     * constructs a new event
     * @param name is the name of the event
     * @param startTime is the start time of event
     * @param endTime is the end time of the event
     * @param location is the location of the event
     * @param description is the description of the event
     * @param capacity is the capacity of the event
     * @param id is the unique id of the event
     */
    protected Event(String name, Timestamp startTime, Timestamp endTime, String location,
                    String description, int capacity, String id){
        this.name = name;
        this.ID = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.description = description;
        this.capacity = capacity;
        this.attendee = new ArrayList<>();
    }

    /**
     * Gets name of this event.
     * @return name of event
     */
    public String getName() {
        return name;
    }

    /**
     * Gets id of this event.
     * @return id of event
     */
    protected String getId() {
        return ID;
    }

    /**
     * Gets start time of this event.
     * @return start time of event
     */
    public Timestamp getStartTime() {
        return startTime;
    }

    /**
     * Gets end time of this event.
     * @return end time of event
     */
    public Timestamp getEndTime(){return endTime;}

    /**
     * Gets location of this event, which represents as the name of room holds this event
     * @return name of room which holds the event
     */
    protected String getLocation() {
        return location;
    }

    /**
     * Gets capacity of this event
     * @return an int represents the capacity of this event
     */
    protected int getCapacity() {return capacity;}

    /**
     * Set capacity of this event
     * @param capacity an int represnets the capacity of this event
     */
    protected void setCapacity(int capacity){
        this.capacity = capacity;}

    /**
     * Checks if the user of given username is one of the attendees of this event.
     * @param username the username of user we want to check
     * @return true if given username in attendee list, else false
     */
    protected boolean isInEvent(String username) {
        return attendee.contains(username);
    }

    /**
     * Sets start time of this event. Gives a new start time to event.
     * @param newStart new start time we want to set for event
     */
    protected void setStartTime(Timestamp newStart) {
        startTime = newStart;
    }

    /**
     * Sets end time of this event. Gives a new end time to event.
     * @param newEnd new end time we want to set for event
     */
    protected void setEndTime(Timestamp newEnd) {
        endTime = newEnd;
    }

    /**
     * Adds a new attendee to the attendee list of this event.
     * @param attendeeName the username of user we want to add into attendee list
     * @return true if added successfully, else false
     */
    protected boolean addAttendee(String attendeeName) {
        if (attendee.size() >= 2) return false;
        return attendee.add(attendeeName);
    }

    /**
     * Removes an attendee from the attendee list of this event.
     * @param attendeeName the username of user we want to remove from attendee list
     * @return true if removed successfully, else false
     */
    protected boolean removeAttendee(String attendeeName) {
        return attendee.remove(attendeeName);
    }

    /**
     * Gives the toString information of this event in a string.
     * The information contains name, start time, end time, location, capacity and description.
     * @return the String of information of the event, contains name, start time, location, capacity and description
     */
    public String toString(){
        return "Name of event: " + name + "\n" +
                "Start time of event: " + startTime.toString() + "\n" +
                "End time of event:" + endTime.toString() +"\n" +
                "Location of event: " + location + "\n" +
                "Capacity of event: " + capacity + "\n" +
                "Description of event :" + description + "\n";
    }

    /**
     * Sets the Speaker of this event.
     * This is an abstract method which will be working on changing the speaker of event.
     * @param speaker new speaker of event
     * @return true if changed speaker successfully, else false
     */
    protected abstract boolean changeHost(String speaker);

    /**
     * Gets the speaker of this event.
     * This is an abstract method which will show the hosts of the event.
     * @return the list of hosts for this event
     */
    protected abstract List<String> getHosts();

    /**
     * Gets the attendees of this event in a list.
     * This is an abstract method which will show attendees of the event.
     * @return the list of attendees for this event
     */
    protected List<String> getAttendees(){return attendee;}

    /**
     * Checks of there are still space in the event
     * @return true if and only if the event is not full
     */
    protected boolean canSignup(){ return capacity > attendee.size(); }

}
