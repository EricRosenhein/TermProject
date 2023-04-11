package userinterface;

// system imports
import javafx.event.Event;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Properties;

// project imports
import impresario.IModel;

public class ScanTreeBarcodeView extends View {

    protected TextField scanBarcode;
    protected Button submitButton;
    protected Button cancelButton;
    protected MessageView statusLog;

    //constructor for this class -- takes a model object
    //-----------------------------------------------------------------
    public ScanTreeBarcodeView (IModel searchTree)
    {
        super (searchTree, "ScanTreeBarcodeView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        container.getChildren().add(new Text("                           "));
        container.getChildren().add(new Text("                           "));

        populateFields();

        myModel.subscribe("TreeSearchStatusMessage", this);
    }

    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Boy Scout Troop 209 Tree Sales: \nScan Tree Barcode ");
        titleText.setFont(Font.font("Serif", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.BURLYWOOD);
        container.getChildren().add(titleText);

        return container;
    }


    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        //GridPane setup
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        //Title prompt setup
        Text prompt = new Text("Search a tree by entering its barcode.\n"
                              + "Select \"Submit\" to search.");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        //Barcode entry field
        Text scanBarcodeLabel = new Text (" Barcode: ");
        Font myFont = Font.font("Ariel", FontWeight.BOLD, 12);
        scanBarcodeLabel.setFont(myFont);
        scanBarcodeLabel.setWrappingWidth(150);
        scanBarcodeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(scanBarcodeLabel, 0, 1);

        scanBarcode = new TextField();
        scanBarcode.setEditable(true);
        grid.add(scanBarcode, 1, 1);

        //Submit button
        submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                processAction(e);
            }
        });

        //Cancel button

        cancelButton = new Button("Back");
        cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("CancelTransaction", null);
            }
        });

        HBox btnContainer = new HBox(10);
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.getChildren().add(cancelButton);
        btnContainer.getChildren().add(submitButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(btnContainer);

        return vbox;
    }

    //Validate data entered after hitting the submit button
    //-------------------------------------------------------------------------
    public void processAction(Event evt)
    {
        clearErrorMessage();
        String bar = scanBarcode.getText();
        // All fields completed validation
        if ((bar == null || bar.length() == 0))
        {
            displayErrorMessage("ERROR: Please complete the entire form.");
        }
        // Barcode validation
        else if (bar.length() != 5) {
            displayErrorMessage("ERROR: Barcode must be 5 integers long!");
        }
        // Barcode parse validation
        else if (checkBarcode(bar) == false) {
            displayErrorMessage("ERROR: Barcode must contain only numbers!");
        }
        else
        {
            myModel.stateChangeRequest("SearchTrees", bar);
        }
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

    //---------------------------------------------------------------
    public void updateState(String key, Object value)
    {
        if (key.equals("TreeSearchStatusMessage") == true)
        {
            String msg = (String)value;
            if ((msg.startsWith("ERR") == true) || (msg.startsWith("Err") == true))
            {
                displayErrorMessage(msg);
            }
            else
            {
                displayMessage(msg);
            }
        }
    }

    //---------------------------------------------------------------
    public void populateFields()
    {

    }

    //Create the status log field
    //----------------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }
}
