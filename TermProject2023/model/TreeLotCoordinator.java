// include package
package model;

// system imports
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javafx.stage.Stage;
import javafx.scene.Scene;

// project imports
import impresario.IModel;
import impresario.IView;
import impresario.ModelRegistry;

import event.Event;
import userinterface.*;


public class TreeLotCoordinator implements IView, IModel
{
    // For Impresario
    private Properties dependencies;
    private ModelRegistry myRegistry;

    // GUI Components
    private Map<String, Scene> myViews;
    private Stage myStage;
    private String transactionErrorMessage = "";
    private Boolean openSessionFlag;

    // ----------------------------------------------------------------
    public TreeLotCoordinator()
    {
        myStage = MainStageContainer.getInstance();
        myViews = new HashMap<String, Scene>();

        // STEP 3.1: Create the Registry object - if you inherit from
        // EntityBase, this is done for you. Otherwise, you do it yourself
        myRegistry = new ModelRegistry("TreeLotCoordinator");
        if(myRegistry == null)
        {
            new Event(Event.getLeafLevelClassName(this), "TreeLotCoordinator", "could not instantiate Registry", Event.ERROR);
        }
        // STEP 3.2: Be sure to set the dependencies correctly
        setDependencies();

        // Try to find an open sesison
        setOpenSessionFlag();

        // Set up the initial view
        createAndShowTLCView();
    }
    // ----------------------------------------------------------------
    private void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("RegisterScout", "Transaction Error");
        dependencies.setProperty("AddTree", "Transaction Error");
        dependencies.setProperty("AddTreeType", "Transaction Error");
        dependencies.setProperty("UpdateScout", "Transaction Error");
        dependencies.setProperty("UpdateTree", "Transaction Error");
        dependencies.setProperty("UpdateTreeType", "Transaction Error");
        dependencies.setProperty("RemoveScout", "Transaction Error");
        dependencies.setProperty("RemoveTree", "Transaction Error");
        dependencies.setProperty("FindOpenSession", "OpenSessionFlag");

        myRegistry.setDependencies(dependencies);
    }


    // ----------------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("TransactionError"))
        {
            return transactionErrorMessage;
        }
        else if (key.equals("OpenSessionFlag"))
        {
            return openSessionFlag;
        }
        else
            return "";

    }


    // ----------------------------------------------------------------
    // Changes the current state we are in depending on the current transaction
    public void stateChangeRequest(String key, Object value) 
    {
        // STEP 4: Write the sCR method component for the key you just set up dependencies for
        // DEBUG System.out.println("TreeLotCoordinator.sCR: key = " + key);
        if ((key.equals("Done")) || key.equals("CancelTransaction"))
        {
            createAndShowTLCView();
        }
        else if (key.equals("RegisterScout") || key.equals("AddTree") || key.equals("AddTreeType") || key.equals("UpdateScout") ||
		key.equals("UpdateTree") || key.equals("UpdateTreeType") || key.equals("RemoveScout") || key.equals("RemoveTree") ||
        key.equals("StartShift") || key.equals("EndShift") || key.equals("SellTree"))
        {
            String transType = key;
            doTransaction(transType);
        }

        myRegistry.updateSubscribers(key, this);
    }

    private void setOpenSessionFlag ()
    {
        Session current = new Session();

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

        myStage.setScene(newScene);
        myStage.sizeToScene();

        //place in center
        WindowPosition.placeCenter(myStage);
    }


    //---------------------------------------------------------------------------------------
	public void doTransaction(String transactionType)
	{
        // DEBUG System.out.println("Handling transaction type: " + transactionType);
		
        try
	    {
		Transaction transaction = TransactionFactory.createTransaction(transactionType);

		transaction.subscribe("CancelTransaction", this);
		transaction.stateChangeRequest("DoYourJob", "");
        }
	    catch (Exception ex)
	    {
		transactionErrorMessage = "TransactionMenu: doTransaction() - TRANSACTION FAILURE: Unrecognized transaction!!";
		new Event(Event.getLeafLevelClassName(this), "createTransaction",
		   "Transaction Creation Failure: Unrecognized transaction " + ex.toString(),
		    Event.ERROR);
        }
	}

    // ----------------------------------------------------------------
    public void updateState(String key, Object value) 
    {
        // DEBUG System.out.println("TreeLotCoordinator:updateState, key is: " + key);

		stateChangeRequest(key, value);
    }

/*
    //---------------------------------------------------------------------
    public void registerScout()
    {
        RegisterScoutView registerScoutView = new RegisterScoutView(this);
    }

    //---------------------------------------------------------------------
    public void updateScout()
    {
        UpdateScoutView updateScoutView = new UpdateScoutView(this);
    }

    //---------------------------------------------------------------------
    public void removeScout()
    {
        RemoveScoutView removeScoutView = new RemoveScoutView(this);
    }

    //---------------------------------------------------------------------
    public void addTree()
    {
        AddTreeView addTreeView = new AddTreeView(this);
    }

    //---------------------------------------------------------------------
    public void updateTree()
    {
        UpdateTreeView updateTreeView = new UpdateTreeView(this);
    }

    //---------------------------------------------------------------------
    public void removeTree()
    {
        RemoveTreeView removeTreeView = new RemoveTreeView(this);
    }

    //---------------------------------------------------------------------
    public void addTreeType()
    {
        AddTreeTypeView addTreeTypeView = new AddTreeTypeView(this);
    }

    //---------------------------------------------------------------------
    public void updateTreeType()
    {
        UpdateTreeTypeView updateTreeTypeView = new UpdateTreeTypeView(this);
    }

*/


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

