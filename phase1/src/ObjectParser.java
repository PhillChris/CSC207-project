import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/** A common interface for all parsers */
public abstract class ObjectParser {
  /** The hashmap of commands for this ObjectParser */
  static HashMap<String, Function<List<String>, Void>> keyWords = new HashMap<>();
  /** The writer for this ObjectParser */
  private BufferedWriter writer;
  public ObjectParser(BufferedWriter writer) {
    this.writer = writer;
    this.keyWords.put("ENDDAY", (emptyArray) -> {
      TransitTime.endDay(emptyArray);
      return null;
    });
  }

  void write(String message) {
    try {
      writer.write(message + System.lineSeparator());
    } catch (IOException e) {
      System.out.println("File not found, create an events.txt and rerun the program");
    }
  }

  static void checkInput(List<String> input, int expected) throws InvalidInputException {
    if (input.size() != expected) {
      throw new InvalidInputException();
    }
  }

  /** A method which adds some given object to the transit system */
  abstract void add(List<String> info);

  /** A method which removes some given object from the transit system */
  abstract void remove(List<String> info);

  /** A methods which prints some kind of status report to outputs.txt */
  abstract void report(List<String> info);

  public void parse(List<String> tempLineWords) {
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

  public HashMap<String, Function<List<String>, Void>> getKeyWords() {
    return this.keyWords;
  }
}
