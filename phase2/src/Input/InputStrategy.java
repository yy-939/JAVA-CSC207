package Input;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

/**
 * this is an interface represent the use of strategy to read and react to inputs
 * @author Group0065
 * @version 1.0.0
 */
public interface InputStrategy {

    /**
     * the method will be implemented, will be called when to read input of string
     * @return the input if legitimate, or null, if not
     */
    String inputString();

    /**
     * the method will be implemented, and called when reading input of string and transform it to integer
     * @return integer get from user input.
     */
    Integer inputInteger();

    /**
     * the method will be implemented in UserInput class, will be called when to read input of time
     * @return the input of time after converted to proper format for further use. or null, if not.
     */
    Timestamp inputTime();

    /**
     * the method will be implemented in UserInput class, will be called when to read input of Date
     * @return null if the the input is not acceptable as date. Otherwise, convert the input into a proper format for the
     * further use
     */
    List<Timestamp> inputDate();

}
