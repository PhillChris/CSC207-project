package transit.system;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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
    try {
      handler = new FileHandler("log.txt", true);
    } catch (IOException a) {
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
   * @param classThrown the class in which this log is recorded
   * @param methodThrown the method in which this log is recorded
   */
  public void logWarningMessage(String classThrown, String methodThrown, String message) {
    this.logger.logp(Level.WARNING, classThrown, methodThrown, message);
  }

  /** Closes all active handlers for this LogWriter */
  public void closeHandlers() {
    for (Handler h : this.logger.getHandlers()) {
      h.close();
    }
  }
}
