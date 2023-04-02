package userinterface;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
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

public abstract class TreeView extends View
{
    protected TextField barcode;
    protected TextField treeType;
    protected TextField notes;
    protected TextField dateStatusUpdated;
    protected ComboBox<String> status;

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

        container.getChildren().add(createTitle(" Wyhsenhein's Tree Sales "));

        container.getChildren().add(createFormContents(instructions));

        container.getChildren().add(createStatusLog(" "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("TreeUpdateStatusMessage", this);

    }

    // --------------------------------------------------------------
    // Abstract methods for subclasses to define
    public abstract void processAction(Event event);
    public abstract void populateFields();

    // --------------------------------------------------------------

    /* Creates the GUI components for the view
     *
     * @return grid     the grid that will hold the GUI components
     */
    protected Node createFormContents(String instructions)
    {
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
        barcode.setEditable(true);
        grid.add(barcode, 1, 1);

        Text treeTypeLabel = new Text("Tree Type: ");
        treeTypeLabel.setFont(font);
        treeTypeLabel.setWrappingWidth(150);
        treeTypeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(treeTypeLabel, 0, 2);

        treeType = new TextField();
        treeType.setEditable(true);
        grid.add(treeType, 1, 2);

        Text statusLabel = new Text("Status: ");
        statusLabel.setFont(font);
        statusLabel.setWrappingWidth(150);
        statusLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(statusLabel, 0, 3);

        status = new ComboBox<>();
        status.getItems().add(0, "Active");
        status.getItems().add(1, "Sold");
        status.getItems().add(2, "Damaged");
        grid.add(status, 1, 3);

        Text dateStatusUpdatedLabel = new Text("Date Last Updated: ");
        dateStatusUpdatedLabel.setFont(font);
        dateStatusUpdatedLabel.setWrappingWidth(150);
        dateStatusUpdatedLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(dateStatusUpdatedLabel, 0, 4);

        dateStatusUpdated = new TextField();
        dateStatusUpdated.setEditable(true);
        grid.add(dateStatusUpdated, 1, 4);

        Text notesLabel = new Text("Notes: ");
        notesLabel.setFont(font);
        notesLabel.setWrappingWidth(150);
        notesLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(notesLabel, 0, 5);

        notes = new TextField();
        notes.setEditable(true);
        notes.setPrefHeight(50);
        notes.setPrefWidth(25);
        grid.add(notes, 1, 5);

        Button cancelButton = new Button("Cancel");
        Button submitButton = new Button("Submit");

        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                // DEBUG - This should take us back to the TransactionMenu
                myModel.stateChangeRequest("CancelTransaction", null);

            }
        });

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.BOTTOM_RIGHT);
        buttonContainer.getChildren().add(cancelButton);
        buttonContainer.getChildren().add(submitButton);
        grid.add(buttonContainer, 0, 6);

        return grid;
    }

    // ----------------------------------------------------------------------
    /**
     * @param key
     * @param value
     */
    @Override
    public void updateState(String key, Object value)
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
}
