package model;

import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Properties;

public class RemoveScoutTransaction extends Transaction
{
    private Scout scout;
    // GUI Components
    private String transactionErrorMessage = "";
    private String scoutUpdateStatusMessage = "";

    //--------------------------------------------------------------
    public RemoveScoutTransaction()
    {
        super();

        // DEBUG
        System.out.println("In RemoveScoutTransaction constructor");
    }

    //--------------------------------------------------------------
    /** Setter method for dependencies **/
    @Override
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("CancelRemoveScout", "CancelTransaction");
        dependencies.setProperty("RemoveScoutData", "TreeUpdateStatusMessage");

        myRegistry.setDependencies(dependencies);
    }

    //--------------------------------------------------------------
    /* Gets the state of the transaction
     *
     * @param key
     * @return
     */
    @Override
    public Object getState(String key)
    {
        if (key.equals("DoYourJob"))
        {
            doYourJob();
        }
        else if (key.equals("RemoveScoutData"))
        {
            //processScoutData((Properties)value);
        }

        return null;
    }

    //--------------------------------------------------------------
    /* Requests to change the current state
     *
     * @param key
     * @param value
     */
    @Override
    public void stateChangeRequest(String key, Object value)
    {

        if (key.equals("DoYourJob"))
        {
            doYourJob();
        } else if (key.equals("RemoveScoutData"))
        {
            //processScoutData((Properties) value);
        }

        myRegistry.updateSubscribers(key, this);
    }

    //--------------------------------------------------------------
    /* Creates the view for this transaction
     *
     * @return scene    a scene for this view
     */
    @Override
    protected Scene createView()
    {
        // DEBUG
        System.out.println("In RemoveScoutTransaction : createView()");

        Scene currentScene = myViews.get("RemoveScoutView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("RemoveScoutView", this);
            currentScene = new Scene(newView);
            myViews.put("RemoveScoutView", currentScene);
        }

        return currentScene;
    }
}

/*******************************************************************************************
 * Revision History:
 *
 *
 *
 * 04/02/2023 02:10 AM Sebastian Whyte
 * Initial check in.
 ******************************************************************************************/
