import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/** A parent class for all parsers of different object types */
public abstract class ObjectParser {
  /** The command hashmap for this ObjectParser, mapping command names to their given methods */
  static HashMap<String, Function<List<String>, Void>> keyWords = new HashMap<>();
  /** The file writer for this ObjectParser , writing the results of a give program to events.txt */
  private BufferedWriter writer;

  /**
   * Abstract constructor for ObjectParsers used only for inheritance.
   *
   * @param writer The file writer being used to write the results of this program to output.txt
   */
  public ObjectParser(BufferedWriter writer) {
    this.writer = writer;
    makeCommonHashMap();
  }

  /**
   * Checks if the given list of strings matches the expected number of parameters to a given
   * transit system command.
   *
   * @param input the list of parameters to be passed to a given function
   * @param expected the expected number of inputs to a given function
   * @throws InvalidInputException
   */
  void checkInput(List<String> input, int expected) throws InvalidInputException {
    if (input.size() != expected) {
      throw new InvalidInputException();
    }
  }

  /**
   * Writes a given message to the end of output.txt.
   *
   * @param message the message to be written to output.txt
   */
  void write(String message) {
    try {
      writer.write(message + System.lineSeparator());
    } catch (IOException e) {
      System.out.println("File not found, create an events.txt and rerun the program");
    }
  }

  /**
   * Adds some given object to the transit system. To be implemented by all parsers in this transit
   * system.
   *
   * @param info The parameters needed to generate this object
   */
  abstract void add(List<String> info);

  /**
   * Removes some given object from the transit system. To be implemented by all parsers in this
   * transit system.
   *
   * @param info The parameters needed to locate this object for removal
   */
  abstract void remove(List<String> info);

  /**
   * Generates some kind of status report and writes it to outputs.txt. To be implemented by all
   * parsers in this transit system.
   *
   * @param info The parameters needed to locate this object for reporting
   */
  abstract void report(List<String> info);

  /**
   * Parses a list containing a command and its parameters, and calls the appropriate function in
   * the program with the given parameters.
   *
   * @param tempLineWords The current line of the input file to be parsed and executed appropriately
   */
  void parse(List<String> tempLineWords) {
    if (keyWords.get(tempLineWords.get(0)) == null) {
      this.write("Invalid command: This command does not exist");
    } else if (tempLineWords.size() > 1) {
      // executes the command which the given keyword
      // maps to by passing the appropriate parameters
      keyWords.get(tempLineWords.get(0)).apply(tempLineWords.subList(1, tempLineWords.size()));
    } else {
      // executes a parameterless function with an empty ArrayList for hash map type consistency
      keyWords.get(tempLineWords.get(0)).apply(new ArrayList<>());
    }
  }

  /** @return this parser's hashmap sending command words to their given functions */
  public HashMap<String, Function<List<String>, Void>> getKeyWords() {
    return this.keyWords;
  }

  /**
   * A helper method adding the common elements across all parser types to the hash maps of any type
   * of parser in the transit system.
   */
  private void makeCommonHashMap() {
    this.keyWords.put(
        "ENDDAY",
        (emptyArray) -> {
          TransitTime.endDay(emptyArray);
          return null;
        });
  }
}
