package Input;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class provides a way to get user input and transform the user input in a format that we need them to be.
 * @author Group0065
 * @version 1.0.0
 */
public class UserInput implements InputStrategy {
    private Scanner sc;
    private InputPresenter presenter;

    /**
     * create a new scanner
     */
    public UserInput(){
        this.sc = new Scanner(System.in);
        this.presenter = new InputPresenter();
    }

    /**
     * read the nextline and checks if it is an empty string, if not returns it
     * @return the result from reading the next line
     */
    @Override
    public String inputString() {
            String result = sc.nextLine();
            if (result.matches("[ ]*")) {
                return null;
            }else{
                return result;

        }
    }

    @Override
    /**
     * Gets input from user and check if it is a valid number, if it is return it, if not ask user to
     * enter until user enter a valid number or user enter some spaces to leave
     * @return the time input from the user， or null if user leaves.
     */
    public Integer inputInteger() {
        int numTime = -1;
        while(numTime < 0) {
            String time = sc.nextLine();
            if(time.matches("[ ]*")){
                return null;
            }
            try {
                numTime = Integer.parseInt(time);
                if(numTime < 0){
                    presenter.invalidValue();
                }
            } catch (NumberFormatException e) {
                presenter.invalidInteger();
            }
        }
        return numTime;
    }

    //private helper
    private boolean isWrongFormat(String myTime) {
        try {
            Timestamp.valueOf(myTime);
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    @Override
    /**
     * Reads from user input and check if the input is a valid time format, if it is, return it, if not
     * ask user to enter again until gets a valid time,or user enter some spaces to exit.
     * @return timestamp gets from user, or null if user entered space to leave
     */
    public Timestamp inputTime() {
        String time = sc.nextLine();
        while (!time.matches("[ ]*") && isWrongFormat(time + ":00.0")) {
            presenter.wrongTimeFormat();
            time = sc.nextLine();
        }
        if(time.matches("[ ]*")){
            return null;
        }
        return Timestamp.valueOf(time + ":00.0");
    }

    @Override
    /**
     * Reads from input to change it into date format, if the input is not valid to do so, then ask user to
     * enter a valid input until user enters a valid input or enter some spaces to leave
     * @return a list of two timestamp which has a complete whole day between them
     */
    public List<Timestamp> inputDate() {
        String date = sc.nextLine();
        int day = 86400000;
        while(!date.matches("[ ]*")){
        if(isWrongFormat(date + " 00:00:00.0")) {
            presenter.wrongDateFormat();
            date = sc.nextLine();
        } else {
            Timestamp time1 = Timestamp.valueOf(date + " 00:00:00.0");
            Timestamp time2 = new Timestamp(time1.getTime() + day);
            List<Timestamp> timeList = new ArrayList<>();
            timeList.add(time1);
            timeList.add(time2);
            return timeList;
        }
        }
        return null;
    }


}

