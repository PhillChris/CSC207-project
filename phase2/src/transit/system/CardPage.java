package transit.system;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CardPage extends AuthenticatedPage {
  public CardPage(Stage primaryStage, User user) {
    super(primaryStage, user);
    makeScene(primaryStage);
  }

  protected void makeScene(Stage primaryStage) {
    placeButton(
        "Add card",
        () -> {
          user.addCard();
          primaryStage.setScene(new CardPage(primaryStage, this.user).getScene());
        },
        0,
        0);
    TextField removeCardNumber = placeTextField(1, 1);
    placeButton(
        "Remove card",
        () -> {
          makeConfirmationAlert(
              "Removal confirmation",
              "Confirm removal:",
              "Are you sure that you want to remove this card?",
              () -> {
                try {
                  user.removeCard(
                      user.getCardsCopy().get(Integer.parseInt(removeCardNumber.getText())));
                  primaryStage.setScene(new CardPage(primaryStage, this.user).getScene());
                } catch (NumberFormatException a) {
                  Alert alert =
                      makeAlert(
                          "Invalid number",
                          "",
                          "The provided argument is not a valid number",
                          AlertType.ERROR);
                  alert.showAndWait();
                }
              });
        },
        0,
        1);
    placeButton(
        "Go Back",
        () -> primaryStage.setScene(new UserPage(primaryStage, this.user).getScene()),
        0,
        2);

    // addClock();
    this.scene = new Scene(grid, 300, 250);
  }

  protected void addUserData(Stage primaryStage) {
    int i = 0;
    for (Integer id : this.user.getCardsCopy().keySet()) {
      placeButton(
          "Card #" + user.getCardsCopy().get(id).getId(),
          () -> {
            Stage secondaryStage = new Stage();
            secondaryStage.setTitle("Tap Card#" + user.getCardsCopy().get(id).getId());
            secondaryStage.setScene(new TapPage(secondaryStage, user.getCardsCopy().get(id)).getScene());
            secondaryStage.show();
          },
          0,
          3 + i);

      placeButton(
          "Add funds",
          () ->
            primaryStage.setScene(
                new AddFundsPage(primaryStage, user, user.getCardsCopy().get(id)).getScene()),
          1,
          3 + i);

      // if the current card is suspended
      if (!user.getCardsCopy().get(id).isSuspended()) {
        placeButton(
            "Report card stolen",
            () -> {
              user.getCardsCopy().get(id).suspendCard();
              primaryStage.setScene(new CardPage(primaryStage, this.user).getScene());
            },
            2,
            3 + i);
      } else {
        placeButton(
            "Activate this card",
            () -> {
              user.getCardsCopy().get(id).activateCard();
              primaryStage.setScene(new CardPage(primaryStage, this.user).getScene());
            },
            1,
            3 + i);
      }
      i++;
    }
  }
}
