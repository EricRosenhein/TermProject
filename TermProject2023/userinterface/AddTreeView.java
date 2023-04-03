package userinterface;

import java.time.LocalDate;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import impresario.IModel;
import javafx.event.Event;
import javafx.scene.Node;

public class AddTreeView extends TreeView
{
    public AddTreeView(IModel addTree)
    {
        super(addTree, "Please enter the following information to add a Tree.");

        // DEBUG
        System.out.println("In AddTreeView constructor");
    }

    // --------------------------------------------------------------
    @Override
    protected Node createFormContents(String instructions)
    {
        // Grid to hold the components
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Prompt for the user
        Text prompt = new Text(instructions);
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        // Set the font
        Font font = Font.font("Arial", FontWeight.BOLD, 12);

        // GUI components
        Text barcodeLabel = new Text("Tree Barcode: ");
        barcodeLabel.setFont(font);
        barcodeLabel.setWrappingWidth(150);
        barcodeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(barcodeLabel, 0, 1);

        barcode = new TextField();
        barcode.setEditable(true);
        grid.add(barcode, 1, 1);

        Text notesLabel = new Text("Notes: ");
        notesLabel.setFont(font);
        notesLabel.setWrappingWidth(150);
        notesLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(notesLabel, 0, 2);

        notes = new TextField();
        notes.setEditable(true);
        notes.setPrefHeight(50);
        notes.setPrefWidth(25);
        grid.add(notes, 1, 2);

        Button cancelButton = new Button("Cancel");
        Button submitButton = new Button("Submit");

        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                // DEBUG - This should take us back to the TransactionMenu
                myModel.stateChangeRequest("CancelTransaction", null);

            }
        });

        //Submit button
		submitButton = new Button("Submit");
		submitButton.setOnAction(new EventHandler<ActionEvent>() {

				   @Override
				   public void handle(ActionEvent e) {
					  clearErrorMessage();
					  processAction(e);
					}
		   });

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.BOTTOM_RIGHT);
        buttonContainer.getChildren().add(cancelButton);
        buttonContainer.getChildren().add(submitButton);
        grid.add(buttonContainer, 0, 3);

        return grid;
    }

    // --------------------------------------------------------------
    /* Processes the given event. Used for validation
     *
     * @param event     event to process
     */
    @Override
    public void processAction(Event event)
    {
        String bcode = barcode.getText();
        String treeType = bcode.substring(0,2);
        String no = notes.getText();

        LocalDate today = LocalDate.now();
        String now = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String dateScoutStatusUpdated = now;

        if(treeType.equals("20") == false //|| bcodeSub.equals( "21") == false || (bcodeSub.equals( "30") == false) || (bcodeSub.equals("31") == false) || (bcodeSub.equals("40") == false) ||
        //(bcodeSub.equals( "41") == false) || (bcodeSub.equals( "50") == false) || (bcodeSub.equals( "51") == false) || (bcodeSub.equals( "60") == false) || (bcodeSub.equals( "61") == false) ||
        || (bcode.length() != 5)) 
        {
            displayErrorMessage("ERROR: Invalid barcode");
        }
        else if((no == null) || (no.trim().length() == 0)) {
            displayErrorMessage("Please enter notes");
        }
        else {
            Properties props = new Properties();
            props.setProperty("Barcode", bcode);
            props.setProperty("TreeType", treeType);
            props.setProperty("Notes", no);
            props.setProperty("DateStatusUpdated", dateScoutStatusUpdated);

            myModel.stateChangeRequest("InsertTreeData", props);
		    displayMessage("Tree inserted!");
        }

    }

    // --------------------------------------------------------------
    /* Populates the text fields
     *
     */
    @Override
    public void populateFields()
    {

    }

    // --------------------------------------------------------------
}
