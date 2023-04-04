package userinterface;

import impresario.IModel;
import javafx.event.Event;

public class UpdateScoutView extends ScoutView
{
    public UpdateScoutView(IModel updateScout)
    {
        super(updateScout, "Please choose the fields you'd like to update.");
    }

    //------------------------------------------------------------
    /**
     * @param event
     */
    @Override
    public void processAction(Event event)
    {

    }

    //------------------------------------------------------------
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
 * 04/01/2023 Dominic Laure
 * Dominic made this a subclass of ScoutView.
 *******************************************************************************************/