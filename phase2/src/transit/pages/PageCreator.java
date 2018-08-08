package transit.pages;

import transit.system.*;

import java.util.HashMap;

import static transit.system.Main.primaryStage;
import static transit.system.Main.secondaryStage;

/** A factory to produce new pages */
public class PageCreator {

  /**
   * Makes a new AddFundsPage
   *
   * @param card The card used by this page
   */
  void makeAddFundsPage(Card card) {
    new AddFundsPage(secondaryStage, card);
  }

  /**
   * Makes a new AdminUserPage
   *
   * @param adminUser The user associated with this page
   */
  void makeAdminUserPage(User adminUser) {
    new AdminUserPage(primaryStage, adminUser);
  }

  /**
   * Makes a new AnalyticsPage
   *
   * @param statistics The statistics associated with this page
   */
  void makeAnalyticsPage(HashMap<String, Statistics> statistics) {
    new AnalyticsPage(secondaryStage, statistics);
  }

  /**
   * Makes a new AppendRoutePage
   *
   * @param route The route associated with this page
   */
  void makeAppendRoutePage(Route route) {
    new AppendRoutePage(secondaryStage, route);
  }

  /**
   * Makes a new Card Page
   *
   * @param cards The card commands associated with this page
   */
  void makeCardPage(UserCardCommands cards) {
    new CardPage(secondaryStage, cards);
  }

  /**
   * Makes a new ChangeNamePage
   *
   * @param user The user associated with this page
   */
  void makeChangeNamePage(User user) {
    new ChangeNamePage(secondaryStage, user);
  }

  /**
   * Makes a new ChangePasswordPage
   *
   * @param user The user associated with this page
   */
  void makeChangePasswordPage(User user) {
    new ChangePasswordPage(secondaryStage, user);
  }

  /** Makes a new LoginPage */
  public void makeLoginPage() {
    new LoginPage(primaryStage);
  }

  /** Makes a RouteCreationPage */
  void makeRouteCreationPage() {
    new RouteCreationPage(secondaryStage);
  }

  /** Makes a SignUpPage */
  void makeSignUpPage() {
    new SignUpPage(primaryStage);
  }

  /**
   * Makes a new TapPage
   *
   * @param cards The card commands associated with this page
   * @param card The card associated with this page
   */
  void makeTapPage(UserCardCommands cards, Card card) {
    new TapPage(secondaryStage, cards, card);
  }

  /**
   * Makes a new UserPage
   *
   * @param user The user associated with this page
   */
  void makeUserPage(User user) {
    new UserPage(primaryStage, user);
  }

  /**
   * Makes a new AnalyticsPage
   *
   * @param user Makes a new UserAnalyticsPage
   */
  void makeUserAnalyticsPage(User user) {
    new UserAnalyticsPage(secondaryStage, user);
  }

  /** Makes a new StationGraphPage */
  void makeStationGraphPage() {
    new StationGraphPage(secondaryStage);
  }
}
