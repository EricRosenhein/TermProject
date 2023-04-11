// specify package
package userinterface;

// system imports
import model.Tree;
import java.util.Properties;

// project imports
import impresario.IModel;

public class UpdateTreeView extends TreeView {

    public UpdateTreeView(IModel updateTree) {
        super(updateTree, "Please edit the following information to update the selected Tree.\nSelect \"Submit\" when " +
                                "all desired changes have been made.");

        // DEBUG System.out.println("In AddTreeView constructor");
    }

    // --------------------------------------------------------------
    @Override
    protected String getTitleText()
    {
        return " Boy Scout Troop 209 Tree Sales: \nUpdate a Tree ";
    }

    // --------------------------------------------------------------
    @Override
    protected void populateFields() {
        Tree treeToUpdate = (Tree) myModel.getState("GetTreeToUpdate");

        String bar = (String)treeToUpdate.getState("Barcode");
        barcode.setText(bar);

        String n = (String)treeToUpdate.getState("Notes");
        notes.setText(n);

        String cb = (String)treeToUpdate.getState("Status");
        statusComboBox.setValue(cb);
    }

    //---------------------------------------------------------------------
    /** Makes sure that user can't edit barcode
     *
     * @return flag
     */
    @Override
    protected boolean getBarcodeEditableFlag()
    {
        return false;
    }

    // ---------------------------------------------------------------------
    @Override
    protected boolean getStatusComboBoxFlag()
    {
        return true;
    }

    // -----------------------------------------------------------------------------
    @Override
    protected void getOperation()
    {
        String note = notes.getText();

        // Check if the length of the notes exceed 200 characters
        if(note.length() > 200)
        {
            displayErrorMessage("ERROR: Notes exceeds 200 characters");
        }
        else
        {
            Properties p = gatherUserEnteredData();
            myModel.stateChangeRequest("UpdateTreeData", p);
        }
    }
}
