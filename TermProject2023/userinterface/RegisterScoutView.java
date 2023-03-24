package userinterface;

import impresario.IModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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

        VBox container = new VBox(10);

        container.setPadding(new Insets(15, 5, 5, 5));

        container.getChildren().add(createTitle());

        container.getChildren().add(createFormContents());
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