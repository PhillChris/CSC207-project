public interface Station {
  String getName();
  String getRoute();
  double getInitialFee();
  double getFinalFee(Station initialStation);
  Station getAssociatedStation();
  void associate(Station associatedStation);
}
