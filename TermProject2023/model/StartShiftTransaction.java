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
    protected Session currentSession;
    protected ScoutCollection scoutCollection = new ScoutCollection();
    protected Vector<Scout> fullScoutList;
    protected Vector<Scout> selectedScoutList;

    protected String shiftStatusMessage = "";
    // Constructor
    //---------------------------------------------------------------------
    public StartShiftTransaction()
    {
        super();
        selectedScoutList = new Vector();
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
            return fullScoutList;
        }
        else if(key.equals("GetSelectedScouts"))
        {
            // return the scouts who were selected
            return selectedScoutList;
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
//        boolean openSessionFlag = false;
//        try
//        {
//            Session session = new Session();
//            openSessionFlag = session.findOpenSession();
//        }
//        // THINK HARDER ABOUT THIS
//        catch(InvalidPrimaryKeyException e)
//        {
//            sessionStatusMessage = "ERROR: Multiple open Sessions found.";
//            openSessionFlag = false;
//        }
//        return openSessionFlag;
    }

    //------------------------------------------------------
    protected void searchForAvailableScouts()
    {
        //Find all active scouts method

        fullScoutList = scoutCollection.findActiveScoutsWithNameLike("", "");
    }

    //------------------------------------------------------
    void searchForSelectedScout(String scoutID)
    {

        Scout chosenScout = fullScoutList.retrieve(scoutID); // retrieve method needed in ScoutCollection using scoutID
        selectedScoutList.addSelectedScout(chosenScout); // have a public method in ScoutCollection that just calls the private addScout method

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
