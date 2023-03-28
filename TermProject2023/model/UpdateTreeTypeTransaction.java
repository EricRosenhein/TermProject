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
		dependencies.setProperty("OK", "CancelTransaction");

		myRegistry.setDependencies(dependencies);
	}

    public void processTransaction(Properties props)
	{

		// String typeDescription = props.getProperty("typeDescription");
		// String cost = props.getProperty("cost");
		// String barcodePrefix = props.getProperty("barcodePrefix");

		newTreeType = new TreeType(props);
        	newTreeType.update();
		updateStatusMessage = (String)newTreeType.getState("UpdateStatusMessage");
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
		if (key.equals("UpdateTreeTypeData") == true)
		{
			processTransaction((Properties)value);
		}

		myRegistry.updateSubscribers(key, this);
	}

    //------------------------------------------------------
	protected Scene createView()
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
