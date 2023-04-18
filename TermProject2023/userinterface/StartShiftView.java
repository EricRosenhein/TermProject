package userinterface;

// system imports
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import java.util.Properties;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import model.ScoutCollection;
import Utilities.GlobalData;

// NOTE: Once the user hits add a scout to the shift, we will make a properties object and make a Record with it 04/18/2023


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
    protected TextField startingCash;
    protected ComboBox scoutsComboBox;
    protected TextField companionName;
    protected TextField companionHours;
    protected TextField scoutStartTimeHour;
    protected TextField scoutStartTimeMinute;
    protected TextField scoutEndTimeHour;
    protected TextField scoutEndTimeMinute;

    private TableView<Scout> table = new TableView<>();

    protected Button addSessionButton;
    protected Button addScoutToShiftButton;
    protected Button startShiftButton;
    protected Button cancelButton;

    protected ScoutCollection scoutCollection = new ScoutCollection();
    protected Vector scoutList;


    // For showing error message
    protected MessageView statusLog;

    //------------------------------------------------------------
    public StartShiftView(IModel shift)
    {
        super(shift, "StartShiftView");
        //DEBUG System.out.println("model/StartShiftView: Constructor: getting here!");

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
        Text shiftDateLabel = new Text("Date: ");
        shiftDateLabel.setFont(font);
        shiftDateLabel.setWrappingWidth(150);
        shiftDateLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(shiftDateLabel, 0, 1);

        datePicker = new DatePicker(LocalDate.now());
        grid.add(datePicker, 1, 1);
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 1 - datePicker");

        Text shiftStartLabel = new Text("Start Time: ");
        shiftStartLabel.setFont(font);
        shiftStartLabel.setWrappingWidth(150);
        shiftStartLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(shiftStartLabel, 0, 2);
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 1.1");

        //Start time hour HBox
        HBox shiftStartT = new HBox(5);
        shiftStartT.setAlignment(Pos.CENTER_LEFT);

        startTimeHour = new TextField();
        startTimeHour.setEditable(true);
        startTimeHour.setMaxWidth(35);
        shiftStartT.getChildren().add(startTimeHour);
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 1.2");

        Text startHourEnd = new Text("H");
        startHourEnd.setFont(font);
        startHourEnd.setTextAlignment(TextAlignment.LEFT);
        shiftStartT.getChildren().add(startHourEnd);
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 1.3");

        startTimeMinute = new TextField();
        startTimeMinute.setEditable(true);
        startTimeMinute.setMaxWidth(35);
        shiftStartT.getChildren().add(startTimeMinute);
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 1.4");

        Text startMinEnd = new Text("M");
        startMinEnd.setFont(font);
        startMinEnd.setTextAlignment(TextAlignment.LEFT);
        shiftStartT.getChildren().add(startMinEnd);
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 1.5");

        grid.add(shiftStartT, 1, 2);
        //End of start HBox
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 1.6");

        Text shiftEndLabel = new Text("End Time ");
        shiftEndLabel.setFont(font);
        shiftEndLabel.setWrappingWidth(150);
        shiftEndLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(shiftEndLabel, 0, 3);
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 1.7");

        //End time hour HBox
        HBox shiftEndT = new HBox(5);
        shiftEndT.setAlignment(Pos.CENTER_LEFT);

        endTimeHour = new TextField();
        endTimeHour.setEditable(true);
        endTimeHour.setMaxWidth(35);
        shiftEndT.getChildren().add(endTimeHour);
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 1.8");

        Text endHourEnd = new Text("H");
        endHourEnd.setFont(font);
        endHourEnd.setTextAlignment(TextAlignment.LEFT);
        shiftEndT.getChildren().add(endHourEnd);
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 1.9");

        endTimeMinute = new TextField();
        endTimeMinute.setEditable(true);
        endTimeMinute.setMaxWidth(35);
        shiftEndT.getChildren().add(endTimeMinute);
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 1.10");

        Text endMinEnd = new Text("M");
        endMinEnd.setFont(font);
        endMinEnd.setTextAlignment(TextAlignment.LEFT);
        shiftEndT.getChildren().add(endMinEnd);
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 1.11");

        grid.add(shiftEndT, 1, 3);
        //End of end time HBox
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 2 - added shift time data fields");

        Text startingCashLabel = new Text("Starting Cash: ");
        startingCashLabel.setFont(font);
        startingCashLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(startingCashLabel,0,4);
        
        startingCash = new TextField();
        startingCash.setEditable(true);
        grid.add(startingCash, 1,4);

        addSessionButton = new Button("Start Session");
        grid.add(addSessionButton,2,4);
        addSessionButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                clearErrorMessage();
                myModel.stateChangeRequest("StartSession","");
            }
        });

        Text scoutsBoxLabel = new Text("Select a Scout: ");
        scoutsBoxLabel.setFont(font);
        scoutsBoxLabel.setWrappingWidth(150);
        scoutsBoxLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(scoutsBoxLabel, 0, 5);

        scoutsComboBox = new ComboBox();
        scoutsComboBox.setValue(null);
        //scoutsComboBox.getItems().addAll();//scoutList
        grid.add(scoutsComboBox, 1, 5);

        Text companionLabel = new Text("Companion Name: ");
        companionLabel.setFont(font);
        companionLabel.setWrappingWidth(150);
        companionLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(companionLabel, 0, 6);

        //Companion HBox
        HBox companionInfo = new HBox(5);
        companionInfo.setAlignment(Pos.CENTER_LEFT);

        companionName = new TextField();
        companionName.setEditable(true);
        companionName.setMaxWidth(100);
        companionInfo.getChildren().add(companionName);

        Text hourLabel = new Text("Hours: ");
        hourLabel.setFont(font);
        hourLabel.setTextAlignment(TextAlignment.LEFT);
        companionInfo.getChildren().add(hourLabel);

        companionHours = new TextField();
        companionHours.setEditable(true);
        companionHours.setMaxWidth(35);
        companionInfo.getChildren().add(companionHours);

        grid.add(companionInfo, 1, 6);
        //end of companion hbox
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 3 - added companion hours fields");

        HBox cashAndStartBox = new HBox(5);
        cashAndStartBox.setAlignment(Pos.CENTER_LEFT);
        Text scoutStartLabel = new Text("Scout Start Time: ");
        scoutStartLabel.setFont(font);
        scoutStartLabel.setWrappingWidth(150);
        scoutStartLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(scoutStartLabel, 0, 7);

        //Scout Start time hour HBox
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

        grid.add(scoutShiftStart, 1, 7);
        //End of start HBox

        Text scoutEndLabel = new Text("Scout End Time: ");
        scoutEndLabel.setFont(font);
        scoutEndLabel.setWrappingWidth(150);
        scoutEndLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(scoutEndLabel, 0, 8);

        //Scout End time hour HBox
        HBox scoutEndT = new HBox(5);
        scoutEndT.setAlignment(Pos.CENTER_LEFT);

        scoutEndTimeHour = new TextField();
        scoutEndTimeHour.setEditable(true);
        scoutEndTimeHour.setMaxWidth(35);
        scoutEndT.getChildren().add(scoutEndTimeHour);

        Text scoutEndHourEndLabel = new Text(" H ");
        scoutEndHourEndLabel.setFont(font);
        scoutEndHourEndLabel.setTextAlignment(TextAlignment.LEFT);
        scoutEndT.getChildren().add(scoutEndHourEndLabel);

        scoutEndTimeMinute = new TextField();
        scoutEndTimeMinute.setEditable(true);
        scoutEndTimeMinute.setMaxWidth(35);
        scoutEndT.getChildren().add(scoutEndTimeMinute);

        Text scoutEndMinEnd = new Text(" M ");
        scoutEndMinEnd.setFont(font);
        scoutEndMinEnd.setTextAlignment(TextAlignment.LEFT);
        scoutEndT.getChildren().add(scoutEndMinEnd);
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 4 - added scout shift time fields");

        Button addButton = new Button("Add Scout to Shift");

        // Add event handler to add button
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();

                //populate table with scout from combobox

            }
        });
        scoutEndT.getChildren().add(addButton);

        grid.add(scoutEndT, 1, 8);
        //End of end time HBox


        // Cancel and submit buttons
        cancelButton = new Button("Cancel");
        startShiftButton = new Button("StartShift");

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

        startShiftButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                processAction(e);
            }
        });

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);

        buttonContainer.getChildren().add(cancelButton);
        buttonContainer.getChildren().add(startShiftButton);



        vbox.getChildren().add(grid);

        addTableColumns();
        vbox.getChildren().add(table);  
        vbox.getChildren().add(buttonContainer);
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 5 - added navigation buttons");

        return vbox;
    }

    //-------------------------------------------------------------------------------------------------
    private void populateFields()
    {
        populateScoutComboBox();

    }

    //-------------------------------------------------------------------------------------------------
    /** Populates combo box containing the scouts
     */
    private void populateScoutComboBox()
    {
        /* Rewriting in StartShiftTransaction : searchForAvailableScouts() */

        String fn = "";
        String ln = "";
        String id = "";

        // Makes a request to the model to change the state
        myModel.stateChangeRequest("SearchForAvailableScouts", null);

        // Gets the available scouts by passing in the string to the model
        scoutList = (Vector)myModel.getState("GetAvailableScouts");

        // For each scout in the list, add them to combo box
        for(int i = 0; i < scoutList.size(); i++)
        {
            // Get the current scout in the list
            Scout currentScout = (Scout)scoutList.elementAt(i);

            // Get ln from the scout list
            ln = currentScout.getLastName();

            // Get fn from the scout list
            fn = currentScout.getFirstName();

            // Get troop id from the scout list
            id = currentScout.getTroopID();

            // Format the string -> Last Name, First Name (Troop ID)
            String formattedScout = ln + ", " + fn + " (" + id + ")";

            // Add the formatted string to combo box
            scoutsComboBox.getItems().add(formattedScout);
        }

    }

    //-------------------------------------------------------------------------------------------------
    /** Populates the table at the bottom of the view
     */
    private void addTableColumns() {
        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        TableColumn middleNameCol = new TableColumn("Middle Name");
        TableColumn troopIDCol = new TableColumn("Troop ID");
        troopIDCol.setCellValueFactory(new PropertyValueFactory<>("TroopID"));


        table.setMinHeight(GlobalData.TABLE_MIN_HEIGHT);
        table.setMaxHeight(GlobalData.TABLE_MAX_HEIGHT);

        Text placeholder = new Text("No Scouts added to Shift yet");
        table.setPlaceholder(placeholder);

        table.getColumns().addAll( firstNameCol, lastNameCol, troopIDCol);

    }

    //-------------------------------------------------------------------------------------------------
    /** Processes the given event
     *
     * @param evt   event to process
     */
    public void processAction(Event evt)
    {
        // Gets the index of the selected scout --will move this to appropriate place later
        // int selectedScoutIndex = scoutsComboBox.getSelectionModel().getSelectedIndex();
        // scoutList.get(selectedScoutIndex);
    }

    //-----------------------------------------------------------------------
    /**
     *  Handling validation for all fields. If validation passes, create a Shift object with a Properties object
     *  that we pass into the Shift(Properties shiftInfo) constructor. Then, that Shift object would be added to the
     *  list and that list will be shown on the table.
     */
//    public void validateStartShift()
//    {
//        // Validate everything here or do separate validation methods?

//        String startHour = startTimeHour.getText();
//        String startMin = startTimeMinute.getText();
//        String endHour =  endTimeHour.getText();
//        String endMin = endTimeMinute.getText();
//        String compName = companionName.getText();
//        String compHours = companionHours.getText();
//        String TextField scoutStartTimeHour;
//        protected TextField scoutStartTimeMinute;
//        protected TextField scoutEndTimeHour;
//        protected TextField scoutEndTimeMinute;
//    }

    //-----------------------------------------------------------------------
    /** Validates the companion name. Things to check for - length, numbers/special characters in name, blanks, null?
     *
     * @param companionName
     */
    private void validateCompanionName(String companionName)
    {
        // Check if name is null or only has whitespaces
        if (companionName == null || companionName.trim().length() == 0)
        {
            displayMessage("Companion name can not be empty!");
        }
        // Companion name should not be more than 50 characters
        else if (companionName.length() > 50)
        {
            displayErrorMessage("Companion name must be less than 50 characters!");
        }
        // Check if name has numbers and special characters
        else if ()
        {
            
        }
    }

    //-----------------------------------------------------------------------
    /** Validates the scout hours. Things to check for - length, char in time, blanks, null?
     *
     * @param scoutStartTime, scoutEndTime
     */
    private void validateScoutTimes(String scoutStartTime, String scoutEndTime)
    {

    }

    //-----------------------------------------------------------------------
    private void validateCompanionHours(String companionHours)
    {

    }

    //-----------------------------------------------------------------------
    /** Stores the data from the text fields and sends the desired operation to the model
     * 
     */
    protected void getOperation()
    {
        Properties p = gatherUserEnteredData();
        //myModel.stateChangeRequest("InsertTreeData", p);
    }

    //-----------------------------------------------------------------------
    /** Retrieves the data from the text fields
     *
     * @return props     Properties object containing the data retrieved
     */
    protected Properties gatherUserEnteredData()
    {
        LocalDateTime ldt = LocalDateTime.now();
        String nowTime = ldt.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        int selectedScoutIndex = scoutsComboBox.getSelectionModel().getSelectedIndex();
        Scout selectedScout = (Scout) scoutList.get(selectedScoutIndex);

        Properties props = new Properties();
        props.setProperty("CompanionName", (String) companionName.getText());
        props.setProperty("StartTime", scoutStartTimeHour.getText() + scoutStartTimeMinute.getText());
        props.setProperty("EndTime", scoutEndTimeHour.getText() + scoutEndTimeMinute.getText());
        props.setProperty("CompanionHours", companionHours.getText());
        return props;

        // The props object returned above will probably be used as an argument in Transaction(Properties proprs) constructor
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
    public void clearErrorMessage() {
        statusLog.clearErrorMessage();
    }

}
