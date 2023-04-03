package model;

// system imports
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.Properties;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;

import userinterface.View;
import userinterface.ViewFactory;

public class RegisterScoutTransaction extends Transaction {
    
    private Scout scout;

    // GUI Components
    private String transactionErrorMessage = "";
    private String scoutUpdateStatusMessage = "";

    // --------------------------------------------------------------
    protected RegisterScoutTransaction() {
	    super();
    }

    // ----------------------------------------------------------
    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("CancelScout", "CancelTransaction");
        dependencies.setProperty("InsertScoutData", "ScoutUpdateStatusMessage");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the account,
     * verifying ownership, crediting, etc. etc.
     */
    // ----------------------------------------------------------
    public void processScoutData(Properties p) {
	try
	{
        String troopIDSent = p.getProperty("TroopID");
        Scout oldScout = new Scout(troopIDSent);
        scoutUpdateStatusMessage = "ERROR: Scout with troop ID: " + troopIDSent + "already exists!";
    }
	catch (InvalidPrimaryKeyException ex)
	{       
		scout = new Scout(p);
        scout.update();
        scoutUpdateStatusMessage = (String) scout.getState("UpdateStatusMessage");
	}
    }

    // -----------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
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
        } else if (key.equals("InsertScoutData") == true) {
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
        Scene currentScene = myViews.get("RegisterScoutView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("RegisterScoutView", this);
            currentScene = new Scene(newView);
            myViews.put("RegisterScoutView", currentScene);

            return currentScene;
        } else {
            return currentScene;
        }
    }
}
