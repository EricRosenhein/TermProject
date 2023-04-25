package model;

import exception.InvalidPrimaryKeyException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

public class Shift extends EntityBase
{
    // Instance variables

    private int shiftId;
    private int sessionId;
    private int scoutId;
    private String companionName;
    private String startTime;
    private String endTime;
    private int companionHours;

    private String updateStatusMessage = "";
    private static final String myTableName = "Shift";
    protected Properties dependencies;

    //---------------------------------------------------------------------
    /** Default constructor
     */
    public Shift()
    {
        super(myTableName);
        setDependencies();
        persistentState = new Properties();
    }

    //---------------------------------------------------------------------
    /** Overloaded Constructor that takes a Properties object containing the shift information
     *
     * @param shiftInfo     information about the shift
     */
    public Shift(Properties shiftInfo){
        super(myTableName);

        //DEBUG System.out.println("model/Shift: Shift(Properties shiftInfo): Inside constructor");

        setDependencies();

        persistentState = new Properties();

        Enumeration allKeys = shiftInfo.propertyNames();

        while (allKeys.hasMoreElements() == true)
        {
            String nextKey = (String)allKeys.nextElement();
            String nextValue = shiftInfo.getProperty(nextKey);

            if (nextValue != null)
            {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    //----------------------------------------------------------------------
    /** Sets the dependencies
     */
    private void setDependencies() {
        dependencies = new Properties();
        myRegistry.setDependencies(dependencies);
    }

    //----------------------------------------------------------------------
    /** Gets the state that is mapped to the given key
     *
     * @param key       key to use to retrieve its state
     * @return object   the value mapped to the key
     */
    public Object getState(String key) {
        if (key.equals("UpdateStatusMessage") == true)
            return updateStatusMessage;

        return persistentState.getProperty(key);
    }

    //----------------------------------------------------------------------
    /** Requests to change the current state
     *
     * @param key       key to map value to and for updating subscribers
     * @param value     value to be mapped to the key
     */
    public void stateChangeRequest(String key, Object value) {

        persistentState.setProperty(key, (String)value);
        myRegistry.updateSubscribers(key, this);
    }

    /** Called via the IView relationship */
    // ----------------------------------------------------------
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }



    // -----------------------------------------------------------------------------------
    /** Makes a call to update the database
     */
    public void update() {
        updateStateInDatabase();
    }

    // -----------------------------------------------------------------------------------
    /** Upddates the state of the shift in the database
     */
    private void updateStateInDatabase() {
        try {
            if (persistentState.getProperty("ID") != null) {
                Properties whereClause = new Properties();
                whereClause.setProperty("ID",
                        persistentState.getProperty("ID"));
                updatePersistentState(mySchema, persistentState, whereClause);
               /* updateStatusMessage = "Shift data for shift"
                        + persistentState.getProperty("SessionID")
                        + " updated successfully in database!"; */
                updateStatusMessage = "Scout shift data successfully updated in database!";
                /*
                updateStatusMessage = "Scout added to shift: "
                        + persistentState.getProperty("ID") + ", "
                        + persistentState.getProperty("SessionID") + ", "
                        + persistentState.getProperty("ScoutID")
                        + " updated successfully in database!";
                 */

            } else {
                Integer transactionId = insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("ID", "" + transactionId);
                /* updateStatusMessage = "Shift data for new Shift: "
                        + persistentState.getProperty("SessionID")
                        + " installed successfully in database!"; */
                updateStatusMessage = "Shift data for new Scout successfully added to database!";
            }
        } catch (SQLException ex) {
            updateStatusMessage = "ERROR: Error registering Shift data in database!";
        }
    }

    //------------------------------------------------------------------------------------------
    /** Populates the values of the shift from the data retrieved
     *
     * @param allDataRetrieved  vector with the data regarding the shift
     */
    private void populatePersistentState(Vector<Properties> allDataRetrieved)
    {
        Properties retrievedShiftData = allDataRetrieved.elementAt(0);

        persistentState = new Properties();

        Enumeration allKeys = retrievedShiftData.propertyNames();

        while (allKeys.hasMoreElements() == true)
        {
            String nextKey = (String) allKeys.nextElement();
            String nextValue = retrievedShiftData.getProperty(nextKey);

            if (nextValue != null)
            {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    // -----------------------------------------------------------------------------------
    /** Initializes the schema
     *
     * @param tableName     name of the table to initialize
     */
    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }
}
