//specify package
package userinterface;

// system imports
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import Utilities.GlobalData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
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


/** This is the Register Scout View class for the Application */
//=============================================================
public abstract class ScoutView extends View
{
    protected TextField firstName;
    protected TextField middleName;
    protected TextField lastName;
    protected DatePicker datePicker;
    protected String date;
    protected LocalDateTime ldt = LocalDateTime.now();

    protected String now = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    protected TextField areaCode;
    protected TextField threeDigits;
    protected TextField fourDigits;
    protected String phoneNumber;
    protected TextField email;
    protected TextField troopId;
    protected ComboBox statusComboBox;
    Boolean statusComboBoxFlag = getStatusComboBoxFlag();
    protected MessageView statusLog;

    //------------------------------------------------------------
    public ScoutView(IModel scout, String instructions)
    {
        super(scout, "ScoutView");
        // DEBUG System.out.println("In ScoutView constructor");

        // Housekeeping initializations
        datePicker = new DatePicker(LocalDate.now());

        VBox container = new VBox(10);

        container.setPadding(new Insets(15, 5, 5, 5));
        container.setAlignment(Pos.CENTER);

        container.getChildren().add(createTitle());

        container.getChildren().add(createFormContents(instructions));

        container.getChildren().add(createStatusLog("       "));

        container.getChildren().add(new Text("                           "));
        container.getChildren().add(new Text("                           "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("ScoutUpdateStatusMessage", this);
    }

    // ----------------------------------------------------------------------
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

    //--------------------------------------------------------------
    protected String getTitleText()
    {
        return " Boy Scout Troop 209 Tree Sales: ";
    }

    // --------------------------------------------------------------
    // Abstract methods for subclasses to define
    public abstract void populateFields();

    // --------------------------------------------------------------
    /* Creates the GUI components for the view
     *
     * @return grid     the grid that will hold the GUI components
     */
    private Node createFormContents(String instructions)
    {
        VBox vbox = new VBox(10);

        //GridPane setup
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Instruction Prompt setup
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

        datePicker = new DatePicker(LocalDate.now());
        grid.add(datePicker, 1, 4);

        Text phoneNumberLabel = new Text("Phone Number: ");
        phoneNumberLabel.setFont(font);
        phoneNumberLabel.setWrappingWidth(150);
        phoneNumberLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(phoneNumberLabel, 0, 5);


        // Phone Number HBox for formatting
        HBox phoneNum = new HBox(5);
        phoneNum.setAlignment(Pos.CENTER_LEFT);


        Text areaCodeStart = new Text("(");
        areaCodeStart.setFont(font);
        areaCodeStart.setTextAlignment(TextAlignment.LEFT);
        phoneNum.getChildren().add(areaCodeStart);

        areaCode = new TextField();
        areaCode.setEditable(true);
        areaCode.setMaxWidth(35);
        phoneNum.getChildren().add(areaCode);

        Text areaCodeEnd = new Text(")");
        areaCodeEnd.setFont(font);
        areaCodeEnd.setTextAlignment(TextAlignment.LEFT);
        phoneNum.getChildren().add(areaCodeEnd);

        threeDigits = new TextField();
        threeDigits.setEditable(true);
        threeDigits.setMaxWidth(35);
        phoneNum.getChildren().add(threeDigits);

        Text pNumberDash = new Text("-");
        areaCodeEnd.setFont(font);
        areaCodeEnd.setTextAlignment(TextAlignment.LEFT);
        phoneNum.getChildren().add(pNumberDash);

        fourDigits = new TextField();
        fourDigits.setEditable(true);
        fourDigits.setMaxWidth(42);
        phoneNum.getChildren().add(fourDigits);


        grid.add(phoneNum, 1, 5);

        // End of HBox for phone number

        Text emailLabel = new Text("Email: ");
        emailLabel.setFont(font);
        emailLabel.setWrappingWidth(150);
        emailLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(emailLabel, 0, 6);

        email = new TextField();
        email.setEditable(true);
        grid.add(email, 1, 6);

        Text troopIdLabel = new Text("Troop ID: ");
        troopIdLabel.setFont(font);
        troopIdLabel.setWrappingWidth(150);
        troopIdLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(troopIdLabel, 0, 7);

        troopId = new TextField();
        setTroopIdEditable(troopId, getTroopIdEditableFlag());
        grid.add(troopId, 1, 7);

        if (statusComboBoxFlag == true)
        {
            Text statusBoxLabel = new Text("Status: ");
            statusBoxLabel.setFont(font);
            statusBoxLabel.setWrappingWidth(150);
            statusBoxLabel.setTextAlignment(TextAlignment.RIGHT);
            grid.add(statusBoxLabel, 0, 8);

            statusComboBox = new ComboBox();
            statusComboBox.getItems().addAll("Active", "Inactive");
            grid.add(statusComboBox, 1, 8);
        }

        // Cancel and submit buttons
        Button cancelButton = new Button("Cancel");
        Button submitButton = new Button("Submit");

        // Handle events for regular buttons
        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                clearErrorMessage();
                myModel.stateChangeRequest("CancelTransaction", "");
            }
        });

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                validateScout();
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


    //---------------------------------------------------------------------
    protected boolean getTroopIdEditableFlag()
    {
        return true;
    }

    //----------------------------------------------------------------------
    protected void setTroopIdEditable(TextField tf, boolean flag)
    {
        tf.setEditable(flag);
    }

    // ----------------------------------------------------------------------
    protected boolean getStatusComboBoxFlag()
    {
        return true;
    }


    // ----------------------------------------------------------------------
    /**
     * @param key
     * @param value
     */
    public void updateState(String key, Object value)
    {
        if (key.equals("ScoutUpdateStatusMessage") == true)
        {
            String msg = (String)value;
            if (msg.startsWith("ERR") == true)
                displayErrorMessage(msg);
            else
                displayMessage(msg);
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


    /**
     *  Handling validation for all fields
     */
    //----------------------------------------------------------
    public void validateScout()
    {
        clearErrorMessage();

        // DEBUG System.out.println("userinterface/RegisterScoutView: processAction(Event evt): Start of method");

        phoneNumber = areaCode.getText() + threeDigits.getText() + fourDigits.getText();

        // Date validation
        LocalDate dt = datePicker.getValue();
        LocalDateTime selectedDate = dt.atStartOfDay();   // Convert LocalDate to LocalDateTime for comparisons


        // All fields completed validation
        if ((firstName.getText() == null || firstName.getText().length() == 0) ||
                (middleName.getText() == null || middleName.getText().length() == 0) ||
                (lastName.getText() == null || lastName.getText().length() == 0) ||
                (email.getText() == null || email.getText().length() == 0) ||
                (troopId.getText() == null || troopId.getText().length() == 0) || (dt == null))
        {
            displayErrorMessage("ERROR: Please complete the entire form.");
        }
        // Crazy long name validation
        else if (firstName.getText().length() > 25 || lastName.getText().length() > 25 ||
                middleName.getText().length() > 25)
        {
            displayErrorMessage("ERROR: Please enter a name under 25 characters.");
        }
        // Phone number validation
        else if (areaCode.getText().length() != 3 ||
                threeDigits.getText().length() != 3 ||
                fourDigits.getText().length() != 4) {
            displayErrorMessage("ERROR: Please enter a valid, 10-digit phone number");
        }
        // Email validation
        else if (email.getText().contains("@") == false) {
            displayErrorMessage("ERROR: Please enter a valid email address.");
        }
        // Troop ID validation
        else if (troopId.getText().length() != 5) {
            displayErrorMessage("ERROR: Troop ID must be 5 integers long!");
        }
        // TroopID parse validation
        else if (checkTroopId(troopId.getText()) == false) {
            displayErrorMessage("ERROR: Troop ID must contain only numbers!");
        }
        // Phone number parse validation
        else if (checkPhoneNumber(phoneNumber) == false) {
            displayErrorMessage("ERROR: Phone number must contain only numbers!");
        }
        else
        {
            // DEBUG System.out.println("userinterface/RegisterScoutView: processAction(Event evt): Final validation of date in else");
            LocalDateTime youngScoutDate = ldt.minusYears(GlobalData.YOUNG_SCOUT_AGE);   // Must be at least 10 to enter Scouting
            LocalDateTime oldScoutDate = ldt.minusYears(GlobalData.OLD_SCOUT_AGE);     // Must be under 18 to be a Scout
            // DEBUG System.out.println("userinterface/RegisterScoutView: processAction(Event evt): ysd = " + youngScoutDate);
            // DEBUG System.out.println("userinterface/RegisterScoutView: processAction(Event evt): osd = " + oldScoutDate);

            if ((selectedDate.isAfter(youngScoutDate) == true))
            {
                displayErrorMessage("ERROR: Scouts must be at least " + GlobalData.YOUNG_SCOUT_AGE + " years old.");
            }
            else if ((selectedDate.isBefore(oldScoutDate) == true)  || (selectedDate.isEqual(youngScoutDate) == true))
            {
                displayErrorMessage("ERROR: Scouts must be under " + GlobalData.OLD_SCOUT_AGE + " years old.");
            }
            else
            {
                date = dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                getOperation();
            }
        }
    }
    
    // -----------------------------------------------------------------------------
    protected Properties gatherUserEnteredData()
    {
        Properties props = new Properties();
        props.setProperty("FirstName", firstName.getText());
        props.setProperty("MiddleName", middleName.getText());
        props.setProperty("LastName", lastName.getText());
        props.setProperty("DateOfBirth", date);
        props.setProperty("PhoneNumber", phoneNumber);
        props.setProperty("Email", email.getText());
        props.setProperty("TroopID", troopId.getText());

        if (statusComboBoxFlag == true) {
            String cb = (String)statusComboBox.getValue();
            // DEBUG System.out.println("userinterface/ScoutView: gatherUserEnteredData()): statusComboBox Value - " + cb);
            props.setProperty("Status", cb);
        }

        props.setProperty("DateStatusUpdated", now);
        return props;
    }

    protected void getOperation()
    {
        Properties p = gatherUserEnteredData();
        myModel.stateChangeRequest("", p);
    }


    /**
     *  Validation method to check if TroopId is ony integers
     */
    //----------------------------------------------------------
    private boolean checkTroopId(String tId)
    {
        try
        {
            Integer.parseInt(tId);
            return true;
        }
        catch (NumberFormatException e)
        {
            // DEBUG System.out.print(e.getMessage());
            return false;
        }
    }

    public Boolean validateCostValue(String costVal)
    {
        if (costVal.length() > GlobalData.MAX_TREE_TYPE_COST_LENGTH)
            return false;
        else {
            try {
                double costValue = Double.parseDouble(costVal);
                return true;
            }
            catch (NumberFormatException excep)
            {
                return false;
            }

        }
    }

    /**
     *  Validation method to check if Phone Number is ony integers
     */
    //----------------------------------------------------------
    private boolean checkPhoneNumber(String pN)
    {
        try
        {
            Long.parseLong(pN);
            return true;
        }
        catch (NumberFormatException e)
        {
            // DEBUG System.out.print(e.getMessage());

            return false;
        }
    }
}
