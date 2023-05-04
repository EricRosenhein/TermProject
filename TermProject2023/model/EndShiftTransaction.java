package model;

import exception.InvalidPrimaryKeyException;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    protected double totalCashSales = 0;
    protected double totalCheckSales = 0;
    protected double endingCash = 0;
    protected String endTime;
    protected String startTime;

    protected DecimalFormat df = new DecimalFormat("0.00");


    // Constructor
    //---------------------------------------------------------------------
    public EndShiftTransaction()
    {
        super();
        // DEBUG System.out.println("model/EndShiftTransaction: Constructor: getting here");
        try
        {
            currentSession = new Session();
            transactionReceiptCollection = new TransactionReceiptCollection();
            boolean sessionFound = currentSession.findOpenSession();
            // DEBUG System.out.println("model/EndShiftTransaction: Constructor: in try! sessionFound" + sessionFound);
            if(sessionFound == true)
            {
                String openSessionID = currentSession.getOpenSessionID();
                currentSession = new Session(openSessionID);
                transactionReceipts = transactionReceiptCollection.findTransactionReceiptsWithSessionID(openSessionID);

                calculateTotalSales(transactionReceipts);
                endTime = (String) currentSession.getState("EndTime");
                startTime = (String) currentSession.getState("StartTime");
            }
            else
            {
                sessionStatusMessage = "ERROR: No open Sessions found.";
            }
        }
        catch (InvalidPrimaryKeyException e)
        {
            // DEBUG System.out.println("model/EndShiftTransaction: Constructor: in catch!");
            sessionStatusMessage = "ERROR: Multiple open Sessions found.";
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------
    /** Sets dependencies **/
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("EndShift", "SessionStatusMessage");
        dependencies.setProperty("CancelEndShift", "CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    //-----------------------------------------------------------
    /** Retrieves the value/state mapped to the given key
     *
     * @param key       key to use for looking up its value
     * @return
     */
    public Object getState(String key)
    {
        if (key.equals("") == true)
        {
            return null;
        }
        else if (key.equals("SessionStatusMessage") == true) {
            return sessionStatusMessage;
        }
        else if(key.equals("GetEndingCash") == true)
        {
            return df.format(endingCash);
        }
        else if (key.equals("GetTotalCheckSales") == true)
        {
            return df.format(totalCheckSales);
        }
        else if (key.equals("GetEndTime") == true)
        {
            return endTime;
        }
        else if (key.equals("GetStartTime") == true)
        {
            return startTime;
        }
        else
        return null;
    }

    //-----------------------------------------------------------
    /** Changes the current state
     *
     * @param key
     * @param value     value to be mapped to the given key
     */
    public void stateChangeRequest(String key, Object value)
    {
        if (key.equals("DoYourJob") == true)
        {
            // DEBUG System.out.println("model/StartShiftTransaction: stateChangeRequest(): getting here!");

            doYourJob();
        }
        else if(key.equals("EndShift"))
        {
            endShift((Properties)value);
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

        double startingCash = Double.parseDouble((String)currentSession.getState("StartingCash"));
        endingCash = totalCashSales + startingCash;
    }


    //------------------------------------------------------
    /** Creates the view for End Shift
     *
     * @return currentScene     scene consisting of the End Shift View
     */
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
