// specify the package
package model;

import java.sql.SQLException;
import java.util.*;
// system imports
import javafx.stage.Stage;
import javafx.scene.Scene;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import impresario.*;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.WindowPosition;


/** The class containing the Transaction for the ATM application */
//==============================================================
abstract public class Transaction implements IView, IModel
{
	// For Impresario
	protected Properties dependencies;
	protected ModelRegistry myRegistry;

	protected Stage myStage;
	protected Map<String, Scene> myViews;

	private String updateStatusMessage = "";

	/**
	 * Constructor for this class.

	 *
	 */
	//----------------------------------------------------------
	protected Transaction()
	{
		// DEBUG System.out.println("In Transaction constructor");

		myStage = MainStageContainer.getInstance();
		myViews = new HashMap<String, Scene>();
		myRegistry = new ModelRegistry("Transaction");

		if (myRegistry == null)
		{
			new Event(Event.getLeafLevelClassName(this), "Transaction",
				"Could not instantiate Registry", Event.ERROR);
		}

		setDependencies();

	}

	/** Constructor that takes in a Properties object; may break -SW
	 *
	 * @param props		object with properties
	 */
	//----------------------------------------------------------
	protected Transaction(Properties props)
	{
		super(myTableName);

		try
		{
			setDependencies();
			persistentState = new Properties();

			Enumeration allKeys = props.propertyNames();
			while (allKeys.hasMoreElements() == true) {
				String nextKey = (String) allKeys.nextElement();
				String nextValue = props.getProperty(nextKey);
				if (nextValue != null) {
					persistentState.setProperty(nextKey, nextValue);
				}
			}
		}
		catch (Exception ex)
		{
			System.out.println("Transaction : Transaction(Properties props) - could not create table!");
		}
	}

	//----------------------------------------------------------
	protected abstract void setDependencies();

	//---------------------------------------------------------
	protected abstract Scene createView();

	/**
	 * Template method
	 *
	 */
	//---------------------------------------------------------
	protected void doYourJob()
	{		
		// DEBUG System.out.println("In Transaction : doYourJob()");

		Scene newScene = createView();
		
		swapToView(newScene);		
	}

	// Forward declarations
	//-----------------------------------------------------------
	public abstract Object getState(String key);

	//-----------------------------------------------------------
	public abstract void stateChangeRequest(String key, Object value);

	/** Called via the IView relationship
	 * Re-define in sub-class, if necessary
	 */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}

	// -----------------------------------------------------------------------------------
	public void update() {
		updateStateInDatabase();
	}

	// -----------------------------------------------------------------------------------
	private void updateStateInDatabase() {
		try {
			if (persistentState.getProperty("ID") != null) {
				// Update Transaction
			} else {
				Integer transactionId = insertAutoIncrementalPersistentState(mySchema, persistentState);
				persistentState.setProperty("ID", "" + transactionId);
				updateStatusMessage = "Transaction data for new Transaction: "
						+ persistentState.getProperty("transactionId")
						+ " installed successfully in database!";
			}
		} catch (SQLException ex) {
			updateStatusMessage = "ERROR: Error registering scout data in database!";
		}
	}

	/** Register objects to receive state updates. */
	//----------------------------------------------------------
	public void subscribe(String key, IView subscriber)
	{
		// DEBUG: System+.out.println("Cager[" + myTableName + "].subscribe");
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

	//-----------------------------------------------------------------------------
	public void swapToView(Scene newScene)
	{	
		// DEBUG System.out.println("In Transaction : swapToView()");

		if (newScene == null)
		{
			System.out.println("Transaction : swapToView() - Missing view for display");
			new Event(Event.getLeafLevelClassName(this), "swapToView",
				"Missing view for display ", Event.ERROR);

			return;
		}
		
		myStage.setScene(newScene);
		myStage.sizeToScene();
					
		//Place in center
		WindowPosition.placeCenter(myStage);

	}
}

