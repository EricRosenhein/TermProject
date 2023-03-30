import event.Event;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.TreeLotCoordinator;
import userinterface.MainStageContainer;
import userinterface.WindowPosition;

/** This is the main driver class for the Boy Scout Troop 209 Tree Sales Application */
//======================================================================
public class TreeLotSystem extends Application
{
    private TreeLotCoordinator tLC;
    private Stage mainStage;

    public void start(Stage primaryStage)
    {
        // Change later
        System.out.println("Boy Scout Troop 209 App v1.00");
        System.out.println("Copyright 2023 Sebastian Whyte, Eric Rosenhein, Dominic Laure, Nathan Cummings, Shain Krutz");

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
            tLC = new TreeLotCoordinator();
        }
        catch(Exception ex)
        {
            System.err.println("TreeLotSystem:start() - could not load TreeLotCoordinator!");
            new Event(Event.getLeafLevelClassName(this), "TreeLotSystem.<init>", "Unable to create TreeLotSystem object", Event.ERROR);
            
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
