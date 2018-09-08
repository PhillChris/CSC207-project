package transit.system;

import javafx.scene.control.Label;

/** Exception that gets thrown that will update a label to display a message*/
public abstract class MessageTransitException extends TransitException {
  /**
   * Updates the error label to tell the user about the error made
   * @param errorMessage The error label being updated
   */
  public abstract void setMessage(Label errorMessage);
}
