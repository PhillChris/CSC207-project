package transit.system;

import transit.pages.Page;

import java.io.IOException;
import java.util.logging.*;

/** Handles all logging responsibilities in this system */
public class LogWriter {

  /** The LogWriter present in this TransitSystem, null if no LogWriters has yet been initialized */
  private static LogWriter thisLogWriter;
  /** The logger which writes to this LogWriter */
  private Logger logger;

  /**
   * Constructs a new LogWriter, only to be called if one has not already been initialized
   *
   * @throws IOException if log.txt is not found in the file path
   */
  private LogWriter() {
    this.logger = Logger.getLogger(LogWriter.class.getName());
    logger.setUseParentHandlers(false);
    Handler handler;
    // if you can't find the log file, write everything to console instead
    try {
      handler = new FileHandler("log.txt", true);
    } catch (IOException a) {
      System.out.println("Console used as log, as log.txt was not found");
      handler = new ConsoleHandler();
    }
    handler.setFormatter(new SimpleFormatter());
    this.logger.addHandler(handler);
    thisLogWriter = this;
  }

  /**
   * @return a LogWriter if one has been initialized, or creates a new one if none exists
   * @throws IOException if log.txt is not found in the file path
   */
  public static LogWriter getLogWriter() {
    if (thisLogWriter != null) {
      return thisLogWriter;
    }
    return new LogWriter();
  }

  /**
   * Logs a message with level INFO to log.txt, with the given message at the given location
   *
   * @param message the message to be recorded
   * @param classThrown the class in which this log is recorded
   * @param methodThrown the method in which this log is recorded
   */
  public void logInfoMessage(String classThrown, String methodThrown, String message) {
    this.logger.logp(Level.INFO, classThrown, methodThrown, message);
  }

  /**
   * Logs a message with level WARNING to log.txt, with the given message at the given location
   *
   * @param message the message to be recorded
   * @param classLocation the class in which this log is recorded
   * @param methodLocation the method in which this log is recorded
   */
  public void logWarningMessage(String classLocation, String methodLocation, String message) {
    this.logger.logp(Level.WARNING, classLocation, methodLocation, message);
  }

  /**
   * Logs the addition of balance to card
   *
   * @param toAdd the balance added to a card
   * @param id the id of the card added
   */
  public void logAddBalance(int toAdd, int id) {
    LogWriter.getLogWriter()
        .logInfoMessage(
            Card.class.getName(),
            "addBalance",
            "Added $" + String.format("%.2f", toAdd / 100.0) + " to card #" + id);
  }

  /** Logs the ending of the program */
  public void logEndProgram() {
    LogWriter.getLogWriter()
        .logInfoMessage(Page.class.getName(), "Page", "Program session terminated");
    LogWriter.getLogWriter().closeHandlers();
  }

  /** @param username The username of the user removed by the program */
  public void logRemoveUser(String username) {
    LogWriter.getLogWriter()
        .logInfoMessage(
            User.class.getName(),
            "removeUser",
            "Removed user " + username + " from transit system");
  }

  /** @param id The id of the card being removed */
  public void logRemoveCard(int id) {
    LogWriter.getLogWriter()
        .logInfoMessage(User.class.getName(), "removeCard", "User removed card #" + id);
  }

  /** Log the addition of a new card */
  public void logAddCard() {
    LogWriter.getLogWriter()
        .logInfoMessage(
            User.class.getName(), "addCard", "User added new card with default balance");
  }

  /**
   * Logs a Tap In
   *
   * @param user User that tapped in
   * @param station Station tapped at
   * @param cardId Id of card use
   */
  public void logTapIn(String user, String station, int cardId) {
    LogWriter.getLogWriter()
        .logInfoMessage(
            User.class.getName(),
            "tapIn",
            "User "
                + user
                + " tapped in at station "
                + station
                + " with card #"
                + cardId);
  }

  /**
   * @param station Station tapped at
   * @param cardId Id of card use
   */
  public void logInvalidTrip(String user, String station, int cardId) {
    LogWriter.getLogWriter()
        .logWarningMessage(
            User.class.getName(),
            "tapOut",
            "User "
                + user
                + " tapped out improperly at station "
                + station
                + " with card #"
                + cardId
                + ", charged maximum possible fee.");
  }

  /**
   *
   */
  public void logTapOut(String user, String station, int cardId, int tripFee){
    LogWriter.getLogWriter()
            .logInfoMessage(
                    User.class.getName(),
                    "tapOut",
                    "User "
                            + user
                            + " tapped out at station "
                            + station
                            + " with card #"
                            + cardId
                            + ", charged $"
                            + String.format("%.2f", tripFee / 100.0));
  }

  /** Closes all active handlers for this LogWriter */
  public void closeHandlers() {
    for (Handler h : this.logger.getHandlers()) {
      h.close();
    }
  }
}
