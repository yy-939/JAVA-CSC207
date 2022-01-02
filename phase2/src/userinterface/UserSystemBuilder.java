package userinterface;

import account.AccountSystem;
import account.Admin;
import account.Organizer;
import data.DataReader;
import event.EventSystem;
import message.MessageSystem;
import room.RoomSystem;

/**
 * A builder class for building different types of userSystem.
 * And put the lower controllers read from file to put in the new constructed system.
 * @author Group0065
 * @version 1.0.0
 */
public class UserSystemBuilder<T> {
    EventSystem eventSystem;
    MessageSystem messageSystem;
    RoomSystem roomSystem;
    DataReader dr = new DataReader();

    public UserSystemBuilder(){

    }
    public void buildEventSystem(){
        eventSystem = dr.createEventSystem();
    }

    public void buildMessageSystem(){
        messageSystem = dr.createMessageSystem();
    }

    public void buildRoomSystem(){
        roomSystem = dr.createRoomSystem();
    }

    //private helper
    private boolean checkSystem(){
        if(eventSystem == null){
            return false;
        }
        if(messageSystem == null){
            return false;
        }
        if(roomSystem == null){
            return false;
        }
        return true;
    }

    /**
     * builds an attendee system and set all stored systems be ones read from file.
     * @param username user who is logging in
     * @return an attendeeSystem for the current user
     */
    public AttendeeSystem buildAttendeeSystem(String username){
        if(!checkSystem()){
            return null;
        }
        AttendeeSystem user = new AttendeeSystem(username);
        changeSystems(user);
        return user;
    }

    /**
     * builds an admin system and set all stored systems be ones read from file.
     * @param username user who is logging in
     * @return an adminSystem for the current user
     */
    public AdminSystem buildAdminSystem(String username){
        if(!checkSystem()){
            return null;
        }
        AdminSystem user = new AdminSystem(username);
        changeSystems(user);
        return user;
    }

    /**
     * builds a speaker system and set all stored systems be ones read from file.
     * @param username user who is logging in
     * @return a speakerSystem for the current user
     */
    public SpeakerSystem buildSpeakerSystem(String username){
        if(!checkSystem()){
            return null;
        }
        SpeakerSystem user = new SpeakerSystem(username);
        changeSystems(user);
        return user;
    }

    /**
     * builds an organizer system and set all stored systems be ones read from file.
     * @param username user who is logging in
     * @return an organizerSystem for the current user
     */
    public OrganizerSystem buildOrganizerSystem(String username){
        if(!checkSystem()){
            return null;
        }
        OrganizerSystem user = new OrganizerSystem(username);
        changeSystems(user);
        return user;
    }

    //private helper
    private void changeSystems(UserSystem user){
        user.setEventSystem(eventSystem);
        user.setMessageSystem(messageSystem);
        user.setRoomSystem(roomSystem);

    }

}
