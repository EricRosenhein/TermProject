// specify package
package model;

// system imports
import javafx.scene.Scene;
import java.util.Properties;
import java.util.Vector;

// project imports
import userinterface.View;
import userinterface.ViewFactory;

public class UpdateScoutTransaction extends Transaction {

    private ScoutCollection scoutCollection = new ScoutCollection();
    private Vector scoutList;
    private Scout scoutToUpdate;

    // GUI Components
    private String transactionErrorMessage = "";
    private String scoutUpdateStatusMessage = "";

    // --------------------------------------------------------------
    protected UpdateScoutTransaction(){
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
        String firstName = (String)props.getProperty("FirstName");
        String lastName = (String)props.getProperty("LastName");
        scoutList = scoutCollection.findScoutsWithNameLike(firstName, lastName);

        createAndShowScoutCollectionView();
    }

    /**
     * This method encapsulates all the logic of creating the account,
     * verifying ownership, crediting, etc. etc.
     */
    // ----------------------------------------------------------
    public void processScoutData(Properties p) {
        String firstName = p.getProperty("FirstName");
        scoutToUpdate.stateChangeRequest("FirstName", firstName);

        String middleName = p.getProperty("MiddleName");
        scoutToUpdate.stateChangeRequest("MiddleName", middleName);

        String lastName = p.getProperty("LastName");
        scoutToUpdate.stateChangeRequest("LastName", lastName);

        String dateOfBirth = p.getProperty("DateOfBirth");
        scoutToUpdate.stateChangeRequest("DateOfBirth", dateOfBirth);

        String phoneNumber = p.getProperty("PhoneNumber");
        scoutToUpdate.stateChangeRequest("PhoneNumber", phoneNumber);

        String email = p.getProperty("Email");
        scoutToUpdate.stateChangeRequest("Email", email);

        String status = p.getProperty("Status");
        scoutToUpdate.stateChangeRequest("Status", status);

        String dateOfUpdate = p.getProperty("DateStatusUpdated");
        scoutToUpdate.stateChangeRequest("DateStatusUpdated", dateOfUpdate);

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
        else if(key.equals("CancelSearch") == true)
        {
            doYourJob();
        }
        else if(key.equals("ScoutChosen") == true)
        {
            scoutToUpdate = (Scout)value;
            createAndShowUpdateScoutView();
        }
        else if (key.equals("UpdateScoutData") == true) {
            processScoutData((Properties)value);
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
        myViews.put("ScoutCollectionView", currentScene);
        swapToView(currentScene);
    }
}