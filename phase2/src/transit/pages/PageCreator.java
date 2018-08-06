package transit.pages;

import javafx.stage.Stage;
import transit.system.*;

import java.util.HashMap;

/** A factory to produce new pages */
public class PageCreator {
    private static Stage primaryStage;
    private static Stage secondaryStage;

    public static void setStages(Stage stage1, Stage stage2){
        primaryStage = stage1;
        secondaryStage = stage2;
    }

    void makeAddFundsPage(Stage primaryStage, Card card){ new AddFundsPage(primaryStage, card);}

    void makeAdminUserPage(Stage primaryStage, User adminUser){new AdminUserPage(primaryStage, adminUser);}

    void makeAnalyticsPage(HashMap<String, Statistics> statistics){new AnalyticsPage(statistics);}

    void makeAppendRoutePage(Route route){new AppendRoutePage(route);}

    void makeCardPage(Stage stage, UserCardCommands cards){new CardPage(stage, cards);}

    void makeChangeNamePage(Stage primaryStage, User user){new ChangeNamePage(primaryStage, user);}

    void makeChangePasswordPage(Stage primaryStage, User user){new ChangeNamePage(primaryStage, user);}

    void makeLoginPage(Stage primaryStage){new LoginPage(primaryStage);}

    void makeRouteCreationPage(Stage stage){new RouteCreationPage(stage);}

    void makeSignUpPage(Stage stage){new SignUpPage(stage);}

    void makeTapPage(Stage stage, UserCardCommands cards, Card card, String selectedType){
        new TapPage(stage, cards, card, selectedType);
    }

    void makeTimeControlPage(Stage stage){new TimeControlPage(stage);}

    void makeUserPage(Stage primaryStage, User user){new UserPage(primaryStage, user);}
}
