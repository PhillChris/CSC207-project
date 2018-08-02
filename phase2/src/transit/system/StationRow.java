package transit.system;
import java.time.LocalDate;

/** Represents a row in the StationStatsPage table*/
public class StationRow extends Row {
  /** Makes a new instance of a StationRow*/
  public StationRow(Station station, LocalDate selectedDate) {
    this.name = station.toString();
    this.tapsIn = station.getTapsOn(selectedDate);
    this.tapsOut = station.getTapsOff(selectedDate);
  }
}
