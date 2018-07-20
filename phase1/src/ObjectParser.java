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
  }

  /**
   * Checks if the given list of strings matches the expected number of parameters to a given
   * transit system command.
   *
   * @param input the list of parameters to be passed to a given function
   * @param expected the expected number of inputs to a given function
   * @throws InvalidInputException Thrown if invalid number of parameters in system command
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
  // abstract void add(List<String> info);
  // TODO: reinstate common interface for adding stuff?

  /**
   * Removes some given object from the transit system. To be implemented by all parsers in this
   * transit system.
   *
   * @param info The parameters needed to locate this object for removal
   */
  //abstract void remove(List<String> info);
  // TODO: reinstate common interface for removing stuff?

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
  HashMap<String, Function<List<String>, Void>> getKeyWords() {
    return this.keyWords;
  }

  /**
   * @param email The email of a User
   * @return The User with given email
   * @throws UserNotFoundException Thrown if no such User has given email
   */
  User findUser(String email) throws UserNotFoundException {
    if (User.getAllUsersCopy().containsKey(email)) {
      return User.getAllUsersCopy().get(email);
    } else {
      throw new UserNotFoundException();
    }
  }

  /**
   * @param email The email belong to a particular AdminUser
   * @return the AdminUser with the given email
   * @throws UserNotFoundException Thrown if no AdminUser has given email
   */
  AdminUser findAdminUser(String email) throws UserNotFoundException {
    User user = findUser(email);
    if (user instanceof AdminUser) {
      return (AdminUser) user;
    } else {
      throw new UserNotFoundException();
    }
  }

  /**
   * Retrieves this User's card with the given ID
   *
   * @param user The user whose card we are looking for
   * @param cardID The user-specific id of this card
   * @return The Card with the given ID
   * @throws CardNotFoundException Thrown if this User contains no card with given ID
   */
  Card findCard(User user, int cardID) throws CardNotFoundException {
    for (Card tempCard : user.getCardsCopy()) {
      if (tempCard.getId() == cardID) {
        return tempCard;
      }
    }
    throw new CardNotFoundException();
  }

  /**
   * Finds a specific station in this transit system
   *
   * @param stationType the type of the station to be found
   * @param stationName the name of the station to be found
   * @return the station to be found
   * @throws InvalidStationTypeException Thrown if station type does not exist
   */
  Station findStation(String stationType, String stationName) throws InvalidStationTypeException {
    switch (stationType) {
      case "BUS":
        return findBusStation(stationName);
      case "SUBWAY":
        return findSubwayStation(stationName);
      default:
        throw new InvalidStationTypeException();
    }
  }

  /**
   * Finds a specific bus station in this transit system.
   *
   * @param stationName the name of the bus station to be found
   * @return the bus station to be found
   */
  private Station findBusStation(String stationName) {
    BusFactory fact = new BusFactory();
    return fact.newStation("").getStationsCopy().get(stationName);
  }

  /**
   * Finds a specific subway station in this transit system.
   *
   * @param stationName the name of this subway station to be found
   * @return the subway station to be found
   */
  private Station findSubwayStation(String stationName) {
    SubwayFactory fact = new SubwayFactory();
    return fact.newStation("").getStationsCopy().get(stationName);
  }
}
