import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/** A common interface for all parsers*/
public abstract class ObjectParser {
  /** A method which adds some given object to the transit system */
  abstract void add(List<String> info);
  /** A method which removes some given object from the transit system */
  abstract void remove(List<String> info);
  /** A methods which prints some kind of status report to outputs.txt */
  abstract void report(List<String> info);
  /** The writer for this ObjectParser */
  private BufferedWriter writer;
  /** The hashmap of commands for this ObjectParser*/
  static HashMap<String, Function<List<String>, Void>> keyWords = new HashMap<>();

  public ObjectParser(BufferedWriter writer) {
    this.writer = writer;
  }

  public void parse(List<String> tempLineWords) {
    if (keyWords.get(tempLineWords.get(0)) == null) {
      TransitReadWrite.write("Invalid command: This command does not exist");
    } else if (tempLineWords.size() > 1) {
      // executes the command which the given keyword
      // maps to by passing the appropriate parameters
      keyWords
          .get(tempLineWords.get(0))
          .apply(tempLineWords.subList(1, tempLineWords.size()));
    } else {
      // executes a parameterless function with an empty ArrayList for hash map type consistency
      keyWords.get(tempLineWords.get(0)).apply(new ArrayList<>());
    }
  }

  static void checkInput (List<String> input, int expected) throws InvalidInputException{
    if (input.size() != expected) {
      throw new InvalidInputException();
    }
  }
}
