package event;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * An entity class which extends abstract class event.
 * Inherits all existing attributes of event class and with new attribute speaker.
 * PanelDiscussion is only one type of different kinds of events.
 * @author Group0065
 * @version 1.0.0
 */

class PanelDiscussion extends Event {
    private List<String> speaker;

    protected PanelDiscussion(String name, Timestamp startTime, Timestamp endTime, String location, String description,
                              int capacity, List<String> speaker, String id) {
        super(name, startTime, endTime, location, description, capacity, id);
        this.speaker = speaker;
    }

    /**
     * Changes the speaker of this discussion by given new speaker.
     *
     * @param speaker new speaker of this discussion
     * @return true if add speaker successfully, else false
     */
    @Override
    public boolean changeHost(String speaker) {
        if (speaker == null) return false;
        this.speaker.add(speaker);
        return true;
    }

    /**
     * Shows the speaker of this talk.
     *
     * @return the list that only contains the speaker for this talk
     */
    @Override
    protected List<String> getHosts() {
        return speaker;
    }
}