package model;

import exception.InvalidPrimaryKeyException;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Properties;
import java.util.Vector;

// project imports
import model.Scout;
import model.ScoutCollection;

public class StartShiftTransaction extends Transaction
{

    protected String shiftStatusMessage = "";
    // Constructor
    //---------------------------------------------------------------------
    public StartShiftTransaction()
    {
        super();
    }

    //----------------------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("StartSession", "SessionStatusMessage");
        dependencies.setProperty("StartShift", "ShiftStatusMessage");
        dependencies.setProperty("CancelStartShift", "CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    //----------------------------------------------------------------------
    protected void processTransaction(Properties props)
    {
        System.out.println("model/StartShiftTransaction: processTransaction(Properties props): getting here!");
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("AddScoutToList") == true)
        {
            return shiftStatusMessage;
        }
        else if (key.equals("ShiftStatusMessage") == true) {
            return shiftStatusMessage;
        }
        else if(key.equals("GetAvailableScouts"))
        {
            return scoutList;
        }
        else if(key.equals("GetSelectedScout"))
        {
            // return the scout who was selected
        }

        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        if (key.equals("DoYourJob") == true)
        {
            // DEBUG System.out.println("model/StartShiftTransaction: stateChangeRequest(): getting here!");

            doYourJob();
        }
        else if(key.equals("BeginShift") == true)
        {
            // do nothing
        }
        else if(key.equals("SearchForAvailableScouts"))
        {
            searchForAvailableScouts();
        }
        else if (key.equals("SearchForSelectedScout"))
        {
            searchForSelectedScout();
        }
        else if (key.equals("StartSession"))
        {
            startSession();
        }


        myRegistry.updateSubscribers(key, this);
    }

    // -------------------------------------------------------------
    private void startSession()
    {

    }

    //------------------------------------------------------
    void searchForAvailableScouts()
    {
        scoutList = scoutCollection.findActiveScoutsWithNameLike("", "");
    }

    //------------------------------------------------------
    void searchForSelectedScout()
    {
        String fn = "";
        String ln = "";

        scoutList = scoutCollection.findActiveScoutsWithNameLike(fn, ln);
    }



    // Show the TreeSearchView first
    //------------------------------------------------------
    protected Scene createView()
    {
        //DEBUG System.out.println("model/StartShiftTransaction : createView(): getting here");

        Scene currentScene = myViews.get("StartShiftView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("StartShiftView", this);
            currentScene = new Scene(newView);
            myViews.put("StartShiftView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }
}
