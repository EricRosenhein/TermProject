package model;

import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Properties;

public class UpdateTreeTransaction extends Transaction
{
    // GUI Components
    private String transactionErrorMessage = "";
    private String treeUpdateStatusMessage = "";

    //-----------------------------------------------------------
    public UpdateTreeTransaction()
    {
        super();

        // DEBUG
        System.out.println("In UpdateTreeTransaction constructor");
    }

    //-----------------------------------------------------------
    /**
     *
     */
    @Override
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("CancelUpdateTree", "CancelTransaction");
        dependencies.setProperty("UpdateTreeData", "TreeUpdateStatusMessage");

        myRegistry.setDependencies(dependencies);
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("TransactionError"))
        {
            return transactionErrorMessage;
        }
        else
        if (key.equals("TreeUpdateStatusMessage"))
        {
            return treeUpdateStatusMessage;
        }

        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        // DEBUG System.out.println("DepositTransaction.sCR: key: " + key);

        if (key.equals("DoYourJob"))
        {
            doYourJob();
        }
        else
        if (key.equals("UpdateTreeData"))
        {
            //processTreeData((Properties)value);
        }

        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the frame
     */
    //------------------------------------------------------
    protected Scene createView()
    {
        Scene currentScene = myViews.get("UpdateTreeView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("UpdateTreeView", this);
            currentScene = new Scene(newView);
            myViews.put("UpdateTreeView", currentScene);
        }

        return currentScene;
    }
}

/****************************************************************************************
 *
 *
 *
 * 04/01/2023 11:35 PM Sebastian Whyte
 * Initial check in.
 ****************************************************************************************/


