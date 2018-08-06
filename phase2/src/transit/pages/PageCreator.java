package transit.pages;

import transit.system.*;

import java.util.HashMap;

import static transit.system.Main.primaryStage;
import static transit.system.Main.secondaryStage;

/** A factory to produce new pages */
public class PageCreator {

    void makeAddFundsPage(Card card){ new AddFundsPage(secondaryStage, card);}

    void makeAdminUserPage(User adminUser){new AdminUserPage(primaryStage, adminUser);}

    void makeAnalyticsPage(HashMap<String, Statistics> statistics){new AnalyticsPage(secondaryStage, statistics);}

    void makeAppendRoutePage(Route route){new AppendRoutePage(secondaryStage, route);}

    void makeCardPage(UserCardCommands cards){new CardPage(secondaryStage, cards);}

    void makeChangeNamePage(User user){new ChangeNamePage(secondaryStage, user);}

    void makeChangePasswordPage(User user){new ChangeNamePage(secondaryStage, user);}

    void makeLoginPage(){new LoginPage(primaryStage);}

    void makeRouteCreationPage(){new RouteCreationPage(secondaryStage);}

    void makeSignUpPage(){new SignUpPage(primaryStage);}

    void makeTapPage(UserCardCommands cards, Card card, String selectedType){
        new TapPage(secondaryStage, cards, card, selectedType);
    }

    void makeUserPage(User user){new UserPage(primaryStage, user);}
}
