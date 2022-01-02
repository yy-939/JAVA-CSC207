package event;

import conferencemain.MainPresenter;

import java.util.List;
import java.util.Map;

/**
 * Presenter class of event.
 * Contains the text message to print out to user.
 * Most of these methods are called in EventSystem class.
 * @author Group0065
 * @version 1.0.0
 */
public class EventPresenter extends MainPresenter {

    /**
     * Prints out the list of toString description of events to user.
     * If the list is empty, prints out "There is no event."
     * @param events a list of event id and name
     * @param message additional message
     */
    void printEventList(List<String[]> events, String message) {
        System.out.println(message);
        for(String[] s : events) {
            System.out.println("Starting Time: " + s[0] + ", End Time: " + s[1] + ", Event Id: " + s[2] + ", Event Name: " + s[3]);
        }
    }

    /**
     * Prints the error message that there is no event
     */
    void printNoEvent() {
        super.printErrorMessage("There is no event");
    }

    /**
     * Prints the error message that there is no event with no attendees
     */
    void eventNotEmpty() {
        super.printErrorMessage("Event is no event with no attendees.");
    }

    /**
     * Prints out "Enter event id:".
     */
    void askEventIdToDelete() {
        System.out.println("Enter event id to delete an event:");
        super.getInput();
    }

    /**
     * Prints the error message that there is no event
     */
    void printNoEmpty() {
        super.printErrorMessage("There is no event with no attendees");
    }


    /**
     * Prints out the result of the event is successfully created or not.
     * @param create a boolean, true if the event is successfully created, else false
     */
    void eventCreateMessage(boolean create, String id) {
        if (create){super.printActionMessage("Event create successfully. New Event ID is " + id);
        }else{super.printErrorMessage("Fail to create events.");}
    }

    /**
     * Prints out the result of the attendee is successfully added or not.
     * @param attendee a boolean, true if the attendee is successfully added, else false
     */
    void addAttendeeMessage(boolean attendee) {
        if (attendee){super.printActionMessage("attendee has been add to event");
        }else{super.printErrorMessage("attendee is already in the event");}
    }

    /**
     * Prints out the list of toString description of all events starts in given time period.
     * If there are no event in given map, tells user that no event found
     * @param events a map which stores map time to a list of event id and name of events start at the time
     */
    void getEventByTimeMessage(List<String[]> events) {
        if (events.isEmpty()) {
            super.printErrorMessage("no events found");
        }
        for (String[] s : events) {
            System.out.println("Time: " + s[0] + ", Event Id: " + s[1] + ", Event Name: " + s[2]);
        }
    }


    /**
     * Prints out the list of toString description of all events held in room with given name.
     * If there is no event in given map, tells user that no event found.
     */
    void getEventByLocationMessage(String id, String name, String start, String end, String location, List<String> speakers) {
        System.out.println("-------------------------------------------------------");
        System.out.println("Event id: " + id);
        System.out.println("Event name: " + name);
        System.out.println("Start time: " + start);
        System.out.println("End time: " + end);
        if(speakers == null || speakers.isEmpty()){
            System.out.println("This event doesn't have a Speaker.");
        }else if(speakers.size() == 1){
            System.out.println("The Speaker of this event is: " + speakers.get(0));
        }else{
            StringBuilder sb = new StringBuilder();
            for(String speaker: speakers){
                sb.append(speaker).append(", ");
            }
            sb.reverse();
            sb.delete(0,2);
            sb.reverse();
            System.out.println("The Speakers of this event are: " + sb);
        }
        System.out.println("-------------------------------------------------------");
    }

    /**
     * Prints the error message that there is no event at given location
     */
    void noEventAtLocation(String location){
        super.printErrorMessage("No events found at " + location + " .");
    }

    /**
     * Prints all events at given location
     */
    void locationMessage(String location){
        System.out.println("Here are all events at the location " + location + ":");
    }

    /**
     * Prints out the information that there is no such event type given by user.
     */
    void eventTypeMessage() {
        super.printErrorMessage("No such event type");
    }

    /**
     * Prints out the result of the speaker is successfully scheduled or not.
     * If the speaker is not successfully scheduled, prints out the event doesn't exist(which is the only reason for failing)
     * @param schedule a boolean which is true if scheduled successfully, else false
     */
    void scheduleSpeakerMessage(boolean schedule) {
        if (schedule){super.printActionMessage("speaker has been schedule to event");}else{
            {super.printErrorMessage("Not all speakers have been scheduled for the event.");}
        }
    }

    /**
     * Prints out the result of the event time is successfully rescheduled or not.
     * If the reschedule fails, print out feedback for time conflict to user.
     * @param reschedule a boolean which is true if rescheduled successfully, else false
     */
    void rescheduleMessage(boolean reschedule) {
        if (reschedule){super.printActionMessage("the event has been reschedule");
        }else{super.printErrorMessage("the time is same with original time of event.");}
    }

    /**
     * Prints out the toString description parameter.
     * @param description toString to print
     */
    void descriptionMessage(String description) {
        System.out.println(description);
    }

    /**
     * Prints the error message that no event available
     */
    void printNotAvailable() {
        super.printErrorMessage("No Event Available");
    }

    /**
     * Prints out "Enter event type:".
     */
    void askEventType() {
        System.out.println("Enter event type: (Talk(take 1 speaker), Party(take no speaker), or PanelDiscussion(take multiple speakers))"); // more types will be added in phase2
        super.getInput();
    }

    /**
     * Prints out "Enter event id:".
     */
    void askEventId() {
        System.out.println("Enter event id:");
        super.getInput();
    }

    /**
     * Prints out "Enter event name:".
     */
    void askEventName() {
        System.out.println("Enter event name:");
        super.getInput();
    }

    /**
     * Prints out "Enter event description:".
     */
    void askEventDescription() {
        System.out.println("Enter event description:");
        super.getInput();
    }


    /**
     * Prints out "Enter start time, format 'yyyy-mm-dd hh':".
     */
    void askStartTime() {
        System.out.println("Enter start time, format 'yyyy-mm-dd hh':");
        super.getInput();
    }

    /**
     * Prints out "Enter end time, format 'yyyy-mm-dd hh':".
     */
    void askEndTime(){
        System.out.println("Enter end time, format 'yyyy-mm-dd hh':");
        super.getInput();
    }


    /**
     * Prints out "[ERROR] Event not exist.".
     */
    void eventNotExist() {
        super.printErrorMessage("Event does not exist.");
    }

    /**
     * Prints out "[ACTION] Event has been canceled."
     */
    void cancelEvent() {
        super.printActionMessage("Event has been canceled.");
    }

    /**
     * Prints out the error message that end time is before start time
     */
    void endTimeBeforeStartTime() {
        super.printErrorMessage("End time is before start time.");
    }

    /**
     * Prints out info of all speaker of signed events given in a list as parameter.
     * If the list is empty, prints "There are no speakers".
     * @param speakers the list of speakers that need to be printed
     */
    void getSpeakersForSignedEvents(List<String> speakers) {
        if (!speakers.isEmpty()) {
            System.out.println(speakers.toString());
        } else {
            super.printErrorMessage("There are no speakers");
        }}


    /**
     * prints the message to ask the user to input the date
     */
    void askForDateInput(){
        System.out.println("Please give the date you want to search for (format yyyy-mm-dd)");
        super.getInput();
    }

    /**
     * prints the message of the events on specific date
     * @param date is the date
     */
    void printEventOnDate(String date){
        System.out.println("The events on the given date " + date + " are:");
    }

    /**
     * prints no event happens on a specific date
     * @param date input date from user
     */
    void noEventDate(String date){
        super.printErrorMessage("No event on date " + date);
    }

    /**
     * prints all information of an message
     * @param id the id of the event
     * @param name the name of the event
     * @param start the start time of the event
     * @param end the end time of the event
     * @param location the location of the event
     * @param speakers the speaker of the event
     */
    void dateMessage(String id, String name, String start, String end, String location, List<String> speakers){
        System.out.println("-------------------------------------------------------");
        System.out.println("Event id: " + id);
        System.out.println("Event name: " + name);
        System.out.println("Start time: " + start);
        System.out.println("End time: " + end);
        System.out.println("Location: " + location);
        if(speakers == null || speakers.isEmpty()){
            System.out.println("This event doesn't have a Speaker.");
        }else if(speakers.size() == 1){
            System.out.println("The Speaker of this event is: " + speakers.get(0));
        }else{
            StringBuilder sb = new StringBuilder();
            for(String speaker: speakers){
                sb.append(speaker).append(", ");
            }
            sb.reverse();
            sb.delete(0,2);
            sb.reverse();
            System.out.println("The Speakers of this event are: " + sb);
        }
        System.out.println("Speaker");
        System.out.println("-------------------------------------------------------");
    }

    /**
     * print the message that a user does not host the event
     * @param speaker username of speaker we want to check
     */
    void speakHostNoEvent(String speaker){
        super.printErrorMessage(speaker + " doesn't hold any event.");
    }

    /**
     * prints the host of the event
     * @param speaker username of speaker we want to check
     */
    void printEventHosts(String speaker){
        System.out.println("The events hold by " + speaker + " are:");
    }

    /**
     * prints the information of the event
     * @param id is the id of the event
     * @param name is the name of the event
     * @param start is the start time of the event
     * @param end is the end time of the event
     * @param location is the location of the event
     * @param speakers the speaker of the event
     */
    void speakerMessage(String id, String name, String start, String end, String location, List<String> speakers){
        System.out.println("-------------------------------------------------------");
        System.out.println("Event id: " + id);
        System.out.println("Event name: " + name);
        System.out.println("Start time: " + start);
        System.out.println("End time: " + end);
        System.out.println("Location: " + location);
        if(speakers == null || speakers.isEmpty()){
            System.out.println("This event doesn't have a Speaker.");
        }else if(speakers.size() == 1){
            System.out.println("The Speaker of this event is: " + speakers.get(0));
        }else{
            StringBuilder sb = new StringBuilder();
            for(String speaker: speakers){
                sb.append(speaker).append(", ");
            }
            sb.reverse();
            sb.delete(0,2);
            sb.reverse();
            System.out.println("The Speakers of this event are: " + sb);
        }
        System.out.println("-------------------------------------------------------");
    }

    /**
     * prints the message that the user is not signing up for the even
     */
    void noEventAttended(){
        super.printErrorMessage("You haven't signed up any event.");
    }

    /**
     * prints the events that the user signed up
     */
    void printAttendedEvents(){
        System.out.println("Events you signed up for are:");
    }

    /**
     * Prints out details of event attended by user
     * @param id id of event
     * @param name  name of event
     * @param start start time of event
     * @param end end time of event
     * @param location location of event
     * @param speakers speakers who hold the event
     */
    void userAttended(String id, String name, String start, String end, String location, List<String> speakers){
        System.out.println("-------------------------------------------------------");
        System.out.println("Event id: " + id);
        System.out.println("Event name: " + name);
        System.out.println("Start time: " + start);
        System.out.println("End time: " + end);
        System.out.println("Location: " + location);
        if(speakers == null || speakers.isEmpty()){
            System.out.println("This event doesn't have a Speaker.");
        }else if(speakers.size() == 1){
            System.out.println("The Speaker of this event is: " + speakers.get(0));
        }else{
            StringBuilder sb = new StringBuilder();
            for(String speaker: speakers){
                sb.append(speaker).append(", ");
            }
            sb.reverse();
            sb.delete(0,2);
            sb.reverse();
            System.out.println("The Speakers of this event are: " + sb);
        }
        System.out.println("-------------------------------------------------------");
    }

    /**
     * prints the message that a specific room is not found
     */
    void failToFindRoom(){
        super.printErrorMessage("Fail to find the room because eventId does not exist or room does not exist.");
    }

    /**
     * prints the action message that the user changes the capacity of a room successfully
     */
    void changeCapacity(){
        super.printActionMessage("You had successfully changed the event capacity");
    }

    /**
     * print the error message the user fail to change the event capacity
     */
    void failToChangeCapacity(){
        super.printErrorMessage("Fail to change the event capacity");
    }


    /**
     * print the error message when the number of host and type of event is not matched
     */
    void hostNotMatch() {super.printErrorMessage("Type of event and number of host not match, Talk take 1 speaker, " +
            "Party take no speaker, or PanelDiscussion take multiple speakers.");}

    /**
     * print the error message when the number of host is maximum and can not add host
     */
    void hostMax() {super.printErrorMessage("Number of host of this event has reach maximum, Talk take 1 speaker, " +
            "Party take no speaker, or PanelDiscussion take multiple speakers.");}

    /**
     * print the events summary of a conference
      * @param totalNumber the total number of events in the conference
     * @param enrollSum is map of the events to the number attendees
     */
    void printAllEventsSummary(int totalNumber, Map<String, Integer> enrollSum){
        super.printActionMessage("The statistic information of this conference is successfully printed:");
        System.out.println("There are " + totalNumber + " events in the conference totally.");
        StringBuilder summaryStr = new StringBuilder();
        String summaryStr1;
        for (String event: enrollSum.keySet()) {
            int attendeeNum = enrollSum.get(event);
            String message = event + ": " + attendeeNum + " attendees, ";
            summaryStr.append(message);
        }
        summaryStr1 = summaryStr.toString();
        summaryStr1 = summaryStr1.substring(0, summaryStr1.length()-2);
        System.out.println("The number of attendees in each event are: " + summaryStr1);
        super.getInput();
    }

    /**
     * prints the information of the top events
     * @param eventRates the rates of event
     */
    void printTopEvents(Map<Double, String[]> eventRates){
        String ratestr;
        super.printActionMessage("The statistic information of events that have highest attend rate are printed:");
        StringBuilder summaryStr = new StringBuilder();
        for (Double rate: eventRates.keySet()) {
            summaryStr.append(eventRates.get(rate)[0]);
            summaryStr.append(" ").append(eventRates.get(rate)[1]).append(" has attend rate: ");
            if (rate != 0) {
                ratestr = String.valueOf(rate * 100).substring(0, 5) + "%";}else{
                ratestr = (rate * 100) + "%";
            }
            summaryStr.append(ratestr).append(" ");
        }
        System.out.println(summaryStr);
        super.getInput();
    }
    /**
     * Prints the error message that the input time period is repeated with the original time period.
     */
    void repeatedTime(){
        super.printErrorMessage("The new time is the same as the original time. "+
                "Please enter 's' to stop, or re-enter by enter 'r'.");
        super.getInput();
    }

    /**
     * Prints the message that the user exit the choice of reschedule event and the schedule remains.
     */
    void remainsAndExit(){
        super.printActionMessage("You quited the reschedule and the schedule is remains as the original.");
    }
}
