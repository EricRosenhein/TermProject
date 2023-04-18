package userinterface;

// system imports
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
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
import model.ScoutCollection;

/** This is the Start A Shift class for the Application */
//=============================================================
public class StartShiftView extends View
{
    //Date
    protected DatePicker datePicker;
    protected String date;
    protected LocalDateTime ldt = LocalDateTime.now();
    protected String now = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    //Start&End
    protected TextField startTimeHour;
    protected TextField startTimeMinute;
    protected TextField endTimeHour;
    protected TextField endTimeMinute;
    protected ComboBox scoutsComboBox;
    protected TextField companionName;
    protected TextField companionHours;
    protected TextField companionStartTimeHour;
    protected TextField companionStartTimeMinute;
    protected TextField companionEndTimeHour;

    protected TextField scoutStartTimeHour;
    protected TextField scoutStartTimeMinute;
    protected TextField scoutEndTimeHour;
    protected TextField scoutEndTimeMinute;

   // private TableView<StartShift> table = new TableView<>();

    protected Button add;
    protected Button startShift;
    protected Button cancel;

    protected ScoutCollection scoutCollection = new ScoutCollection();
    protected Vector scoutList;


    // For showing error message
    protected MessageView statusLog;

    //------------------------------------------------------------
    public StartShiftView(IModel shift)
    {
        super(shift, "StartAShiftiew");
        // DEBUG System.out.println("In ScoutView constructor");

        // Housekeeping initializations
        datePicker = new DatePicker(LocalDate.now());

        VBox container = new VBox(10);

        container.setPadding(new Insets(15, 5, 5, 5));
        container.setAlignment(Pos.CENTER);

        container.getChildren().add(createTitle());

        container.getChildren().add(createFormContents());

        container.getChildren().add(createStatusLog("       "));

        container.getChildren().add(new Text("                           "));
        container.getChildren().add(new Text("                           "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("ShiftUpdateStatusMessage", this);
    }

    public StartShiftView(IModel model, String classname) {
        super(model, classname);
    }

    // ----------------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Boy Scout Troop 209 Tree Sales: \nStart A Shift ");
        titleText.setFont(Font.font("Serif", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.BURLYWOOD);
        container.getChildren().add(titleText);

        return container;
    }

    // Create the main form content
    //------------------------------------------------------------
    private VBox createFormContents()
    {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text(" Start A Shift ");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        // Set the font
        Font font = Font.font("Arial", FontWeight.BOLD, 12);

        //data fields
        Text shiftDateLabel = new Text("Date ");
        shiftDateLabel.setFont(font);
        shiftDateLabel.setWrappingWidth(150);
        shiftDateLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(shiftDateLabel, 0, 1);

        datePicker = new DatePicker(LocalDate.now());
        grid.add(datePicker, 1, 1);

        Text shiftStartLabel = new Text("Start Time ");
        shiftStartLabel.setFont(font);
        shiftStartLabel.setWrappingWidth(150);
        shiftStartLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(shiftStartLabel, 0, 2);

        //Start time hour HBox
        HBox shiftStartT = new HBox(5);
        shiftStartT.setAlignment(Pos.CENTER_LEFT);

        startTimeHour = new TextField();
        startTimeHour.setEditable(true);
        startTimeHour.setMaxWidth(35);
        shiftStartT.getChildren().add(startTimeHour);

        Text startHourEnd = new Text("H");
        startHourEnd.setFont(font);
        startHourEnd.setTextAlignment(TextAlignment.LEFT);
        shiftStartT.getChildren().add(startHourEnd);

        startTimeMinute = new TextField();
        startTimeMinute.setEditable(true);
        startTimeMinute.setMaxWidth(35);
        shiftStartT.getChildren().add(startTimeMinute);

        Text startMinEnd = new Text("M");
        startMinEnd.setFont(font);
        startMinEnd.setTextAlignment(TextAlignment.LEFT);
        shiftStartT.getChildren().add(startMinEnd);
        //End of start HBox

        Text shiftEndLabel = new Text("End Time ");
        shiftEndLabel.setFont(font);
        shiftEndLabel.setWrappingWidth(150);
        shiftEndLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(shiftEndLabel, 0, 3);

        //End time hour HBox
        HBox shiftEndT = new HBox(5);
        shiftEndT.setAlignment(Pos.CENTER_LEFT);

        endTimeHour = new TextField();
        endTimeHour.setEditable(true);
        endTimeHour.setMaxWidth(35);
        shiftEndT.getChildren().add(endTimeHour);

        Text endHourEnd = new Text("H");
        endHourEnd.setFont(font);
        endHourEnd.setTextAlignment(TextAlignment.LEFT);
        shiftEndT.getChildren().add(endHourEnd);

        endTimeMinute = new TextField();
        endTimeMinute.setEditable(true);
        endTimeMinute.setMaxWidth(35);
        shiftEndT.getChildren().add(endTimeMinute);

        Text endMinEnd = new Text("M");
        endMinEnd.setFont(font);
        endMinEnd.setTextAlignment(TextAlignment.LEFT);
        shiftEndT.getChildren().add(endMinEnd);
        //End of end time HBox

        Text scoutsBoxLabel = new Text("Scouts ");
        scoutsBoxLabel.setFont(font);
        scoutsBoxLabel.setWrappingWidth(150);
        scoutsBoxLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(scoutsBoxLabel, 0, 4);

        scoutsComboBox = new ComboBox();
        scoutsComboBox.getItems().addAll();//scoutList
        grid.add(scoutsComboBox, 1, 4);

        Text companionLabel = new Text("Companion ");
        companionLabel.setFont(font);
        companionLabel.setWrappingWidth(150);
        companionLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(companionLabel, 0, 5);

        //Companion HBox
        HBox companionInfo = new HBox(5);
        companionInfo.setAlignment(Pos.CENTER_LEFT);

        companionName = new TextField();
        companionName.setEditable(true);
        companionName.setMaxWidth(100);
        companionInfo.getChildren().add(companionName);

        Text hourLabel = new Text("Hours ");
        hourLabel.setFont(font);
        hourLabel.setTextAlignment(TextAlignment.LEFT);
        companionInfo.getChildren().add(hourLabel);

        companionHours = new TextField();
        companionHours.setEditable(true);
        companionHours.setMaxWidth(35);
        companionInfo.getChildren().add(companionHours);
        //end of companion hbox

        Text scoutStartLabel = new Text("Scout Start ");
        scoutStartLabel.setFont(font);
        scoutStartLabel.setWrappingWidth(150);
        scoutStartLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(scoutStartLabel, 0, 6);

        //Companion Start time hour HBox
        HBox scoutShiftStart = new HBox(5);
        scoutShiftStart.setAlignment(Pos.CENTER_LEFT);

        scoutStartTimeHour = new TextField();
        scoutStartTimeHour.setEditable(true);
        scoutStartTimeHour.setMaxWidth(35);
        scoutShiftStart.getChildren().add(scoutStartTimeHour);

        Text scoutStartHourEndLabel = new Text("H");
        scoutStartHourEndLabel.setFont(font);
        scoutStartHourEndLabel.setTextAlignment(TextAlignment.LEFT);
        scoutShiftStart.getChildren().add(scoutStartHourEndLabel);

        scoutStartTimeMinute = new TextField();
        scoutStartTimeMinute.setEditable(true);
        scoutStartTimeMinute.setMaxWidth(35);
        scoutShiftStart.getChildren().add(scoutStartTimeMinute);

        Text scoutStartMinEndLabel = new Text("M");
        scoutStartMinEndLabel.setFont(font);
        scoutStartMinEndLabel.setTextAlignment(TextAlignment.LEFT);
        scoutShiftStart.getChildren().add(scoutStartMinEndLabel);
        //End of start HBox

        Text scoutEndLabel = new Text("End Time ");
        scoutEndLabel.setFont(font);
        scoutEndLabel.setWrappingWidth(150);
        scoutEndLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(scoutEndLabel, 0, 7);

        //End time hour HBox
        HBox scoutEndT = new HBox(5);
        scoutEndT.setAlignment(Pos.CENTER_LEFT);

        scoutEndTimeHour = new TextField();
        scoutEndTimeHour.setEditable(true);
        scoutEndTimeHour.setMaxWidth(35);
        scoutEndT.getChildren().add(scoutEndTimeHour);

        Text scoutEndHourEndLabel = new Text("H");
        scoutEndHourEndLabel.setFont(font);
        scoutEndHourEndLabel.setTextAlignment(TextAlignment.LEFT);
        scoutEndT.getChildren().add(scoutEndHourEndLabel);

        scoutEndTimeMinute = new TextField();
        scoutEndTimeMinute.setEditable(true);
        scoutEndTimeMinute.setMaxWidth(35);
        scoutEndT.getChildren().add(scoutEndTimeMinute);

        Text scoutEndMinEnd = new Text("M");
        scoutEndMinEnd.setFont(font);
        scoutEndMinEnd.setTextAlignment(TextAlignment.LEFT);
        scoutEndT.getChildren().add(scoutEndMinEnd);
        //End of end time HBox
        Button addButton = new Button("add");

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();

            }
        });




        // Cancel and submit buttons
        Button cancelButton = new Button("Cancel");
        Button StartShiftButton = new Button("StartShift");

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

        StartShiftButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();

            }
        });

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);

        buttonContainer.getChildren().add(cancelButton);
        buttonContainer.getChildren().add(StartShiftButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(buttonContainer);

        return vbox;
    }


    private void populateFields(){

        //pass 2 null names and get list of all scouts in database?
        String fn = "";
        String ln = "";

        scoutList = scoutCollection.findActiveScoutsWithNameLike(fn, ln);
        //idea to populate combobox:
        //Loop through scoutList and get last name, first name, and troopID
        for(int i = 0; i > scoutList.size(); i++) {
            scoutList.elementAt(i);
        }
        //add first name, last name, and troopID to their own arraylist
        // create another loop to format and create another arraylist of correctly formatted strings
        //create loop that adds correctly formatted strings to combobox



    }

    // ----------------------------------------------------------------------
    /**
     * @param key
     * @param value
     */
    public void updateState(String key, Object value)
    {
        if (key.equals("ShiftUpdateStatusMessage") == true)
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

}
