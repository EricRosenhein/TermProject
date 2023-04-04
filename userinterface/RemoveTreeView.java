package userinterface;

import impresario.IModel;
import javafx.event.Event;

public class RemoveTreeView extends TreeView
{
    public RemoveTreeView(IModel removeTree)
    {
        super(removeTree, "Please select the tree you'd like to remove.");
    }

    // ----------------------------------------------------------------------
    @Override
    public void processAction(Event event)
    {

    }

    // ----------------------------------------------------------------------
    @Override
    public void populateFields()
    {

    }
}

/*******************************************************************************************
 * Revision History:
 *
 *
 *
 * 04/03/2023 Sebastian Whyte
 * Initial check in. In progress
 ******************************************************************************************/
