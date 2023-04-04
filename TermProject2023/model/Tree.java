// specify package
package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

// project imports
import exception.InvalidPrimaryKeyException;
import impresario.IView;

import exception.InvalidPrimaryKeyException;

/** This is the Tree class for the Application - interfaces with database table 'Tree'*/
//===========================================================================
public class Tree extends EntityBase implements IView {

    //Instance Variables
    private static final String myTableName = "Tree";
    protected Properties dependencies;
    private String updateStatusMessage = "";
    private String deleteStatusMessage = "";

    private boolean oldFlag = true;

    //---------------------------------------------------------------------
    // Constructor accepting barcode to search for an existing record
    public Tree(String bar) throws InvalidPrimaryKeyException {

        super(myTableName);
        setDependencies();
        oldFlag = true;
        String query = "SELECT * FROM " + myTableName + " WHERE (Barcode = " + bar + ")";
        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
        if (allDataRetrieved != null){
            int size = allDataRetrieved.size();
            if (size != 1){
                oldFlag = false; // necessary?
                throw new InvalidPrimaryKeyException("Multiple trees matching barcode : "+ bar + " found.");
            } else{
                Properties retrievedTreeData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();
                Enumeration allKeys = retrievedTreeData.propertyNames();
                while (allKeys.hasMoreElements() == true){
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedTreeData.getProperty(nextKey);

                    if (nextValue != null){
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }
            }
        }
        // If no tree found for barcode, throw exception
        else{
            oldFlag = false; // necessary?
            throw new InvalidPrimaryKeyException("No trees matching barcode : "+ bar +" found.");
        }
    }

    /** This is the second constructor - takes in a Properties object to populate the Tree object with */
    //---------------------------------------------------------------------
    public Tree(Properties treeInfo){
        super(myTableName);
        setDependencies();
        persistentState = new Properties();
        Enumeration allKeys = treeInfo.propertyNames();
        while (allKeys.hasMoreElements() == true){
            String nextKey = (String)allKeys.nextElement();
            String nextValue = treeInfo.getProperty(nextKey);
            if (nextValue != null){
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    /** Empty (default) constructor */
    //------------------------------------------------------------------------
    public Tree() {
        super(myTableName);
        setDependencies();
        persistentState = new Properties();
    }

    //----------------------------------------------------------------------
    private void setDependencies() {
        dependencies = new Properties();
        myRegistry.setDependencies(dependencies);
    }

    // ----------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("UpdateStatusMessage") == true)
        {
            return updateStatusMessage;
        }
        else if (key.equals("DeleteStatusMessage") == true)
        {
            return deleteStatusMessage;
        }

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



    // -----------------------------------------------------------------------------------
    public void update() {
        updateStateInDatabase();
    }
    public void delete() {
        deleteStateInDatabase();
    }

    // -----------------------------------------------------------------------------------
    private void updateStateInDatabase() {
        try {
            if (oldFlag == true) {
                Properties whereClause = new Properties();
                whereClause.setProperty("Barcode",
                        persistentState.getProperty("Barcode"));
                updatePersistentState(mySchema, persistentState, whereClause);
                oldFlag = true;
                updateStatusMessage = "Tree data for tree : "
                        + persistentState.getProperty("Barcode")
                        + " updated successfully in database!";
            } else {
                insertPersistentState(mySchema, persistentState);
                oldFlag = true;
                updateStatusMessage = "Tree data for new tree : "
                        + persistentState.getProperty("Barcode")
                        + "installed successfully in database!";
            }
        } catch (SQLException ex) {
            updateStatusMessage = "Error registering tree data in database!";
        }
    }

    //------------------------------------------------------------------
    private void deleteStateInDatabase( ) // NOT NEEDED IN OTHER CLASSES -- REFER TO DELETE TREE SEQUENCE DIAGRAM
    {
        try
        {
            Properties whereClause = new Properties();
            whereClause.setProperty ( "Barcode", persistentState.getProperty( "Barcode" ) );
            deletePersistentState( mySchema, whereClause );
            deleteStatusMessage =  "The Tree with barcode " +persistentState.getProperty("Barcode") + " DELETED successfully!";
        }
        catch ( SQLException ex )
        {
            deleteStatusMessage = "Error in deleting tree data in database!";
        }
    }

    /**
     * This method is needed solely to enable the Tree information to be
     * displayable in a table
     */
    // --------------------------------------------------------------------------
    public Vector<String> getEntryListView() {
        Vector<String> v = new Vector<String>();

        v.addElement(persistentState.getProperty("Barcode"));
        v.addElement(persistentState.getProperty("TreeType"));
        v.addElement(persistentState.getProperty("Notes"));
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


    // -----------------------------------------------------------------------------------
    public String getBarcode()
    {
        String barcode = persistentState.getProperty("Barcode");
        return barcode;
    }


    // -----------------------------------------------------------------------------------
    public String getNotes()
    {
        String notes = persistentState.getProperty("Notes");
        return notes;
    }


    // Prints out a description of the chosen tree
    // ----------------------------------------------------------------------------------
    public String toString() {
        return "Barcode: " + persistentState.getProperty("Barcode") + "; TreeType: "
                + persistentState.getProperty("TreeType") + "; Notes: " + persistentState.getProperty("Notes")
                + "; Status: " + persistentState.getProperty("Status") + "; DateStatusUpdated: "
                + persistentState.getProperty("DateStatusUpdated") + "\n";
    }


}
