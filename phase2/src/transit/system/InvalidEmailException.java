package transit.system;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class InvalidEmailException extends MessageTransitException {
  public void setMessage(Label errorMessage) {
    errorMessage.setTextFill(Color.web("red"));
    errorMessage.setText("Invalid email");
  }
}
