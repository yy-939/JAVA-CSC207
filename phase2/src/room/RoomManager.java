package room;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An use case class of room.
 * Stores all room in a map which has room name map to the corresponding room.
 * Contains constructor of room, which is able to construct a new room.
 * Methods in this class contains check room status, operating on specific room, and get room with given information.
 * All the parameter should be given by controller of room.
 * @author Group0065
 * @version 1.0.0
 */
public class RoomManager implements Serializable {
    private Map<String, Room> roomList;

    public RoomManager(){
        roomList = new HashMap<>();
    }

    /**
     * Gives the toString description of room with given name.
     * @param roomName the name of room ask for toString description
     * @return the toString description of room
     */
    protected String printRoom(String roomName) {
        return (roomList.get(roomName) == null) ? null : roomList.toString();
    }

    /**
     * Gets toString description of all rooms in this roomList.
     * @return toString description of all rooms in this roomList in one String
     */
    protected String printAllRoom() {
        StringBuilder sb = new StringBuilder();
        sb.append("Room List: \n");
        for (String roomName: roomList.keySet()) {
            sb.append(roomList.get(roomName).toString());
        }
        return sb.toString();
    }

    /**
     * Makes a new room with given information, then add this new room into this roomList.
     * Room can be construct if there is not other room with input name.
     * @param capacity capacity of the new room
     * @param availableTime available time of the new room
     * @param roomName name of the new room
     * @return true if new room is constructed successfully, else false
     */
    protected boolean addRoom(int capacity, Integer[][] availableTime, String roomName) {
        if (roomList.get(roomName) == null) {
            Room room = new Room(capacity, availableTime, roomName);
            roomList.put(roomName, room);
            return true;
        }
        return false;
    }

    /**
     * Checks if the room is exist in this roomList.
     * For controller to use this method check and then generates different information to the presenter.
     * Anytime when asking user to enter room name, this method should be called.
     * All other methods that needs roomName should assume valid roomName.
     * @param roomName name of room want to check
     * @return true if there exists a room with given room name, else false
     */
    public boolean hasRoom(String roomName) {
        return (roomList.get(roomName) != null);
    }

    /**
     * Checks if the room with given name is available at given time.
     * Assume room name given is valid, so the validity check will be finished before this method is called.
     * @param startTime given start time
     * @param endTime given end time
     * @param roomName name of the room
     * @return true if room with given name is available at given time, else false
     */
    public boolean checkRoomAvailability(Timestamp startTime, Timestamp endTime, String roomName){
        // Assume roomName is in the list
        return (roomList.get(roomName).isAvailable(startTime, endTime));
    }

    /**
     * Checks if the given time is in the room valid time slots with given room name
     * @param startTime given time
     * @param endTime given time
     * @param roomName name of the room
     * @return true if given time is in valid time slots at this room else false
     */
    public boolean checkRoomTimeSlots(Timestamp startTime, Timestamp endTime, String roomName) {
        return (roomList.get(roomName).isValidTimeSlots(startTime, endTime));
    }

    /**
     * Adds event with given name at given time to a room with given name.
     * Event can be added if the room with given name doesn't hold any other event at given time.
     * @param roomName name of room want to add the event to
     * @param eventId Id of event want to add to room
     * @param startTime time of event starts
     * @param endTime time of event ends
     * @return true if added successfully, else false
     */
    public boolean addEventToRoom(String roomName, String eventId, Timestamp startTime, Timestamp endTime) {
        // Return boolean not String so controller knows what needs to send to presenter
        return roomList.get(roomName).addEventToSchedule(startTime, endTime, eventId);
    }

    /**
     * Removes an event with given name at given time from a room with given name.
     * Event can only be removed if there is an event with given name at given time in room with given name
     * @param roomName name of room
     * @param eventId Id of event
     * @return true if removed successfully, else false
     */
    public boolean removeEventFromRoom(String roomName, String eventId) {
        return roomList.get(roomName).removeEventFromSchedule(eventId);
    }

    /**
     * Returns the capacity of a room, help to check if event capacity is available.
     * @param roomName name of room
     * @return capacity of the room.
     */
    public int getRoomCapacity(String roomName) {
        return roomList.get(roomName).getCapacity();
    }

    /**
     * Generates a String representation of the available time with given roomName
     * @param roomName name of the room
     * @return a String representation of the available time at this room.
     */
    public String getRoomTimeSlots(String roomName) {
        return roomList.get(roomName).printAvailableTime();
    }

    /**
     * Generates a String representation of the room info
     * @return room info for all rooms
     */
    protected List<String> getRoomsInfo() {
        List<String> roomInfo = new ArrayList<>();
        for(Room r : roomList.values()) {
            roomInfo.add(r.toString());
        }
        return roomInfo;
    }

    /**
     * check if event exist in some room
     * @return room info for all rooms
     */
    protected String getEventLocation(String EventID) {
        for(Room r : roomList.values()) {
            if(r.getEvents().contains(EventID)){
                return r.getRoomName();
            }
        }
        return null;
    }
}
