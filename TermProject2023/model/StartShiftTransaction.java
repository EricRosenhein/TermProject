package model;

import exception.InvalidPrimaryKeyException;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;


public class StartShiftTransaction extends Transaction
{
    protected Session currentSession;
    //protected ScoutCollection scoutCollection = new ScoutCollection();
    protected Vector<Scout> fullScoutList;
    protected Vector<Scout> selectedScoutList;

    protected String sessionStatusMessage = "";
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
        dependencies.setProperty("AddScoutToShift", "ShiftStatusMessage");
        dependencies.setProperty("CancelStartShift", "CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    //----------------------------------------------------------------------
    protected void processTransaction(Properties props)
    {
        //System.out.println("model/StartShiftTransaction: processTransaction(Properties props): getting here!");
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("SessionStatusMessage") == true)
        {
            return sessionStatusMessage;
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
        else if(key.equals("AddScoutToShift") == true)
        {
            Properties sentValues = (Properties)value;
            String sentScoutID = sentValues.getProperty("ScoutID");
            searchForSelectedScout(sentScoutID);
            createShift(sentValues);

        }
        else if(key.equals("SearchForAvailableScouts"))
        {
            searchForAvailableScouts();
        }
        else if (key.equals("StartSession"))
        {
            Properties props = (Properties)value;
            startSession(props);
        }


        myRegistry.updateSubscribers(key, this);
    }

    // -------------------------------------------------------------
    private void startSession(Properties p)
    {
        currentSession = new Session();
        try {
            boolean openSessionFlag = currentSession.findOpenSession();
            if (openSessionFlag == true)
            {
                sessionStatusMessage = "ERROR: (Unexplained): A shift is already open!";
            }
            else
            {
                Enumeration propertyNames = p.propertyNames();
                while (propertyNames.hasMoreElements() == true)
                {
                    String nextKey = (String)propertyNames.nextElement();
                    String nextVal = p.getProperty(nextKey);
                    currentSession.stateChangeRequest(nextKey, nextVal);
                }
                currentSession.stateChangeRequest("EndingCash", "");
                currentSession.stateChangeRequest("TotalCheckTransactionsAmount", "");
                currentSession.stateChangeRequest("Notes", "");
                currentSession.update();
                sessionStatusMessage = (String) currentSession.getState("UpdateStatusMessage");
            }
        }
        catch (InvalidPrimaryKeyException excep)
        {
            sessionStatusMessage = "ERROR: (Unexplained): MULTIPLE shifts is already open!";
        }

    }

    private void createShift(Properties sV)
    {
        //get session id and add to properties object
        //create a new shift record
        Session openSession = new Session();
        try
        {
            String openSessionId = openSession.getOpenSessionID();
            sV.setProperty("SessionID", openSessionId);

            boolean scoutOnShift = checkIfScoutOnShift(openSessionId, sV.getProperty("ScoutID"));
            if(scoutOnShift == false)
            {
                Shift newShift = new Shift(sV);
                newShift.update();
                shiftStatusMessage = (String)newShift.getState("UpdateStatusMessage");
            }
            else
            {
                String selectedScoutID = sV.getProperty("ScoutID");
                removeScoutFromList(selectedScoutID);
                shiftStatusMessage = "ERROR: Scout is already on Shift.";
            }

            // DEBUG System.out.println(shiftStatusMessage);

        }
        catch (InvalidPrimaryKeyException e) {
            // DEBUG System.out.println(e);
            // DEBUG e.printStackTrace();
            shiftStatusMessage = "ERROR: Open Session ID not found.";
        }
    }

    //------------------------------------------------------
    protected void searchForAvailableScouts()
    {
        //Find all active scouts method
        ScoutCollection scoutCollection = new ScoutCollection();
        fullScoutList = scoutCollection.findActiveScoutsWithNameLike("", "");
    }

    //------------------------------------------------------
    protected void searchForSelectedScout(String scoutID)
    {
        ScoutCollection temp = new ScoutCollection();
        temp.setScoutList(fullScoutList);
        Scout chosenScout = temp.retrieve(scoutID); // retrieve method needed in ScoutCollection using scoutID
        selectedScoutList.add(chosenScout); // have a public method in ScoutCollection that just calls the private addScout method

    }

    // ------------------------------------------------------
    protected void removeScoutFromList(String scoutID)
    {
        ScoutCollection temp = new ScoutCollection();
        temp.setScoutList(fullScoutList);
        Scout chosenScout = temp.retrieve(scoutID); // retrieve method needed in ScoutCollection using scoutID
        selectedScoutList.remove(chosenScout);
    }

    // ---------------------------------------------------
    /** Checks if scout is already on shift/open session
     *
     * @param openSessionID     id of the session
     * @param scoutID           id of the scout
     * @return boolean          indicating whether the scout is currently on a shift
     */
    protected boolean checkIfScoutOnShift(String openSessionID, String scoutID)
    {
        Shift newShift = new Shift();

        try
        {
            if (newShift.findScoutShift(openSessionID, scoutID) == true)
            {
                return true;
            }
        }
        catch(InvalidPrimaryKeyException e)
        {
            shiftStatusMessage = "ERROR: Scout already added to current shift.";
        }
        // If scout is found in query, return true. In StartShiftView, if this returns true then display error message

        return false;
    }


    // Show the TreeSearchView first
    //------------------------------------------------------
    protected Scene createView()
    {
        //DEBUG System.out.println("model/StartShiftTransaction : createView(): getting here");
  // create our initial view
        View newView = ViewFactory.createView("StartShiftView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }
}
