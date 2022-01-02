package message;

import com.sun.xml.internal.bind.v2.TODO;
import sun.security.krb5.internal.crypto.Aes128;

import java.io.Serializable;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;


/**
 * An use case class of messaging feature.
 * Stores all sent messages in a HashMap, which has username of all users map to their sent messages.
 * Contains constructor of Message, this class should be used to construct new message.
 * Methods in this class contains get messages with given sender and receiver, get all sent or received messages of user,
 * and send message with given information.
 * Input information is given by MessageSystem.
 * @author Group0065
 * @version 1.0.0
 */
public class MessagingManager implements Serializable {
    private Map<String, List<Message>> receiverMap = new HashMap<>();//key is the receiver
    private Map<String, List<Message>> senderMap = new HashMap<>();//key is sender
    private Integer totalNumber;
    // username map a list of message sent
    //TODO: Change the messageMap into a list of message received.

    public MessagingManager(){
        totalNumber = 0;
    }

    /**
     * Returns the all the messages sent by the user with given username.
     * Get a list of messages by getting the list of messages the username mapped to in hashmap messageMap.
     * The result should be in view of user with given username, so the toString should be in sender's view.
     *
     * @param username the username of the user that we want to check its sent message
     * @return a list of toString of messages those are all sent by the user with given username in sender's view
     */
    protected List<String> getSentMessages(String username) {
        List<Message> messageList = getSentMessagesHelper(username);
        List<Message> sorted = sortMessages(messageList);
        return getSentToString(sorted);

    }



    private List<Message> getSentMessagesHelper(String username) {
        List<Message> lst = new ArrayList<>();
        for(String sender: senderMap.keySet()){
            if(sender.equals(username)){
                if(senderMap.get(sender) == null || senderMap.get(sender).isEmpty()){
                    return null;
                }
                lst = new ArrayList<>(senderMap.get(sender));
            }
        }
        if(lst.isEmpty()){
            return null;
        }
        return lst;
    }

    //a helper to get all received messages from certain receiver
    private List<Message> getReceivedMessagesHelper(String receiver) {
        List<Message> lst = new ArrayList<>();
        for (String username: receiverMap.keySet()) {
            if (username.equals(receiver)) {
                if(receiverMap.get(receiver) == null || receiverMap.get(receiver).isEmpty()){
                    return null;
                }
                lst = new ArrayList<>(receiverMap.get(receiver));
            }

        }
        if(lst.isEmpty()){
            return null;
        }
        return lst;

    }

    //a helper to print toString of messages in given list for receiver
    private List<String> getReceivedToString(List<Message> messageList){
        if(messageList == null || messageList.isEmpty()){
            return null;
        }
        List<String> result = new ArrayList<>();
        for(Message message: messageList){
            result.add(message.toStringReceived());
            message.markedRead();
        }
        return result;
    }

    //a helper to print toString of messages in given list for sent
    private List<String> getSentToString(List<Message> messageList){
        if(messageList == null || messageList.isEmpty()){
            return null;
        }
        List<String> result = new ArrayList<>();
        for(Message message: messageList){
            result.add(message.toString());
        }
        return result;
    }


    //private helper get message received from other user.
    private List<Message> getMessagesFromSpecificAccount(String sender, String currUser) {
        if(getReceivedMessagesHelper(currUser) == null){
            return null;
        }
        if(getReceivedMessagesHelper(currUser).isEmpty()){
            return null;
        }
        List<Message> messageList = new ArrayList<>();
        for (Message m : getReceivedMessagesHelper(currUser)) {
            if (m.getSenderUsername().equals(sender)) {
                messageList.add(m);
            }
        }
        return messageList;
    }

    //private helper get message sent to other user.
    private List<Message> getMessagesToSpecificAccount(String currUser, String receiver){
        if(getSentMessagesHelper(currUser) == null){
            return null;
        }
        if(getSentMessagesHelper(currUser).isEmpty()){
            return null;
        }
        List<Message> messageList = new ArrayList<>();
        for(Message m: getSentMessagesHelper(currUser)){
            if(m.getReceiverUsername().contains(receiver)){
                messageList.add(m);
            }
        }
        return messageList;
    }




    /**
     * Gets the message sent from current user to a receiver user.
     * This receiver user only need to be one of the receivers of the message we want to get.
     * Result of this method is going to be viewed by current user, so the toString of messages should be in sender's
     * view.
     *
     * @param sender   the current user's username
     * @param receiver the username of receiver user
     * @return list of toString of all messages that are sent from the current user to receiver user in senders view
     */
    // This method will be used when we have stronger search functions. For phase we want to keep the menu small
    // and simple.
    protected List<String> getSentMessageToSpecificAccount(String sender, String receiver) {
        List<Message> messageList = getMessagesToSpecificAccount(sender, receiver);
        List<Message> sorted = sortMessages(messageList);
        return getSentToString(sorted);
    }

    /**
     * this is to return a list of messages that is sorted by order with bubble sort(could be helper)
     *
     * @param messages is a list of messages not in order
     */
    // helper function
    protected List<Message> sortMessages(List<Message> messages) {
        if(messages == null || messages.isEmpty()){
            return null;
        }
        ArrayList<Message> messagesInOrder = new ArrayList<>();
        while(!messages.isEmpty()) {
            Integer i = messages.get(0).getOrder();
            Message pivot = messages.get(0);
            for (Message message : messages) {
                if(message.getOrder() < i){
                    pivot = message;
                    i = message.getOrder();
                }
            }
            messagesInOrder.add(pivot);
            messages.remove(pivot);
        }
        return messagesInOrder;
    }
    // Not a helper method

    /**
     * Gets the message sent from sender user to current user.
     * This current user only need to be one of the receivers of the message we want to get.
     * Result of this method is going to be viewed by current user, so the toString of messages should be in receiver's
     * view.
     *
     * @param receiver the current user's username
     * @param sender   the username of sender user
     * @return list of toString of all messages that are sent from sender user to current user in receiver's view
     */
    protected List<String> getReceivedMessagesFromSpecificAccount(String sender, String receiver) {
        List<Message> messageList = getMessagesFromSpecificAccount(sender, receiver);
        List<Message> sorted = sortMessages(messageList);
        return getReceivedToString(sorted);

    }

    /**
     * Gives toString of all messages received by user with given username.
     * This toString of messages should be in receivers' view.
     * This given user only need to be on of the receivers of the message we want to get.
     *
     * @param currUsername the username of user we want to find all messages received by
     * @return list of toString of messages that have the user with given username to be one of its receivers
     */
    protected List<String> getReceivedMessages(String currUsername) {
        List<Message> messageList = getReceivedMessagesHelper(currUsername);
        List<Message> sorted = sortMessages(messageList);
        return getReceivedToString(sorted);
    }


    /**
     * Constructs a new WordMessage with given information and then add it into the messageMap to store it.
     * senderUsername should be the current user's user name. You can't send message for others.
     * List of receivers will require AccountManager, either check username exist or get a list of Speaker.
     * In controller level, you SHOULD check all username when you ask user to input receiver username.
     *
     * @param senderUsername the name of the sender, which should be current username
     * @param receivers      a list of receivers the WordMessage is going to send to, has to be a list
     * @param subject        the subject of the WordMessage want to send
     * @param content        the content of the WordMessage want to send
     */
    protected void sendWordMessage(String senderUsername, List<String> receivers, String subject, String content) {
        // List of receivers will require AccountManager, either check username exist or get a list of Speaker
        // In controller level, you SHOULD check all username when you ask user to input receiver username
        
        for (String receiver : receivers) {
            Message newMessage = new WordMessage(senderUsername, receivers, subject, content, totalNumber);
            if (!receiverMap.containsKey(receiver)) {
                List<Message> messages = new ArrayList<>();
                messages.add(newMessage);
                receiverMap.put(receiver, messages);
            }
            else{
                receiverMap.get(receiver).add(newMessage);

            }
        }
        Message newMessageForSender = new WordMessage(senderUsername, receivers, subject, content, totalNumber);
        if(!senderMap.containsKey(senderUsername)){
            List<Message> messageList = new ArrayList<>();
            messageList.add(newMessageForSender);
            senderMap.put(senderUsername, messageList);
        }else{
            senderMap.get(senderUsername).add(newMessageForSender);
        }
        totalNumber = totalNumber + 1;
    }

    /**
     * Marks the received message of given user with given message ID as unread.
     * @param order message ID of the message want to mark as unread
     * @param currUsername user who wants to mark a received message as unread
     * @return true if marked as unread, false if the message does not exist
     */
    //true if operated false can't
    protected boolean markAsUnread(Integer order, String currUsername){
        if(!receiverMap.containsKey(currUsername)){
            return false;
        }
        for(Message message: receiverMap.get(currUsername)){
            if(message.getOrder().equals(order)){
                message.markedUnread();
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes the received message of given user with given message ID from this user's mailbox
     * @param order ID of message want to delete
     * @param currUsername user who wants to delete received message
     * @return true if deleted, false if message doesn't not exist
     */
    //true if operated false can't
    protected boolean deleteReceivedMessage(Integer order, String currUsername){
        if(!receiverMap.containsKey(currUsername)){
            return false;
        }
        if(receiverMap.get(currUsername).isEmpty() || receiverMap.get(currUsername) == null){
            return false;
        }
        List<Message> messageList = new ArrayList<>();
        for(Message message: receiverMap.get(currUsername)){
            if(!message.getOrder().equals(order)){
                messageList.add(message);
            }
        }
        if(messageList.size() == receiverMap.get(currUsername).size()) {
            return false;
        }else{
            receiverMap.put(currUsername, messageList);
            return true;

        }
    }

    /**
     * Deletes the sent message of given user with given message ID from this user's outbox
     * @param order ID of sent message want to delete
     * @param currUsername user who wants to delete sent message
     * @return true if deleted, false if message doesn't not exist
     */
    //true if operated false can't
    protected boolean deleteSentMessage(Integer order, String currUsername){
        if(!senderMap.containsKey(currUsername)){
            return false;
        }
        if(senderMap.get(currUsername) == null || senderMap.get(currUsername).isEmpty()){
            return false;
        }
        List<Message> messageList = new ArrayList<>();
        for(Message message: senderMap.get(currUsername)){
            if(!message.getOrder().equals(order)){
                messageList.add(message);
            }
        }
        if(messageList.size() == senderMap.get(currUsername).size()) {
            return false;
        }else{
            senderMap.put(currUsername, messageList);
            return true;

        }
    }

    /**
     * Gets the receiver version toString of received message with given message ID in user's mailbox.
     * @param order ID of received message want to view
     * @param currUsername user who wants to view received message
     * @return receiver version toString of message with given ID in given user's mailbox, null of can't find
     */
    protected String getGivenOrderReceivedMessage(Integer order, String currUsername){
        if(!receiverMap.containsKey(currUsername)){
            return null;
        }
        if(receiverMap.get(currUsername).isEmpty() || receiverMap.get(currUsername) == null){
            return null;
        }
        for(Message message: receiverMap.get(currUsername)){
            if(message.getOrder().equals(order)){
                return message.toStringReceived();
            }
        }
        return null;
    }

    /**
     * Gets the sender version toString of sent message with given message ID in user's outbox.
     * @param order ID of sent message want to view
     * @param currUsername user who wants to view sent message
     * @return sender version toString of message with given ID in given user's outbox, null of can't find
     */
    protected String getGivenOrderSentMessage(Integer order, String currUsername){
        if(!senderMap.containsKey(currUsername)){
            return null;
        }
        if(senderMap.get(currUsername) == null || senderMap.get(currUsername).isEmpty()){
            return null;
        }
        for(Message message: senderMap.get(currUsername)){
            if(message.getOrder().equals(order)){
                return message.toString();
            }
        }
        return null;
    }

    /**
     * Deletes all copies of message with given message ID in any user's mailbox and outbox
     * @param order the message ID of message want to delete
     * @return true if deleted, false if does not exist
     */
    protected boolean adminDeleteMessage(Integer order){
        Integer i = 0;
        for(String username: senderMap.keySet()){
            List<Message> newList = new ArrayList<>();
            if(senderMap.get(username) != null && !senderMap.get(username).isEmpty()){
                for(Message message: senderMap.get(username)){
                    if(!message.getOrder().equals(order)){
                        newList.add(message);
                    }
                }
                if(!(newList.size() == senderMap.get(username).size())){
                    senderMap.put(username, newList);
                    i++;
                }
            }

        }
        for(String username: receiverMap.keySet()){
            List<Message> newList = new ArrayList<>();
            if(receiverMap.get(username) != null && !receiverMap.isEmpty()){
                for(Message m: receiverMap.get(username)){
                    if(!m.getOrder().equals(order)){
                        newList.add(m);
                    }
                }
                if(!(newList.size() == receiverMap.get(username).size())){
                    receiverMap.put(username, newList);
                    i++;
                }
            }
        }
        if(i > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Gets receiver version toString of all unread message of given user
     * @param username username of given user
     * @return list of receiver version toString of all unread message of given user
     */
    protected List<String> getUnreadMessage(String username){
        List<Message> messageList = new ArrayList<>();
        if(receiverMap.get(username) == null || receiverMap.get(username).isEmpty()){
            return null;
        }
        for(Message message: receiverMap.get(username)){
            if(!message.getRead()){
                messageList.add(message);
            }
        }
        List<Message> sorted = sortMessages(messageList);
        return getReceivedToString(sorted);

    }

}

