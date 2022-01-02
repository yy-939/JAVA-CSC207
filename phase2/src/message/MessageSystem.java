package message;


import Input.UserInput;

import java.util.List;


/**
 * A controller class of message.
 * Stores MessagingManager class and MessagePresenter class.
 * Calls the methods of MessagingManager by the input from user and calls the methods in MessagePresenter to
 * give feedback to user.
 * @author Group0065
 * @version 1.0.1
 */
public class MessageSystem {
    private MessagingManager messages;
    private MessagePresenter presenter;
    private UserInput sc = new UserInput();


    /**
     * creates the message system with the specific messaging manager and the specific presenter.
     * @param messages the messagingmanager represents the messages' use case
     */
    public MessageSystem(MessagingManager messages) {
        this.messages = messages;
        presenter = new MessagePresenter();
    }

    /**
     * returns the manager that has all messages in it
     * @return the messageManager
     */
    public MessagingManager getMessages() {
        return messages;
    }


    //get all messages send from a user

    /**
     * Prints out all toString description of messages sent from a user with given username.
     * @param username username of the user we want to check for all sent messages
     */
    public void getMassageSent(String username) {
        presenter.printAllSentMessage(messages.getSentMessages(username));
    }

    //get all messages received by a user

    /**
     * Prints out all toString description of messages received by a user with given username.
     * @param username username of the user we want to check for all received messages
     */
    public void getMassageReceive(String username) {
        presenter.printAllReceivedMessage(messages.getReceivedMessages(username));
    }






    /**
     * Sends same message for all attendees or speakers from organizer whose user name given as parameter or
     * send to all attendees in events hosted by speaker.
     * The receiver doesn't need to be given by user input or parameter, can only send to all registered speakers.
     * The subject and content of WordMessage are both given by user input.
     * Gives prompt to user to enter subject and content step by step, and check if receivers are valid,
     * if not, will print out feedback to user.
     * After this message is sent successfully, there will be feedback printed out to user.
     * @param senderName username of the sender
     */
    public void sendMessageToList (String senderName, List<String> receiverUsernames) {
        if (receiverUsernames == null || receiverUsernames.isEmpty()){
            presenter.noReceivers();
            return;
        }
        presenter.askSubject();
        String subject = sc.inputString();
        if(subject==null){
            return;
        }
        presenter.askContent();
        String content = sc.inputString();
        if(content==null){
            return;
        }
        messages.sendWordMessage(senderName, receiverUsernames, subject, content);
        presenter.printSendMessage(true);
    }



    /**
     * Receives the message ID from the user input and get the received message with given id, and print receiver
     * version toString of that message to user.
     * If no such received message appears, then print result to user.
     * @param username the username of current user
     */
    public void getReceivedMessageWithGivenOrder(String username){
        presenter.askOrder();
        Integer numOrder = sc.inputInteger();
        if(numOrder == null){
            return;
        }
        String message = messages.getGivenOrderReceivedMessage(numOrder, username);
        if(message == null || message.equals("")){
            presenter.invalidReceivedID(numOrder);
        }else{
            presenter.printGivenOrderReceivedMessage(message, numOrder);
        }
    }

    /**
     * Receives the message ID from user input and get the sent message with given ID, and print sender version
     * message toString of that message to user.
     * If no such sent message appears, then print the result to user.
     * @param username username of current user
     */
    public void getSentMessageWithGivenOrder(String username){
        presenter.askOrder();
        Integer numOrder = sc.inputInteger();
        if(numOrder == null){
            return;
        }
        String message = messages.getGivenOrderSentMessage(numOrder, username);
        if(message == null || message.equals("")){
            presenter.invalidSentID(numOrder);
        }else{
            presenter.printGivenOrderSentMessage(message, numOrder);
        }

    }

    /**
     * Receives the message ID from user input and delete the message received by current user with given ID from
     * this user's mailbox.
     * Prints out result to user.
     * @param username username of current user
     */
    public void deleteReceivedMessage(String username){
        presenter.askOrder();
        Integer numOrder = sc.inputInteger();
        if(numOrder == null){
            return;
        }
        if(messages.deleteReceivedMessage(numOrder, username)){
            presenter.deletedSuccessfully();
        }else{
            presenter.invalidReceivedID(numOrder);
        }
    }

    /**
     * Receives the message ID from user input and deletes the sent message from user with given ID from user's
     * outbox.
     * Prints out the result to user.
     * @param username username of current user
     */
    public void deleteSentMessage(String username){
        presenter.askOrder();
        Integer numOrder = sc.inputInteger();
        if(numOrder == null){
            return;
        }
        if(messages.deleteSentMessage(numOrder, username)){
            presenter.deletedSuccessfully();
        }else{
            presenter.invalidSentID(numOrder);
        }
    }

    /**
     * Receives the message ID from user input, and mark the received message of current user with given ID
     * as unread.
     * Print out the result to user.
     * @param username username of current user
     */
    public void markAsUnread(String username){
        presenter.askOrder();
        Integer numOrder = sc.inputInteger();
        if(numOrder == null){
            return;
        }
        if(messages.markAsUnread(numOrder, username)){
            presenter.markAsUnread();
        }else{
            presenter.invalidReceivedID(numOrder);
        }
    }

    /**
     * Deletes all copies of message with given ID in any user's mailbox and outbox.
     * Can only use by admin user.
     */
    public void adminDeleteMessage(){
        presenter.askOrder();
        Integer numOrder = sc.inputInteger();
        if(numOrder == null){
            return;
        }
        if(messages.adminDeleteMessage(numOrder)){
            presenter.deletedSuccessfully();
        }else{
            presenter.invalidID(numOrder);
        }
    }

    /**
     * Prints out receiver version toString of any unread message received by current user.
     * @param username username of current user
     */
    public void viewUnreadMessage(String username){
        List<String> messageList = messages.getUnreadMessage(username);
        if(messageList == null || messageList.isEmpty()){
            presenter.noUnreadMessage();
        }else{
            presenter.printUnreadMessage(messageList);
        }
    }





}
