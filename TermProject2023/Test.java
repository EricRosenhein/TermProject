
import event.Event;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.TransactionMenu;
import userinterface.MainStageContainer;
import userinterface.WindowPosition;

/* Test driver classs */

public class Test extends Application
{
    private TransactionMenu transactionMenu;
    private Stage mainStage;

    public void start(Stage primaryStage)
    {
        // Change later
        System.out.println("Boy Scout Troop 209 App v1.00");
        System.out.println("Copyright 2023 Sebastian Whyte, Eric Rosenhein, Dominic, Nathan, Shain");

        MainStageContainer.setStage(primaryStage, "Boy Scout Troop 209 v1.0");
        mainStage = MainStageContainer.getInstance();

         // Finish setting up the stage (ENABLE THE GUI TO BE CLOSED USING THE TOP RIGHT
        // 'X' IN THE WINDOW), and show it.
        mainStage.setOnCloseRequest(new EventHandler <javafx.stage.WindowEvent>()
        {
            public void handle(javafx.stage.WindowEvent event) {
                System.exit(0);
            }
        });

        try
        {
            transactionMenu = new TransactionMenu();
        }
        catch(Exception ex)
        {
            System.err.println("Test:start() - could not load TransactionMenu!");
            new Event(Event.getLeafLevelClassName(this), "Test.<init>", "Unable to create Test object", Event.ERROR);
            
            ex.printStackTrace();
        }
        
        WindowPosition.placeCenter(mainStage);

        mainStage.show();
    }

     // ----------------------------------------------------------------------
    public static void main(String[] args)
    {
        launch(args);
    }
}
