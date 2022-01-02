package conferencemain;

import account.AccountManager;
import account.AccountSystem;
import conferencemain.WelcomePresenter;
import data.DataReader;
import event.Event;
import room.RoomPresenter;
import userinterface.UserSystem;
import userinterface.UserSystemFactory;
import authentication.ConferenceLoginSystem;
import event.EventSystem;
import message.MessageSystem;
import authentication.ConferenceRegisterSystem;
import room.RoomSystem;

import java.util.Scanner;

/**
 * An controller class represents the welcome system for users.
 * It stores MessageSystem, EventSystem, RoomSystem, AccountSystem and AccountManager.
 * It gets input from user and give corresponding moves by calling methods from MessageSystem,
 * EventSystem, RoomSystem, AccountSystem and AccountManager.
 * @author Group0065
 * @version 1.2.0
 */
public class WelcomeSystem {
    UserSystem userSystem;
    AccountSystem accountSystem;
    WelcomePresenter wp;

    /**
     * Creates an welcome system with specific account manager and message, event, room, and account systems.
     */
    public WelcomeSystem(){
        wp = new WelcomePresenter();
    }

    //helper for run. Used to test if class can be found
    private boolean getData(){
        try {
            DataReader dr = new DataReader();
            dr.readAccount();
            dr.readEvent();
            dr.readMessage();
            dr.readRoom();
            return true;
        } catch (ClassNotFoundException cnfe){
            return false;
        }
    }

    /**
     * The start of this whole conference system.
     */
    public void run(){
        if (this.getData()) {
            start();


        }
    }
    /**
     * Print strings reflecting the current system and directs the related options for users.
     * Check if the data are saved successfully with ending the program.
     * If user choose "0", the login option would be selected and enters related user system.
     * If user choose "1", the create new account option would be selected and enters related user system.
     * If user choose "q", the program will end. Under this case, checks if the data inputted by users are saved.
     * but quited the program.
     */
    public void start(){
        Scanner sc = new Scanner(System.in);
        String wMessage = "CSC207 Phase 2 Conference System";
        wp.printWelcomeMessage(wMessage);
        wp.printMenu();
        while (true){
            String input = sc.nextLine();
            switch (input){
                case "0":
                    wp.requestTo("login");
                    if(!this.login()) {
                        return;
                    }
                    break;
                case  "1":
                    wp.requestTo("create an account");
                    if(!this.register()){
                        return;
                    }
                    break;
                case "q":
                    wp.requestTo("end program");
                    aa:
                    for(;;){
                        wp.exit();
                        switch (sc.nextLine()){
                            case "y":
                                wp.printActionMessage("Exits conference system successfully.");
                                return;
                            case "n":
                                break aa;
                            default:
                                wp.printInvalidInput();
                        }
                    }
                    break;
                default:
                    wp.printInvalidInput();
                    wp.getInput();
                    continue;
            }
            wp.printWelcomeMessage(wMessage);
            wp.printMenu();
        }
    }

    // helper for login() and register()
    private boolean createUserSys(String username, String type){
    this.userSystem = new UserSystemFactory().makeUserSys(type, username);
    if(userSystem != null) {
        userSystem.selectOption(accountSystem);
        return true;
    }else{
    return false;
    }
    }

    // helper for start()
    private boolean login(){
        DataReader dr = new DataReader();
        ConferenceLoginSystem start = new ConferenceLoginSystem();
        try {
            AccountManager accountManager = dr.readAccount();
            accountSystem = new AccountSystem(accountManager);
            String[] accInfo = start.logIn(accountManager);
            if (accInfo != null) {
//            accountManager.setCurrentAccount(accInfo[0]);
                return this.createUserSys(accInfo[0], accInfo[1]);
            }
        }catch(ClassNotFoundException cNFE){
            wp.printErrorMessage("Class not found, unable to start the system.");
            return false;
        }
        return true;
    }


    // helper for start()
    private boolean register(){
        DataReader dr = new DataReader();
        ConferenceRegisterSystem register = new ConferenceRegisterSystem();
        String option = "Attendee";
        try {
            AccountManager accountManager = dr.readAccount();
            accountSystem = new AccountSystem(accountManager);
            String username = register.createAccount(option, accountManager);
            if (username != null) {
//            accountManager.setCurrentAccount(username);
                return this.createUserSys(username, option);
            }
        }catch(ClassNotFoundException cNFE){
            wp.printErrorMessage("Class not found, unable to start the system.");
            return false;
        }
        return true;

    }
}
