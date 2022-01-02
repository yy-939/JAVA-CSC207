package event;

import account.AccountManager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * A factory class that can construct a new Event, include Talk, Party and Panel discussion for now.
 * It gives account type, username and password for a new account as parameter.
 * @author Group0065
 * @version 1.0.4
 */
public class EventFactory {
    private int numTalk;
    private int numParty;
    private int numDiscuss;
    public EventFactory(int numTalk, int numParty, int numDiscuss) {
        this.numTalk = numTalk;
    }

    /**
     * Creates a new event with given information, and then add the new constructed event into
     * eventList and eventSchedule.
     * Creates an event based on input: if there is only one host, it will be a talk.
     * Other events to be added in phase 2. (party if there is no speaker, panel discussion if multiple speaker)
     * Assumes that it has been checked to make sure there is only one event in a given room.
     * Assumes that it has been checked to make sure the capacity does not exceed the room capacity.
     * @param type type of the new event
     * @param name name of the new event
     * @param startTime start time of the new event start
     * @param endTime end time of the new event start
     * @param location room name of the new event held in
     * @param description description of the new event
     * @param capacity the max number of people can participate in the new event
     * @param host the host of the new event
     * @return return event id if create successfully, otherwise return null.
     */
    public Event createEvent(String type, String name, Timestamp startTime, Timestamp endTime,
                             String location, String description, int capacity,
                             List<String> host) {
        if (type == null) {
            return null;
        } else if (type.equalsIgnoreCase("Talk")) {
            if (host.size() != 1) return null;
            String id = "T" + numTalk;
            Event talk = new Talk(name, startTime, endTime, location, description, capacity, host.get(0), id);
            numTalk++;
            return talk;
        } else if (type.equalsIgnoreCase("Party")) {
            if (host.size() != 0) return null;
            String id = "P" + numParty;
            Event party = new Party(name, startTime, endTime, location, description, capacity, id);
            numParty++;
            return party;
        } else if (type.equalsIgnoreCase("PanelDiscussion")) {
            String id = "D" + numDiscuss;
            Event discussion = new PanelDiscussion(name, startTime, endTime, location, description, capacity, host, id);
            numDiscuss++;
            return discussion;
        } else {
            return null;
        }
    }

    /**
     * updates the state of the events in the system
     * @return the latest number of events
     */
    protected List<Integer> updateEvent(){
        List<Integer> nums = new ArrayList<>();
        nums.add(numTalk);
        nums.add(numParty);
        nums.add(numDiscuss);
        return nums;
    }
}
