package userinterface;

import Utilities.GlobalData;
import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

/** This is the main Transaction Choice View for the Boy Scout Troop 209 Tree Sales Application */
//==================================================================================
public class TLCView extends View
{
    private static final int MIN_RADIO_BUTTON_WIDTH = GlobalData.MIN_RADIO_BUTTON_WIDTH;
    private VBox container;
    private MessageView statusLog;
    private Boolean openSessionFlag;
    private RadioButton startShift;
    private RadioButton endShift;
    private RadioButton sellTree;

    // ----------------------------------------------------------------------
    public TLCView(IModel transactionMenu)
    {
        super(transactionMenu, "TLCView");

        // create a container for showing the contents
        container = new VBox(10);

        container.setPadding(new Insets(15, 60, 5, 60));

        // create a Node (Text) for showing the title
        container.getChildren().add(createTitle());

        // create a Node (GridPane) for showing data entry fields
        container.getChildren().add(createFormContents());

        // Error message area
        container.getChildren().add(createStatusLog(" "));

        getChildren().add(container);

        populateFields();
    }

    //----------------------------------------------------------------------------
    private Node createTitle()
    {
        Text titleText = new Text(" Boy Scout Troop 209 Tree Sales System:\nChoose Transaction ");
        titleText.setFont(Font.font("Serif", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.BURLYWOOD);

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
        RadioButton registerScoutButton = new RadioButton("Register a Scout");
        registerScoutButton.setMinWidth(MIN_RADIO_BUTTON_WIDTH);
        grid.add(registerScoutButton, 0, 0);

        RadioButton updateScoutButton = new RadioButton("Update a Scout");
        updateScoutButton.setMinWidth(MIN_RADIO_BUTTON_WIDTH);
        grid.add(updateScoutButton, 0, 1);

        RadioButton removeScoutButton = new RadioButton("Remove a Scout");
        removeScoutButton.setMinWidth(MIN_RADIO_BUTTON_WIDTH);
        grid.add(removeScoutButton, 0, 2);

        RadioButton addTreeButton = new RadioButton("Add a Tree");
        addTreeButton.setMinWidth(MIN_RADIO_BUTTON_WIDTH);
        grid.add(addTreeButton, 0, 3);

        RadioButton updateTreeButton = new RadioButton("Update a Tree");
        updateTreeButton.setMinWidth(MIN_RADIO_BUTTON_WIDTH);
        grid.add(updateTreeButton, 0, 4);

        RadioButton removeTreeButton = new RadioButton("Remove a Tree");
        removeTreeButton.setMinWidth(MIN_RADIO_BUTTON_WIDTH);
        grid.add(removeTreeButton, 0, 5);

        RadioButton addTreeTypeButton = new RadioButton("Add Tree Type");
        addTreeTypeButton.setMinWidth(MIN_RADIO_BUTTON_WIDTH);
        grid.add(addTreeTypeButton, 0, 6);

        RadioButton updateTreeTypeButton = new RadioButton("Update Tree Type");
        updateTreeTypeButton.setMinWidth(MIN_RADIO_BUTTON_WIDTH);
        grid.add(updateTreeTypeButton, 0, 7);

        startShift = new RadioButton("Start Shift");
        startShift.setMinWidth(MIN_RADIO_BUTTON_WIDTH);
        grid.add(startShift, 0, 8);

        //inactive by default
        endShift = new RadioButton("End Shift");
        endShift.setMinWidth(MIN_RADIO_BUTTON_WIDTH);
        grid.add(endShift, 0, 9);
        endShift.setDisable(true);

        //inactive by default
        sellTree = new RadioButton("Sell Tree");
        sellTree.setMinWidth(MIN_RADIO_BUTTON_WIDTH);
        grid.add(sellTree, 0, 10);
        sellTree.setDisable(true);
       // sellTree.;

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
        startShift.setToggleGroup(toggleGroup);
        endShift.setToggleGroup(toggleGroup);
        sellTree.setToggleGroup(toggleGroup);

        // submit button
        Button submitButton = new Button("Submit");

        // Handle event when user clicks the submit button
        submitButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                // DEBUG System.out.println("\nYou clicked the Submit button!\n");



                // Check which radio button the user chose
                if (toggleGroup.getSelectedToggle() != null)
                {
                    // Check the selected radio button
                    RadioButton selected = (RadioButton)toggleGroup.getSelectedToggle();

                    // DEBUG String str = selected.getText();
                    // DEBUG System.out.println("Selected button: " + str);

                    if (selected == registerScoutButton)
                    {
                        myModel.stateChangeRequest("RegisterScout", "");
                    }
                    else if(selected == updateScoutButton)
                    {
                        myModel.stateChangeRequest("UpdateScout", "");
                    }
                    else if(selected == removeScoutButton)
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
                    else if(selected == updateTreeTypeButton)
                    {
                        myModel.stateChangeRequest("UpdateTreeType", "");
                    }
                    else if(selected == startShift)
                    {
                        myModel.stateChangeRequest("StartShift", "");
                    }
                    else if(selected == endShift)
                    {
                        //myModel.stateChangeRequest("EndShift", "");
                    }
                    else if(selected == sellTree)
                    {
                        //myModel.stateChangeRequest("SellTree", "");
                    }
                }
                else
                {
                    displayErrorMessage("Please select an option.");
                }
            }
        });


        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.BOTTOM_CENTER);
        buttonContainer.getChildren().add(submitButton);
        grid.add(buttonContainer, 0, 11);

        return grid;
    }
    // ----------------------------------------------------------------------
    private boolean findOpenSessionFlag()
    {
        //Session model getstate
        openSessionFlag = (Boolean)myModel.getState("FindOpenSession");
        return openSessionFlag;
    }


    // ----------------------------------------------------------------------
    //@Override
    public void updateState(String key, Object value)
    {

    }

    //-----------------------------------------------------------------------
    public void populateFields()
    {
        //Check for an open session
        openSessionFlag = findOpenSessionFlag();
        // DEBUG System.out.println("Checking for open session - flag value: " + openSessionFlag);
        if (openSessionFlag == true) {
            startShift.setDisable(true);
            endShift.setDisable(false);
            sellTree.setDisable(false);
        }
        else {
            startShift.setDisable(false);
            endShift.setDisable(true);
            sellTree.setDisable(true);
        }
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

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

}
