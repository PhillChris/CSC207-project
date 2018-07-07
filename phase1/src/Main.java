import java.io.FileWriter;
import java.io.IOException;

public class Main {

  /**
   * Runs the transit system simulation, with event input from events.txt
   *
   * @param args standard Java arguments
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    try {
      Parser.parse();
    } catch (InitLineException init) {
      Parser.write("Input line invalid, program not executable.");
    }
  }
}
