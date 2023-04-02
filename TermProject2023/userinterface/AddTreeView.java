package userinterface;

import impresario.IModel;
import javafx.event.Event;

public class AddTreeView extends TreeView
{
    public AddTreeView(IModel addTree)
    {
        super(addTree, "Please enter the following information to add a Tree.");

        // DEBUG
        System.out.println("In AddTreeView constructor");
    }

    // --------------------------------------------------------------
    /* Processes the given event. Used for validation
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

    // --------------------------------------------------------------
}

/****************************************************************************************
 *
 *
 *
 * 04/01/2023 11:35 PM Sebastian Whyte
 * Initial check in.
 ****************************************************************************************/
