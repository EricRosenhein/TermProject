// specify the package
package model;

// system imports
import javafx.scene.Scene;
import java.util.Properties;

// project imports
import exception.InvalidPrimaryKeyException;

import userinterface.View;
import userinterface.ViewFactory;

public class UpdateTreeTransaction extends Transaction
{
    protected Tree treeToUpdate;

    protected String treeSearchStatusMessage = "";
    protected String treeUpdateStatusMessage = "";

    // Constructor
    //---------------------------------------------------------------------
    public UpdateTreeTransaction()
    {
        super();
    }

    //----------------------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("SearchTrees", "TreeSearchStatusMessage");
        dependencies.setProperty("UpdateTreeData", "TreeUpdateStatusMessage");
        dependencies.setProperty("ConfirmTreeDeletion", "TreeUpdateStatusMessage");
        dependencies.setProperty("CancelUpdateTree", "CancelTransaction");
        dependencies.setProperty("CancelRemoveTree", "CancelTransaction");


        myRegistry.setDependencies(dependencies);
    }

    // -----------------------------------------------------------------------
    protected void searchTrees(String barcode)
    {
        try
        {
            treeToUpdate = new Tree(barcode);

            createAndShowUpdateTreeTransactionView();
        }
        catch (InvalidPrimaryKeyException e)
        {
            treeSearchStatusMessage = "ERROR: No tree found with barcode: " + barcode;
        }
    }

    //----------------------------------------------------------------------
    protected void processTransaction(Properties props)
    {

        String newNotes = props.getProperty("Notes");
        String newStatus = props.getProperty("Status");

        if ((newNotes != null) && (newNotes.length() > 0))
            treeToUpdate.stateChangeRequest("Notes", newNotes);
        if ((newStatus != null) && (newStatus.length() > 0))
            treeToUpdate.stateChangeRequest("Status", newStatus);

        treeToUpdate.update();

        treeUpdateStatusMessage = (String)treeToUpdate.getState("UpdateStatusMessage");
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("TreeUpdateStatusMessage") == true)
        {
            return treeUpdateStatusMessage;
        }
        else if (key.equals("TreeSearchStatusMessage") == true)
        {
            return treeSearchStatusMessage;
        }
        else if(key.equals("GetTreeToUpdate") == true)
        {
            return treeToUpdate;
        }
        else if (key.equals("GetTreeToRemove") == true)
        {
            return treeToUpdate;
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
        else if(key.equals("SearchTrees") == true)
        {
            searchTrees((String)value);
        }
        else if (key.equals("UpdateTreeData") == true)
        {
            processTransaction((Properties)value);
        }
        else if (key.equals("ConfirmTreeDeletion") == true)
        {
            processTransaction((Properties)value);
        }

        myRegistry.updateSubscribers(key, this);
    }

    // Show the TreeSearchView first
    //------------------------------------------------------
    protected Scene createView()
    {

        Scene currentScene = myViews.get("ScanTreeBarcodeView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("ScanTreeBarcodeView", this);
            currentScene = new Scene(newView);
            myViews.put("ScanTreeBarcodeView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }

    //--------------------------------------------------------------------------------
    protected void createAndShowUpdateTreeTransactionView()
    {
            // create our initial view
            View newView = ViewFactory.createView("UpdateTreeView", this);
            Scene currentScene = new Scene(newView);
            myViews.put("UpdateTreeView", currentScene);
            swapToView(currentScene);

    }
}
