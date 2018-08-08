package transit.system;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/** Exception that gets thrown when a user enters an email of an invalid format*/
public class InvalidEmailException extends MessageTransitException {
  /**
   * Updates the error label to tell the user that the given email is in an invalid format.
   * @param errorMessage The error label being updated.
   */
  public void setMessage(Label errorMessage) {
    errorMessage.setTextFill(Color.web("red"));
    errorMessage.setText("Invalid email");
  }
}
