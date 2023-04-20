// specify package
package model;

// system imports
import exception.InvalidPrimaryKeyException;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;


// project imports


public class Session extends EntityBase
{
    private static final String myTableName = "Session";
    protected Properties dependencies;

    //----------------------------------------------------------------------
    private String updateStatusMessage = "";
    // check for open session everyTime the program boots to update initial gui appropriately
    // empty constructor
    // method to find open session - "SELECT * FROM Session WHERE ((EndTime IS NULL) OR (EndTime = ''))
    public Session(){
        super(myTableName);
        setDependencies();
        persistentState = new Properties();
    }
    /** This is the second constructor - takes in a Properties object to populate the Scout object with */
    //---------------------------------------------------------------------
    public Session(Properties sessionInfo){
        super(myTableName);

        //DEBUG System.out.println("userinterface/Scout: Scout(Properties scoutInfo): Start of method");

        setDependencies();
        persistentState = new Properties();
        Enumeration allKeys = sessionInfo.propertyNames();
        while (allKeys.hasMoreElements() == true){
            String nextKey = (String)allKeys.nextElement();
            String nextValue = sessionInfo.getProperty(nextKey);
            if (nextValue != null){
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    //------------------------------------------------------------------------------------------
    private void populatePersistentState(Vector<Properties> allDataRetrieved)
    {
        Properties retrievedSessionData = allDataRetrieved.elementAt(0);
        persistentState = new Properties();
        Enumeration allKeys = retrievedSessionData.propertyNames();
        while (allKeys.hasMoreElements() == true) {
            String nextKey = (String) allKeys.nextElement();
            String nextValue = retrievedSessionData.getProperty(nextKey);

            if (nextValue != null) {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    /** find session given an ID */
    //------------------------------------------------------------------------------------------
    public void findSessionWithID(String ID) throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM " + myTableName + " WHERE (ID = " + ID + ")";
        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
        if (allDataRetrieved != null) {
            int size = allDataRetrieved.size();
            if (size > 1) {
                throw new InvalidPrimaryKeyException("ERROR: Multiple Sessions with ID: " + ID + " found.");
            } else if (size == 1) {
                populatePersistentState(allDataRetrieved);
            }
            else
            {
                throw new InvalidPrimaryKeyException("ERROR: No session with ID: " + ID+ " found!");
            }
        } else {
            throw new InvalidPrimaryKeyException("ERROR: No session with ID: " + ID+ " found!");
        }
    }

    // ------------------------------------------------------------------------------------------
    public Boolean findOpenSession() throws InvalidPrimaryKeyException {
        // method to find open session - "SELECT * FROM Session WHERE ((EndTime IS NULL) OR (EndTime = ''))
        String query = "SELECT * FROM " + myTableName + " WHERE ((EndTime IS NULL) OR (EndTime = ''))";
        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            int size = allDataRetrieved.size();
            if (size > 1) {
                throw new InvalidPrimaryKeyException("ERROR: Multiple open Sessions found.");
            } else if (size == 1) {

                populatePersistentState(allDataRetrieved);
                return true;
            }
            else // size is zero
            {
                return false;
            }
        } else {
            return false;
        }
    }

    // ------------------------------------------------------------------------------------------
    public String getOpenSessionID() throws InvalidPrimaryKeyException
    {
        String query = "SELECT * FROM " + myTableName + " WHERE ((EndTime IS NULL) OR (EndTime = ''))";
        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            int size = allDataRetrieved.size();
            if (size > 1) {
                throw new InvalidPrimaryKeyException("ERROR: Multiple open Sessions found.");
            } else if (size == 1) {

                populatePersistentState(allDataRetrieved);
                String openSessionID = persistentState.getProperty("ID");
                return openSessionID;
            } else // size is zero
            {
                throw new InvalidPrimaryKeyException("ERROR: No open Sessions found.");
            }
        }
        else //null
        {
            throw new InvalidPrimaryKeyException("ERROR: No open Sessions found.");
        }
    }

    //----------------------------------------------------------------------
    private void setDependencies() {
        dependencies = new Properties();
        myRegistry.setDependencies(dependencies);
    }
    // ----------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("UpdateStatusMessage") == true)
            return updateStatusMessage;

        return persistentState.getProperty(key);
    }

    // ----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {

        persistentState.setProperty(key, (String)value);
        myRegistry.updateSubscribers(key, this);
    }

    /** Called via the IView relationship */
    // ----------------------------------------------------------
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }
    // -----------------------------------------------------------------
    public void update() {
        updateStateInDatabase();
    }

    // -----------------------------------------------------------------------------------
    private void updateStateInDatabase() {
        try {
            if (persistentState.getProperty("ID") != null) {
                Properties whereClause = new Properties();
                whereClause.setProperty("ID",
                        persistentState.getProperty("ID"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Session: "
                        + persistentState.getProperty("ID")     // May have to change these messages
                        + " started successfully!";
            } else {
                Integer sessionId = insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("ID", "" + sessionId);
                updateStatusMessage = "Session: "
                        + persistentState.getProperty("ID")     // May have to change these messages
                        + " started successfully!";
            }
        } catch (SQLException ex) {
            updateStatusMessage = "ERROR: Error starting a session!";
        }
    }
    // -----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }
    // ----------------------------------------------------------------------------------
    public String toString() {
        return "SessionID: " + persistentState.getProperty("ID") + "; Start Date: "
                + persistentState.getProperty("StartDate") + "; Start Time: " + persistentState.getProperty("StartTime")
                + "; End Time: " + persistentState.getProperty("EndTime") + "; Starting Cash: "
                + persistentState.getProperty("StartingCash")
                + "; Ending Cash: " + persistentState.getProperty("EndingCash") + "; Total Check Transactions Amount: "
                + persistentState.getProperty("TotalCheckTransactionsAmount") + "; Notes: "
                + persistentState.getProperty("Notes") + "\n";
    }

}
