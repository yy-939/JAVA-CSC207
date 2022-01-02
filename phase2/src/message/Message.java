package message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is an entity class of messaging feature.
 * This is an abstract class of methods, all different kinds of methods extends this class.
 * All the constructed messages are already sent by its sender.
 * For most of the attributes in this class there is no setter method because the sent message should not be changed.
 * @author Group0065
 * @version 1.0.0
 */
abstract class Message implements Serializable {
    private String senderUsername;
    private List<String> receiverUsername;
    private Integer order;//unique
    private Boolean read;

    /**
     * create a message with one sender and a list of receivers, and the
     * @param sender the sender's name
     * @param receiver the list of receiver's names
     * @param order the unique order in the message based on when it is created
     */
    public Message(String sender, List<String> receiver, Integer order) {
        senderUsername = sender;
        receiverUsername = receiver;
        this.order = order;
        read = false;
    }


    /**get the message's order in the system based on when it is created
     * @return the order of the message
     */
    protected Integer getOrder(){return order;}

    /**
     * sets the order of the message.
     * @param num is the order assigned to the message
     */
    protected void setOrder(Integer num){this.order = num;}
    /**
     * Gets sender's username of this message.
     * @return sender's username of the message
     */
    protected String getSenderUsername() {
        return senderUsername;
    }

    /**
     * Gets receivers' usernames of this message.
     * @return a list of receiver usernames
     */
    protected List<String> getReceiverUsername() {
        return new ArrayList<>(receiverUsername);
    }

    //The 2 methods below are for the more types of messages in phase 2.

    /**
     * Gets content of this message.
     * This is an abstract getter function of content.
     * It is abstract because different kinds of messages may have different format of content.
     * @return the content of message
     */
    protected abstract String getContent();

    /**
     * Gets subject of this message.
     * This is an abstract getter function of subject.
     * It is abstract because different kinds of messages may have different format of subject.
     * @return the subject of message
     */
    protected abstract String getSubject();

    /**
     * Represents toString information of this message.
     * This is an abstract function which gives a String version of the message which is for the receiver of the message.
     * It is abstract because different kinds of messages may have different format of content and subject.
     * @return the string information of message without receiver.(Receiver view of message)
     */
    protected abstract String toStringReceived();

    /**
     *changes the state of a message has been read or not, if it is read, then will be marked as true
     */
    protected void markedRead(){
        this.read = true;
    }

    /**
     *changes the state of a message has been read or not, if it is wanted to be unread, then the state will be false
     */
    protected void markedUnread(){
        this.read = false;
    }

    /**
     * returns the state of a message being read or not. if read, return true, if it is unread, return false
     * @return the state of a message being read or not
     */
    protected boolean getRead(){
        return this.read;
    }
}
