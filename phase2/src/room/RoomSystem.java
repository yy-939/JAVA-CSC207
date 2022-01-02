package room;


import Input.InputStrategy;
import Input.RoomUserInput;
import Input.UserInput;

import java.sql.Timestamp;
import java.util.Scanner;

/**
 * A controller class of room.
 * Stores RoomManager and RoomPresenter.
 * Calls methods of RoomManager and RoomPresenter follows user input.
 * @author Group0065
 * @version 1.0.7
 */
public class RoomSystem {
    private RoomManager rooms;
    private RoomPresenter presenter;
    private InputStrategy sc = new RoomUserInput();

    public RoomSystem(RoomManager rooms) {
        this.rooms = rooms;
        this.presenter = new RoomPresenter();
    }

    private String scanner(){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /**
     * Gets the RoomManager stored in roomSystem
     * @return roomManager stored inside
     */
    public RoomManager getRooms() {
        return rooms;
    }

    //private helper
    private Integer inputCapacity(){
        InputStrategy scan = new UserInput();
        int capacity = scan.inputInteger();
        return capacity;
    }

    /**
     * Constructs a new room with information provided by user input and add this room into the room list.
     */
    //add a new room
    public void addRoom () {
        presenter.askNewRoomName();
        String roomName = sc.inputString();
        if(roomName==null){return;}
        if (rooms.hasRoom(roomName)) {
            presenter.roomExistMessage();
        }else {
            presenter.askNewRoomCapacity();
            Integer numCapacity = inputCapacity();
            if(numCapacity == null){
                return;
            }
            presenter.askNewRoomStartSlot();
            Integer startSlot = sc.inputInteger();
            if(startSlot == null){
                return;
            }
            presenter.askNewRoomEndSlot();
            Integer endSlot = sc.inputInteger();
            if(endSlot == null){
                return;
            }
            if (startSlot >= endSlot) {
                presenter.wrongTimeSlot();
            }
            Integer[][] availableTime = {{startSlot, endSlot}};
            presenter.addANewRoom(rooms.addRoom(numCapacity, availableTime, roomName));
        }
    }


    /**
     * Displays toString version of room info
     */
    public void getRoomsInfo() {
        presenter.printRoomInfo(rooms.getRoomsInfo());
    }

    //private helper
    private boolean isWrongFormat(String myTime) {
        try {
            Timestamp.valueOf(myTime);
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    /**
     * Ask user to input capacity of events.
     * @return integer capacity if it is available, -1 if there is an error.
     */
    public Integer inputEventCapacity(String location) {
        presenter.askEventCapacity();

        Integer capacity = inputCapacity();
        if(capacity == null){
            return null;
        }
        if (capacity > rooms.getRoomCapacity(location)) {
            presenter.capacityInvalid(rooms.getRoomCapacity(location));
            return null;
        }else {
            return capacity;
        }

    }

    /**
     * Adds a specific event to a specific room
     * @param roomName the room name is the name of the room that the user want to add the event to
     * @param eventID the event id is the id of the event that need to be added to the room
     * @param startTime is the time that the user wants to the event to be held
     * @param endTime is the time that the user wants to the event to end
     * @return if the event added to the room successfully.
     */
    //add an event to room
    public boolean addEventToRoom(String roomName, String eventID, Timestamp startTime, Timestamp endTime) {
        if (eventID == null) {
            return false;
        }
        return rooms.addEventToRoom(roomName, eventID, startTime, endTime);
    }

    /**
     * Removes the event from room
     * @param eventID the event's id that we want to remove
     * @return true if the event removed successfully
     */
    //remove an event from room
    public boolean removeEventFromRoom(String eventID) {
        String roomName = rooms.getEventLocation(eventID);
        return rooms.removeEventFromRoom(roomName, eventID);
    }

    /**
     * Ask user to input a room name.
     * @return name of the room if it exists, else return null.
     */
    public String askForRoomName(){
        presenter.askRoomInput();
        String roomName = sc.inputString();
        if(roomName==null){
            return null;
        }
        if(!rooms.hasRoom(roomName)){
            presenter.roomNotExistReEnter();
            return null;
        }

        return roomName;
    }

    /**
     * Ask user to input a start time when creating events.
     * @return name of the room if it exists, else return null.
     */
    public Timestamp askForStartTime(){
        presenter.askEventStartTime();
        return sc.inputTime();
    }

    /**
     * Ask user to input a end time when creating events.
     * @return name of the room if it exists, else return null.
     */
    public Timestamp askForEndTime(){
        presenter.askEventEndTime();
        return sc.inputTime();
    }

    /**
     * check if the event start and end time is available for the room.
     * @return ture if time period is available.
     */
    public boolean CheckroomAvailability(String roomName, Timestamp start, Timestamp end){
        if(roomName == null || start == null || end == null){
            return false;
        }
        if (start.after(end)){presenter.wrongTimeSlot();
        return false;
        }
        else if (!rooms.checkRoomTimeSlots(start, end, roomName)){
            presenter.timeNotAvailable(roomName, start.toString(), end.toString());
            return false;
        }
        else if (!rooms.checkRoomAvailability(start, end, roomName)){
            presenter.timeNotAvailable(roomName, start.toString(), end.toString());
            return false;
        }
        else{
            return true;
        }


    }

    /**
     * Checks if the room name from user input is stored in roomManager, if exists,
     * returns the room name, else return null
     * @return room name given from user input
     */
    public String checkRoomExistence(){
        presenter.askRoomInput();
        String roomName = sc.inputString();
        if(roomName == null){
            return null;
        }
        if(rooms.hasRoom(roomName)){
            return roomName;
        }else{
            presenter.roomNotExist();
            return null;
        }

    }

    private int getRoomCapacity(String room){
        return this.rooms.getRoomCapacity(room);
    }

    /**
     * Changes the capacity of room with given room number to the new capacity given from user input.
     * Won't do anything if the new capacity is the same as the old capacity given as an parameter
     * @param room the room want to change its capacity
     * @param currentCapacity current capacity of the room
     * @return the new capacity of room
     */
    public int changeCapacity(String room, int currentCapacity) {
        Integer newInput = this.inputEventCapacity(room);
        if(newInput == null){
            return -1;
        }
        if (newInput == currentCapacity || newInput < 0) {
            presenter.failToChangeCapacity();
            return -1;
        } else {return newInput;}
        }
    }


