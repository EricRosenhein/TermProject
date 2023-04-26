package model;

import exception.InvalidPrimaryKeyException;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Properties;
import java.util.Vector;
import java.text.DecimalFormat;

public class EndShiftTransaction extends Transaction
{
    protected Session currentSession;
    protected TransactionReceiptCollection transactionReceiptCollection;
    Vector<TransactionReceipt> transactionReceipts;

 //   protected String shiftStatusMessage = ""; // un-needed bs object
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
        else if (key.equals("SessionStatusMessage") == true) {
            return sessionStatusMessage;
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

    //-----------------------------------------------------------
    /** Ends the current shift and sets the values for it
     *
     *     notes collected from the text fields
     */
    protected void endShift(Properties p)
    {
        // Set the end time for the session
        LocalDateTime ldt = LocalDateTime.now();
        String endTime = p.getProperty("EndTime");
        String notes = p.getProperty("Notes");

        // Set values of the session
        currentSession.stateChangeRequest("EndingCash", ""+ endingCash);
        currentSession.stateChangeRequest("TotalCheckTransactionsAmount", ""+ totalCheckSales);
        currentSession.stateChangeRequest("EndTime", endTime);
        currentSession.stateChangeRequest("Notes", notes);

        // Update session in the database
        currentSession.update();

        // Update session status message
        sessionStatusMessage = "Shift closed successfully!";
    }


    // ------------------------------------------------------------------------------------
    /** Calculates the total sales
     *
     * @param transactionReceipts      vector consisting of transactionReceipt objects
     */
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
                String transactionAmt = (String)currentTransactionReceipt.getState("TransactionAmount");
                totalCashSales += Double.parseDouble(transactionAmt);
            }
            else
            {
                String transactionAmt = (String)currentTransactionReceipt.getState("TransactionAmount");
                totalCheckSales += Double.parseDouble(transactionAmt);
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
