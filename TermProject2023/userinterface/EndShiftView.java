package userinterface;

// system imports
import java.util.Properties;

import Utilities.GlobalData;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

// project imports
import impresario.IModel;
import model.Session;

public class EndShiftView extends View
{
    // Instance variables
    protected Text endCashDisplay;
    protected Text endCheckDisplay;
    protected TextField endTime;
    protected TextArea shiftNotes;
    protected ComboBox<String> transactionTypeComboBox;
    protected Button cancelButton;
    protected Button submitButton;

    protected MessageView statusLog;



    // TransactionReceipt object, TransactionReceipt collection?
    protected Session session;

    // ----------------------------------------------------------------------
    public EndShiftView(IModel shift) {
        super(shift, "EndShiftView");

        //DEBUG System.out.println("userinterface/EndShiftView: Constructor: getting here!");

        VBox container = new VBox(10);

        container.setPadding(new Insets(15, 5, 5, 5));
        container.setAlignment(Pos.CENTER);

        container.getChildren().add(createTitle());

        // DEBUG System.out.println("userinterface/EndShiftView: Constructor: About to create form contents.");

        container.getChildren().add(createFormContents());

        // DEBUG System.out.println("userinterface/EndShiftView: Constructor: Created form contents!");

        container.getChildren().add(createStatusLog("       "));

        container.getChildren().add(new Text("                           "));
        container.getChildren().add(new Text("                           "));

        getChildren().add(container);
        //DEBUG System.out.println("userinterface/" + this.getClass().getName() + ": about to populate fields");
        populateFields();
        //DEBUG System.out.println("userinterface/" + this.getClass().getName() + ": finished populate fields");

        myModel.subscribe("SessionStatusMessage", this);
    }

    // ----------------------------------------------------------------------

    /**
     * Creates the title for this view
     *
     * @return container    the container holding the title
     */
    private Node createTitle() {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Boy Scout Troop 209 Tree Sales: \nEnd A Shift ");
        titleText.setFont(Font.font("Serif", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.BURLYWOOD);
        container.getChildren().add(titleText);

        return container;
    }

    //------------------------------------------------------------

    /**
     * Create the main form content
     *
     * @return vbox     a VBox container holding the form content
     */
    private VBox createFormContents() {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text(" Select \"Confirm\" to confirm the totals displayed after entering any notes for the shift. ");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        // Set the font
        Font font = Font.font("Arial", FontWeight.BOLD, 12);


        //String endingCashValue = (String)myModel.getState("GetTotalCashSales");
        //Text endCash = new Text("Total Cash Sales: " + endingCashValue);
        endCashDisplay = new Text("");
        endCashDisplay.setWrappingWidth(400);
        endCashDisplay.setTextAlignment(TextAlignment.CENTER);
        endCashDisplay.setFill(Color.BLACK);
        grid.add(endCashDisplay,0,1,2,1);

        //String endingCheckValue = (String)myModel.getState("GetTotalCheckSales");
        //Text endCheck = new Text("Total Check Sales: " + endingCheckValue);
        endCheckDisplay = new Text("");
        endCheckDisplay.setWrappingWidth(400);
        endCheckDisplay.setTextAlignment(TextAlignment.CENTER);
        endCheckDisplay.setFill(Color.BLACK);
        grid.add(endCheckDisplay, 0, 2,2,1);

        Text endTimeLabel = new Text("End Time:");
        endTimeLabel.setFont(font);
        endTimeLabel.setWrappingWidth(150);
        endTimeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(endTimeLabel,0,3);

        endTime = new TextField();
        endTime.setEditable(true);
        grid.add(endTime,1,3);

        // Make this text field better -SW 04/20/2023, start cursor at top left
        Text notesLabel = new Text("Notes: ");
        notesLabel.setFont(font);
        notesLabel.setWrappingWidth(150);
        notesLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(notesLabel, 0, 4);

        shiftNotes = new TextArea();
        shiftNotes.setPrefSize(150, 100);
        shiftNotes.setEditable(true);
        shiftNotes.setWrapText(true);
        grid.add(shiftNotes,1,4);

        // Cancel and submit buttons
        cancelButton = new Button("Cancel");
        submitButton = new Button("Confirm");

        // Handle events for regular buttons
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e)
            {
                clearErrorMessage();
                myModel.stateChangeRequest("CancelTransaction", null);
            }
        });

        // Handle events for regular buttons
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e)
            {
                // Check if user picked Cash or Check for transaction type

                // Do action
                clearErrorMessage();
                processAction(e);
            }
        });

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().add(cancelButton);
        buttonContainer.getChildren().add(submitButton);

        vbox.getChildren().add(grid);
        //addTableColumns();
        vbox.getChildren().add(buttonContainer);
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 5 - added navigation buttons");

        return vbox;
    }

    //-------------------------------------------------------------------------------------------------
    /**
     * Populates fields of the form
     */
    private void populateFields()
    {
        String endTimeValue = (String)myModel.getState("GetEndTime");
        // DEBUG String ec = (String)myModel.getState("GetEndingCash");
        // DEBUG String tc = (String)myModel.getState("GetTotalCheckSales");

        // DEBUG System.out.println(ec);
        // DEBUG System.out.println(tc);

        endCashDisplay.setText("Ending Cash Value: $" + (String)myModel.getState("GetEndingCash"));
        endCheckDisplay.setText("Total Check Transactions Value: $" + (String)myModel.getState("GetTotalCheckSales"));
        endTime.setText(endTimeValue);
    }

    // ----------------------------------------------------------
//    /** Checks whether the transaction type is Cash or Check
//     *
//     * @param transactionType
//     */
//    protected void validateTranscationType(String transactionType)
//    {
//
//    }

    private Boolean checkTime(String tH, String tM)
    {
        if ((tH.length() != GlobalData.MAX_TIME_LENGTH) || (tM.length() != GlobalData.MAX_TIME_LENGTH))
            return false;
        else
        {
            try {
                int hour = Integer.parseInt(tH);
                int min = Integer.parseInt(tM);
                if ( hour < 0 || hour > 23 || min < 0 || min > 59 )
                    return false;
                else
                    return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }


    // ----------------------------------------------------------
    public void processAction(Event ev)
    {
        String startTimeValue = (String)myModel.getState("GetStartTime");

        String sHour = startTimeValue.substring(0,2);
        String sMin = startTimeValue.substring(3,5);
        int sHourInt = Integer.parseInt(sHour);
        int sMinInt = Integer.parseInt(sMin);

        // DEBUG System.out.println("userinterface/EndShiftView: sHour: " + sHour + "   sMin: " + sMin);


        String eTime =  endTime.getText();

        String eHour = eTime.substring(0,2);
        String eMin = eTime.substring(3,5);
        int eHourInt = Integer.parseInt(eHour);
        int eMinInt = Integer.parseInt(eMin);

        // DEBUG System.out.println("userinterface/EndShiftView: eHour: " + eHour + "   eMin: " + eMin);



        int shiftNotesLength = shiftNotes.getLength();

        // DEBUG System.out.println("userinterface/EndShiftView: processAction: shiftNotes: " + shiftNotes);

        if (shiftNotesLength > 200)
        {
            displayErrorMessage("ERROR: Shift Notes has a limit of 200 characters!");
        }
        if( (eTime == null) || (eTime.length() == 0))
        {
            displayErrorMessage("ERROR: Please enter an end time.");
        }
        else if ((checkTime(sHour,sMin) == false) || (checkTime(eHour,eMin) == false))
        {
            displayErrorMessage("ERROR: Times must be entered in 24 hour time format.");
        }
        //QC: Check if start time is later than end time
        else if ((sHourInt > eHourInt))
        {
            displayErrorMessage("ERROR: Start time is later than end time.");
        }
        else if (((sHourInt == eHourInt) && (sMinInt > eMinInt)))
        {
            displayErrorMessage("ERROR: Start time is later than end time.");

        }
        else if ((sHourInt == eHourInt) && (sMinInt == eMinInt))
        {
            displayErrorMessage("ERROR: Start time is equal to end time.");

        }
        else
        {
            Properties props = new Properties();
            props.setProperty("Notes", shiftNotes.getText());
            props.setProperty("EndTime", endTime.getText());
            myModel.stateChangeRequest("EndShift", props);
        }
    }


    // ----------------------------------------------------------------------
    /* Creates the status log that we use to display messages to the user
     *
     * @param initialMessage    the initial message we want to display
     * @return statusLog        object we use to display the message
     */
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    // ----------------------------------------------------------
    /* Displays message to user
     *
     * @param message   message to display
     */
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    // -------------------------------------------------------------
    /* Display error message to user
     *
     * @param message   error message to display
     */
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    // ----------------------------------------------------------
    /* Clears error message
     *
     */
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

    // ----------------------------------------------------------
    @Override
    public void updateState(String key, Object value)
    {
        if (key.equals("SessionStatusMessage") == true)
        {
            String msg = (String)value;
            if (msg.startsWith("ERR") == true)
                displayErrorMessage(msg);
            else
                displayMessage(msg);
        }
    }
}
