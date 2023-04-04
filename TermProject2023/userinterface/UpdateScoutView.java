package userinterface;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.TransactionFactory;
import model.Scout;

public class UpdateScoutView extends View {
    protected TextField firstName;
    protected TextField middleName;
    protected TextField lastName;
    protected DatePicker datePicker;
    protected String date;
    protected TextField areaCode;
    protected TextField threeDigits;
    protected TextField fourDigits;
    protected String phoneNumber;
    protected TextField email;
    protected TextField troopID;
    protected Button cancelButton;
    protected Button submitButton;

    protected MessageView statusLog;

    //------------------------------------------------------------
    public UpdateScoutView(IModel updateScout)
    {
        super(updateScout, "UpdateScoutView");

        // TEST
        System.out.println("In UpdateScoutView constructor");

        // Housekeeping initializations
        datePicker = new DatePicker(LocalDate.now());
        VBox container = new VBox(10);

        container.setPadding(new Insets(15, 5, 5, 5));
        container.setAlignment(Pos.CENTER);

        container.getChildren().add(createTitle());

        container.getChildren().add(createFormContents());


        container.getChildren().add(createStatusLog("                                "));
        getChildren().add(container);

        populateFields();

        myModel.subscribe("ScoutUpdateStatusMessage", this);
    }

    // ----------------------------------------------------------------------
    private Node createTitle()
    {
        Text titleText = new Text(" Boy Scout Troop 209 Tree Sales: Update Scout ");
        titleText.setFont(Font.font("Serif", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.BURLYWOOD);

        return titleText;
    }

    // ----------------------------------------------------------------------
    private Node createFormContents()
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("Scout Informtion");
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
        grid.add(datePicker, 1, 4);

        // Needs validation method
        Text phoneNumberLabel = new Text("Phone Number: ");
        phoneNumberLabel.setFont(font);
        phoneNumberLabel.setWrappingWidth(150);
        phoneNumberLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(phoneNumberLabel, 0, 5);

        areaCode = new TextField();
        areaCode.setEditable(true);
        grid.add(areaCode, 1, 5);

        threeDigits = new TextField();
        threeDigits.setEditable(true);
        grid.add(threeDigits, 2, 5);

        fourDigits = new TextField();
        fourDigits.setEditable(true);
        grid.add(fourDigits, 3, 5);

        Text emailLabel = new Text("Email: ");
        emailLabel.setFont(font);
        emailLabel.setWrappingWidth(150);
        emailLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(emailLabel, 0, 6);

        email = new TextField();
        email.setEditable(true);
        grid.add(email, 1, 6);


        Text troopIDLabel = new Text("Troop ID: ");
        troopIDLabel.setFont(font);
        troopIDLabel.setWrappingWidth(150);
        troopIDLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(troopIDLabel, 0, 7);

        troopID = new TextField();
        troopID.setEditable(false);
        grid.add(troopID, 1, 7);

        // Cancel and submit buttons
        cancelButton = new Button("Cancel");
        submitButton = new Button("Submit");

        // Handle events for regular buttons
        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                // DEBUG - This should take us back to the TransactionMenu
                myModel.stateChangeRequest("CancelTransaction", "");
            }
        });

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                processAction(e);
            }
        });

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.BOTTOM_RIGHT);
        buttonContainer.getChildren().add(cancelButton);
        buttonContainer.getChildren().add(submitButton);
        grid.add(buttonContainer, 0, 9);

        return grid;
    }

    protected MessageView createStatusLog(String initialMessage) {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    public void populateFields(){
        Scout scoutToUpdate = (Scout) myModel.getState("GetScoutToUpdate");

        firstName.setText((String) scoutToUpdate.getState("FirstName"));
        middleName.setText((String) scoutToUpdate.getState("MiddleName"));
        lastName.setText((String)scoutToUpdate.getState("LastName"));
        datePicker.setValue(LocalDate.parse((CharSequence) scoutToUpdate.getState("DateOfBirth")));
        areaCode.setText(((String) scoutToUpdate.getState("PhoneNumber")).substring(0,3));
        threeDigits.setText(((String) scoutToUpdate.getState("PhoneNumber")).substring(3,6));
        fourDigits.setText(((String) scoutToUpdate.getState("PhoneNumber")).substring(6,9));
        email.setText((String) scoutToUpdate.getState("Email"));
        troopID.setText((String) scoutToUpdate.getState("TroopID"));
    }
    public void processAction(Event evt)
    {
        phoneNumber = areaCode.getText() + threeDigits.getText() + fourDigits.getText();
        LocalDate dt = datePicker.getValue();
        if (dt != null)
            date = dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        else
        {
            displayErrorMessage("Invalid date entered");
            return;
        }

        if(firstName.getText().isEmpty() || middleName.getText().isEmpty() ||
                lastName.getText().isEmpty() ||
                email.getText().isEmpty() || troopID.getText().isEmpty())
        {
            displayErrorMessage("Please fill every field");
            return;
        }
        else if(firstName.getText().length() > 25 || middleName.getText().length() > 25
            || lastName.getText().length() > 25)
        {
            displayErrorMessage("Names cannot exceed 25 characters");
            return;
        }
        else if(areaCode.getText().length() != 3 ||
                threeDigits.getText().length() != 3 ||
                fourDigits.getText().length() != 4)
        {
            displayErrorMessage("Please enter a valid phone number");
            return;
        }
        else if(email.getText().contains("@") == false)
        {
            displayErrorMessage("Please enter a valid email");
            return;
        }
        else if(troopID.getText().length() < 5)
        {
            displayErrorMessage("Troop ID must be 5 integers long!");
            return;
        }
        else if(checkTroopID(troopID.getText()) == false)
        {
            return;
        }
        else if(checkPhoneNumber(phoneNumber) == false)
        {
            return;
        }
        else
        {
            UpdateScout();
        }
    }

    private void UpdateScout()
    {
        Properties props = new Properties();
        props.setProperty("FirstName", firstName.getText());
        props.setProperty("MiddleName", middleName.getText());
        props.setProperty("LastName", lastName.getText());
        props.setProperty("DateOfBirth", date);
        props.setProperty("PhoneNumber", phoneNumber);
        props.setProperty("Email", email.getText());
        props.setProperty("TroopID", troopID.getText());

        LocalDateTime ldt = LocalDateTime.now();
        String now = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        props.setProperty("DateStatusUpdated", now);

        myModel.stateChangeRequest("UpdateScoutData", props);
        displayMessage("Scout Updated!");
    }
    

    // ----------------------------------------------------------------------
    public void updateState(String key, Object value)
    {
        //    if (key.equals("ScoutUpdateStatusMessage"))
        //    {
        //        String msg = (String)value;
        //        if (msg.startsWith("ERR")) {
        //            displayErrorMessage(msg);
        //        }
        //        else {
        //            displayMessage(msg);
        //        }
        //    }
    }

    /**
     * Update method
     */
    //---------------------------------------------------------


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

    private boolean checkTroopID(String troopID)
    {
        try
        {
            Integer.parseInt(troopID);
            return true;
        }
        catch (NumberFormatException e)
        {
            System.out.print(e.getMessage());
            displayErrorMessage("Troop ID must contain only numbers!");
            return false;
        }
    }

    private boolean checkPhoneNumber(String phoneNumber)
    {
        try
        {
            Long.parseLong(phoneNumber);
            return true;
        }
        catch (NumberFormatException e)
        {
            System.out.print(e.getMessage());
            displayErrorMessage("Phone Number must contain only numbers!");
            return false;
        }
    }
}
