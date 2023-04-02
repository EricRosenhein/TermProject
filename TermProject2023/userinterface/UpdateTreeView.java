package userinterface;

import impresario.IModel;
import javafx.event.Event;

public class UpdateTreeView extends TreeView
{
    public UpdateTreeView(IModel updateTree)
    {
        super(updateTree, "Update Tree Record?");
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
