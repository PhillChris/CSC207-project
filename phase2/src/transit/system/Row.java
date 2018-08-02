package transit.system;

public class Row {
  protected String name;
  protected int tapsIn;
  protected int tapsOut;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getTapsIn() {
    return tapsIn;
  }

  public void setTapsIn(int tapsIn) {
    this.tapsIn = tapsIn;
  }

  public int getTapsOut() {
    return tapsOut;
  }

  public void setTapsOut(int tapsOut) {
    this.tapsOut = tapsOut;
  }
}
