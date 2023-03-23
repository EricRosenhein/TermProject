package userinterface;

import event.Event;
import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class TransactionMenuView extends View
{
    private VBox container;
    //private Stage myStage;

    // ----------------------------------------------------------------------
    public TransactionMenuView(IModel transactionMenu) 
    {
        super(transactionMenu, "TransactionMenuView");
        
        //myStage = MainStageContainer.getInstance();
       
        // create a container for showing the contents
		container = new VBox(10);

        container.setPadding(new Insets(15, 5, 5, 5));

		// create a Node (Text) for showing the title
		container.getChildren().add(createTitle());

		// create a Node (GridPane) for showing data entry fields
		container.getChildren().add(createFormContents());

		// Error message area
		//container.getChildren().add(createStatusLog("                          "));

		getChildren().add(container);

    }

     //----------------------------------------------------------------------------
     private Node createTitle() 
     {
         Text titleText = new Text("       Choose Transaction       ");
         titleText.setFont(Font.font("Serif", FontWeight.BOLD, 20));
         titleText.setTextAlignment(TextAlignment.CENTER);
         titleText.setFill(Color.BLACK);
             
         return titleText;
     }

      //----------------------------------------------------------------------------
    private Node createFormContents() 
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

       // Radio button choices
        RadioButton registerScoutButton = new RadioButton();
        registerScoutButton.setText("Register a Scout");
        grid.add(registerScoutButton, 0, 0);

        RadioButton updateScoutButton = new RadioButton();
        updateScoutButton.setText("Update a Scout");
        grid.add(updateScoutButton, 0, 1);

        RadioButton removeScoutButton = new RadioButton();
        removeScoutButton.setText("Remove a Scout");
        grid.add(removeScoutButton, 0, 2);

        RadioButton addTreeButton = new RadioButton();
        addTreeButton.setText("Add a Tree");
        grid.add(addTreeButton, 0, 3);

        RadioButton updateTreeButton = new RadioButton();
        updateTreeButton.setText("Update a Tree");
        grid.add(updateTreeButton, 0, 4);

        RadioButton removeTreeButton = new RadioButton();
        removeTreeButton.setText("Remove a Tree");
        grid.add(removeTreeButton, 0, 5);

        RadioButton addTreeTypeButton = new RadioButton();
        addTreeTypeButton.setText("Add Tree Type");
        grid.add(addTreeTypeButton, 0, 6);

        RadioButton updateTreeTypeButton = new RadioButton();
        updateTreeTypeButton.setText("Update Tree Type");
        grid.add(updateTreeTypeButton, 0, 7);

        // Toggles the radio buttons so only one button can be selected at a time
        ToggleGroup radioGroup = new ToggleGroup();

        registerScoutButton.setToggleGroup(radioGroup);
        updateScoutButton.setToggleGroup(radioGroup);
        removeScoutButton.setToggleGroup(radioGroup);
        addTreeButton.setToggleGroup(radioGroup);
        updateTreeButton.setToggleGroup(radioGroup);
        removeTreeButton.setToggleGroup(radioGroup);
        addTreeTypeButton.setToggleGroup(radioGroup);
        updateTreeTypeButton.setToggleGroup(radioGroup);

        // Cancel and submit buttons
        Button cancelButton = new Button("Cancel");
        Button submitButton = new Button("Submit");

        // Handle events
        cancelButton.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent e) 
            {
               // TEST 
               System.out.println("You clicked the Cancel button!");
            }
        });

        submitButton.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent e) 
            {
               // TEST 
               System.out.println("You clicked the Submit button!");
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
    //@Override
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
 * 03/22/2023 Sebastian Whyte
 * Initial check in. Created the GUI for the transaction menu. Added radio, submit, and cancel buttons
*******************************************************************************************/