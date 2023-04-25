package userinterface;

// system imports
import java.util.Properties;
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
    protected TextField transactionAmount;
    protected TextField totalCashSales;
    protected TextField totalCheckSales;
    protected TextField startingCash;
    protected TextField endingCash;
    protected TextField shiftNotes;
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

        container.getChildren().add(createFormContents());

        container.getChildren().add(createStatusLog("       "));

        container.getChildren().add(new Text("                           "));
        container.getChildren().add(new Text("                           "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("ShiftUpdateStatusMessage", this);
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

        Text prompt = new Text(" End Shift ");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        // Set the font
        Font font = Font.font("Arial", FontWeight.BOLD, 12);

        //data fields
        Text transactionTypeLabel = new Text("Transaction Type: ");
        transactionTypeLabel.setFont(font);
        transactionTypeLabel.setWrappingWidth(150);
        transactionTypeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(transactionTypeLabel, 0, 1);

        transactionTypeComboBox = new ComboBox<>();
        transactionTypeComboBox.getItems().addAll("Cash", "Check");
        grid.add(transactionTypeComboBox, 1, 1);

        Text transactionAmountLabel = new Text("Transaction Amount: ");
        transactionAmountLabel.setFont(font);
        transactionAmountLabel.setWrappingWidth(150);
        transactionAmountLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(transactionAmountLabel, 0, 2);

        transactionAmount = new TextField();
        transactionAmount.setEditable(true);
        grid.add(transactionAmount, 1, 2);

        Text totalCashSalesLabel = new Text("Total Cash Sales: ");
        totalCashSalesLabel.setFont(font);
        totalCashSalesLabel.setWrappingWidth(150);
        totalCashSalesLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(totalCashSalesLabel, 0, 3);

        totalCashSales = new TextField();
        totalCashSales.setEditable(true);
        grid.add(totalCashSales, 1, 3);

        Text totalCheckSalesLabel = new Text("Total Check Sales: ");
        totalCheckSalesLabel.setFont(font);
        totalCheckSalesLabel.setWrappingWidth(150);
        totalCheckSalesLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(totalCheckSalesLabel, 0, 4);

        totalCheckSales = new TextField();
        totalCheckSales.setEditable(true);
        grid.add(totalCheckSales, 1, 4);

        // Maybe move this up?
        Text startingCashLabel = new Text("Starting Cash: ");
        startingCashLabel.setFont(font);
        startingCashLabel.setWrappingWidth(150);
        startingCashLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(startingCashLabel, 0, 5);

        startingCash = new TextField();
        startingCash.setEditable(true);
        grid.add(startingCash, 1, 5);

        Text endingCashLabel = new Text("Ending Cash: ");
        endingCashLabel.setFont(font);
        endingCashLabel.setWrappingWidth(150);
        endingCashLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(endingCashLabel, 0, 6);

        endingCash = new TextField();
        endingCash.setEditable(true);
        grid.add(endingCash, 1, 6);

        Text shiftNotesLabel = new Text("Notes: ");
        shiftNotesLabel.setFont(font);
        shiftNotesLabel.setWrappingWidth(150);
        shiftNotesLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(shiftNotesLabel, 0, 7);

        // Make this text field better -SW 04/20/2023
        shiftNotes = new TextField();
        shiftNotes.setEditable(true);
        grid.add(shiftNotes, 1, 7);

        // Cancel and submit buttons
        cancelButton = new Button("Cancel");
        submitButton = new Button("Submit");

        // Handle events for regular buttons
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e)
            {
                clearErrorMessage();
                myModel.stateChangeRequest("CancelTransaction", "");
            }
        });

        // Handle events for regular buttons
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e)
            {

                // Do action
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

    }

    // ----------------------------------------------------------
    /** Checks whether the transaction type is Cash or Check
     *
     * @param transactionType
     */
    protected void validateTranscationType(String transactionType)
    {

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
        // Do stuff
    }
}
