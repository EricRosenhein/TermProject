package userinterface;

// system imports
import javafx.event.Event;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Tree;

import java.util.Properties;

// project imports
import impresario.IModel;

public class UpdateTreeView extends TreeView {

    public UpdateTreeView(IModel updateTree)
    {
        super(updateTree, "Please enter the following information to add a Tree.");

        // DEBUG System.out.println("In AddTreeView constructor");
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
        barcode.setEditable(false);
        grid.add(barcode, 1, 1);

        Text statusLabel = new Text("Status: ");
        statusLabel.setFont(font);
        statusLabel.setWrappingWidth(150);
        statusLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(statusLabel, 0, 2);

        status = new ComboBox<>();
        status.getItems().add(0, "Active");
        //status.getItems().add(1, "Sold");
        status.getItems().add(2, "Damaged");
        grid.add(status, 1, 2);

        Text notesLabel = new Text("Notes: ");
        notesLabel.setFont(font);
        notesLabel.setWrappingWidth(150);
        notesLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(notesLabel, 0, 3);

        notes = new TextField();
        notes.setEditable(true);
        notes.setPrefHeight(50);
        notes.setPrefWidth(25);
        grid.add(notes, 1, 3);

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
        grid.add(buttonContainer, 0, 4);

        return grid;
    }

    // --------------------------------------------------------------
    @Override
    public void processAction(Event event) {
        clearErrorMessage();

        String no = notes.getText();
        String selectedStatus = status.getValue();

        if(no.length() > 200) {
            displayErrorMessage("ERROR: Notes exceeds 200 characters");
        }
        else {
            Properties props = new Properties();
            props.setProperty("Notes", no);
            props.setProperty("Status", selectedStatus);

            myModel.stateChangeRequest("SubmitTreeData", props);
            displayMessage("Tree Updated!");
        }
    }

    // --------------------------------------------------------------
    @Override
    public void populateFields() {
        Tree treeToUpdate = (Tree) myModel.getState("GetTreeToUpdate");
        barcode.setText(treeToUpdate.getBarcode());
        notes.setText(treeToUpdate.getNotes());
    }



}
