// specify package
package model;

// system imports
import javafx.scene.Scene;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Vector;

// project imports
import userinterface.View;
import userinterface.ViewFactory;

public class RemoveScoutTransaction extends Transaction
{
    private ScoutCollection scoutCollection = new ScoutCollection();
    private Vector scoutList;
    private Scout scoutToRemove;

    // GUI Components
    private String transactionErrorMessage = "";
    private String scoutUpdateStatusMessage = "";

    // --------------------------------------------------------------
    protected RemoveScoutTransaction(){
        super();
    }

    // ----------------------------------------------------------
    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("CancelScout", "CancelTransaction");
        dependencies.setProperty("RemoveScout", "ScoutUpdateStatusMessage");

        myRegistry.setDependencies(dependencies);
    }

    protected void searchScouts(Properties props)
    {
        String firstName = (String)props.getProperty("FirstName");
        String lastName = (String)props.getProperty("LastName");
        scoutList = scoutCollection.findActiveScoutsWithNameLike(firstName, lastName);

        createAndShowScoutCollectionView();
    }

    /**
     * This method encapsulates all the logic of creating the account,
     * verifying ownership, crediting, etc. etc.
     */
    // ----------------------------------------------------------
    public void removeScout() {
        // Set scout status to Inactive
        scoutToRemove.persistentState.setProperty("Status", "Inactive");

        LocalDateTime ldt = LocalDateTime.now();
        String now = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        scoutToRemove.persistentState.setProperty("DateStatusUpdated", now);

        scoutToRemove.update();

        scoutUpdateStatusMessage = (String)scoutToRemove.getState("UpdateStatusMessage");
        scoutUpdateStatusMessage = scoutUpdateStatusMessage.replaceAll("update", "remove");
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
        else if(key.equals("GetScoutToRemove") == true)
        {
            return scoutToRemove;
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
        else if(key.equals("CancelSearch") == true)
        {
            doYourJob();
        }
        else if(key.equals("ScoutChosen") == true)
        {
            scoutToRemove = (Scout) value;
            createAndShowRemoveScoutView();
        }
        else if (key.equals("RemoveScout") == true) {
            removeScout();
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

    protected void createAndShowRemoveScoutView()
    {
        // create the update view
        View newView = ViewFactory.createView("RemoveScoutView", this);
        Scene currentScene = new Scene(newView);
        myViews.put("RemoveScoutView", currentScene);
        swapToView(currentScene);
    }

    protected void createAndShowScoutCollectionView()
    {
        // create the collection view
        View newView = ViewFactory.createView("ScoutCollectionView", this);
        Scene currentScene = new Scene(newView);
        myViews.put("ScoutCollectionView", currentScene);
        swapToView(currentScene);
    }
}