package model;

// system imports
import javafx.scene.Scene;
import java.util.Properties;

// project imports
import exception.InvalidPrimaryKeyException;

import userinterface.View;
import userinterface.ViewFactory;

public class SellTreeTransaction extends Transaction
{
    // GUI Components
    protected Tree treeToSell;
    protected TreeType typeOfTreeToSell;

    private String transactionErrorMessage = "";
    protected String treeSearchStatusMessage = "";
    protected String treeUpdateStatusMessage = "";

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("CancelSellTree", "CancelTransaction");
        dependencies.setProperty("SellTree", "SellTreeStatusMessage");

        myRegistry.setDependencies(dependencies);
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
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

            createAndShowSellTreeTransactionView();
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
        // Get the tree's status
        String treeCurrentStatus = (String)treeToSell.getState("Status");

        // If tree status is sold, display an error to user
        if (treeCurrentStatus.equals("Sold") == true)
            treeUpdateStatusMessage = "ERROR: Tree with barcode: " + treeToSell.getState("Barcode") + " is already sold!";
        // Else, we will delete the tree that the user wants to buy
        else
        {
            treeToSell.delete();
            treeUpdateStatusMessage = (String) treeToSell.getState("DeleteStatusMessage");
        }

        // Try to create a Transaction object so we can initialize its table in database
        try {
            Transaction transaction = new Transaction(props) {
                @Override
                protected void setDependencies() {

                }

                @Override
                protected Scene createView() {
                    return null;
                }

                @Override
                public Object getState(String key) {
                    return null;
                }

                @Override
                public void stateChangeRequest(String key, Object value) {

                }
            };
        }
        catch (Exception ex)
        {
            System.out.println("SellTreeTransaction : processTransaction(Properties props) - could not make Transaction object!");
        }
    }


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
