import java.util.List;

/** A common interface for all parsers*/
public abstract class ObjectParser {
  /** A method which adds some given object to the transit system */
  abstract void add(List<String> info);
  /** A method which removes some given object from the transit system */
  abstract void remove(List<String> info);
  /** A methods which prints some kind of status report to outputs.txt */
  abstract void report(List<String> info);
  static void checkInput (List<String> input, int expected) throws InvalidInputException{
    if (input.size() != expected) {
      throw new InvalidInputException();
    }
  }
}
