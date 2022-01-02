package message;

import com.sun.java.accessibility.util.EventID;
import conferencemain.MainPresenter;
import event.Event;

import java.util.List;
/**
 * Presenter of message feature.
 * Takes the output from Messaging Manager and present the messages to the users.
 * Also presents the function in Manager successful or not.
 * @author Group0065
 * @version 1.0.0
 */
public class MessagePresenter extends MainPresenter {


    /**
     * Presents all the messages sent by the users to read.
     *
     * @param messageSent is the list of the messages that sent by the user, get from Messaging Manager
     */
    void printAllSentMessage(List<String> messageSent) {
        if (messageSent == null || messageSent.isEmpty()) {
            super.printErrorMessage("You haven't sent any message yet.");
        } else {
            System.out.println("The messages you have sent are:");
            super.printSeparateLine();
            for (String message : messageSent) {
                System.out.println(message);
                super.printSeparateLine();
            }
        }

    }

    /**
     * Presents all the messages Received by the users to read.
     *
     * @param messageReceived is the list of the messages that sent by the user, get from Messaging Manager
     */

    void printAllReceivedMessage(List<String> messageReceived) {
        if (messageReceived == null || messageReceived.isEmpty()) {
            super.printErrorMessage("You haven't received any message yet.");
        } else {
            System.out.println("The messages you have received are:");
            super.printSeparateLine();
            for (String message : messageReceived) {
                System.out.println(message);
                super.printSeparateLine();
            }
        }
    }


    /**
     * Prints receiver version toString of message with given order.
     * @param message toString of message we want to print
     * @param order ID of the message we want to print
     */
    void printGivenOrderReceivedMessage(String message, Integer order){
        System.out.println("The message you received with message Id " + order.toString() + " is: ");
        super.printSeparateLine();
        System.out.println(message);
        super.printSeparateLine();
    }

    /**
     * Prints sender version toString of message with given order.
     * @param message toString of message we want to print
     * @param order ID of the message we want to print
     */
    void printGivenOrderSentMessage(String message, Integer order){
        System.out.println("The message you sent with message Id " + order.toString() + " is: ");
        super.printSeparateLine();
        System.out.println(message);
        super.printSeparateLine();
    }

    /**
     * Prints toString all unread messages given as parameter
     * @param messages a list of toString of message we want to print
     */
    void printUnreadMessage(List<String> messages){
        System.out.println("You have unread message: ");
        for(String message: messages){
            super.printSeparateLine();
            System.out.println(message);
            super.printSeparateLine();
        }
    }


    /**
     * Informs the user if the message able to be sent or not
     *
     * @param ableToMessage is the boolean tells able or not
     */

    void printSendMessage(boolean ableToMessage) {
        if (ableToMessage) {
            super.printActionMessage("Message sent successfully.");
        } else {
            super.printErrorMessage("Message is unable to be sent.");
        }
    }

    /**
     * Asks the user to enter content
     */
    void askContent() {
        System.out.println("Enter message content:");
        super.getInput();
    }

    /**
     * Asks the user to enter message subject
     */
    void askSubject() {
        System.out.println("Enter message subject:");
        super.getInput();
    }

    /**
     * Asks user to enter message ID
     */
    void askOrder(){
        System.out.println("Please enter the message ID of message: ");
        super.getInput();
    }

    /**
     * Tells user no unread message received
     */
    void noUnreadMessage(){
        System.out.println("You don't have any unread message.");
    }


    /**
     * Tells user that no received message with such message ID
     * @param order ID that no received message with given ID
     */
    void invalidReceivedID(Integer order){
        super.printErrorMessage("No received message with ID " + order.toString());
    }

    /**
     * Tells user that no sent message with such message ID
     * @param order ID that no sent message with given ID
     */
    void invalidSentID(Integer order){
        super.printErrorMessage("No sent message with ID " + order.toString());
    }

    /**
     * Tells user that message with given ID is marked as unread successfully
     */
    void markAsUnread(){
        System.out.println("Marked given message as unread successfully.");
    }


    /**
     * Tells user the input ID is not valid for the condition
     * @param order input message id from user
     */
    void invalidID(Integer order){
        super.printErrorMessage("Message with given ID " + order.toString() + " does not exist.");
    }

    /**
     * Tells user the message is successfully deleted
     */
    void deletedSuccessfully(){
        System.out.println("Message deleted successfully.");
    }



    /**
     * informs there are no receivers.
     */

    void noReceivers() {
        super.printErrorMessage("There is no receiver.");
    }




}
