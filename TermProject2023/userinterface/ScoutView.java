package userinterface;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public abstract class ScoutView extends View
{
    // Instance variables
    protected TextField firstName;
    protected TextField middleName;
    protected TextField lastName;
    //protected TextField dateOfBirth;
    protected TextField phoneNumber;
    protected TextField areaCode;
    protected TextField phonePrefix;
    protected TextField lineNumber;
    protected TextField email;
    protected TextField troopID;
    protected TextField dateStatusUpdated;

    protected Button submitButton;
    protected Button cancelButton;
    protected MessageView statusLog;

    private DatePicker datePicker;
    private HBox dateOfBirth;

    // ----------------------------------------------------------------------

    /* Constructor which will initialize the GUI components for the given model
     *
     * @param scout     type of scout view we need to make
     */
    public ScoutView(IModel scout, String instructions)
    {
        super(scout, "ScoutView");

        VBox container = new VBox(10);

        container.setPadding(new Insets(15, 5, 5, 5));
        container.setAlignment(Pos.CENTER);

        container.getChildren().add(createTitle("Boy Scout Troop 209 Tree Sales System"));

        container.getChildren().add(createFormContents(instructions));

        container.getChildren().add(createStatusLog(" "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("ScoutUpdateStatusMessage", this);

    }

    // ----------------------------------------------------------------------
    // Abstract methods for the subclasses to define
    public abstract void processAction(Event event);
    public abstract void populateFields();

    // ----------------------------------------------------------------------
    /* Creates the GUI components for this view
     *
     * @return grid     a grid used for holding the GUI components
     */
    protected Node createFormContents(String instructions)
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text(instructions);
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        // Set the font
        Font font = Font.font("Arial", FontWeight.BOLD, 12);

        // Data fields
        Text firstNameLabel = new Text("First Name: ");
        firstNameLabel.setFont(font);
        firstNameLabel.setWrappingWidth(150);
        firstNameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(firstNameLabel, 0, 1);

        firstName = new TextField();
        firstName.setEditable(true);
        grid.add(firstName, 1, 1);

        Text middleNameLabel = new Text("Middle Name: ");
        middleNameLabel.setFont(font);
        middleNameLabel.setWrappingWidth(150);
        middleNameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(middleNameLabel, 0, 2);

        middleName = new TextField();
        middleName.setEditable(true);
        grid.add(middleName, 1, 2);

        Text lastNameLabel = new Text("Last Name: ");
        lastNameLabel.setFont(font);
        lastNameLabel.setWrappingWidth(150);
        lastNameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(lastNameLabel, 0, 3);

        lastName = new TextField();
        lastName.setEditable(true);
        grid.add(lastName, 1, 3);

        // Needs validation method
        Text dateOfBirthLabel = new Text("Date of Birth: ");
        dateOfBirthLabel.setFont(font);
        dateOfBirthLabel.setWrappingWidth(150);
        dateOfBirthLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(dateOfBirthLabel, 0, 4);

        datePicker = new DatePicker();
        dateOfBirth = new HBox(datePicker);
        grid.add(dateOfBirth, 1, 4);



        /*
        // DEBUGGING DATE PICKER
        Button button = new Button("Read Date");        // text will change once user selects date and clicks on this button
        grid.add(button, 2, 4);

        button.setOnAction(action -> {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Get the selected date from the DatePicker
            LocalDate date = datePicker.getValue();
            if (date != null)
            {
                button.setText(formatter.format(date));
            }
        });
        */

        // Needs validation method
        Text phoneNumberLabel = new Text("Phone Number\n(Format: XXX-XXX-XXXX)");
        phoneNumberLabel.setFont(font);
        phoneNumberLabel.setWrappingWidth(150);
        phoneNumberLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(phoneNumberLabel, 0, 5);

        phoneNumber = new TextField();
        phoneNumber.setEditable(true);
        grid.add(phoneNumber, 1, 5);


        Text emailLabel = new Text("Email: ");
        emailLabel.setFont(font);
        emailLabel.setWrappingWidth(150);
        emailLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(emailLabel, 0, 6);

        email = new TextField();
        email.setEditable(true);
        grid.add(email, 1, 6);

        // Troop ID will be auto generated, so we may not need an editable text field. Maybe call the auto generate method as a parameter into the setText()
        Text troopIDLabel = new Text("Troop ID: ");
        troopIDLabel.setFont(font);
        troopIDLabel.setWrappingWidth(150);
        troopIDLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(troopIDLabel, 0, 7);

        troopID = new TextField();
        troopID.setEditable(true);
        grid.add(troopID, 1, 7);

        Text dateStatusUpdatedLabel = new Text("Date Last Updated: ");
        dateStatusUpdatedLabel.setFont(font);
        dateStatusUpdatedLabel.setWrappingWidth(150);
        dateStatusUpdatedLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(dateStatusUpdatedLabel, 0, 8);

        dateStatusUpdated = new TextField();
        dateStatusUpdated.setEditable(true);
        grid.add(dateStatusUpdated, 1, 8);

        // Cancel and submit buttons
        Button cancelButton = new Button("Cancel");
        Button submitButton = new Button("Submit");

        // Event handlers for cancel and submit buttons
        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e)
            {
                clearErrorMessage();
                processAction(e);
            }
        });

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
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);
        buttonContainer.getChildren().add(cancelButton);
        buttonContainer.getChildren().add(submitButton);
        grid.add(buttonContainer, 1, 10);

        return grid;
    }

    // ----------------------------------------------------------------------
    /* Updates the state of the view
     *
     * @param key       key
     * @param value     value we want mapped to the key
     */
    @Override
    public void updateState(String key, Object value)
    {

    }

    // ----------------------------------------------------------------------
    // Create the status log field
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    // -------------------------------------------------------------
    /**
     * Display error message
     */
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    // ----------------------------------------------------------
    /**
     * Display info message
     */
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    // ----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }
}

/*******************************************************************************************
 * Revision History:
 *
 *
 *
 * 04/01/2023 Dominic Laure
 * Initial check in.
 ******************************************************************************************/
