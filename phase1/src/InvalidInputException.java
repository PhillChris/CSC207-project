/** Exception thrown when the parameters provided to a command in events.txt are incorrect */
public class InvalidInputException extends TransitException {
  public String getMessage() {
    return "Invalid Input: The parameters provided to this function are not correct";
  }
}
