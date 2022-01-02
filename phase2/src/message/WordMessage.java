package message;

import message.Message;

import java.io.Serializable;
import java.util.List;

/**
 * An entity class for messaging feature.
 * A class extends abstract class Message.
 * Represents messages which have their subject and content in words. Hence this class is called WordMessage.
 * All the constructed messages are already sent by its sender.
 * For most of the attributes in this class there is no setter method because the sent message should not be changed.
 * @author Group0065
 * @version 1.0.0
 */
class WordMessage extends Message implements Serializable {
    private final String subject;
    private final String content;

    public WordMessage(String sender, List<String> receiver, String subject, String WordContent, Integer order) {
        super(sender, receiver, order);
        content = WordContent;
        this.subject = subject;
    }

    /**
     * Gets content.
     * @return the content of WordMessage
     */
    @Override
    protected String getContent() {
        return content;
    }

    /**
     * Gets subject.
     * @return the subject of WordMessage
     */
    @Override
    protected String getSubject() {
        return subject;
    }


    /**
     * Represents toString information of this message. It contains the name of sender and receivers, the subject and content.
     * Should be used when the sender checks the message information.
     * @return the string of information of the message.(Sender view of message)
     */
    @Override
    public String toString(){
        String receiverName = String.join(", ", getReceiverUsername());
        return "Sender: " + this.getSenderUsername() + "\n" +
                "Receiver: " + receiverName + "\n" +
                "Message ID: " + getOrder().toString() + "\n" +
                "Subject: " + subject + "\n" +
                "Content: " + content + "\n";
    }

    // Method for received message, we don't need all of the receivers

    /**
     * Represents toString information of this message. It contains the name of sender only, the subject and content.
     * Should be used when the checking user is one of the receivers, so this user doesn't need to know
     * all the receivers.
     * @return the string information of message without receiver.(Receiver view of message)
     */
    @Override
    public String toStringReceived() {
        return "Received from: " + this.getSenderUsername() + "\n" +
                "Message ID: " + getOrder().toString() + "\n" +
                "Subject: " + subject + "\n" +
                "Content: " + content + "\n";
    }


}