// specify package
package userinterface;

// system imports
import java.util.Properties;
import impresario.IModel;


public class AddTreeView extends TreeView
{
    public AddTreeView(IModel addTree)
    {
        super(addTree, "Please enter the following information to add a Tree.");

        // DEBUG System.out.println("In AddTreeView constructor");
    }

    // --------------------------------------------------------------
    @Override
    protected String getTitleText()
    {
        return " Boy Scout Troop 209 Tree Sales: \nAdd a Tree ";
    }

    // --------------------------------------------------------------
    /* Populates the text fields
     *
     */
    public void populateFields()
    {
    }

    // ---------------------------------------------------------------------
    @Override
    protected boolean getBarcodeEditableFlag()
    {
        return true;
    }

    // ---------------------------------------------------------------------
    @Override
    protected boolean getStatusComboBoxFlag()
    {
        return false;
    }

    // -----------------------------------------------------------------------------
    @Override
    protected void getOperation()
    {
        Properties p = gatherUserEnteredData();
        myModel.stateChangeRequest("InsertTreeData", p);
    }
}