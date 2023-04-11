package userinterface;

// system imports
import Utilities.GlobalData;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.util.Properties;

// project imports
import impresario.IModel;
import model.TreeType;

public class UpdateTreeTypeView extends View
{
    // GUI components
    protected TextField typeDescriptionField;
    protected TextField costField;
    protected TextField barcodePrefixField;

    protected Button cancelButton;
    protected Button submitButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public UpdateTreeTypeView(IModel updateTreeType)
    {
        super(updateTreeType, "UpdateTreeTypeView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // create our GUI components, add them to this panel
        container.getChildren().add(createTitle());
        container.getChildren().add(createFormContent());

        // Error message area
        container.getChildren().add(createStatusLog("                                            "));
        container.getChildren().add(new Text("                                  "));
        container.getChildren().add(new Text("                                  "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("TreeTypeUpdateStatusMessage", this);
    }

    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Boy Scout Troop 209 Tree Sales: \nUpdate TreeType ");
        titleText.setFont(Font.font("Serif", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.BURLYWOOD);
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

        Text typeDescriptionLabel = new Text(" Type Description: ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        typeDescriptionLabel.setFont(myFont);
        typeDescriptionLabel.setWrappingWidth(150);
        typeDescriptionLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(typeDescriptionLabel, 0, 1);

        typeDescriptionField = new TextField();
        typeDescriptionField.setEditable(true);
        grid.add(typeDescriptionField, 1, 1);

        Text costLabel = new Text(" Cost: ");
        costLabel.setFont(myFont);
        costLabel.setWrappingWidth(150);
        costLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(costLabel, 0, 2);

        costField = new TextField();
        costField.setEditable(true);
        grid.add(costField, 1, 2);

        Text barcodePrefixLabel = new Text(" Barcode Prefix: ");
        barcodePrefixLabel.setFont(myFont);
        barcodePrefixLabel.setWrappingWidth(150);
        barcodePrefixLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(barcodePrefixLabel, 0, 3);

        barcodePrefixField = new TextField();
        barcodePrefixField.setEditable(false);
        grid.add(barcodePrefixField, 1, 3);

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
        TreeType treeTypeToUpdate = (TreeType) myModel.getState("GetTreeTypeToUpdate");

        String tD = (String)treeTypeToUpdate.getState("TypeDescription");
        typeDescriptionField.setText(tD);

        String c = (String)treeTypeToUpdate.getState("Cost");
        costField.setText(c);

        String bp = (String)treeTypeToUpdate.getState("BarcodePrefix");
        barcodePrefixField.setText(bp);
    }

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
    //--------------------------------------------------------------------------
    public void processAction(Event evt)
    {
        String typeDescriptionVal = typeDescriptionField.getText();
        String costVal = costField.getText();
        String bpfixVal = barcodePrefixField.getText();

        if( ((typeDescriptionVal == null) || (typeDescriptionVal.isEmpty())) || costField.getText().isEmpty() || barcodePrefixField.getText().isEmpty())
        {
            displayErrorMessage("ERROR: Please complete the entire form.");
        }
        else if(typeDescriptionVal.length() > GlobalData.MAX_TREE_TYPE_DESCRIPTION_LENGTH)
        {
            displayErrorMessage("ERROR: Type Description has a limit of 25 characters!");
        }
        else if (validateCostValue(costVal) == false)
        {
            displayErrorMessage("ERROR: Cost value too long, or not numeric!");
        }
        else
        {
            updateTreeType();
        }
    }

    private void updateTreeType()
    {
        Properties props = new Properties();
        props.setProperty("TypeDescription", typeDescriptionField.getText());
        props.setProperty("Cost", costField.getText());

        myModel.stateChangeRequest("UpdateTreeTypeData", props);

    }

    /**
     * Update method
     */
    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        if (key.equals("TreeTypeUpdateStatusMessage") == true)
        {
            String msg = (String)value;
            if (msg.startsWith("ERR") == true)
                displayErrorMessage(msg);
            else
                displayMessage(msg);
        }
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
