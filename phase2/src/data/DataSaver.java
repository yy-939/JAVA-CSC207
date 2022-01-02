package data;

import conferencemain.MainPresenter;
import java.io.*;

/**
 * A class which is used to save data and get data from file.
 * @param <T> lower controller classes of our conference system
 * @author Group0065
 * @version 1.0.2
 */
public class DataSaver<T> {

    private MainPresenter wp = new MainPresenter();

    /**
     * This method is used to read ser files from the given path
     * @param path path of ser file
     * @return the constructed class from ser file
     * @throws ClassNotFoundException throws when the class information is lost in our coding package
     */
    public T readFromFile(String path) throws ClassNotFoundException {

        // Use the example from class
        try {
            InputStream file = new FileInputStream(path); // String path should be "fileName.ser"
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the EventManager
            @SuppressWarnings("unchecked")
            T em = (T)input.readObject();
            input.close();
            return em;
        } catch (IOException ex) {
//            logger.log(Level.SEVERE, "Cannot read from input file, returning" +
//                    "a new StudentManager.", ex);
            wp.printErrorMessage("fail to read from " + path);
            return null;
        }
    }

    /**
     * Saves the class given to ser file with given path
     * @param filePath path of ser file
     * @param var class we want to store
     */
    public void saveToFile(String filePath, T var) {

        try {
            OutputStream file = new FileOutputStream(filePath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);

            // serialize the EventManager
            output.writeObject(var);
            output.close();
        } catch (IOException ex) {
            wp.printErrorMessage("fail to save to " + filePath);
        }
    }

    /**
     * Creates the full src path in the whole project to save the file
     * @param path the path of file in package but not the whole project
     * @return the full path of file
     */
    public String getSrcPath(String path) {
// return "phase1" + File.separator + "ConferenceSystem" + File.separator + "src"
// + File.separator + "data" + File.separator + path;
// Read and save everything outside phase 1 in case of changes latter we just need to change this method return path;
        return "phase2" + File.separator + "src" + File.separator + "data" + File.separator + path;
    }
}