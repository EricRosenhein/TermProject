// specify the package
package model;

// system imports

import exception.InvalidPrimaryKeyException;
import impresario.IView;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Properties;
import java.util.Vector;

/** The class containing the TransactionReceiptCollection for the application */
// ==============================================================
public class TransactionReceiptCollection extends EntityBase implements IView
{
    private static final String myTableName = "TransactionReceipt";

    private Vector<TransactionReceipt> transactionReceiptList;

    // constructor for this class
    // ----------------------------------------------------------
    public TransactionReceiptCollection() {
        super(myTableName);
        setDependencies();
        transactionReceiptList = new Vector<>();
    }

    // -----------------------------------------------------------------
    private Vector doQuery(String query) {
        try {
            Vector allDataRetrieved = getSelectQueryResult(query);
            if (allDataRetrieved != null) {
                for (int index = 0; index < allDataRetrieved.size(); index++) {
                    Properties data = (Properties) allDataRetrieved.elementAt(index);
                    TransactionReceipt transactionReceipt = new TransactionReceipt(data);
                    transactionReceiptList.add(transactionReceipt);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        // DEBUG System.out.println("model/ScoutCollection: doQuery(String query): Printing scoutList \n" + scoutList);
        return transactionReceiptList;
    }

    /** find transaction receipts given an SessionID */
    //------------------------------------------------------------------------------------------
    public Vector findTransactionReceiptsWithSessionID(String ID)
    {
        String query = "SELECT * FROM " + myTableName + " WHERE (SessionID = " + ID + ")";

        return doQuery(query);
    }


    // --------------------------------------------------------------------------
    private void setDependencies() {
        Properties dependencies = new Properties();

        myRegistry.setDependencies(dependencies);
    }

    @Override
    public void updateState(String key, Object value) {

    }

    @Override
    public Object getState(String key) {
        return null;
    }

    @Override
    public void stateChangeRequest(String key, Object value) {

    }

    @Override
    protected void initializeSchema(String tableName) {

    }
}
