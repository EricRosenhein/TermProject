package userinterface;

import event.Event;
import impresario.IModel;
import userinterface.View;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
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

/** This is the main Transaction Choice View for the Boy Scout Troop 209 Tree Sales Application */
//==================================================================================
public class TLCView extends View
{
    private VBox container;
    private MessageView statusLog;

    // ----------------------------------------------------------------------
    public TLCView(IModel transactionMenu)
    {
        super(transactionMenu, "TLCView");

        // create a container for showing the contents
        container = new VBox(10);

        container.setPadding(new Insets(15, 5, 5, 5));

        // create a Node (Text) for showing the title
        //container.getChildren().add(createTitle());
        container.getChildren().add(createTitle("Boy Scout Troop 209 Tree Sales System: Choose Transaction"));

        // create a Node (GridPane) for showing data entry fields
        container.getChildren().add(createFormContents(""));

        // Error message area
        container.getChildren().add(createStatusLog(" "));

        getChildren().add(container);

    }

    //----------------------------------------------------------------------------
    protected Node createFormContents(String instructions)
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
        ToggleGroup toggleGroup = new ToggleGroup();

        registerScoutButton.setToggleGroup(toggleGroup);
        updateScoutButton.setToggleGroup(toggleGroup);
        removeScoutButton.setToggleGroup(toggleGroup);
        addTreeButton.setToggleGroup(toggleGroup);
        updateTreeButton.setToggleGroup(toggleGroup);
        removeTreeButton.setToggleGroup(toggleGroup);
        addTreeTypeButton.setToggleGroup(toggleGroup);
        updateTreeTypeButton.setToggleGroup(toggleGroup);

        // Cancel and submit buttons
        Button cancelButton = new Button("Cancel");
        Button submitButton = new Button("Submit");

        // Handle events for regular buttons
        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                // DEBUG
                System.out.println("You clicked the Cancel button!");
            }
        });

        // Handle event when user clicks the submit button
        submitButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                // DEBUG
                //System.out.println("\nYou clicked the Submit button!\n");

                // Check which radio button the user chose
                if (toggleGroup.getSelectedToggle() != null)
                {
                    // Check the selected radio button and get the text
                    RadioButton selected = (RadioButton)toggleGroup.getSelectedToggle();
                    String str = selected.getText();

                    // DEBUG
                    System.out.println("Selected button: " + str);

                    if (selected == registerScoutButton)
                    {
                        myModel.stateChangeRequest("RegisterScout", "");
                    }
                    else if (selected == updateScoutButton)
                    {
                        myModel.stateChangeRequest("UpdateScout", "");
                    }
                    else if (selected == removeScoutButton)
                    {
                        myModel.stateChangeRequest("RemoveScout", "");
                    }
                    else if (selected == addTreeButton)
                    {
                        myModel.stateChangeRequest("AddTree", "");
                    }
                    else if (selected == updateTreeButton)
                    {
                        myModel.stateChangeRequest("UpdateTree", "");
                    }
                    else if (selected == removeTreeButton)
                    {
                        myModel.stateChangeRequest("RemoveTree", "");
                    }
                    else if (selected == addTreeTypeButton)
                    {
                        myModel.stateChangeRequest("AddTreeType", "");
                    }
                }
                else
                {
                    displayErrorMessage("Please select an option.");
                }
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

    // ----------------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    // ----------------------------------------------------------------------
    /**
     * Display error message
     */
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    //----------------------------------------------------------
    /**
     * Clear error message
     */
    //----------------------------------------------------------
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
 *
 *
 * 03/24/2023 07:18 PM Sebastian Whyte
 * Added if statement inside the submit button event handler that checks which radio button the user selected.
 * Also, I started working on Transactions for the add operations
 *
 * 03/22/2023 Sebastian Whyte
 * Initial check in. Created the GUI for the transaction menu. Added radio, submit, and cancel buttons
 *******************************************************************************************/