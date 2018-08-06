package transit.pages;

import javafx.stage.Stage;
import transit.system.*;

import java.util.HashMap;

/** A factory to produce new pages */
public class PageCreator {

    void makeAddFundsPage(Card card){ new AddFundsPage(Main.secondaryStage, card);}

    void makeAdminUserPage(User adminUser){new AdminUserPage(Main.primaryStage, adminUser);}

    void makeAnalyticsPage(HashMap<String, Statistics> statistics){
        new AnalyticsPage(Main.secondaryStage, statistics);}

    void makeAppendRoutePage(Route route){new AppendRoutePage(Main.secondaryStage, route);}

    void makeCardPage(UserCardCommands cards){new CardPage(Main.secondaryStage, cards);}

    void makeChangeNamePage(User user){new ChangeNamePage(Main.secondaryStage, user);}

    void makeChangePasswordPage(User user){new ChangeNamePage(Main.secondaryStage, user);}

    void makeLoginPage(){new LoginPage(Main.primaryStage);}

    void makeRouteCreationPage(){new RouteCreationPage(Main.secondaryStage);}

    void makeSignUpPage(){new SignUpPage(Main.primaryStage);}

    void makeTapPage(UserCardCommands cards, Card card, String selectedType){
        new TapPage(Main.secondaryStage, cards, card, selectedType);
    }

    void makeUserPage(User user){new UserPage(Main.primaryStage, user);}
}
