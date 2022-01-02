package userinterface;

import account.*;
import event.EventManager;
import event.EventSystem;
import message.MessagingManager;
import message.MessageSystem;
import room.RoomManager;
import room.RoomSystem;
import data.DataReader;

/**
 * UserSystemFactory is a factory class to construct a new Usersystem
 * UserSystemFactory is given type, username, Messagesystem, EventSystem, and RoomSystem
 * for the new Usersystem as parameter.
 * If the type is attendee, then it constructs a new AttendeeSystem with the given parameters.
 * If the type is speaker, then it constructs a new SpeakerSystem with the given parameters.
 * If the type is organizer, then it constructs a new OrganizerSystem with the given parameters.
 * If the type is admin, then it constructs a new AdminSystem with the given parameters.
 * Else returns null.
 * @author Group0065
 * @version 1.0.0
 */
public class UserSystemFactory {
    UserSystemBuilder builder = new UserSystemBuilder();

    /**
     * Decides which type of userSystem we are going to use, and then apply for the corresponding builder method.
     * @param type the type of userSystem we are going to build
     * @param username username of log in user
     * @return userSystem with given type.
     */
    public UserSystem makeUserSys(String type, String username){
        builder.buildEventSystem();
        builder.buildMessageSystem();
        builder.buildRoomSystem();
        switch (type){
            case "Attendee":
                return builder.buildAttendeeSystem(username);
            case "Organizer":
                return builder.buildOrganizerSystem(username);
            case "Speaker":
                return builder.buildSpeakerSystem(username);
            case "Admin":
                return builder.buildAdminSystem(username);
            default: return null;
        }

    }



}
