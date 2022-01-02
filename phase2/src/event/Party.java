package event;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * An entity class which extends abstract class event.
 * Inherits all existing attributes of event class and with new attribute speaker.
 * Party is only one type of different kinds of events.
 * @author Group0065
 * @version 1.0.0
 */
class Party extends Event {

    protected Party(String name, Timestamp startTime, Timestamp endTime, String location, String description,
                   int capacity, String id) {
        super(name, startTime, endTime, location, description, capacity, id);
        }

    /**
     * Changes the speaker of this discussion by given new speaker.
     *
     * @param speaker new speaker of this discussion
     * @return true if add speaker successfully, else false
     */
    @Override
    public boolean changeHost(String speaker) {
        return false;
    }

    /**
     * Shows the speaker of this talk.
     *
     * @return the list that only contains the speaker for this talk
     */
    @Override
    protected List<String> getHosts() {
        return null;
    }


}
