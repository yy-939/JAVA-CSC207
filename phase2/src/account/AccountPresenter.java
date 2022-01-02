package account;

import conferencemain.MainPresenter;

import java.sql.Timestamp;
import java.util.*;

/**
 * A presenter class for account.
 * It contains methods to print out text information for users, which guides the account associated operations.
 * @author Group0065
 * @version 1.0.3
 */
public class AccountPresenter extends MainPresenter {

    /**
     * Represents the types of account we have and let user input number to choose for one type.
     * @param typeList a list contains all kinds of account type
     */
    void createAccount(ArrayList<String> typeList) {
        System.out.println("Choose account type that you want to create:");
        for (int i = 0; i < typeList.size(); i++){
            System.out.println(i + ": " + typeList.get(i));
        }
        System.out.println("input space: return to my account");
        System.out.println("Note: during the process of option, you can enter 'r' to redo the option from the beginning");
        super.getInput();
    }

    /**
     * Prints out "Enter new password:".
     */
    void askNewPassword() {
        System.out.println("Enter new password:");
        super.getInput();
    }

    /**
     * Prints out "Enter username:".
     */
    void askUsername() {
        System.out.println("Enter username:");
        super.getInput();
    }

    /**
     * Prints out "password updated successfully".
     */
    void changePasswordResult() {
       super.printActionMessage("password updated successfully");
    }

    /**
     * Prints out the account info given as parameter.
     * @param info account info to print
     */
    void accountInfo(String info) {
        System.out.println(info);
    }

    /**
     * Prints out all info of speakers which are given in a list as parameter.
     * If there is no speakers in the list, prints "There are no speaker."
     * @param speakers list of speakers info to print
     */
    void getAllSpeakers(List<String> speakers) {
        if (!speakers.isEmpty()) {
            System.out.println(speakers.toString());
        } else {
            super.printErrorMessage("There are no speaker.");
        }
    }

    /**
     * Prints out all info of friends which are given in a list as parameter.
     *
     * @param friends list of friend info to print
     */
    void getFriendList(List<String> friends) {
        if (!friends.isEmpty()) {
            System.out.println("Favourite User List: " + friends.toString());
        } else {
            super.printErrorMessage("you have no friends.");
        }
    }

    /**
     * Prints out info of all signed events of user given in a list as parameter.
     * If the list is empty, prints "you are not signed in any event."
     * @param events list of event info to print
     */
    void getSignedEvents(Map<String, Timestamp[]> events) {
        if (!events.isEmpty()) {
            for (String event : events.keySet()) {
                System.out.println("Time: " + super.getTime(events.get(event)[0]) + ", Event Id: " + event);
            }
        } else {
            super.printErrorMessage("you are not signed in any event.");
        }
    }

    /**
     * Prints out info of all event a speaker gives in a list as parameter.
     * If the list is empty, prints "This speaker does not give any event".
     * @param events list of event info to print
     */
    void getSpeakerEvents (List<String> events) {
        if (!events.isEmpty()) {
            System.out.println(events.toString());
        } else {
            super.printErrorMessage("This speaker does not give any event");
        }
    }

    /**
     * Prints out the info of events organized by an organizer given in a list as parameter.
     * If the list is empty, prints "You haven't create any event."
     * @param events list of events info to print
     */
    void eventOrganized (List<String> events) {
        if (!events.isEmpty()) {System.out.println(events.toString()); }else{
            super.printErrorMessage("You haven't create any event.");
        }}

    /**
     * Prints out feedback of if successfully add an user to friend list or not.
     * @param r a boolean true if added successfully, else false
     */
    void addFriendResult ( boolean r){
        if (r) {
            super.printActionMessage("account successfully added into favourites.");
        } else {
            super.printErrorMessage("user does not exist or is already in your favourites.");
        }
    }

    /**
     * Prints out feedback of if successfully removed an user from friend list or not.
     * @param r a boolean true if removed successfully, else false
     */
    void deleteFriendResult ( boolean r){
        if (r) {
            super.printActionMessage("account is successfully removed from your favourites.");
        } else {
            super.printErrorMessage("user does not exist or is not in your favourites yet.");
        }
    }

    /**
     * Prints out feedback of if successfully signed up an event.
     * @param r a boolean true if signed up successfully, else false
     */
    void singUpEventResult ( boolean r){
        if (r) {
            super.printActionMessage("event is signed up successfully.");
        } else {
            super.printErrorMessage("fail to sign up event.");
        }
    }

    /**
     * Prints out feedback of if successfully cancelled an event.
     * @param r a boolean true if cancelled successfully, else false
     */
    void cancelEventResult ( boolean r){
        if (r) {
            super.printActionMessage("event is canceled successfully.");
        } else {
            super.printErrorMessage("event does not match with the time.");
        }
    }



    /**
     * Prints out an error message regarding the user's input Speaker doesn't exist.
     */
    void speakerNotExist () {
        super.printErrorMessage("Speaker does not exist.");
    }

    /**
     * Prints out a message to ask user for an input of speaker username.
     */
    void askSpeakerName () {
        System.out.println("Please enter speaker username: \n" +
                "NOTE: \n Talk takes 1 speaker \n" +
                " Party takes NO speaker (please enter 's' rather than a speaker name) \n" +
                " PanelDiscussion takes multiple speakers");
        super.getInput();
    }

    /**
     * Prints out an error message the user's input of new password is same with the current one.
     */
    void samePassword () {
        super.printErrorMessage("The new password cannot be same with the latest one.");
    }

    /**
     * Prints out the error message that the account does not exist.
     * @param type A string represents the type of the account.
     */
    void noAccountType(String type){
        super.printErrorMessage("Type " + type + " does not exist currently");
    }

    /**
     * Prints out the message that this user has a time conflict.
     * @param time1 A Timestamp represents the start time
     * @param time2 A Timestamp represents the end time
     * @param username A String represents the username of the user
     */
    void notAvailable(Timestamp time1, Timestamp time2, String username){
        super.printErrorMessage("User " + username + "has other event from " + time1 + "to " + time2);
    }

    /**
     * Prints out the message that the organizer does not exist
     */
    void organizerNotExist () {
        super.printErrorMessage("Organizer does not exist.");
    }

    /**
     * Prints out the message for displaying the success of adding speaker and asking user input next speaker.
     */
    void askNextSpeaker(){
        System.out.println("Please enter id of next Speaker or enter 's' to stop entering.");
        super.getInput();
    }
    void successfullySchedule(String userName){
        super.printActionMessage("Added " + userName + " successfully.");
    }

    /**
     * Prints out the message for invalid input of speaker and ask the user to input next one
     * @param userName A string represents the username of the speaker input
     */
    void invalidSpeakerAskNext(String userName){
        System.out.println(userName + " is not a valid Speaker, please enter an id of valid Speaker or " +
                "enter 's' to stop entering.");
        super.getInput();
    }

    /**
     * Prints out the error message for unsuccessful adding speaker for a event
     */
    void failAddHostEvents(){
        super.printErrorMessage("Cannot schedule, this speaker does not exist.");
    }

    /**
     * Prints out the message to direct the user input receiver name
     */
    void askReceivers(){
        System.out.println("Enter receivers of the message");
        System.out.println("Directly enter the username of receivers. Enter 's' to stop adding receivers");
        super.getInput();
    }

    /**
     * Prints out the message that adding username as receiver successfully.
     * @param receiver A string represents the username of receiver.
     */
    void addReceiverSuccessfully(String receiver){
        System.out.println("Added " + receiver + " as a receiver successfully. You can enter username to add more " +
                "receivers or enter 's' to stop adding receivers.");
        super.getInput();
    }

    /**
     * Prints out the message that adding username as receiver unsuccessfully.
     * @param receiver A string represents the username of receiver.
     */
    void invalidReceiver(String receiver){
        System.out.println("Unable to message " + receiver + ". You can enter username to add more receivers or enter "
                + "'s' to stop adding receivers.");
        super.getInput();
    }

    /**
     * Prints out the error message for unavailable account stat information
     */
    void noUsersExist(){
        printErrorMessage("\"The account statistics information is not available due to no user here\"");
    }
    /**
     * Prints out the summary message of all existed accounts statistic information.
     * @param summary A map used type of each account in strings as keys and number of account in int as the values.
     * @param maxType A string represents the type of user that exists at the most voer all users.
     * @param maxPercentage A double represents the percentage of the max user type over all users.
     * @param totalPeople An int represents the total number of users.
     */
    void printAccountsStatsInfo(Map<String, Integer> summary, String maxType, double maxPercentage, int totalPeople){
        super.printActionMessage("The statistic information of this conference is successfully printed:");
        System.out.println("There are " + summary.size() + " types of " + totalPeople +
                " users in the conference totally.");
        System.out.println(summary.keySet());
        StringBuilder summaryStr = new StringBuilder();
        String summaryStr1;
        for (String type: summary.keySet()) {
            int size = summary.get(type);
            String message = size + " " + type + ", ";
            summaryStr.append(message);
        }
        summaryStr1 = summaryStr.toString();
        summaryStr1 = summaryStr1.substring(0, summaryStr1.length()-2);
        System.out.println("The number of users in each type are: " + summaryStr1);
        System.out.println(maxType + " type is the largest population, in " +
                String.valueOf(maxPercentage * 100).substring(0, 4) + "%");
        super.getInput();
    }

    /**
     * Prints the error message that the failure of reschedule
     */
    void existedHost(){
        super.printErrorMessage("Failed to schedule, This user is already the host in this event, " +
                "please schedule other speakers.");
    }

}