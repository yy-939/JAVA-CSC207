package authentication;

/**
 * An interface provides the validator for username and password
 */
interface Validator {
    boolean validate(String str);

    /**
     * Gets the description
     * @return a string represents the description
     */
    String getDescription();
}
