package data;

import account.AccountManager;
import authentication.ConferenceRegisterSystem;
import conferencemain.MainPresenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;


/**
 * This is a class to convert txt file to account manager,that txt file contains the default account type, username
 * and password. And reform the given information as a ser file.
 * @author Group0065
 * @version 1.0.0
 */
public class UserDataConverter {

    private MainPresenter wp = new MainPresenter();

    /**
     * Creates an new account manager class and add the user from information from the txt file at given path.
     * @param path the path of txt file
     * @return an account manager class with all users with information in txt file stored in it
     */
    private AccountManager readFromFile(String path) {
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String currLine = br.readLine();
            String accountType, username, password;
            AccountManager am = new AccountManager();
            ConferenceRegisterSystem crs = new ConferenceRegisterSystem();
            while (currLine != null) {
                if (!currLine.startsWith("#")) {
                    accountType = currLine;
                    username = br.readLine();
                    password = br.readLine();
                    br.readLine();
                    crs.createAccountWithData(accountType, username, password, am);
                }
                currLine = br.readLine();
            }
            fr.close();
            br.close();
            return am;
        } catch (IOException e) {
            wp.printErrorMessage("fail to read from " + path);
            return new AccountManager();
        }
    }

    /**
     * Converts txt data into accountManager and store the whole class in ser file at the new path
     * @param prePath the path of txt file
     * @param currPath the path new ser file is going to save at
     * @param ds dataSaver class, used to make system
     */
    public void convertData(String prePath, String currPath, DataSaver<AccountManager> ds) {
        ds.saveToFile(currPath, readFromFile(prePath));
        // AccountManager am = readFromFile(prePath);
        // System.out.println(am.getAllAccounts());
    }
}
