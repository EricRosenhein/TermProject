package model;

import exception.InvalidPrimaryKeyException;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Properties;
import java.util.Vector;

public class EndShiftTransaction extends Transaction
{
    protected Session currentSession;
    protected TransactionReceiptCollection transactionReceiptCollection;
    Vector<TransactionReceipt> transactionReceipts;

    protected String shiftStatusMessage = "";
    protected String sessionStatusMessage = "";

    // Constructor
    //---------------------------------------------------------------------
    public EndShiftTransaction()
    {
        super();
    }

    //----------------------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("EndSession", "SessionStatusMessage");
        dependencies.setProperty("EndShift", "ShiftStatusMessage");
        dependencies.setProperty("CancelEndShift", "CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("") == true)
        {
            return null;
        }
        else if (key.equals("ShiftStatusMessage") == true) {
            return shiftStatusMessage;
        }

        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        if (key.equals("DoYourJob") == true)
        {
            // DEBUG System.out.println("model/StartShiftTransaction: stateChangeRequest(): getting here!");

            doYourJob();
        }
        else if(key.equals("EndShift"))
        {
            endShift();
        }

        myRegistry.updateSubscribers(key, this);
    }

    protected void endShift()
    {
        try
        {
            boolean sessionFound = currentSession.findOpenSession();

            if(sessionFound == true)
            {
                String openSessionID = currentSession.getOpenSessionID();
                currentSession = new Session(openSessionID);
                transactionReceipts = transactionReceiptCollection.findTransactionReceiptsWithSessionID(openSessionID);

                calculateTotalSales(transactionReceipts);

            }
            else
            {
                sessionStatusMessage = "ERROR: No open Sessions found.";
            }
        }
        catch (InvalidPrimaryKeyException e)
        {
            sessionStatusMessage = "ERROR: Multiple open Sessions found.";
        }
    }

    protected void calculateTotalSales(Vector transactionReceipts)
    {
        // Add the TransactionAmount of all Receipts to TotalCashSales or TotalCheck sales based on PaymentMethod
        // Add StartingCash from Session and add to TotalCashSales to get the EndingCash
        // Present the EndingCash and TotalCheckSales to view for confirmation
        int totalCashSales = 0;
        int totalCheckSales = 0;
        int endingCash = 0;

        for(int i = 0; i < transactionReceipts.size(); i++)
        {
            TransactionReceipt  currentTransactionReceipt = (TransactionReceipt)transactionReceipts.get(i);

            if(currentTransactionReceipt.getState("PaymentMethod").equals("Cash"))
            {
                totalCashSales += Integer.parseInt((String)currentTransactionReceipt.getState("PaymentAmount"));
            }
            else
            {
                totalCheckSales += Integer.parseInt((String)currentTransactionReceipt.getState("PaymentAmount"));
            }
        }
    }

    // Show the TreeSearchView first
    //------------------------------------------------------
    protected Scene createView()
    {
        //DEBUG System.out.println("model/StartShiftTransaction : createView(): getting here");

        Scene currentScene = myViews.get("EndShiftView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("EndShiftView", this);
            currentScene = new Scene(newView);
            myViews.put("EndShiftView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }
}
