package model;

// system imports
import javafx.scene.Scene;

import java.time.LocalDate;
import java.util.Properties;

// project imports
import exception.InvalidPrimaryKeyException;

import userinterface.SellTreeView;
import userinterface.View;
import userinterface.ViewFactory;

public class SellTreeTransaction extends Transaction
{
    // GUI Components
    protected Tree treeToSell;
    protected TreeType typeOfTreeToSell;

    private String transactionReceiptStatusMessage = "";
    protected String treeSearchStatusMessage = "";
    protected String treeTypeUpdateStatusMessage = "";

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("SearchTrees", "TreeSearchStatusMessage");
        dependencies.setProperty("InsertTransactionData", "TransactionReceiptStatusMessage");
        dependencies.setProperty("CancelSellTree", "CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("TransactionReceiptStatusMessage") == true)
        {
            return transactionReceiptStatusMessage;
        }
        else if(key.equals("TreeSearchStatusMessage"))
        {
            return treeSearchStatusMessage;
        }
        else
        if (key.equals("GetTreeToSell") == true)
        {
            return treeToSell;
        }

        return null;
    }

    // -----------------------------------------------------------------------
    // ScanTreeBarcodeView isn't getting to this method 04/13/2023 -SW
    protected void searchTrees(String barcode)
    {
        try
        {
            treeToSell = new Tree(barcode);
            if(treeToSell.getState("Status").equals("Sold"))
            {
                treeSearchStatusMessage = "ERROR: Tree with barcode: " + barcode + " has already been sold";
            }
            else
            {
                createAndShowSellTreeTransactionView();
            }
        }
        catch (InvalidPrimaryKeyException e)
        {
            treeSearchStatusMessage = "ERROR: No tree found with barcode: " + barcode;
        }
    }

    //----------------------------------------------------------------------
    /** Processes the transaction for selling a tree, it takes in the props info from the Sell Tree View fields
     *
     * @param props         Properties object from Sell Tree View
     */
    protected void processTransaction(Properties props)
    {
        // DEBUG System.out.println("model/SellTreeTransaction: processTransaction(): getting here");
        // Get the tree's status
        String treeCurrentStatus = (String)treeToSell.getState("Status");
        // DEBUG System.out.println("model/SellTreeTransaction: processTransaction(): Tree status is " + treeCurrentStatus);
        // If tree status is sold, display an error to user
        if (treeCurrentStatus.equals("Sold") == true)
            transactionReceiptStatusMessage = "ERROR: Tree with barcode: " + treeToSell.getState("Barcode") + " is already sold!";
        // Else, we will delete the tree that the user wants to buy
        else
        {
            Session session = new Session();
            try {
                String openSessionId = session.getOpenSessionID();
                String currentDate = (String)props.get("DateStatusUpdated");
                String notes = (String)props.get("Notes");

                // insert transaction into database
                props.setProperty("SessionID", openSessionId);
                props.remove("Notes");
                TransactionReceipt transactionReceipt = new TransactionReceipt(props);
                transactionReceipt.update();
                transactionReceiptStatusMessage = (String) transactionReceipt.getState("TransactionReceiptStatusMessage");

            } catch(InvalidPrimaryKeyException e) {
                transactionReceiptStatusMessage = "ERROR: No open session!";
            }
            }
        }

        // Try to create a Transaction object so we can initialize its table in database
//        try {
//            TransactionReceipt transactionReceipt = new TransactionReceipt(props);
//            transactionReceipt.update();
//            System.out.println("TransactionReceipt object made");
//        }
//        catch (Exception ex)
//        {
//            System.out.println("SellTreeTransaction : processTransaction(Properties props) - could not make TransactionReceipt object!");
//        }
//        TransactionReceipt transactionReceipt = new TransactionReceipt(props);
//        transactionReceipt.update();
//        transactionReceiptStatusMessage = (String)transactionReceipt.getState("TransactionReceiptStatusMessage");
//        System.out.println("TransactionReceipt object made");
//    }


    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        if (key.equals("DoYourJob") == true)
        {
            doYourJob();
        }
        /*
        else
        if (key.equals("SellTree") == true)
        {
            // Do thing
            processTransaction((Properties)value);
        }
         */
        else
        if (key.equals("SearchTrees") == true)
        {
            // Do thing
            searchTrees((String)value);
        }
        else if(key.equals("InsertTransactionData"))
        {
            // Process trans data
            processTransaction((Properties) value);
        }

        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the frame
     */
    //------------------------------------------------------
    protected Scene createView()
    {
        Scene currentScene = myViews.get("ScanTreeBarcodeView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("ScanTreeBarcodeView", this);
            currentScene = new Scene(newView);
            myViews.put("ScanTreeBarcodeView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }

    /** Creates and shows Sell A Tree view
     *
     */
    //------------------------------------------------------
    private void createAndShowSellTreeTransactionView()
    {
        // create our initial view
        View newView = ViewFactory.createView("SellTreeView", this);
        Scene currentScene = new Scene(newView);
        myViews.put("SellTreeView", currentScene);
        swapToView(currentScene);
    }
}
