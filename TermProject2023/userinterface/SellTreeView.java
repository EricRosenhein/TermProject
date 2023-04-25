package userinterface;

// system imports
import Utilities.GlobalData;
import exception.InvalidPrimaryKeyException;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

// project imports
import impresario.IModel;
import model.Tree;
import model.TreeType;


// NOTE: Customer info should get stored in Transaction database table

public class SellTreeView extends View
{
    // GUI Components
    protected TextField barcode;
    protected TextField cost;
    protected TextField notes;
    protected TextField firstName;
    protected TextField middleName;
    protected TextField lastName;
    protected String fullCustomerName;
    protected TextField areaCode;
    protected TextField threeDigits;
    protected TextField fourDigits;
    protected String phoneNumber;
    protected TextField email;
    protected ComboBox paymentMethodComboBox;
    protected ComboBox transactionTypeComboBox;
    protected LocalDateTime ldt = LocalDateTime.now();

    protected String now = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    protected Button cancelButton;
    protected Button submitButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public SellTreeView(IModel sellTree)
    {
        super(sellTree, "SellTreeView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // create our GUI components, add them to this panel
        container.getChildren().add(createTitle());
        container.getChildren().add(createFormContent());

        // Error message area
        container.getChildren().add(createStatusLog("                                            "));
        container.getChildren().add(new Text("                          "));
        container.getChildren().add(new Text("                          "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("TransactionReceiptStatusMessage", this);
    }

    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Boy Scout Troop 209 Tree Sales: \nSell A Tree ");
        titleText.setFont(Font.font("Serif", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.BURLYWOOD);
        container.getChildren().add(titleText);

        return container;
    }

    // ----------------------------------------------------------------------
    /** Creates the GUI components for view
     *
     * @return vbox     container for the components
     */
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Set the font
        Font font = Font.font("Arial", FontWeight.BOLD, 12);

        // Add Text fields and prompts
        Text transactionTypeLabel = new Text("Transaction Type: ");
        transactionTypeLabel.setFont(font);
        transactionTypeLabel.setWrappingWidth(150);
        transactionTypeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(transactionTypeLabel, 0, 1);

        // Transaction type combo box
        transactionTypeComboBox = new ComboBox();
        transactionTypeComboBox.getItems().addAll("Tree Sale", "Wreath Sale", "Item Sale");
        // Add transaction type combo box to grid
        grid.add(transactionTypeComboBox, 1, 1);


        Text barcodeLabel = new Text("Tree Barcode: ");
        barcodeLabel.setFont(font);
        barcodeLabel.setWrappingWidth(150);
        barcodeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(barcodeLabel, 0, 2);

        barcode = new TextField();
        barcode.setEditable(false);
        grid.add(barcode, 1, 2);

        Text costLabel = new Text("Cost: ");
        costLabel.setFont(font);
        costLabel.setWrappingWidth(150);
        costLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(costLabel, 0, 3);

        cost = new TextField();
        cost.setEditable(true);
        grid.add(cost, 1, 3);


        Text notesLabel = new Text("Notes: ");
        notesLabel.setFont(font);
        notesLabel.setWrappingWidth(150);
        notesLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(notesLabel, 0, 4);

        notes = new TextField();
        notes.setEditable(true);
        notes.setPrefHeight(50);
        notes.setPrefWidth(25);
        grid.add(notes, 1, 4);

        Text firstNameLabel = new Text("First Name: ");
        firstNameLabel.setFont(font);
        firstNameLabel.setWrappingWidth(150);
        firstNameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(firstNameLabel, 0, 5);

        firstName = new TextField();
        firstName.setEditable(true);
        grid.add(firstName, 1, 5);

        Text middleNameLabel = new Text("Middle Name: ");
        middleNameLabel.setFont(font);
        middleNameLabel.setWrappingWidth(150);
        middleNameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(middleNameLabel, 0, 6);

        middleName = new TextField();
        middleName.setEditable(true);
        grid.add(middleName, 1, 6);

        Text lastNameLabel = new Text("Last Name: ");
        lastNameLabel.setFont(font);
        lastNameLabel.setWrappingWidth(150);
        lastNameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(lastNameLabel, 0, 7);

        lastName = new TextField();
        lastName.setEditable(true);
        grid.add(lastName, 1, 7);

        Text phoneNumberLabel = new Text("Phone Number: ");
        phoneNumberLabel.setFont(font);
        phoneNumberLabel.setWrappingWidth(150);
        phoneNumberLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(phoneNumberLabel, 0, 8);


        // Phone Number HBox for formatting
        HBox phoneNum = new HBox(5);
        phoneNum.setAlignment(Pos.CENTER_LEFT);


        Text areaCodeStart = new Text("(");
        areaCodeStart.setFont(font);
        areaCodeStart.setTextAlignment(TextAlignment.LEFT);
        phoneNum.getChildren().add(areaCodeStart);

        areaCode = new TextField();
        areaCode.setEditable(true);
        areaCode.setMaxWidth(35);
        phoneNum.getChildren().add(areaCode);

        Text areaCodeEnd = new Text(")");
        areaCodeEnd.setFont(font);
        areaCodeEnd.setTextAlignment(TextAlignment.LEFT);
        phoneNum.getChildren().add(areaCodeEnd);

        threeDigits = new TextField();
        threeDigits.setEditable(true);
        threeDigits.setMaxWidth(35);
        phoneNum.getChildren().add(threeDigits);

        Text pNumberDash = new Text("-");
        areaCodeEnd.setFont(font);
        areaCodeEnd.setTextAlignment(TextAlignment.LEFT);
        phoneNum.getChildren().add(pNumberDash);

        fourDigits = new TextField();
        fourDigits.setEditable(true);
        fourDigits.setMaxWidth(42);
        phoneNum.getChildren().add(fourDigits);


        grid.add(phoneNum, 1, 8);

        // End of HBox for phone number

        Text emailLabel = new Text("Email: ");
        emailLabel.setFont(font);
        emailLabel.setWrappingWidth(150);
        emailLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(emailLabel, 0, 9);

        email = new TextField();
        email.setEditable(true);
        grid.add(email, 1, 9);

        Text paymentMethodLabel = new Text("Payment Method: ");
        paymentMethodLabel.setFont(font);
        paymentMethodLabel.setWrappingWidth(150);
        paymentMethodLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(paymentMethodLabel, 0, 10);



        paymentMethodComboBox = new ComboBox();
        paymentMethodComboBox.getItems().addAll("Cash", "Check");
        grid.add(paymentMethodComboBox, 1, 10);

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
    /** Populates the fields of the view
     *
     */
    public void populateFields()
    {
        // Do things
        Tree treeToSell = (Tree)myModel.getState("GetTreeToSell");

        String b = (String)treeToSell.getState("Barcode");
        barcode.setText(b);

        String barPrefix = extract(barcode.getText());
        try
        {
            TreeType treeType = new TreeType(barPrefix);
            String c = (String)treeType.getState("Cost");
            cost.setText(c);
        }
        catch(InvalidPrimaryKeyException ex)
        {
            displayErrorMessage("ERROR: No tree type found with barcode prefix " + barPrefix);
        }


        String n = (String)treeToSell.getState("Notes");
        notes.setText(n);

        paymentMethodComboBox.getSelectionModel().selectFirst();
        transactionTypeComboBox.getSelectionModel().selectFirst();
    }


    //-------------------------------------------------------------------------------------------------
    public void processAction(Event evt)
    {
        // Call to change the current state
        validateSellTree();
    }

    /**
     * Update method
     */
    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        if (key.equals("TransactionReceiptStatusMessage") == true)
        {
            String msg = (String)value;
            if (msg.startsWith("ERR") == true)
                displayErrorMessage(msg);
            else
                displayMessage(msg);
        }
    }

    /**
     * Handling cost validation
     */
    //------------------------------------------------------------------------------------------------
    public boolean validateCostValue(String costVal)
    {
        if (costVal.length() > GlobalData.MAX_TREE_TYPE_COST_LENGTH)
            return false;
        else {
            try {
                double costValue = Double.parseDouble(costVal);
                return true;
            }
            catch (NumberFormatException excep)
            {
                return false;
            }
        }
    }

    /**
     * Handling Name validation
     */
    //------------------------------------------------------------------------------------------------
    public boolean validateName(String first, String middle, String last)
    {
        if(first != null && first.length() > 25)
        {
            return false;
        }
        else if(middle != null && middle.length() > 25)
        {
            return false;
        }
        else if(last != null && last.length() > 25)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     *  Validation method to check if Phone Number is ony integers
     */
    //----------------------------------------------------------
    private boolean checkPhoneNumber(String pN)
    {
        try
        {
            Long.parseLong(pN);
            return true;
        }
        catch (NumberFormatException e)
        {
            // DEBUG System.out.print(e.getMessage());

            return false;
        }
    }

    /**
     * Handling Phone number validation
     */
    //------------------------------------------------------------------------------------------------
    public boolean validatePhoneNumber(String areaCode, String threeDigits, String fourDigits)
    {
        String fullPhoneNumber = areaCode + threeDigits + fourDigits;
//        if(fullPhoneNumber.equals(""))
//        {
//            return true;
//        }
        if ((fullPhoneNumber.equals("") == false) && (areaCode.length() != 3 ||
                threeDigits.length() != 3 ||
                fourDigits.length() != 4)) {
            return false;
        }
        else if((fullPhoneNumber.equals("") == false) && (checkPhoneNumber(fullPhoneNumber) == false))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Handling Email validation
     */
    //------------------------------------------------------------------------------------------------
    public boolean validateEmail(String email)
    {
        if((email.equals("") == false) && (email.contains("@") == false))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     *  Handling validation for all fields
     */
    //----------------------------------------------------------
    public void validateSellTree()
    {
        String costVal = cost.getText();
        String fName = firstName.getText();
        String mName = middleName.getText();
        String lName = lastName.getText();
        String aCode = areaCode.getText();
        String tDigits = threeDigits.getText();
        String fDigits = fourDigits.getText();
        String mail = email.getText();

        if (validateCostValue(costVal) == false)
        {
            displayErrorMessage("ERROR: Cost value too long, or not numeric!");
        }
        else if(validateName(fName, mName, lName) == false)
        {
            displayErrorMessage("ERROR: Please enter a name under 25 characters.");
        }
        else if(validatePhoneNumber(aCode, tDigits, fDigits) == false)
        {
            displayErrorMessage("ERROR: Please enter a valid, 10-digit phone number");
        }
        else if(validateEmail(mail) == false)
        {
            displayErrorMessage("ERROR: Please enter a valid email address.");
        }
        else
        {
            getOperation();
        }
    }

    /** Obtains the data from the text fields
     *
     * @return props    Properties object that has the user data
     */
    //---------------------------------------------------------
    protected Properties gatherUserEnteredData()
    {
        fullCustomerName = firstName.getText() + " " + middleName.getText() + " " + lastName.getText();
        phoneNumber = areaCode.getText() + threeDigits.getText() + fourDigits.getText();

        LocalDateTime ldt = LocalDateTime.now();
        String nowTime = ldt.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        Properties props = new Properties();
        props.setProperty("SessionID", "1");
        props.setProperty("TransactionType", (String) transactionTypeComboBox.getValue());
        props.setProperty("Barcode", barcode.getText());
        props.setProperty("TransactionAmount", cost.getText());
        props.setProperty("CustomerName", fullCustomerName);
        props.setProperty("CustomerPhone", phoneNumber);
        props.setProperty("CustomerEmail", email.getText());
        props.setProperty("PaymentMethod", (String)paymentMethodComboBox.getValue());
        props.setProperty("TransactionDate", now);
        props.setProperty("TransactionTime", nowTime);
        props.setProperty("DateStatusUpdated", now);
        return props;

        // The props object returned above will probably be used as an argument in Transaction(Properties proprs) constructor
    }

    // -----------------------------------------------------------------------
    protected void getOperation()
    {
        // Stores the info from the fields
        Properties p = gatherUserEnteredData();

        // DEBUG: Try to change the state
        try
        {
            myModel.stateChangeRequest("InsertTransactionData", p);
        }
        catch (Exception ex)
        {
            System.out.println("SellTreeView : getOperation() - Could not Insert Transaction Receipt Data!");
        }
    }

    //-----------------------------------------------------------
    // This method
    // @
    private String extract(String bar){

        if ((bar != null) && (bar.length() > 0))
        {
            if (bar.length() > 2) return bar.substring(0,2);
            else return "";
        }
        else
            return "";
    }

    // -----------------------------------------------------------------------

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
