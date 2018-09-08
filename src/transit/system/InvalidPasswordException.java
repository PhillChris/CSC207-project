package transit.system;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/** Exception that gets thrown when a user enters an invalid password (less than 6 characters) */
public class InvalidPasswordException extends MessageTransitException {
  /**
   * Updates the error label to tell the user that the given pass is invalid (less than 6 characters).
   * @param errorMessage The error label being updated.
   */
  public void setMessage(Label errorMessage) {
    errorMessage.setTextFill(Color.web("red"));
    errorMessage.setText("Invalid password");
  }
}
