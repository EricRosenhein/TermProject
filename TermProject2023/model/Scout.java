package model;

import exception.InvalidPrimaryKeyException;
import impresario.IView;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import exception.InvalidPrimaryKeyException;

/** This is the Scout class for the Application - interfaces with database table 'Scout'*/
//===========================================================================
public class Scout extends EntityBase implements IView{

    // Instance variables
    private static final String myTableName = "Scout";
    private String status = "Active";
    protected Properties dependencies;
    private String updateStatusMessage = "";

    /** Constructor that takes in the primary key value */
    // ----------------------------------------------------------------------------
    public Scout(String troopId) throws InvalidPrimaryKeyException {
        
		super(myTableName);

		setDependencies();

		String query = "SELECT * FROM " + myTableName + " WHERE (TroopId = " + troopId + ")";

		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // You must get at least one scout
		if (allDataRetrieved != null)
        {
            // Get the size of the vector
			int size = allDataRetrieved.size();

            // There should be EXACTLY one scout. If you get more than that, there is an error
			if (size != 1)
            {
				throw new InvalidPrimaryKeyException("Multiple scouts matching troop id : "+ troopId + " found.");
			}
            // Else, copy all the retrieved data into persisent state
            else
            {
				Properties retrievedScoutData = allDataRetrieved.elementAt(0);
				persistentState = new Properties();

				Enumeration allKeys = retrievedScoutData.propertyNames();

				while (allKeys.hasMoreElements() == true)
                {
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedScoutData.getProperty(nextKey);
				
					if (nextValue != null){
						persistentState.setProperty(nextKey, nextValue);
					}
				}
			}
		}
		// Else, if no scout found for troopId, throw exception
		else
        {
			throw new InvalidPrimaryKeyException("No scout matching troop id : "+ troopId +" found.");
		}
	}

    /** This is the second constructor - takes in a Properties object to populate the Scout object with */
    //---------------------------------------------------------------------
    public Scout(Properties scoutInfo){
		super(myTableName);
		setDependencies();
		persistentState = new Properties();
		Enumeration allKeys = scoutInfo.propertyNames();
		while (allKeys.hasMoreElements() == true){
			String nextKey = (String)allKeys.nextElement();
			String nextValue = scoutInfo.getProperty(nextKey);
			if (nextValue != null){
				persistentState.setProperty(nextKey, nextValue);
			}
		}
	}

    /** Empty (default) constructor */
    //------------------------------------------------------------------------
    public Scout()
    {
		super(myTableName);
		setDependencies();
		persistentState = new Properties();
    }

    //----------------------------------------------------------------------
    private void setDependencies()
    {
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
    public void stateChangeRequest(String key, Object value)
    {
	    persistentState.setProperty(key, (String)value);
    }

    /** Called via the IView relationship */
    // ----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    // -----------------------------------------------------------------------------------
    public void update()
    {
        updateStateInDatabase();
    }

    // -----------------------------------------------------------------------------------
    private void updateStateInDatabase() {
        try
        {
            // If scout ID is not null, then it is already in the database - update its data
            if (persistentState.getProperty("ID") != null)
            {
                Properties whereClause = new Properties();

                // Map the key to a value
                whereClause.setProperty("ID", persistentState.getProperty("ID"));

                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Scout data for scout : "
                    	+ persistentState.getProperty("LastName")
                        + ", "
			            + persistentState.getProperty("FirstName")
                        + " updated successfully in database!";
            }
            // Else, we insert the scout because there is no record of it in the database
            else
            {
                //DEBUG System.out.println(persistentState);
                //DEBUG System.out.println(mySchema);
                Integer scoutId = insertAutoIncrementalPersistentState(mySchema, persistentState);

                //Maps scout ID to its int value (after conversion from integer)
                persistentState.setProperty("ID", "" + scoutId);

                updateStatusMessage = "Scout data for new scout : "
                        + persistentState.getProperty("LastName") + ", " 
			            + persistentState.getProperty("FirstName")
                        + "installed successfully in database!";
            }
        } catch (SQLException ex) {
            updateStatusMessage = "Error registering scout data in database!";
        }
    }


    /**
     * This method is needed solely to enable the Scout information to be
     * displayable in a table
     */
    // --------------------------------------------------------------------------
    public Vector<String> getEntryListView() {
        Vector<String> v = new Vector<String>();

        v.addElement(persistentState.getProperty("ID"));
        v.addElement(persistentState.getProperty("LastName"));
        v.addElement(persistentState.getProperty("FirstName"));
        v.addElement(persistentState.getProperty("MiddleName"));
        v.addElement(persistentState.getProperty("DateOfBirth"));
        v.addElement(persistentState.getProperty("PhoneNumber"));
        v.addElement(persistentState.getProperty("Email"));
        v.addElement(persistentState.getProperty("TroopId"));
        v.addElement(persistentState.getProperty("Status"));
        v.addElement(persistentState.getProperty("DateStatusUpdated"));

        return v;
    }

    // -----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }
    
    /** Prints out a description of the chosen scout */
    // ------------------------------------------------------------------------------------
    public String toString()
    {
        return "FirstName: " + persistentState.getProperty("FirstName") + "; LastName: "
                + persistentState.getProperty("LastName") + "; MiddleName: " + persistentState.getProperty("LastName")
                + "; DateOfBirth: " + persistentState.getProperty("DateOfBirth") + "; Email: "
                + persistentState.getProperty("Email")
                + "; TroopId: " + persistentState.getProperty("TroopId") + "; Status: "
                + persistentState.getProperty("Status") + "; DateStatusUpdated: "
                + persistentState.getProperty("DateStatusUpdated") + "\n";
    }
    
}
