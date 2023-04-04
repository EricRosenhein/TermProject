package userinterface;

import impresario.IModel;
import javafx.event.Event;

// Update Tree test case #1: "1.	The GUI screen for this update should NOT allow the barcode of the tree to be changed."
// So, maybe override createFormContents()

public class UpdateTreeView extends TreeView
{
    public UpdateTreeView(IModel updateTree)
    {
        super(updateTree, "Please choose the fields you'd like to update.");
    }

    // --------------------------------------------------------------
    /** Processes the given event. Used for validation
     *
     * @param event     event to process
     */
    @Override
    public void processAction(Event event)
    {

    }

    // --------------------------------------------------------------
    /* Populates the text fields
     *
     */
    @Override
    public void populateFields()
    {

    }
}

/****************************************************************************************
 *
 *
 *
 * 04/01/2023 11:35 PM Sebastian Whyte
 * Initial check in.
 ****************************************************************************************/