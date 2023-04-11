package model;

import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Properties;

public class RemoveTreeTransaction extends UpdateTreeTransaction
{
    // Constructor
    //---------------------------------------------------------------------
    public RemoveTreeTransaction()
    {
        super();
    }

    //----------------------------------------------------------------------
    @Override
    protected void processTransaction(Properties props)
    {

        String treeCurrentStatus = (String)treeToUpdate.getState("Status");
        if (treeCurrentStatus.equals("Sold") == true)
            treeUpdateStatusMessage = "ERROR: Tree with barcode: " + treeToUpdate.getState("Barcode") + " is already sold!";
        else
        {
            treeToUpdate.delete();
            treeUpdateStatusMessage = (String) treeToUpdate.getState("DeleteStatusMessage");
        }
    }

    //--------------------------------------------------------------------------------
    protected void createAndShowUpdateTreeTransactionView()
    {
        // create our initial view
        View newView = ViewFactory.createView("ConfirmDeleteTreeView", this);
        Scene currentScene = new Scene(newView);
        myViews.put("ConfirmDeleteTreeView", currentScene);
        swapToView(currentScene);
    }
}
