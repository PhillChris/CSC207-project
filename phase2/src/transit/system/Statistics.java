package transit.system;

/** An abstract statistic to be stored in the system */
public interface Statistics<T> {
  /** The maximum number of days for information to be stored in the system */
  public static final int STORAGELIMIT = 365;

  void update(T data);
}
