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

public class RemoveScoutTransaction extends EntityBase implements IView {
    private static final String myTableName = "Scout";
    protected Properties dependencies;
    // gui
    private String updateStatusMessage = "";

    // constructor
    public Scout(String TroopId) throws InvalidPrimaryKeyException{
		super(myTableName);
		setDependencies();
		String query = "SELECT * FROM " + myTableName + " WHERE (TroopId = " + TroopId + ")";
		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
		if (allDataRetrieved != null){
			int size = allDataRetrieved.size();
			if (size != 1){
				throw new InvalidPrimaryKeyException("Multiple scouts matching id : "+TroopId+" found.");
			} else{
				Properties retrievedScoutData = allDataRetrieved.elementAt(0);
				persistentState = new Properties();
				Enumeration allKeys = retrievedScoutData.propertyNames();
				while (allKeys.hasMoreElements() == true){
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedScoutData.getProperty(nextKey);
					// TroopId = Integer.parseInt(retrievedScoutData.getProperty("TroopId"));
					if (nextValue != null){
						persistentState.setProperty(nextKey, nextValue);
					}
				}
			}
		}
		// If no scout found for Id, throw exception
		else{
			throw new InvalidPrimaryKeyException("No account matching id : "+TroopId+" found.");
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
        dependencies.setProperty("CancelRemoveScout", "CancelTransaction");
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
        if (key.equals("RemoveScout")) {
            processRemoveScout((Properties) value);
        }
        myRegistry.updateSubscribers(key, this);
    }

    /** Called via the IView relationship */
    // ----------------------------------------------------------
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    // -----------------------------------------------------------------------------------
    public void update() {
        updateStateInDatabase();
    }

    // -----------------------------------------------------------------------------------
    private void updateStateInDatabase() {
        try {
            if (persistentState.getProperty("TroopId") != null) {
                Properties whereClause = new Properties();
                whereClause.setProperty("TroopId",
                        persistentState.getProperty("TroopId"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Scout data for TroopId : "
                        + persistentState.getProperty("TroopId")
                        + " removed successfully in database!";
            } else {
                Integer scoutId = insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("ScoutId", "" + scoutId);
                updateStatusMessage = "Scout data for new scout : "
                        + persistentState.getProperty("ScoutId")
                        + "removed successfully in database!";
            }
        } catch (SQLException ex) {
            updateStatusMessage = "Error removing scout data in database!";
        }
    }

    protected void createAndShowScoutView() {
        // create our new view
        View newView = ViewFactory.createView("ScoutView", this);
        Scene newScene = new Scene(newView);

        // make the view visible by installing it into the frame
        swapToView(newScene);
    }

    private void processRemoveScout(Properties scoutInfo) {
        persistentState = new Properties();
        Enumeration allKeys = scoutInfo.propertyNames();
        while (allKeys.hasMoreElements()) {
            String nextKey = (String) allKeys.nextElement();
            String nextValue = scoutInfo.getProperty(nextKey);

            if (nextValue != null) {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
        updateStateInDatabase();
    }

    /**
     * This method is needed solely to enable the Scout information to be
     * displayable in a table
     */
    // --------------------------------------------------------------------------
    public Vector<String> getEntryListView() {
        Vector<String> v = new Vector<String>();

        v.addElement(persistentState.getProperty("ScoutId"));
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
}
