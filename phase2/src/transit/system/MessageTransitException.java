package transit.system;

import javafx.scene.control.Label;

public abstract class MessageTransitException extends TransitException {
  public abstract void setMessage(Label errorMessage);
}
