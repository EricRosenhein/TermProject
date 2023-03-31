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

    /**
     * This method encapsulates all the logic of creating the account,
     * verifying ownership, crediting, etc. etc.
     */
    // ----------------------------------------------------------
    public void searchScoutData(Properties p) {
       try {
           scout = new Scout(p);
           scout.update();
           scoutUpdateStatusMessage = (String) scoutToUpdate.getState("UpdateStatusMessage");
       }

       catch (InvalidPrimaryKeyException e){
           transactionErrorMessage = " No scout found with " + p + "identifications";
       }
    }

    public void processTransaction(Properties p) {
        scoutToUpdate.info(p);
        scoutToUpdate.update();

        scoutUpdateStatusMessage = (String) scoutToUpdate.getState("scoutUpdateStatusMessage");
    }
    // -----------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("TransactionError") == true) {
            return transactionErrorMessage;
        } else if (key.equals("ScoutUpdateStatusMessage") == true) {
            return scoutUpdateStatusMessage;
        } else if (key.equals("GetScoutToUpdate") == true) {
            return scoutToUpdate;
        }

        return null;
    }

    // -----------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {
        // DEBUG System.out.println("DepositTransaction.sCR: key: " + key);

        if (key.equals("DoYourJob") == true) {
            doYourJob();
        } else if (key.equals("SearchScoutData") == true) {
            searchScoutData((Properties) value);
        } else if (key.equals("UpdateScoutData") == true) {
            processTransaction(); // Not yet made
        }

        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the frame
     */
    // ------------------------------------------------------
    protected Scene createView() {
        Scene currentScene = myViews.get("ScoutSearchView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("ScoutSearchView", this);
            currentScene = new Scene(newView);
            myViews.put("ScoutSearchView", currentScene);

            return currentScene;
        } else {
            return currentScene;
        }
    }

    protected void CreateAndShowUpdateScoutSearchTransactionView() {
        Scene currentScene = myViews.get("UpdateScoutTransactionView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("UpdateScoutTransactionView", this);
            currentScene = new Scene(newView);
            myViews.put("UpdateScoutTransactionView", currentScene);

            return currentScene;
        } else {
            return currentScene;
        }
    }
}
