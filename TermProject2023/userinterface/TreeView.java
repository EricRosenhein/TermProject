package userinterface;

// system imports
import java.util.Properties;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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

public abstract class TreeView extends View
{
    protected TextField barcode;
    protected TextField notes;
    protected ComboBox statusComboBox;
    protected Boolean statusComboBoxFlag = getStatusComboBoxFlag();
    protected MessageView statusLog;


    // --------------------------------------------------------------
    /** Constructor which will initialize the GUI components for the given model
     *
     * @param tree          model which will determine which view we are making
     * @param instructions  string to put into the prompt
     */
    public TreeView(IModel tree, String instructions)
    {
        super(tree, "TreeView");

        VBox container = new VBox(10);

        container.setPadding(new Insets(15, 5, 5, 5));
        container.setAlignment(Pos.CENTER);

        container.getChildren().add(createTitle());

        container.getChildren().add(createFormContents(instructions));

        container.getChildren().add(createStatusLog(" "));

        getChildren().add(container);

        container.getChildren().add(new Text("                           "));
        container.getChildren().add(new Text("                           "));

        populateFields();

        myModel.subscribe("TreeUpdateStatusMessage", this);

    }

    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(getTitleText());
        titleText.setFont(Font.font("Serif", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.BURLYWOOD);
        container.getChildren().add(titleText);

        return container;
    }

    // --------------------------------------------------------------
    // Abstract methods for subclasses to define
    protected abstract void populateFields();

    // --------------------------------------------------------------
    protected String getTitleText()
    {
        return " Boy Scout Troop 209 Tree Sales: ";
    }

    // --------------------------------------------------------------
    /* Creates the GUI components for the view
     *
     * @return grid     the grid that will hold the GUI components
     */
    protected Node createFormContents(String instructions)
    {
        VBox vbox = new VBox(10);

        // Grid to hold the components
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Prompt for the user
        Text prompt = new Text(instructions);
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        // Set the font
        Font font = Font.font("Arial", FontWeight.BOLD, 12);

        // GUI components
        Text barcodeLabel = new Text("Tree Barcode: ");
        barcodeLabel.setFont(font);
        barcodeLabel.setWrappingWidth(150);
        barcodeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(barcodeLabel, 0, 1);

        barcode = new TextField();
        setBarcodeEditableFlag(barcode, getBarcodeEditableFlag());
        grid.add(barcode, 1, 1);


        Text notesLabel = new Text("Notes: ");
        notesLabel.setFont(font);
        notesLabel.setWrappingWidth(150);
        notesLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(notesLabel, 0, 2);

        notes = new TextField();
        notes.setEditable(true);
        notes.setPrefHeight(50);
        notes.setPrefWidth(25);
        grid.add(notes, 1, 2);


        if (statusComboBoxFlag == true)
        {
            Text statusBoxLabel = new Text("Status: ");
            statusBoxLabel.setFont(font);
            statusBoxLabel.setWrappingWidth(150);
            statusBoxLabel.setTextAlignment(TextAlignment.RIGHT);
            grid.add(statusBoxLabel, 0, 3);

            statusComboBox = new ComboBox();
            statusComboBox.getItems().addAll("Available", "Sold", "Damaged");
            grid.add(statusComboBox, 1, 3);
        }



        Button cancelButton = new Button("Cancel");
        Button submitButton = new Button("Submit");

        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                clearErrorMessage();
                myModel.stateChangeRequest("CancelTransaction", null);

            }
        });

        submitButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                clearErrorMessage();
                validateTree();
            }
        });

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);

        buttonContainer.getChildren().add(cancelButton);
        buttonContainer.getChildren().add(submitButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(buttonContainer);

        return vbox;
    }

    // ---------------------------------------------------------------------
    /** Sets the truth value of the given text field
     *
     * @param tf    text field to change
     * @param flag  truth value to set text field to (true/false)
     */
    protected void setBarcodeEditableFlag(TextField tf, boolean flag)
    {
        tf.setEditable(flag);
    }

    // ---------------------------------------------------------------------
    protected boolean getBarcodeEditableFlag()
    {
        return true;
    }

    // ---------------------------------------------------------------------
    protected boolean getStatusComboBoxFlag()
    {
        return true;
    }

    // ----------------------------------------------------------------------
    /** Validates tree info for Add Tree & Update Tree
     *
     */
    protected void validateTree()
    {
        clearErrorMessage();

        // All fields completed validation
        if ((barcode.getText() == null) || (barcode.getText().length() == 0))
        {
            displayErrorMessage("ERROR: Please complete the entire form.");
        }
        // Barcode validation
        else if (barcode.getText().length() != 5) {
            displayErrorMessage("ERROR: Barcode must be 5 integers long!");
        }
        // Barcode parse validation
        else if (checkBarcode(barcode.getText()) == false) {
            displayErrorMessage("ERROR: Barcode must contain only numbers!");
        }
        else
        {
            getOperation();
        }

    }

    // ----------------------------------------------------------------------
    /**
     * @param key
     * @param value
     */
    public void updateState(String key, Object value)
    {
        if (key.equals("TreeUpdateStatusMessage") == true)
        {
            String msg = (String)value;
            if (msg.startsWith("ERR") == true)
                displayErrorMessage(msg);
            else
                displayMessage(msg);
        }
    }


    // -----------------------------------------------------------------------------
    protected void getOperation()
    {
        Properties p = gatherUserEnteredData();
        myModel.stateChangeRequest(" ", p);
    }

    // -----------------------------------------------------------------------------
    protected Properties gatherUserEnteredData()
    {
        Properties props = new Properties();
        String bar = barcode.getText();
        props.setProperty("Barcode", bar);

        String n = notes.getText();
        props.setProperty("Notes", n);

        if (statusComboBoxFlag == true) {
            String cb = (String)statusComboBox.getValue();
            // DEBUG System.out.println("userinterface/TreeView: gatherUserEnteredData()): statusComboBox Value - " + cb);
            props.setProperty("Status", cb);
        }

//        DEBUG
//        String bc = props.getProperty("Barcode");
//        String nc = props.getProperty("Notes");
//        System.out.println("model/TreeView: gatherUserEnteredData(): barcode - " + bc + "\nnotes - " + nc);

        return props;
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

    /**
     *  Validation method to check if Barcode is ony integers
     */
    //----------------------------------------------------------
    private boolean checkBarcode(String bar) {
        try {
            Integer.parseInt(bar);
            return true;
        } catch (NumberFormatException e) {
            // DEBUG System.out.print(e.getMessage());

            return false;
        }
    }
}