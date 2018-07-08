import java.io.*;

public class Main {

  /**
   * Runs the transit system simulation, with event input from events.txt
   *
   * @param args standard Java arguments
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader("events.txt"));
    BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
    try {
      TransitReader.read(reader, writer);
    } catch (InitLineException init) {
      writer.write(init.getMessage());
    }
    writer.close();
  }
}
