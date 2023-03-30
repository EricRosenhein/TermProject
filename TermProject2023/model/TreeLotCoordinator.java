package model;

import java.util.HashMap;
import java.util.Map;

import event.Event;
import impresario.IModel;
import impresario.IView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;


public class TreeLotCoordinator implements IView, IModel
{
    // For Impresario
    private Properties dependencies;
    private ModelRegistry myRegistry;

    // GUI Components
    //private HashMap<String, Scene> myViews;
    private Map<String, Scene> myViews;
    private Stage stage;
    private String transactionErrorMessage = "";

    // ----------------------------------------------------------------
    public TreeLotCoordinator()
    {
        stage = MainStageContainer.getInstance();
        myViews = new HashMap<String, Scene>();
 
        myRegistry = new ModelRegistry("TreeLotCoordinator");
        if(myRegistry == null)
        {
            new Event(Event.getLeafLevelClassName(this), "TreeLotCoordinator", "could not instantiate Registry", Event.ERROR);
        }
        setDependencies();

        createAndShowTLCView();
    }

    // ----------------------------------------------------------------
    private void createAndShowTLCView() 
    {
        Scene currentScene = (Scene)myViews.get("TLCView");

        if (currentScene == null)
        {
            View newView = ViewFactory.createView("TLCView", this);
            
            currentScene = new Scene(newView);

            myViews.put("TLCView", currentScene);
        }

        swapToView(currentScene);             
    }

    // ----------------------------------------------------------------
    private void swapToView(Scene newScene) 
    {
        if (newScene == null)
        {
            System.out.println("TreeLotCoordinator : swapToView() - Missing view to display!");
            new Event(Event.getLeafLevelClassName(this), "swaptoView", "Missing view to display", Event.ERROR);
            
            return;
        }

        stage.setScene(newScene);
        stage.sizeToScene();

        WindowPosition.placeCenter(stage);
    }

    // ----------------------------------------------------------------
    @Override
    public Object getState(String key) 
    {
       return "";
    }

   //---------------------------------------------------------------------
   public void subscribe(String key, IView subscriber)
   {
	// DEBUG: System.out.println("Cager[" + myTableName + "].subscribe");
	// forward to our registry
	myRegistry.subscribe(key, subscriber);
    }

    /** Unregister previously registered objects. */
    //----------------------------------------------------------
    public void unSubscribe(String key, IView subscriber)
    {
	// DEBUG: System.out.println("Cager.unSubscribe");
	// forward to our registry
	myRegistry.unSubscribe(key, subscriber);
     }

    // ----------------------------------------------------------------
    // Changes the current state we are in depending on the current transaction
    @Override
    public void stateChangeRequest(String key, Object value) 
    {
        // Check the transaction
        if ((key.equals("Done")) || key.equals("CancelTransaction"))
        {
            createAndShowTLCView();
        }
        else if (key.equals("RegisterScout") || key.equals("AddTree") || key.equals("AddTreeType") || key.equals("UpdateScout") ||
		key.equals("UpdateTree") || key.equals("UpdateTreeType") || key.equals("RemoveScout") || key.equals("RemoveTree"))
        {
            String transactionType = key;
            doTransaction(transactionType);
        }
    }

    //---------------------------------------------------------------------------------------
	public void doTransaction(String transactionType)
	{
        // DEBUG 
		System.out.println("Handling transaction type: " + transactionType);
		
            try
	    {
		Transaction transaction = TransactionFactory.createTransaction(transactionType);

		transaction.subscribe("CancelTransaction", this);
		transaction.stateChangeRequest("DoYourJob", "");
	     }
	     catch (Exception ex)
	     {
		transactionErrorMessage = "TransactionMenu : doTransaction() - TRANSACTION FAILURE: Unrecognized transaction!!";
		new Event(Event.getLeafLevelClassName(this), "createTransaction",
		   "Transaction Creation Failure: Unrecognized transaction " + ex.toString(),
		    Event.ERROR);
	      }
	}

    // ----------------------------------------------------------------
    @Override
    public void updateState(String key, Object value) 
    {
        // DEBUG System.out.println("TransactionMenu:updateState, key is: " + key);

		stateChangeRequest(key, value);
    }
}

/*******************************************************************************************
 * Revision History:
 * 
 * 
 * 
 * 03/24/2023 10:30 PM Sebastian Whyte
 * Added doTransaction() method. Only checked for "RegisterScout" in the if condition statement in stateChangeRequest()
 * 
 * 03/24/2023 07:18 PM Sebastian Whyte
 * Initial check in. Included the add operations inside of stateChangeRequest()
*******************************************************************************************/

