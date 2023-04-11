// specify package
package userinterface;

// system imports
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
import model.Scout;

public class RemoveScoutView extends View
{
    protected Button cancelButton;
    protected Button submitButton;

    protected MessageView statusLog;

    // --------------------------------------------------------
    public RemoveScoutView(IModel removeScout)
    {
        super(removeScout, "RemoveScoutView");

        VBox container = new VBox(10);

        container.setPadding(new Insets(15, 5, 5, 5));
        container.setAlignment(Pos.CENTER);

        container.getChildren().add(createTitle());

        container.getChildren().add(createFormContents());

        container.getChildren().add(createStatusLog("                                "));

        container.getChildren().add(new Text("                           "));
        container.getChildren().add(new Text("                           "));

        getChildren().add(container);

        // populateFields();

        myModel.subscribe("ScoutUpdateStatusMessage", this);
    }

    // ----------------------------------------------------------------------
    private Node createTitle()
    {
        Text titleText = new Text(" Boy Scout Troop 209 Tree Sales: \nRemove Scout ");
        titleText.setFont(Font.font("Serif", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.BURLYWOOD);

        return titleText;
    }

    // ----------------------------------------------------------------------
    private Node createFormContents()
    {
        VBox vbox = new VBox(10);

        // GridPane setup
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Instruction Prompt setup
        Text prompt = new Text("Set this Scout to Inactive?");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 1);

        Scout scoutToDelete = (Scout) myModel.getState("GetScoutToRemove");

        Text scoutInfo = new Text("Name: " + scoutToDelete.getState("FirstName") + " " +
                scoutToDelete.getState("MiddleName") + " " +scoutToDelete.getState("LastName") +
                "\nDate of Birth: " + scoutToDelete.getState("DateOfBirth") +
                "\nPhone Number: " + scoutToDelete.getState("PhoneNumber") +
                "\nEmail: " + scoutToDelete.getState("Email") +
                "\nTroopID: " + scoutToDelete.getState("TroopID"));
        scoutInfo.setWrappingWidth(400);
        scoutInfo.setTextAlignment(TextAlignment.CENTER);
        scoutInfo.setFill(Color.BLACK);
        grid.add(scoutInfo,0,2);

        // Cancel and submit buttons
        cancelButton = new Button("Cancel");
        submitButton = new Button("Confirm");

        // Handle events for regular buttons
        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
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
        buttonContainer.setAlignment(Pos.CENTER);

        buttonContainer.getChildren().add(cancelButton);
        buttonContainer.getChildren().add(submitButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(buttonContainer);

        return vbox;
    }

    public void processAction(Event evt)
    {
        myModel.stateChangeRequest("RemoveScout", "");
    }

    protected MessageView createStatusLog(String initialMessage) {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    // ----------------------------------------------------------------------
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
}