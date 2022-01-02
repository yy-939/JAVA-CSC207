package data;
import account.AccountManager;
import conferencemain.WelcomePresenter;
import event.EventManager;
import event.EventSystem;
import message.MessagingManager;
import message.MessageSystem;
import room.RoomManager;
import room.RoomSystem;


/**
 * This class is a class for reading data from files and construct corresponding user cases and lower controller from
 * the information in file.
 * @author Group0065
 * @version 1.0.1
 */
public class DataReader{
    /**
     * Reads the information of account.
     * @throws ClassNotFoundException if account does not exist
     * @return AccountManager stored in ser file
     */
    public AccountManager readAccount() throws ClassNotFoundException {
        DataSaver<AccountManager> readAccount = new DataSaver<>();
        AccountManager accountManager = readAccount.readFromFile(readAccount.getSrcPath("UserInfoDataBase.ser"));
        if (accountManager == null) {
            UserDataConverter converter = new UserDataConverter();
            converter.convertData(readAccount.getSrcPath("UserTxtDataBase.txt"),
                    readAccount.getSrcPath("UserInfoDataBase.ser"), readAccount);
            accountManager = readAccount.readFromFile(readAccount.getSrcPath("UserInfoDataBase.ser"));
        }
        return accountManager;
    }

    /**
     * Reads the information of message.
     * @throws ClassNotFoundException if message does not exist
     * @return MessagingManager stored in ser file
     */
    public MessagingManager readMessage() throws ClassNotFoundException {
        DataSaver<MessagingManager> readMessage = new DataSaver<>();
        MessagingManager messagingManager = readMessage.readFromFile(readMessage.getSrcPath("MessageDataBase.ser"));
        if (messagingManager == null) messagingManager = new MessagingManager();
        return messagingManager;
    }

    /**
     * Reads the information of room.
     * @throws ClassNotFoundException if room does not exist
     * @return RoomManager stored in ser file
     */
    public RoomManager readRoom() throws ClassNotFoundException {
        DataSaver<RoomManager> readRoom = new DataSaver<>();
        RoomManager roomManager = readRoom.readFromFile(readRoom.getSrcPath("RoomDataBase.ser"));
        if (roomManager == null) roomManager = new RoomManager();
        return roomManager;
    }

    /**
     * Reads the information of event.
     * @throws ClassNotFoundException if event does not exist
     * @return EventManager stored in ser file
     */
    public EventManager readEvent() throws ClassNotFoundException {
        DataSaver<EventManager> readEvent = new DataSaver<>();
        EventManager eventManager = readEvent.readFromFile(readEvent.getSrcPath("EventDataBase.ser"));
        if (eventManager == null) eventManager = new EventManager();
        return eventManager;
    }

    /**
     * Creates event system with manager read from file
     * @return EventSystem with EventManager stored in file
     */
    public EventSystem createEventSystem() {
        try {
            EventManager eventManager = this.readEvent();
            return new EventSystem(eventManager);
        } catch (ClassNotFoundException cNFE) {
            System.out.println("Unable to read stored events from file.");
            return null;
        }
    }

    /**
     * Creates message system with manager read from file
     * @return MessageSystem with MessagingManager stored in ser file
     */
    public MessageSystem createMessageSystem(){
        try{
            MessagingManager messagingManager = this.readMessage();
            return new MessageSystem(messagingManager);
        }catch(ClassNotFoundException cNFE){
            System.out.println("Unable to read stored messages from file.");
            return null;
        }
    }

    /**
     * Creates room system with manager read from file
     * @return RoomSystem with RoomManager stored in ser file
     */
    public RoomSystem createRoomSystem(){
        try{
            RoomManager roomManager = this.readRoom();
            return new RoomSystem(roomManager);
        }catch(ClassNotFoundException cNFE){
            System.out.println("Unable to read stored rooms from file.");
            return null;
        }
    }








}
