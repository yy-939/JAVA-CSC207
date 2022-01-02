package event;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * An entity class which extends abstract class event.
 * Inherits all existing attributes of event class and with new attribute speaker.
 * Talk is only one type of different kinds of events.
 * @author Group0065
 * @version 1.0.0
 */
class Talk extends Event {
    private String speaker;

    protected Talk(String name, Timestamp startTime, Timestamp endTime, String location, String description,
                   int capacity, String speaker, String id) {
        super(name, startTime, endTime,location, description, capacity, id);
        this.speaker = speaker;
    }

    /**
     * Changes the speaker of this talk by given new speaker.
     * @param speaker new speaker of this talk
     * @return true if changed speaker successfully, else false
     */
    @Override
    public boolean changeHost(String speaker) {
        if (speaker == null) return false;
        this.speaker = speaker;
        return true;
    }

    /**
     * Shows the speaker of this talk.
     * @return the list that only contains the speaker for this talk
     */
    @Override
    protected List<String> getHosts() {
        List<String> speaker = new ArrayList<>();
        speaker.add(this.speaker);
        return speaker;
    }

    /**
     * Gives the toString information of this talk in a string.
     * The information contains all information from toString method in event class, also speaker for the talk.
     * @return the String of information of this talk, contains name, start time, location, capacity, description and
     * speaker
     */
    @Override
    public String toString(){
        return super.toString() + "Host: " + speaker;
    }
}
