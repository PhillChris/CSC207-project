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
    try {
      TransitReadWrite.init(reader, writer);
      TransitReadWrite.run(reader);
    } catch (InitLineException init) {
      writer.write(init.getMessage());
    }
    writer.close();
  }
}
