package room;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * An entity class of Room.
 * Stores capacity, available time, unique room name and schedule of a room.
 * Schedule is a map with start time map to event name.
 * @author Group0065
 * @version 1.0.0
 */
class Room implements Serializable {
    private final Integer capacity;
    private NavigableMap<Integer, Integer> availableTime;
    private final String roomName; //Check for uniqueness
    // No getters for this room name as it is stored directly in the use case as keys in the Map
    private Map<String, Timestamp[]> schedule;

    /**
     * constructs a room
     * @param capacity the capacity of the room
     * @param availableTimeSlots the available time to hold events
     * @param roomName the name of the roo,
     */
    protected Room(int capacity, Integer[][] availableTimeSlots, String roomName) {
        // Assume availableTimeSlops do not overlap
        // A list of [[start time 1, end time 1], [start time 2, end time 2]]
        this.capacity = capacity;
        this.availableTime = new TreeMap<>();
        for (Integer[] lst: availableTimeSlots) {
            availableTime.put(lst[0], lst[1]);
        }
        this.roomName = roomName;
        schedule = new HashMap<>();
    }

    /**
     * Gets of capacity of room.
     * @return capacity of room
     */
    protected int getCapacity() {
        return capacity;
    }

    /**
     * Gets of name of room.
     * @return name of room
     */
    protected String getRoomName() {
        return roomName;
    }

    /**
     * Gets of events of room.
     * @return events of room
     */
    protected List<String> getEvents() {
        return new ArrayList<>(schedule.keySet());
    }

    /**
     * Checks if room is available at given time.
     * If this room has no event hold at the given time, then it is available.
     * @param startTime start time we want to check if room is available
     * @param endTime end time we want to check if room is available
     * @return true if available, else false
     */
    protected boolean isAvailable(Timestamp startTime, Timestamp endTime) {
        for (String events: schedule.keySet()){
            Timestamp[] times = schedule.get(events);
            if (!(times[0].after(endTime) || times[1].before(startTime))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the time slot of this room is valid with given time
     * @param startTime start time we want to check if time slot is valid
     * @param endTime end time we want to check if time slot is valid
     * @return true if valid, else false
     */
    protected boolean isValidTimeSlots(Timestamp startTime, Timestamp endTime) {
        int hour1 = startTime.getHours();
        int hour2 = endTime.getHours();
        if (availableTime.get(hour1) != null && availableTime.get(hour2) != null) {
            return true;
        } else {
            Map.Entry<Integer, Integer> timeslot = availableTime.lowerEntry(hour1);
            if (timeslot != null) {
                return timeslot.getKey() <= hour1 && hour2 <= timeslot.getValue();
            }
        }
        return false;
    }

    /**
     * Adds an event to schedule of room.
     * Able to add that event if there is no event hold in room at that time.
     * @param startTime time of event want to add
     * @param endTime time of event want to add
     * @param eventName name of the event we want to add
     * @return true if event is added successfully, else false
     */
    protected boolean addEventToSchedule(Timestamp startTime, Timestamp endTime, String eventName) {
        Timestamp[] times = {startTime, endTime};
        return schedule.putIfAbsent(eventName, times) == null;
    }

    /**
     * Removes an event from schedule of room.
     * Able to remove the event if there is an event with given name at given time in the schedule.
     * @param eventId name of event want to remove
     * @return true if event is removed successfully, else false
     */
    protected boolean removeEventFromSchedule(String eventId) {
        return schedule.entrySet()
                .removeIf(
                        entry -> (eventId.equals(entry.getKey())));
    }

    // This is a helper method for toString
    private String printSchedule(){
        StringBuilder sb = new StringBuilder();
        for(String eventid: schedule.keySet()){
            sb.append("\n\t");
            sb.append("Event ");
            sb.append(eventid);
            sb.append(" is hold in this room at ");
            sb.append(schedule.get(eventid)[0]);
            sb.append(" to ");
            sb.append(schedule.get(eventid)[1]);
            sb.append(".");
        }
        return sb.toString();
    }

    /**
     * Generates a String representation of the room available time slots in ascending order.
     * @return a String representation of the room available time slots.
     */
    protected String printAvailableTime() {
        List<String> lst = new ArrayList<>();
        for(Integer startTime: availableTime.keySet()) {
            lst.add(startTime.toString() + "-" + availableTime.get(startTime).toString());
        }
        return String.join(", ", lst);
    }

    /**
     * Represents the string contains all information of room.
     * @return toString description of room
     */
    @Override
    public String toString(){
        return "The name of this room is: " + roomName + "\n" +
                "The capacity of this room is: " + capacity + "\n" +
                "The room is available during: " + printAvailableTime() + "\n" +
                "The schedule of this room is: " + this.printSchedule();
    }

}
