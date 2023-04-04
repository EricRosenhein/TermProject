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

public class UpdateScoutTransaction extends Transaction {
    
    private ScoutCollection scoutCollection;
    private Vector scoutList;
    private Scout scoutToUpdate;

    // GUI Components
    private String transactionErrorMessage = "";
    private String scoutUpdateStatusMessage = "";

    // --------------------------------------------------------------
    protected UpdateScoutTransaction() throws Exception {
		super();
	}

    // ----------------------------------------------------------
    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("CancelScout", "CancelTransaction");
        dependencies.setProperty("UpdateScoutData", "ScoutUpdateStatusMessage");

        myRegistry.setDependencies(dependencies);
    }

    protected void searchScouts(Properties props)
    {
        //try 
        //{
            //scoutToUpdate = new Scout(props);
            System.out.println(props.getProperty("FirstName"));
            System.out.println(props.getProperty("LastName"));
            String firstName = (String)props.getProperty("FirstName");
            String lastName = (String)props.getProperty("LastName");
            scoutList = scoutCollection.findScoutsWithNameLike(firstName, lastName);

            createAndShowScoutCollectionView();
        //} 

        // catch (InvalidPrimaryKeyException e) 
        // {
        //     transactionErrorMessage = "No Scout found with troop id : ";
        // }
    }

    /**
     * This method encapsulates all the logic of creating the account,
     * verifying ownership, crediting, etc. etc.
     */
    // ----------------------------------------------------------
    public void processScoutData(Properties p) {
        String firstName = p.getProperty("FirstName");
        scoutToUpdate.persistentState.setProperty("FirstName", firstName);

        String middleName = p.getProperty("MiddleName");
        scoutToUpdate.persistentState.setProperty("MiddleName", middleName);

        String lastName = p.getProperty("LastName");
        scoutToUpdate.persistentState.setProperty("LastName", lastName);

        String dateOfBirth = p.getProperty("DateOfBirth");
        scoutToUpdate.persistentState.setProperty("DateOfBirth", dateOfBirth);

        String phoneNumber = p.getProperty("PhoneNumber");
        scoutToUpdate.persistentState.setProperty("PhoneNumber", phoneNumber);

        String email = p.getProperty("Email");
        scoutToUpdate.persistentState.setProperty("Email", email);

        String status = p.getProperty("Status");
        scoutToUpdate.persistentState.setProperty("Status", status);

        String dateOfUpdate = p.getProperty("DateStatusUpdated");
        scoutToUpdate.persistentState.setProperty("DateStatusUpdated", dateOfUpdate);
        
        scoutToUpdate.update();

		scoutUpdateStatusMessage = (String)scoutToUpdate.getState("UpdateStatusMessage");
    }

    // -----------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("TransactionError") == true) {
            return transactionErrorMessage;
        } 
        else if(key.equals("GetScoutList") == true)
        {
            return scoutList;
        }
        else if(key.equals("GetScoutToUpdate") == true)
        {
            return scoutToUpdate;
        }
        else if (key.equals("ScoutUpdateStatusMessage") == true) {
            return scoutUpdateStatusMessage;
        }

        return null;
    }

    // -----------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {
        // DEBUG System.out.println("DepositTransaction.sCR: key: " + key);

        if (key.equals("DoYourJob") == true) {
            doYourJob();
        }    
        else if(key.equals("SearchScouts") == true)
        {
            searchScouts((Properties) value);
        }
         else if (key.equals("UpdateScoutData") == true) {
            processScoutData((Properties) value);
        }

        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the frame
     */
    // ------------------------------------------------------
    protected Scene createView() {
        Scene currentScene = myViews.get("SearchScoutView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("SearchScoutView", this);
			currentScene = new Scene(newView);
			myViews.put("SearchScoutView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
    }

    protected void createAndShowUpdateScoutView()
    {
		// create the update view
		View newView = ViewFactory.createView("UpdateScoutView", this);
		Scene currentScene = new Scene(newView);
		myViews.put("UpdateScoutView", currentScene);
		swapToView(currentScene);
    }

    protected void createAndShowScoutCollectionView()
    {
        // create the collection view
        View newView = ViewFactory.createView("ScoutCollectionView", this);
        Scene currentScene = new Scene(newView);
        myViews.put("UpdateScoutView", currentScene);
        swapToView(currentScene);
    }
}
