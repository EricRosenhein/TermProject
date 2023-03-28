package model;

import java.security.InvalidKeyException;
// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

// project imports
import exception.InvalidPrimaryKeyException;
import database.*;

import impresario.IModel;
import impresario.IView;

import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;


public class TreeType extends EntityBase implements IView
{

    private static final String myTableName = "TreeType";
	protected Properties dependencies;
	private String updateStatusMessage = "";


	// Constructor for existing tree type using treeTypeId
	//---------------------------------------------------------------------
	public TreeType(int treeTypeId) throws InvalidPrimaryKeyException
	{
		super(myTableName);
		
		setDependencies();
		String query = "SELECT * FROM " + myTableName + " WHERE (treeTypeId = " + treeTypeId + ")";
		
		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
		
		// You must get one tree type at least
		if (allDataRetrieved != null)
		{
			int size = allDataRetrieved.size();

			// There should be EXACTLY one tree type. More than that is an error
			if (size != 1)
			{
				throw new InvalidPrimaryKeyException("Multiple tree types matching id : "
					+ treeTypeId + " found.");
			}
			else
			{
				// copy all the retrieved data into persistent state
				Properties retrievedBookData = allDataRetrieved.elementAt(0);
				persistentState = new Properties();

				Enumeration allKeys = retrievedBookData.propertyNames();
				while (allKeys.hasMoreElements() == true)
				{
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedBookData.getProperty(nextKey);
					// bookId = Integer.parseInt(retrievedBookData.getProperty("bookId"));

					if (nextValue != null)
					{
						persistentState.setProperty(nextKey, nextValue);
					}
				}

			}
		}
		// If no book found for this book id, throw an exception
		else
		{
			throw new InvalidPrimaryKeyException("No tree type matching id : "
				+ treeTypeId + " found.");
		}
	}
	
	// Constructor for existing tree type using barcodePrefix
	//---------------------------------------------------------------------
	public TreeType(String barcodePrefix) throws InvalidPrimaryKeyException
	{
		super(myTableName);
		
		setDependencies();
		String query = "SELECT * FROM " + myTableName + " WHERE (barcodePrefix = " + barcodePrefix + ")";
		
		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
		
		// You must get one tree type at least
		if (allDataRetrieved != null)
		{
			int size = allDataRetrieved.size();

			// There should be EXACTLY one tree type. More than that is an error
			if (size != 1)
			{
				throw new InvalidPrimaryKeyException("Multiple tree types matching barcode prefix : "
					+ barcodePrefix + " found.");
			}
			else
			{
				// copy all the retrieved data into persistent state
				Properties retrievedBookData = allDataRetrieved.elementAt(0);
				persistentState = new Properties();

				Enumeration allKeys = retrievedBookData.propertyNames();
				while (allKeys.hasMoreElements() == true)
				{
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedBookData.getProperty(nextKey);

					if (nextValue != null)
					{
						persistentState.setProperty(nextKey, nextValue);
					}
				}

			}
		}
		// If no tree type found for this tree type barcode prefix, throw an exception
		else
		{
			throw new InvalidPrimaryKeyException("No tree type matching barcode prefix : "
				+ barcodePrefix + " found.");
		}
	}

	// Constructor for new tree type
	//---------------------------------------------------------------------
	public TreeType(Properties props)
	{
		super(myTableName);

		setDependencies();
		persistentState = new Properties();
		Enumeration allKeys = props.propertyNames();
		while (allKeys.hasMoreElements() == true)
		{
			String nextKey = (String)allKeys.nextElement();
			String nextValue = props.getProperty(nextKey);

			if (nextValue != null)
			{
				persistentState.setProperty(nextKey, nextValue);
			}
		}
	}

    // Empty Contstructor
	// --------------------------------------------------------------------
	public TreeType()
	{
		super(myTableName);
        setDependencies();
		persistentState = new Properties();
	}

	
	//---------------------------------------------------------------------
	private void setDependencies()
	{
		dependencies = new Properties();
        dependencies.setProperty("CancelAddTreeType", "CancelTransaction");
		myRegistry.setDependencies(dependencies);
	}
	
	//---------------------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("UpdateStatusMessage") == true)
			return updateStatusMessage;

		return persistentState.getProperty(key);
	}

	//---------------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		if(key.equals("ProcessTreeType"))
		{
			processNewTreeType((Properties) value);
		}

		myRegistry.updateSubscribers(key, this);
	}

	//---------------------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}
	
	public void update()
	{
		updateStateInDatabase();
	}
	
	//---------------------------------------------------------------------
	private void updateStateInDatabase() 
	{
		try
		{
			if (persistentState.getProperty("treeTypeId") != null)
			{
				Properties whereClause = new Properties();
				whereClause.setProperty("treeTypeId",
				persistentState.getProperty("treeTypeId"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Tree type data for tree type id : " + persistentState.getProperty("treeTypeId") + " updated successfully in database!";
			}
			else
			{
				int treeTypeId =
					insertAutoIncrementalPersistentState(mySchema, persistentState);
				persistentState.setProperty("treeTypeId", "" + treeTypeId);
				updateStatusMessage = "Tree type data for new Tree type : " +  persistentState.getProperty("treeTypeId" + "installed successfully in database!");
			}
		}
		catch (SQLException ex)
		{
			updateStatusMessage = "Error in installing tree type data in database!";
		}
		//DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
	}
	
	//---------------------------------------------------------------------
	public Vector<String> getEntryListView()
	{
		Vector<String> v = new Vector<String>();

		v.addElement(persistentState.getProperty("treeTypeId"));
		v.addElement(persistentState.getProperty("typeDescription"));
		v.addElement(persistentState.getProperty("cost"));
		v.addElement(persistentState.getProperty("barcodePrefix"));

		return v;
	}
	
	//---------------------------------------------------------------------
	protected void initializeSchema(String tableName)
	{
		if (mySchema == null)
		{
			mySchema = getSchemaInfo(tableName);
		}
	}

	//---------------------------------------------------------------------
	public String toString()
	{
		return "Id: " + persistentState.getProperty("treeTypeId") +
				"; Description: " + persistentState.getProperty("typeDescription") +
				"; Cost: " + persistentState.getProperty("cost") +
				"; Barcode Prefix: " + persistentState.getProperty("barcodePrefix") + "\n";
	}

	// Invokes the toString() method to display info about the chosen book
	// -------------------------------------------------------
    public void display()
    {
        System.out.println(toString());
    }

	//---------------------------------------------------------------------
	public void createAndShowTreeTypeView()
	{
		Scene currentScene = (Scene)myViews.get("TreeTypeView");

		if(currentScene == null)
		{
			View newView = ViewFactory.createView("TreeTypeView", this);
			currentScene = new Scene(newView);
			myViews.put("TreeTypeView", currentScene);
		}

		swapToView(currentScene);
	}

    //---------------------------------------------------------------------
	private void processNewTreeType(Properties props)
	{
		props.forEach((key, value) -> {
			persistentState.setProperty((String) key, (String) value);
		});

		update();
	}

	// Set methods
	//---------------------------------------------------------------------
	public void setTypeDescription(String typeDescription)
	{
		persistentState.setProperty("typeDescription", typeDescription);
	}

	public void setCost(String cost)
	{
		persistentState.setProperty("cost", cost);
	}

	public void setBarcodePrefix(String barcodePrefix)
	{
		persistentState.setProperty("barcodePrefix", barcodePrefix);
	}

	// Get methods
	//---------------------------------------------------------------------
	public String getTreeTypeId()
	{
		return persistentState.getProperty("treeTypeId");
	}

	public String getTypeDescription()
	{
		return persistentState.getProperty("typeDescription");
	}

	public String getCost()
	{
		return persistentState.getProperty("cost");
	}

	public String getBarcodePrefix()
	{
		return persistentState.getProperty("barcodePrefix");
    }
}
