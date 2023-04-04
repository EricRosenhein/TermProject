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


public class TransactionMenu implements IView, IModel
{
    // GUI Components
	//private HashMap<String, Scene> myViews;
    private Map<String, Scene> myViews;
	private Stage stage;
    private String transactionErrorMessage = "";

    // ----------------------------------------------------------------
    public TransactionMenu()
    {
        stage = MainStageContainer.getInstance();
        myViews = new HashMap<String, Scene>();

        /* 
        myRegistry = new ModelRegistry("TransactionMenu");

        if(myRegistry == null)
        {
            new Event(Event.getLeafLevelClassName(this), "TransactionMenu", "could not instantiate Registry", Event.ERROR);
        }

        setDependencies();
        */

        createAndShowTransactionMenuView();
    }

    // ----------------------------------------------------------------
    private void createAndShowTransactionMenuView() 
    {
        Scene currentScene = (Scene)myViews.get("TransactionMenuView");

        if (currentScene == null)
        {
            View newView = ViewFactory.createView("TransactionMenuView", this);
            
            currentScene = new Scene(newView);

            myViews.put("TransactionMenuView", currentScene);
        }

        swapToView(currentScene);             
    }

    // ----------------------------------------------------------------
    private void swapToView(Scene newScene) 
    {
        if (newScene == null)
        {
            System.out.println("TransactionMenu : swapToView() - Missing view to display!");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getState'");
    }

    // ----------------------------------------------------------------
    @Override
    public void subscribe(String key, IView subscriber) 
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'subscribe'");
    }

    // ----------------------------------------------------------------
    @Override
    public void unSubscribe(String key, IView subscriber) 
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unSubscribe'");
    }

    // ----------------------------------------------------------------
    // Changes the current state we are in depending on the current transaction
    @Override
    public void stateChangeRequest(String key, Object value) 
    {
        // Check the transaction
        if ((key.equals("Done")) || key.equals("CancelTransaction"))
        {
            createAndShowTransactionMenuView();
        }
        else if (key.equals("RegisterScout") || key.equals("AddTree") || key.equals("AddTreeType"))
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
			Transaction transaction = TransactionFactory.createTransaction(
				transactionType);

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

