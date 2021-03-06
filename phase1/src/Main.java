import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/** A transit system simulation. */
public class Main {
  /**
   * Runs the transit system simulation, with event input from events.txt
   *
   * @param args standard Java argument
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader("events.txt"));
    BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
    TransitReader fileInterpreter = new TransitReader(reader, writer);
    try {
      fileInterpreter.init();
      fileInterpreter.run();
    } catch (InitLineException init) {
      writer.write(init.getMessage());
    }
    writer.close();
  }
}
