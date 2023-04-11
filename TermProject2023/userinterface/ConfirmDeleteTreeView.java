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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

// project imports
import model.Tree;
import impresario.IModel;

public class ConfirmDeleteTreeView extends TreeView
{
    public ConfirmDeleteTreeView(IModel removeTree)
    {
        super(removeTree, "Confirm Tree Deletion?");

    }

    // ------------------------------------------------------------------
    protected String getTitleText()
    {
        return " Boy Scout Troop 209 Tree Sales: \nRemove Tree";
    }

    // --------------------------------------------------------------
    /** Creates the GUI components. This class overrides the superclass due to the barcode not being editable
     *
     * @param instructions  instructions to display to the user
     * @return grid grid that holds the components
     */
    @Override
    public Node createFormContents(String instructions)
    {
        VBox vbox = new VBox();

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


        Tree treeToDelete = (Tree)myModel.getState("GetTreeToRemove");

        Text treeInfo = new Text("Barcode: " + treeToDelete.getState("Barcode") +
                "\nNotes: " + treeToDelete.getState("Notes") +
                "\nStatus: " + treeToDelete.getState("Status"));
        treeInfo.setWrappingWidth(400);
        treeInfo.setTextAlignment(TextAlignment.CENTER);
        treeInfo.setFill(Color.BLACK);
        grid.add(treeInfo,0,1);

        Button cancelButton = new Button("Cancel");
        Button submitButton = new Button("Confirm");

        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                clearErrorMessage();
                // If user selects yes, we will use a stateChangeRequest -> Key: "CancelRemoveTree". The dependency is set in RemoveTreeTransaction.
                // This will take us back to the Scan Tree Barcode View
                myModel.stateChangeRequest("CancelRemoveTree", null);
            }
        });

        submitButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
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

    // ----------------------------------------------------------------------
    public void processAction(Event event)
    {
        // Use method from RemoveTreeTransaction to check if tree is Sold or Damaged. The status would be from the database
             myModel.stateChangeRequest("ConfirmTreeDeletion", null);
    }

    // ----------------------------------------------------------------------
    @Override
    public void populateFields()
    {
    }


}

