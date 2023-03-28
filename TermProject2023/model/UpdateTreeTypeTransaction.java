// specify the package
package model;

// system imports
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.Properties;
import java.util.Vector;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;

import userinterface.View;
import userinterface.ViewFactory;

public class UpdateTreeTypeTransaction extends Transaction
{
    private TreeType treeTypeToUpdate;

    private String transactionErrorMessage = "";
    private String updateStatusMessage = "";

    // Constructor
    //---------------------------------------------------------------------
    public UpdateTreeTypeTransaction() throws Exception
    {
        super();
    }

    protected void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("UpdateTreeTypeData", "UpdateStatusMessage");
		dependencies.setProperty("CancelAddTreeType", "CancelTransaction");

		myRegistry.setDependencies(dependencies);
	}

    public void searchTreeTypes(String barcodePrefix)
    {
        try 
        {
            treeTypeToUpdate = new TreeType(barcodePrefix);

            CreateAndShowUpdateTreeTypeTransactionView();

            updateStatusMessage = (String)treeTypeToUpdate.getState("UpdateStatusMessage");
        } 

        catch (InvalidPrimaryKeyException e) 
        {
            transactionErrorMessage = "No tree type found with " + barcodePrefix + "barcode prefix";
        }
    }

    public void processTransaction(String cost)
	{

		// String typeDescription = props.getProperty("typeDescription");
		// String cost = props.getProperty("cost");
		// String barcodePrefix = props.getProperty("barcodePrefix");

		
        treeTypeToUpdate.setCost(cost);
        
        treeTypeToUpdate.update();

		updateStatusMessage = (String)treeTypeToUpdate.getState("UpdateStatusMessage");
	}

    //-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("TransactionError") == true)
		{
			return transactionErrorMessage;
		}
		else
		if (key.equals("UpdateStatusMessage") == true)
		{
			return updateStatusMessage;
		}
        else if(key.equals("GetTreeTypeToUpdate") == true)
        {
            return treeTypeToUpdate;
        }
		return null;
	}

    //-----------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		if (key.equals("DoYourJob") == true)
		{
			doYourJob();
		}
		else
        if(key.equals("SearchTreeTypes") == true)
        {
            searchTreeTypes((String)value);
        }
        else
		if (key.equals("UpdateTreeTypeData") == true)
		{
			processTransaction((String)value);
		}

		myRegistry.updateSubscribers(key, this);
	}

    // Show the TreeTypeSearchView first
    //------------------------------------------------------
	protected Scene createView()
	{

		Scene currentScene = myViews.get("TreeTypeSearchView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("TreeTypeSearchView", this);
			currentScene = new Scene(newView);
			myViews.put("TreeTypeSearchView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}

    protected void CreateAndShowUpdateTreeTypeTransactionView()
    {
        Scene currentScene = myViews.get("UpdateTreeTypeTransactionView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("UpdateTreeTypeTransactionView", this);
			currentScene = new Scene(newView);
			myViews.put("UpdateTreeTypeTransactionView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
    }
}
