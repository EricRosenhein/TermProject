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
import javafx.scene.control.ComboBox;
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
import javafx.collections.FXCollections;
import model.TransactionFactory;
import model.Scout;

public class RemoveScoutView extends View
{
    protected ComboBox statusComboBox;
    protected Button cancelButton;
    protected Button submitButton;

    protected MessageView statusLog;
    // Confirm remove scout
    public RemoveScoutView(IModel removeScout)
    {
        super(removeScout, "RemoveScoutView");

        // TEST
        System.out.println("In UpdateScoutView constructor");

        // Housekeeping initializations
       // datePicker = new DatePicker(LocalDate.now());
        VBox container = new VBox(10);

        container.setPadding(new Insets(15, 5, 5, 5));
        container.setAlignment(Pos.CENTER);

        container.getChildren().add(createTitle());

        container.getChildren().add(createFormContents());


        container.getChildren().add(createStatusLog("                                "));
        getChildren().add(container);

       // populateFields();

        myModel.subscribe("ScoutUpdateStatusMessage", this);
    }

    // ----------------------------------------------------------------------
    private Node createTitle()
    {
        Text titleText = new Text(" Boy Scout Troop 209 Tree Sales: Remove Scout ");
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

        Text prompt = new Text("Set this scout to Inactive?");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1); 

        Scout scoutToUpdate = (Scout) myModel.getState("GetScoutToRemove");

        Text scoutInfo = new Text(scoutToUpdate.toString());
        scoutInfo.setWrappingWidth(400);
        scoutInfo.setTextAlignment(TextAlignment.CENTER);
        scoutInfo.setFill(Color.BLACK);
        grid.add(scoutInfo, 0, 0, 3, 2);

        return grid;
    }

    protected MessageView createStatusLog(String initialMessage) {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    // ----------------------------------------------------------------------
    public void updateState(String key, Object value)
    {
        
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
