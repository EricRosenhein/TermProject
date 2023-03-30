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

public class Scout extends EntityBase implements IView {
    private static final String myTableName = "Scout";
    protected Properties dependencies;
    // gui
    private String updateStatusMessage = "";

    // constructor
    public Scout(String scoutId) throws  InvalidPrimaryKeyException {
        
    
		super(myTableName);
		setDependencies();
		String query = "SELECT * FROM " + myTableName + " WHERE (scoutId = " + scoutId + ")";
		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
		if (allDataRetrieved != null){
			int size = allDataRetrieved.size();
			if (size != 1){
				throw new InvalidPrimaryKeyException("Multiple scouts matching id : "+scoutId+" found.");
			} else{
				Properties retrievedScoutData = allDataRetrieved.elementAt(0);
				persistentState = new Properties();
				Enumeration allKeys = retrievedScoutData.propertyNames();
				while (allKeys.hasMoreElements() == true){
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedScoutData.getProperty(nextKey);
					// scoutId = Integer.parseInt(retrievedScoutData.getProperty("scoutId"));
					if (nextValue != null){
						persistentState.setProperty(nextKey, nextValue);
					}
				}
			}
		}
		// If no scout found for Id, throw exception
		else{
			throw new InvalidPrimaryKeyException("No account matching id : "+scoutId+" found.");
		}
	}

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

    public Scout() {
		super(myTableName);
		setDependencies();
		persistentState = new Properties();
	}

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
       
    }

    /** Called via the IView relationship */
    // ----------------------------------------------------------
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    public FindOldScout (String troopId) throws InvalidPrimaryKeyException {
        
        super(myTableName);
		setDependencies();
		String query = "SELECT * FROM " + myTableName + " WHERE (troopId = " + troopId + ")";
		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
		if (allDataRetrieved != null){
			int size = allDataRetrieved.size();
			if (size != 1){
				throw new InvalidPrimaryKeyException("Multiple scouts matching id : "+troopId+" found.");
			} else{
				Properties retrievedScoutData = allDataRetrieved.elementAt(0);
				persistentState = new Properties();
				Enumeration allKeys = retrievedScoutData.propertyNames();
				while (allKeys.hasMoreElements() == true){
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedScoutData.getProperty(nextKey);
					// scoutId = Integer.parseInt(retrievedScoutData.getProperty("scoutId"));
					if (nextValue != null){
						persistentState.setProperty(nextKey, nextValue);
					}
				}
			}
		}
		// If no scout found for Id, throw exception
		else{
			throw new InvalidPrimaryKeyException("No account matching id : "+troopId+" found.");
		}
        // DEBUG sytem.out.println("Scout: FindOldScout");
    }

    // -----------------------------------------------------------------------------------
    public void update() {
        updateStateInDatabase();
    }

    // -----------------------------------------------------------------------------------
    private void updateStateInDatabase() {
        try {
            if (persistentState.getProperty("scoutId") != null) {
                Properties whereClause = new Properties();
                whereClause.setProperty("scoutId",
                        persistentState.getProperty("scoutId"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Scout data for scoutId : "
                        + persistentState.getProperty("ScoutId")
                        + " updated successfully in database!";
            } else {
                Integer scoutId = insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("ScoutId", "" + scoutId);
                updateStatusMessage = "Scout data for new scout : "
                        + persistentState.getProperty("ScoutId")
                        + "installed successfully in database!";
            }
        } catch (SQLException ex) {
            updateStatusMessage = "Error regestering scout data in database!";
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
    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }
    
    // Prints out a description of the chosen book
    public String toString() {
        return "FirstName: " + persistentState.getProperty("FirstName") + "; LastName: "
                + persistentState.getProperty("LastName") + "; MiddleName: " + persistentState.getProperty("LastName")
                + "; DateOfBirth: " + persistentState.getProperty("DateOfBirth") + "; Eamil: "
                + persistentState.getProperty("Email")
                + "; TroopId: " + persistentState.getProperty("TroopId") + "; Status: "
                + persistentState.getProperty("Status") + "; DateStatusUpdated: "
                + persistentState.getProperty("DateStatusUpdated") + "\n";
    }

    // -------------------------------------------------------
    @Override
    public void subscribe(String key, IView subscriber) {
        // TODO Auto-generated method stub

    }

    // -------------------------------------------------------
    @Override
    public void unSubscribe(String key, IView subscriber) {
        // TODO Auto-generated method stub

    }
}
