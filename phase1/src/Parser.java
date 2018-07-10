import java.util.List;

/** A common interface for all parsers*/
public interface Parser {
  /** A method which adds some given object to the transit system */
  void add(List<String> info);
  /** A methods which prints some kind of status report to outputs.txt */
  void report(List<String> info);
}
