
// specify package
package model;

import java.security.InvalidKeyException;
// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

// project imports
import exception.InvalidPrimaryKeyException;
import database.*;

import impresario.IModel;
import impresario.IView;

import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

    /** This is the TreeType class for the Application  - interfaces with database table 'TreeType' */
    //=============================================================================
    public class TreeType extends EntityBase implements IView
    {
        private static final String myTableName = "TreeType";
        protected Properties dependencies;
        private String updateStatusMessage = "";


        //---------------------------------------------------------------------
        // Constructor accepting barcode prefix to search for an existing record
        public TreeType(String barPrefix) throws InvalidPrimaryKeyException
        {
            super(myTableName);
            System.out.println("TreeType.java constructor");

            setDependencies();
            String query = "SELECT * FROM " + myTableName + " WHERE (BarcodePrefix = '" + barPrefix + "')";

            Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

            // You must get one tree type at least
            if (allDataRetrieved.size() != 0) {
                int size = allDataRetrieved.size();

                // There should be EXACTLY one tree type. More than that is an error
                if (size != 1) {
                    throw new InvalidPrimaryKeyException("Multiple tree types matching barcode prefix : "
                            + barPrefix + " found.");
                } else {
                    // copy all the retrieved data into persistent state
                    Properties retrievedTreeTypeData = allDataRetrieved.elementAt(0);
                    persistentState = new Properties();

                    Enumeration allKeys = retrievedTreeTypeData.propertyNames();
                    while (allKeys.hasMoreElements() == true) {
                        String nextKey = (String) allKeys.nextElement();
                        String nextValue = retrievedTreeTypeData.getProperty(nextKey);

                        if (nextValue != null) {
                            persistentState.setProperty(nextKey, nextValue);
                        }
                    }

                }
            }
            // If no tree type found for this tree type barcode prefix, throw an exception
            else {
                throw new InvalidPrimaryKeyException("No tree type matching barcode prefix : "
                        + barPrefix + " found.");
            }
        }

        //---------------------------------------------------------------------
        // Constructor taking in properties argument to populate persistent state
        public TreeType(Properties treeTypeInfo)
        {
            super(myTableName);

            setDependencies();
            persistentState = new Properties();
            Enumeration allKeys = treeTypeInfo.propertyNames();
            while (allKeys.hasMoreElements() == true) {
                String nextKey = (String) allKeys.nextElement();
                String nextValue = treeTypeInfo.getProperty(nextKey);

                if (nextValue != null) {
                    persistentState.setProperty(nextKey, nextValue);
                }
            }
        }


        // --------------------------------------------------------------------
        // Empty Constructor
        public TreeType()
        {
            super(myTableName);
            setDependencies();
            persistentState = new Properties();
        }


        //---------------------------------------------------------------------
        private void setDependencies()
        {
            dependencies = new Properties();
            dependencies.setProperty("CancelAddTreeType", "CancelTransaction");
            dependencies.setProperty("AddTreeType", "TreeTypeUpdateStatusMessage");
            myRegistry.setDependencies(dependencies);
        }

        //---------------------------------------------------------------------
        public Object getState(String key) {
            if (key.equals("UpdateStatusMessage") == true)
                return updateStatusMessage;

            return persistentState.getProperty(key);
        }

        //---------------------------------------------------------------------
        public void stateChangeRequest (String key, Object value)
        {
            persistentState.setProperty(key, (String)value);
            myRegistry.updateSubscribers(key, this);
        }


        //---------------------------------------------------------------------
        public void updateState (String key, Object value)
        {
            stateChangeRequest(key, value);
        }

        public void update ()
        {
           updateStateInDatabase();
        }

        //---------------------------------------------------------------------
        private void updateStateInDatabase ()
        {
            try {
                 if (persistentState.getProperty("ID") != null) {
                        Properties whereClause = new Properties();
                        whereClause.setProperty("ID",
                                persistentState.getProperty("ID"));
                        updatePersistentState(mySchema, persistentState, whereClause);
                        updateStatusMessage = "Tree type data for tree type : " + persistentState.getProperty("TypeDescription") + " updated successfully in database!";
                 } else {
                        int treeTypeId =
                                insertAutoIncrementalPersistentState(mySchema, persistentState);
                        persistentState.setProperty("ID", "" + treeTypeId);
                        updateStatusMessage = "Tree type data for new tree type: " + persistentState.getProperty("TypeDescription" + "installed successfully in database!");
                 }
            } catch (SQLException ex) {
                    updateStatusMessage = "Error in installing tree type data in database!";
            }
                //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
        }

        //---------------------------------------------------------------------
        public Vector<String> getEntryListView ()
        {
             Vector<String> v = new Vector<String>();
             v.addElement(persistentState.getProperty("ID"));
             v.addElement(persistentState.getProperty("TypeDescription"));
             v.addElement(persistentState.getProperty("Cost"));
             v.addElement(persistentState.getProperty("BarcodePrefix"));

             return v;
        }

        // -----------------------------------------------------------------------------------
        protected void initializeSchema(String tableName) {
            if (mySchema == null) {
                mySchema = getSchemaInfo(tableName);
            }
        }

        // Invokes the toString() method to display info about the chosen tree type
        // -------------------------------------------------------
        public void display ()
        {
            System.out.println(toString());
        }

        //---------------------------------------------------------------------
        public String toString ()
        {
            return "Id: " + persistentState.getProperty("ID") +
                        "; Description: " + persistentState.getProperty("TypeDescription") +
                        "; Cost: " + persistentState.getProperty("Cost") +
                        "; Barcode Prefix: " + persistentState.getProperty("BarcodePrefix") + "\n";
        }

        //---------------------------------------------------------------------
        // Get methods
        public String getTreeTypeId ()
        {
            /*
            String treeTypeId = (String)getState("ID");
            return treeTypeId;
            */
            String treeTypeId = persistentState.getProperty("ID");
            return treeTypeId;
        }

        public String getTypeDescription()
        {
            String TypeDescription = persistentState.getProperty("TypeDescription");
            return TypeDescription;
        }

        public String getCost()
        {
            String Cost = persistentState.getProperty("Cost");
            return Cost;
        }

        public String getBarcodePrefix()
        {
            String BarcodePrefix = persistentState.getProperty("BarcodePrefix");
            return BarcodePrefix;
        }


        //---------------------------------------------------------------------
        // Set methods
        public void setTypeDescription(String TypeDescription)
        {
            persistentState.setProperty("TypeDescription", TypeDescription);
        }

        public void setCost(String Cost)
        {
            persistentState.setProperty("Cost", Cost);
        }

        public void setBarcodePrefix(String BarcodePrefix)
        {
            persistentState.setProperty("BarcodePrefix", BarcodePrefix);
        }

    }