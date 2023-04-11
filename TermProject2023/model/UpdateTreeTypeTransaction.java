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
	private String treeTypeUpdateStatusMessage = "";

	// Constructor
	//---------------------------------------------------------------------
	public UpdateTreeTypeTransaction()
	{

		super();
		createView();
	}

	protected void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("UpdateTreeTypeData", "TreeTypeUpdateStatusMessage");
		dependencies.setProperty("CancelUpdateTreeType", "CancelTransaction");

		myRegistry.setDependencies(dependencies);
	}

	public void searchTreeTypes(String barcodePrefix)
	{
		try
		{
			treeTypeToUpdate = new TreeType(barcodePrefix);

			createAndShowUpdateTreeTypeTransactionView();

			treeTypeUpdateStatusMessage = (String)treeTypeToUpdate.getState("UpdateStatusMessage");
		}

		catch (InvalidPrimaryKeyException e)
		{
			treeTypeUpdateStatusMessage = "No tree type found with " + barcodePrefix + "barcode prefix";
		}
	}

	public void processTransaction(Properties updateData)
	{

		// String typeDescription = props.getProperty("typeDescription");
		// String cost = props.getProperty("cost");
		// String barcodePrefix = props.getProperty("barcodePrefix");

		String newDescription = updateData.getProperty("TypeDescription");
		String newCost = updateData.getProperty("Cost");

		if ((newDescription != null) && (newDescription.length() > 0))
		{
			treeTypeToUpdate.setTypeDescription(newDescription);
			if ((newCost != null) && (newCost.length() > 0))
			{
				treeTypeToUpdate.setCost(newCost);
				treeTypeToUpdate.update();
				treeTypeUpdateStatusMessage = (String)treeTypeToUpdate.getState("UpdateStatusMessage");
			}
			else
			{
				treeTypeUpdateStatusMessage = "ERROR: Cost value cannot be empty!";
			}
		}
		else
		{
			treeTypeUpdateStatusMessage = "ERROR: Description cannot be empty!";
		}
			;


	}

	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("TreeTypeUpdateStatusMessage") == true)
		{
			return treeTypeUpdateStatusMessage;
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
			processTransaction((Properties)value);
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

	protected void createAndShowUpdateTreeTypeTransactionView()
	{
			// create our initial view
			View newView = ViewFactory.createView("UpdateTreeTypeView", this);
			Scene currentScene = new Scene(newView);
			myViews.put("UpdateTreeTypeView", currentScene);
			swapToView(currentScene);
	}
}