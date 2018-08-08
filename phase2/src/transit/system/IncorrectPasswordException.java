package transit.system;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/** Exception that gets thrown when a user enters an incorrect password*/
public class IncorrectPasswordException extends MessageTransitException{
  /**
   * Updates the error label to tell the user that the given password is incorrect.
   * @param errorMessage The error label being updated
   */
  public void setMessage(Label errorMessage) {
      errorMessage.setTextFill(Color.web("red"));
      errorMessage.setText("Incorrect password");
  }
}
