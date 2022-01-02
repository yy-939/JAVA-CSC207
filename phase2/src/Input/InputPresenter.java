package Input;

import conferencemain.MainPresenter;

/**
 * This is a presenter class for all classes implements InputStrategy class.
 * @author Group0065
 * @version 1.0.0
 */
public class InputPresenter extends MainPresenter {
    /**
     * prints the error information that the input is not a valid number, and suggest correct form of input.
     * and ask to input again
     */
    void invalidInteger(){
        super.printErrorMessage("This is not a valid number, please enter a valid number.");
        super.getInput();
    }

    /**
     * prints the error message that the input is an invalid time, and suggest correct form of input
     * and ask to input again
     */
    void invalidTimeSlots() {
        super.printErrorMessage("Invalid time slots, please enter time in between 0-23 :");
        super.getInput();
    }

    /**
     * prints the error message that the input is invalid value (negative), and suggest correct form of input
     * and ask to input again
     */
    void invalidValue(){
        super.printErrorMessage("The input number has to be positive, please re-enter a valid number.");
        super.getInput();
    }

    /**
     * prints the error message that the input of time is invalid for some reason, and suggest correct form of input
     * and ask to input again
     */
    void wrongTimeFormat(){
        super.printErrorMessage("Time format invalid or timeslot invalid, please enter again, format 'yyyy-mm-dd hh:mm':");
        super.getInput();
    }
    /**
     * prints the error message that the input of time is invalid for format, and suggest correct form of input
     * and ask to input again
     */
    void wrongDateFormat(){ super.printErrorMessage("Date format invalid, format should be 'yyyy-mm-dd', please re-enter");
    super.getInput();}

    /**
     * Displays error message that the input from user is empty, and ask for another valid input from user.
     */
    void invalidInput(){
        super.printErrorMessage("Invalid Input, input can not be empty. Please re-enter");
        super.getInput();
    }

}
