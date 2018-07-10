public class InvalidInputException extends TransitException {
  public String getMessage() {
    return "Invalid Input: The parameters provided to this function are not correct";
  }
}
