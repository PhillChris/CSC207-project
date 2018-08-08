package transit.system;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/** Exception that gets thrown when a user tries to sign up with an email that already has an associated account */

public class EmailInUseException extends MessageTransitException {
  /**
   * Updates the error label to tell the user that the given email already has an associated user.
   * @param errorMessage The error label being updated
   */
  public void setMessage(Label errorMessage) {
    errorMessage.setTextFill(Color.web("red"));
    errorMessage.setText("Email in use");
  }
}
