package userinterface;

// system imports
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private TableView<Scout> scoutsTable = new TableView<>();

    protected Button addSessionButton;
    protected Button addScoutButton;
    protected Button cancelButton;

    protected ScoutCollection scoutCollection = new ScoutCollection();
    protected Vector scoutList;
    protected Scout selectedScout;

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

        myModel.subscribe("SessionStatusMessage", this);
        myModel.subscribe("ShiftStatusMessage", this);
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

        Text prompt = new Text(" Start a shift then add each Scout working to the shift.\n " +
                "Note: Please enter all times in 24 hour time format (HH:MM)");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        // Set the font
        Font font = Font.font("Arial", FontWeight.BOLD, 12);

        //data fields
        Text shiftDateLabel = new Text("Start Date: ");
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



        //Start time HBox
        HBox shiftStartT = new HBox(5);
        shiftStartT.setAlignment(Pos.CENTER_LEFT);

        // hours
        startTimeHour = new TextField();
        startTimeHour.setEditable(true);
        startTimeHour.setMaxWidth(GlobalData.MAX_TIME_TEXTFIELD_LENGTH);
        shiftStartT.getChildren().add(startTimeHour);
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 1.2");

        Text startHourEnd = new Text(":");
        startHourEnd.setFont(font);
        startHourEnd.setTextAlignment(TextAlignment.CENTER);
        shiftStartT.getChildren().add(startHourEnd);
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 1.3");

        // mins
        startTimeMinute = new TextField();
        startTimeMinute.setEditable(true);
        startTimeMinute.setMaxWidth(GlobalData.MAX_TIME_TEXTFIELD_LENGTH);
        shiftStartT.getChildren().add(startTimeMinute);
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 1.4");

        // add to grid
        grid.add(shiftStartT, 1, 2);
        //End of start HBox
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 1.6");


        Text shiftEndLabel = new Text("End Time: ");
        shiftEndLabel.setFont(font);
        shiftEndLabel.setWrappingWidth(150);
        shiftEndLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(shiftEndLabel, 0, 3);
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 1.7");


        //End time HBox
        HBox shiftEndT = new HBox(5);
        shiftEndT.setAlignment(Pos.CENTER_LEFT);

        // hour
        endTimeHour = new TextField();
        endTimeHour.setEditable(true);
        endTimeHour.setMaxWidth(GlobalData.MAX_TIME_TEXTFIELD_LENGTH);
        shiftEndT.getChildren().add(endTimeHour);
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 1.8");

        Text endHourEnd = new Text(":");
        endHourEnd.setFont(font);
        endHourEnd.setTextAlignment(TextAlignment.LEFT);
        shiftEndT.getChildren().add(endHourEnd);
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 1.9");

        // min
        endTimeMinute = new TextField();
        endTimeMinute.setEditable(true);
        endTimeMinute.setMaxWidth(GlobalData.MAX_TIME_TEXTFIELD_LENGTH);
        shiftEndT.getChildren().add(endTimeMinute);
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 1.10");

        // add to grid
        grid.add(shiftEndT, 1, 3);
        //End of end time HBox
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 2 - added shift time data fields");

        Text startingCashLabel = new Text(" Starting Cash: ");
        startingCashLabel.setFont(font);
        startingCashLabel.setWrappingWidth(150);
        startingCashLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(startingCashLabel,0,4);

        HBox cashBox = new HBox(5);
        startingCash = new TextField();
        startingCash.setEditable(true);
        startingCash.setMaxWidth(100);
        cashBox.getChildren().add(startingCash);

        addSessionButton = new Button("Start Shift");
        cashBox.getChildren().add(addSessionButton);
        addSessionButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                clearErrorMessage();
                validateSession();
            }
        });
        grid.add(cashBox, 1,4);


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
        companionName.setMaxWidth(150);
        companionInfo.getChildren().add(companionName);

        Text hourLabel = new Text("Hours: ");
        hourLabel.setFont(font);
        hourLabel.setTextAlignment(TextAlignment.LEFT);
        companionInfo.getChildren().add(hourLabel);

        companionHours = new TextField();
        companionHours.setEditable(true);
        companionHours.setMaxWidth(GlobalData.MAX_TIME_TEXTFIELD_LENGTH);
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
        scoutStartTimeHour.setMaxWidth(GlobalData.MAX_TIME_TEXTFIELD_LENGTH);
        scoutShiftStart.getChildren().add(scoutStartTimeHour);

        Text scoutStartHourEndLabel = new Text(":");
        scoutStartHourEndLabel.setFont(font);
        scoutStartHourEndLabel.setTextAlignment(TextAlignment.LEFT);
        scoutShiftStart.getChildren().add(scoutStartHourEndLabel);

        scoutStartTimeMinute = new TextField();
        scoutStartTimeMinute.setEditable(true);
        scoutStartTimeMinute.setMaxWidth(GlobalData.MAX_TIME_TEXTFIELD_LENGTH);
        scoutShiftStart.getChildren().add(scoutStartTimeMinute);

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
        scoutEndTimeHour.setMaxWidth(GlobalData.MAX_TIME_TEXTFIELD_LENGTH);
        scoutEndT.getChildren().add(scoutEndTimeHour);

        Text scoutEndHourEndLabel = new Text(":");
        scoutEndHourEndLabel.setFont(font);
        scoutEndHourEndLabel.setTextAlignment(TextAlignment.LEFT);
        scoutEndT.getChildren().add(scoutEndHourEndLabel);

        scoutEndTimeMinute = new TextField();
        scoutEndTimeMinute.setEditable(true);
        scoutEndTimeMinute.setMaxWidth(GlobalData.MAX_TIME_TEXTFIELD_LENGTH);
        scoutEndT.getChildren().add(scoutEndTimeMinute);


        addScoutButton = new Button("Add Scout to Shift");

        // Add event handler to add button
        addScoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();

                validateShiftEntry();

                //populate table with scout from combobox

            }
        });
        scoutEndT.getChildren().add(addScoutButton);

        grid.add(scoutEndT, 1, 8);
        //End of end time HBox


        // Cancel and submit buttons
        cancelButton = new Button("Return");

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

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);

        buttonContainer.getChildren().add(cancelButton);

        vbox.getChildren().add(grid);

        addTableColumns();
        vbox.getChildren().add(scoutsTable);
        vbox.getChildren().add(buttonContainer);
        // DEBUG System.out.println("StartShiftView: createFormContents(): Eric was here 5 - added navigation buttons");

        return vbox;
    }

    //-------------------------------------------------------------------------------------------------
    private void populateFields()
    {
        populateScoutComboBox();
        addScoutButton.setDisable(true);
        cancelButton.setDisable(true);
    }

    // -----------------------------------------------------------------------------
    private void copyTimes()
    {
        String startHour = startTimeHour.getText();
        String startMin = startTimeMinute.getText();
        String endHour = endTimeHour.getText();
        String endMin = endTimeMinute.getText();
        scoutStartTimeHour.setText(startHour);
        scoutStartTimeMinute.setText(startMin);
        scoutEndTimeHour.setText(endHour);
        scoutEndTimeMinute.setText(endMin);

        int sHour = Integer.parseInt(startHour);
        int eHour = Integer.parseInt(endHour);
        int cHours = eHour - sHour;
        String compHours = Integer.toString(cHours);
        companionHours.setText(compHours);
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
        TableColumn troopIDCol = new TableColumn("Troop ID");
        troopIDCol.setCellValueFactory(new PropertyValueFactory<>("TroopID"));
        TableColumn emailCol = new TableColumn("Email");

        emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
        TableColumn phoneNumCol = new TableColumn("Phone Number");
        phoneNumCol.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));


        scoutsTable.setMinHeight(GlobalData.TABLE_MIN_HEIGHT);
        scoutsTable.setMaxHeight(GlobalData.TABLE_MAX_HEIGHT);

        Text placeholder = new Text("No Scouts added to Shift yet");
        scoutsTable.setPlaceholder(placeholder);

        scoutsTable.getColumns().addAll( firstNameCol, lastNameCol, troopIDCol, emailCol, phoneNumCol);

    }

    // ------------------------------------------------------------------------------------------------
    private Boolean checkTime(String tH, String tM)
    {
        if ((tH.length() != GlobalData.MAX_TIME_LENGTH) || (tM.length() != GlobalData.MAX_TIME_LENGTH))
            return false;
        else
        {
            try {
                int hour = Integer.parseInt(tH);
                int min = Integer.parseInt(tM);
                if ( hour < 0 || hour > 23 || min < 0 || min > 59 )
                    return false;
                else
                    return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    // ---------------------------------------------------------------------------------------
    private boolean checkHours(String hourVal)
    {
        if (hourVal.length() > GlobalData.MAX_TIME_LENGTH)
            return false;
        else
        {
            try
            {
                int hourValue = Integer.parseInt(hourVal);
                if (hourValue >= 0)
                    return true;
                else
                    return false;
            }
            catch (NumberFormatException excep)
            {
                return false;
            }
        }
    }
    // ---------------------------------------------------------------------------------------
    private boolean validateCashValue(String cashVal)
    {
        if (cashVal.length() > GlobalData.MAX_STARTING_CASH_LENGTH)
            return false;
        else
        {
            try
            {
                double cashValue = Double.parseDouble(cashVal);
                if (cashValue > 0.0)
                    return true;
                else
                    return false;
            }
            catch (NumberFormatException excep)
            {
                return false;
            }
        }
    }

    // ------------------------------------------------------------------------------------------------
    public void validateSession()
    {
        LocalDate dt = datePicker.getValue();
        //LocalDateTime selectedDate = dt.atStartOfDay();  // Can be used to set boundaries on session start date
        String chosenDate = dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // gather user entered data
        String sHour = startTimeHour.getText();
        String sMin = startTimeMinute.getText();
        String sTime = sHour + ":" + sMin;
        int sHourInt = Integer.parseInt(sHour);
        int sMinInt = Integer.parseInt(sMin);

        String eHour = endTimeHour.getText();
        String eMin = endTimeMinute.getText();
        String eTime = eHour + ":" + eMin;
        int eHourInt = Integer.parseInt(eHour);
        int eMinInt = Integer.parseInt(eMin);

        String sCash = startingCash.getText();

        // validation of all entry fields
        if( (sHour == null) || (sHour.length() == 0) || (sMin == null) || (sMin.length() == 0) ||
                (eHour == null) || (eHour.length() == 0) || (eMin == null) || (eMin.length() == 0) ||
                (sCash == null) || (sCash.length() == 0))
        {
            displayErrorMessage("ERROR: Please complete the entire form.");
        }
        else if ((checkTime(sHour,sMin) == false) || (checkTime(eHour,eMin) == false))
        {
            displayErrorMessage("ERROR: Times must be entered in 24 hour time format.");
        }
        //QC: Check if start time is later than end time
        else if ((sHourInt > eHourInt))
        {
            displayErrorMessage("ERROR: Start time is later than end time.");
        }
        else if (((sHourInt == eHourInt) && (sMinInt > eMinInt)))
        {
            displayErrorMessage("ERROR: Start time is later than end time.");

        }
        else if ((sHourInt == eHourInt) && (sMinInt == eMinInt))
        {
            displayErrorMessage("ERROR: Start time is equal to end time.");

        }
        else if (validateCashValue(sCash) == false)
        {
            displayErrorMessage("ERROR: Staring Cash value too long, or not numeric!");
        }
        else {

            //Populate properties object and call to start a session
            Properties p = new Properties();
            p.setProperty("StartDate", chosenDate);
            p.setProperty("StartTime", sTime);
            p.setProperty("EndTime", eTime);
            p.setProperty("StartingCash", sCash);
            myModel.stateChangeRequest("StartSession", p);
        }
    }

    //-----------------------------------------------------------------------
     /**
     *
     * @param
     */
    private void validateShiftEntry()
    {
        //Gathering data from GUI
        LocalDateTime ldt = LocalDateTime.now();
        String nowTime = ldt.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        int selectedScoutIndex = scoutsComboBox.getSelectionModel().getSelectedIndex();
        selectedScout = (Scout) scoutList.get(selectedScoutIndex);

        String cn = companionName.getText();
        String scoutSTimeHour = scoutStartTimeHour.getText();
        String scoutSTimeMin = scoutStartTimeMinute.getText();
        String scoutSTime = scoutSTimeHour + ":" + scoutSTimeMin;
        int sHourInt = Integer.parseInt(scoutSTimeHour);
        int sMinInt = Integer.parseInt(scoutSTimeMin);

        String scoutETimeHour = scoutEndTimeHour.getText();
        String scoutETimeMin = scoutEndTimeMinute.getText();
        String scoutETime = scoutETimeHour + ":" + scoutETimeMin;
        int eHourInt = Integer.parseInt(scoutETimeHour);
        int eMinInt = Integer.parseInt(scoutETimeMin);

        String ch = companionHours.getText();
        //Validation

        if((cn == null) || (cn.length() == 0) ||
                (scoutSTimeHour == null) || (scoutSTimeHour.length() == 0) ||
                (scoutSTimeMin == null) || (scoutSTimeMin.length() == 0) ||
                (scoutETimeHour == null) || (scoutETimeHour.length() == 0) ||
                (scoutETimeMin == null) || (scoutETimeMin.length() == 0) ||
                (ch == null) || (ch.length() == 0)){
            displayErrorMessage("ERROR: Please complete the entire form.");
        }
        else if ((checkTime(scoutSTimeHour,scoutSTimeMin) == false) ||
                (checkTime(scoutETimeHour,scoutETimeMin) == false))
        {
            displayErrorMessage("ERROR: Times must be entered in 24-hour format.");
        }
        else if ((sHourInt > eHourInt))
        {
            displayErrorMessage("ERROR: Start time is later than end time.");
        }
        else if (((sHourInt == eHourInt) && (sMinInt > eMinInt)))
        {
            displayErrorMessage("ERROR: Start time is later than end time.");
        }
        else if ((sHourInt == eHourInt) && (sMinInt == eMinInt))
        {
            displayErrorMessage("ERROR: Start time is equal to end time.");
        }
        else if ((cn.length() > GlobalData.MAX_NAME_LENGTH))
        {
            displayErrorMessage("ERROR: Names must be under 25 characters.");
        }
        else if ((checkHours(ch) == false))
        {
            displayErrorMessage("ERROR: Companion hours must be 0 (0-59mins) or greater.");
        }
        else {
            //Setting properties object and updating in database
            Properties props = new Properties();
            props.setProperty("ScoutID", (String)selectedScout.getState("ID"));
            props.setProperty("CompanionName", cn);
            props.setProperty("StartTime", scoutSTime);
            props.setProperty("EndTime", scoutETime);
            props.setProperty("CompanionHours", ch);
            myModel.stateChangeRequest("AddScoutToShift",props);
        }
    }

    // ----------------------------------------------------------------------

    /** Updates the state of the current view
     * @param key
     * @param value     value to be mapped to the given key

     */
    public void updateState(String key, Object value)
    {
        if (key.equals("SessionStatusMessage") == true)
        {
            String msg = (String)value;
            if (msg.startsWith("ERR") == true)
                displayErrorMessage(msg);
            else {
                displayMessage(msg);
                addSessionButton.setDisable(true);
                addScoutButton.setDisable(false);
                copyTimes();
            }
        }
        else
        if (key.equals("ShiftStatusMessage") == true)
        {
            String msg = (String)value;
            if (msg.startsWith("ERR") == true)
                displayErrorMessage(msg);
            else {
                displayMessage(msg);
                cancelButton.setDisable(false);
                copyTimes();
                companionName.clear();
            }
            getEntryTableModelValues();
        }
    }

    // ---------------------------------------------------------------------
    private void getEntryTableModelValues()
    {
        Vector<Scout> selectedScoutList = (Vector<Scout>)myModel.getState("GetSelectedScouts");
        ObservableList<Scout> tableEntry = FXCollections.observableList(selectedScoutList);
        scoutsTable.setItems(tableEntry);
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
