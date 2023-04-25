package model;

import exception.InvalidPrimaryKeyException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

public class TransactionReceipt extends EntityBase
{
    private static final String myTableName = "Transaction";
    protected Properties dependencies;
    private String transactionReceiptStatusMessage = "";

    // Constructor
    public TransactionReceipt(String id) throws InvalidPrimaryKeyException {

        super(myTableName);
        setDependencies();

        String query = "SELECT * FROM " + myTableName + " WHERE (ID = " + id + ")";
        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null){
            int size = allDataRetrieved.size();
            if (size != 1){
                throw new InvalidPrimaryKeyException("Multiple Transaction Receipts matching ID : "+ id + " found.");
            } else{
                Properties receievedTransactionReceiptData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();
                Enumeration allKeys = receievedTransactionReceiptData.propertyNames();
                while (allKeys.hasMoreElements() == true){
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = receievedTransactionReceiptData.getProperty(nextKey);

                    if (nextValue != null){
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }
            }
        }
        // If no transaction receipt found for id, throw exception
        else{
            throw new InvalidPrimaryKeyException("ERROR: No Transaction Receipts matching id : "+ id +" found.");
        }
    }

    /** This is the second constructor - takes in a Properties object to populate the TransactionReceipt object with */
    //----------------------------------------------------------
    protected TransactionReceipt(Properties props)
    {
        super(myTableName);

        setDependencies();
        persistentState = new Properties();

        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements() == true) {
            String nextKey = (String) allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);
            if (nextValue != null) {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    /** Empty (default) constructor */
    //------------------------------------------------------------------------
    public TransactionReceipt()
    {
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
        if (key.equals("TransactionReceiptStatusMessage") == true)
            return transactionReceiptStatusMessage;

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

    // -----------------------------------------------------------------------------------
    private void updateStateInDatabase() {
        try {
            if (persistentState.getProperty("ID") != null) {
                // Update Transaction
                Properties whereClause = new Properties();
                whereClause.setProperty("ID",
                        persistentState.getProperty("ID"));
                updatePersistentState(mySchema, persistentState, whereClause);
                transactionReceiptStatusMessage = "TransactionReceipt data for TransactionReceipt: "
                        + persistentState.getProperty("ID") + ", "
                        + persistentState.getProperty("SessionID")
                        + " updated successfully in database!";
            } else {
                Integer transactionId = insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("ID", "" + transactionId);
                transactionReceiptStatusMessage = "Transaction data for new Transaction: "
                        + persistentState.getProperty("TransactionType")
                        + " installed successfully in database!";
            }
        } catch (SQLException ex) {
            transactionReceiptStatusMessage = "ERROR: Error registering transaction data in database!";
        }
    }

    // -----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }
}
