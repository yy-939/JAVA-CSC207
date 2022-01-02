package room;

import conferencemain.MainPresenter;

import java.util.List;

/**
 * The presenter of room.
 * Responses for presenting messages related to room to the users.
 * @author Group0065
 * @version 1.0.0
 */
public class RoomPresenter extends MainPresenter {


    /**
     * Displays the description of the room info
     * @param roomInfo String representation of the room info of all existing room
     */
    void printRoomInfo(List<String> roomInfo) {
        if (roomInfo.size() == 0) {
            super.printErrorMessage("There is no room.");
            return;
        }
        for(String s : roomInfo) {
            System.out.println(s);
            super.printSeparateLine();
        }
    }


    /**
     * Informs the user if the event is removed from the room or not.
     *
     * @param remove is the boolean that tells the presenter the room removing result
     */
    public void removeEventFromRoomResult(boolean remove) {
        if (remove) {
            super.printActionMessage("Event remove successfully");
        } else {
            super.printErrorMessage("remove event failed, please check if the event is not in the room or already started");
        }
    }


    /**
     * Informs the user if the room added to the room list successfully, if not, prints the possible reasons.
     *
     * @param addRoom is the result that get from the room manager, telling the presenter if it is added successfully.
     */

    public void addANewRoom(boolean addRoom) {
        if (addRoom) {
            super.printActionMessage("The new room has been added.");
        } else {
            super.printErrorMessage("Unable to add the room, please check if the room already in the room list" +
                    "or if the room name is correct");
        }
    }

    /**
     * Informs the user that the room is already exist, no need to add it in the room list.
     */
    public void roomExistMessage() { super.printErrorMessage("The room is already exist.");}


    /**
     * Asks user to enter room name.
     */
    void askRoomInput(){System.out.println("Please enter the name of room: ");
    super.getInput();}


    /**
     * Asks the user to enter the room name, such that the system knows which room the user wants to
     * add to the room list.
     */
    void askNewRoomName(){
        System.out.println("Please enter the name of room you want to add, which will be the locations of events.");
        super.getInput();
    }

    /**
     * Asks the user to enter the number of capacity of the room, such that the system can knows
     * the capacity of the new room
     */
    void askNewRoomCapacity(){
        System.out.println("Please enter a number as the capacity of room you want to add");
        super.getInput();
    }


    /**
     * Displays error message that the room does not exist
     */
    void roomNotExist() {printErrorMessage("Room does not exist.");}

    /**
     * Prints out "Enter start time, format 'yyyy-mm-dd hh:mm':".
     */
    void askEventStartTime() {
        System.out.println("Enter start time, format 'yyyy-mm-dd hh:mm':");
        super.getInput();
    }

    /**
     * Asks user to enter the time event ends at
     */
    void askEventEndTime(){
        System.out.println("Enter end time, format 'yyyy-mm-dd hh:mm':");
        super.getInput();
    }

    /**
     * Prints out "Enter event capacity:".
     */
    void askEventCapacity() {
        System.out.println("Enter event capacity:");
        super.getInput();
    }


    /**
     * Prints out result of entering event capacity.
     * @param roomCapacity the capacity of room user want to add event to.
     */
    void capacityInvalid(Integer roomCapacity) {super.printErrorMessage("The capacity is too large for the room, it has capacity of:" + roomCapacity.toString());}


    /**
     * Prints out the error message that the given time is invalid time slots
     * @param time1 valid time slots
     * @param time2 valid time slots
     */
    void timeNotAvailable(String location, String time1, String time2) {
        super.printErrorMessage("Room :" + location + " is not available during " + time1 + " to " + time2);
        super.getInput();
    }

    /**
     * Displays error message to user that there is no room with given name, and asks user to re-enter
     */
    void roomNotExistReEnter(){
        super.printErrorMessage("The input room doesn't exist, please re-enter.");
        super.getInput();
    }

    /**
     * Asks user to enter a number as the start time when the room is available.
     */
    void askNewRoomStartSlot() {
        System.out.println("Please enter a number as the start time when the room is available, choose a number from 0 to 23");
        super.getInput();
    }

    /**
     * Asks user to enter a number as the end time when the room is available.
     */
    void askNewRoomEndSlot() {
        System.out.println("Please enter a number as the end time when the room is available. choose a number from 0 to 23");
        super.getInput();
    }

    /**
     * Displays an error message to user that the start time is later than the end time
     */
    void wrongTimeSlot() {
        super.printErrorMessage("start time should not be later than end time");
        super.getInput();
    }

    /**
     * Displays error message to user that fails to change the capacity of given room
     */
    void failToChangeCapacity(){
        super.printErrorMessage("Fail to change capacity due to input capacity is same as before or invalid");
    }

}