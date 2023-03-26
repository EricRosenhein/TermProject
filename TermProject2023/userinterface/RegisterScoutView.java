package userinterface;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
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

public class RegisterScoutView extends View
{
    protected TextField firstName;
    protected TextField middleName;
    protected TextField lastName;
    protected TextField dateOfBirth;
    protected TextField phoneNumber;
    protected TextField email;
    protected TextField troopID;

    public RegisterScoutView(IModel registerScout)
    {
        super(registerScout, "RegisterScoutView");

        // TEST 
        System.out.println("In RegisterScoutView constructor");

        VBox container = new VBox(10);

        container.setPadding(new Insets(15, 5, 5, 5));
        container.setAlignment(Pos.CENTER);

        container.getChildren().add(createTitle());

        container.getChildren().add(createFormContents());

        getChildren().add(container);
    }

    // ----------------------------------------------------------------------
    private Node createTitle() 
    {
        Text titleText = new Text("       Register a Scout       ");
        titleText.setFont(Font.font("Serif", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.BLACK);
             
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

        Text prompt = new Text("Please enter the following");
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

        dateOfBirth = new TextField();
        dateOfBirth.setEditable(true);
        grid.add(dateOfBirth, 1, 4);

        // Needs validation method
        Text phoneNumberLabel = new Text("Phone Number: ");
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

        // Cancel and submit buttons
        Button cancelButton = new Button("Cancel");
        Button submitButton = new Button("Submit");

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

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.BOTTOM_RIGHT);
        buttonContainer.getChildren().add(cancelButton);
        buttonContainer.getChildren().add(submitButton);
        grid.add(buttonContainer, 0, 9);

        return grid;
    }
 
    // ----------------------------------------------------------------------
    @Override
    public void updateState(String key, Object value) 
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateState'");
    }        
}


/*******************************************************************************************
 * Revision History:
 * 
 * 
 * 
 * 
 * 
 * 03/23/2023 Sebastian Whyte
 * Initial check in. Thinking about making common View methods (createTitle() and createFormContents())
 * abstract methods in the parent class. Not sure yet
*******************************************************************************************/