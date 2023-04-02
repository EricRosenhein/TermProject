package userinterface;

// system imports
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

public class AddTreeTypeView extends View
{
    // GUI components
    protected TextField TypeDescriptionField;
	protected TextField CostField;
	protected TextField BarcodePrefixField;

	protected Button cancelButton;
	protected Button submitButton;

    // For showing error message
	protected MessageView statusLog;

    // constructor for this class -- takes a model object
	//----------------------------------------------------------
    public AddTreeTypeView(IModel addTreeType)
    {
        super(addTreeType, "AddTreeTypeView");

        // create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// create our GUI components, add them to this panel
		container.getChildren().add(createTitle());
		container.getChildren().add(createFormContent());

		// Error message area
		container.getChildren().add(createStatusLog("                                            "));

		getChildren().add(container);
		
		populateFields();
		
		myModel.subscribe("UpdateStatusMessage", this);
    }

    // Create the title container
	//-------------------------------------------------------------
	private Node createTitle()
	{
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);	

		Text titleText = new Text(" Boy Scout Troop 209 Tree Sales: Add TreeType ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setWrappingWidth(300);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		container.getChildren().add(titleText);
		
		return container;
	}

    // ----------------------------------------------------------------------
    private VBox createFormContent() 
    {
        VBox vbox = new VBox(10);

		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
       	grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("TreeType Information");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

		Text TypeDescriptionLabel = new Text(" Type Description : ");
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		TypeDescriptionLabel.setFont(myFont);
		TypeDescriptionLabel.setWrappingWidth(150);
		TypeDescriptionLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(TypeDescriptionLabel, 0, 1);

		TypeDescriptionField = new TextField();
		TypeDescriptionField.setEditable(true);
		grid.add(TypeDescriptionField, 1, 1);

		Text CostLabel = new Text(" Cost : ");
		CostLabel.setFont(myFont);
		CostLabel.setWrappingWidth(150);
		CostLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(CostLabel, 0, 2);

		CostField = new TextField();
		CostField.setEditable(true);
		grid.add(CostField, 1, 2);

		Text BarcodePrefixLabel = new Text(" Barcode Prefix : ");
		BarcodePrefixLabel.setFont(myFont);
		BarcodePrefixLabel.setWrappingWidth(150);
		BarcodePrefixLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(BarcodePrefixLabel, 0, 3);

		BarcodePrefixField = new TextField();
		BarcodePrefixField.setEditable(true);
		grid.add(BarcodePrefixField, 1, 3);

		submitButton = new Button("Submit");
		submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		submitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				clearErrorMessage();
				processAction(e);
			}
		});

		cancelButton = new Button("Cancel");
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

    // Create the status log field
	//-------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	//-------------------------------------------------------------
	public void populateFields()
	{
		TypeDescriptionField.setText((String)myModel.getState("TypeDescription"));
		CostField.setText((String)myModel.getState("Cost"));
	 	BarcodePrefixField.setText((String)myModel.getState("BarcodePrefix"));
	}

    public void processAction(Event evt)
	{

		if(TypeDescriptionField.getText().isEmpty() || CostField.getText().isEmpty() || BarcodePrefixField.getText().isEmpty())
		{
			displayErrorMessage("Please fill every field");
		}
		else if(TypeDescriptionField.getText().length() > 25)
		{
			displayErrorMessage("Type Description has a limit of 25 characters!");
		}
        else if(CostField.getText().length() > 20)
        {
            displayErrorMessage("Cost has a limit of 25 characters!");
        }
        else if(BarcodePrefixField.getText().length() > 2)
        {
            displayErrorMessage("Barcode Prefix has a limit of 2 characters!");
        }
		else
		{
			InsertTreeType();
		}
	}

    private void InsertTreeType()
	{
		Properties props = new Properties();
		props.setProperty("TypeDescription", TypeDescriptionField.getText());
		props.setProperty("Cost", CostField.getText());
		props.setProperty("BarcodePrefix", BarcodePrefixField.getText());

		myModel.stateChangeRequest("InsertTreeTypeData", props);
		displayMessage("TreeType inserted!");
	}

    /**
	 * Update method
	 */
	//---------------------------------------------------------
	public void updateState(String key, Object value)
	{
	}

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
