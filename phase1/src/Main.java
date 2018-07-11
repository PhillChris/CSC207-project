import java.io.*;

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
    TransitReadWrite fileInterpreter = new TransitReadWrite(reader, writer);
    try {
      fileInterpreter.init();
      fileInterpreter.run();
    } catch (InitLineException init) {
      writer.write(init.getMessage());
    }
    writer.close();
  }
}
