package userinterface;

import impresario.IModel;
import javafx.event.Event;

public class RemoveScoutView extends ScoutView
{
    public RemoveScoutView(IModel removeScout)
    {
        super(removeScout, "Confirm Scout Removal?");
    }

    //--------------------------------------------------------------
    /**
     * @param event
     */
    @Override
    public void processAction(Event event)
    {

    }

    //--------------------------------------------------------------
    /**
     *
     */
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
 * 04/02/2023 02:10 AM Sebastian Whyte
 * Initial check in.
 ******************************************************************************************/
