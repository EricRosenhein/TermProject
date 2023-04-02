package userinterface;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

 /** This is the Register Scout View class for the Application */
 //=============================================================
public class RegisterScoutView extends ScoutView
{
    //------------------------------------------------------------  
    public RegisterScoutView(IModel registerScout)
    {
        super(registerScout, "Please enter the following information to register a Scout.");
    }

    //------------------------------------------------------------
    /** Validation for registering a scout
     * @param event
     */
    @Override
    public void processAction(Event event)
    {
       // Checks if the text in phone number matches the regex pattern consisting of digits (d) in format XXX-XXX-XXXX
        if (phoneNumber.getText().matches("\\d{3}-\\d{3}-\\d{4}"))
        {
            displayMessage("Valid phone number");
        }
        else
        {
            displayErrorMessage("Invalid phone number");
        }
    }

    //------------------------------------------------------------
    /* Checks whether the given text is null or if its empty (after trimming white spaces)
     *
     * @param text      string to check
     * @return boolean  indicates whether the text is null or empty
     */
    public boolean empty(String text)
    {
        return text == null || text.trim().isEmpty();
    }

    //------------------------------------------------------------
    /* Populates text fields
     *
     */
    @Override
    public void populateFields() {

    }

    // ----------------------------------------------------------------------
    /** Updates the state of the view
     *
     * @param key       key we use to look up values in the map
     * @param value     value we want mapped to the key
     */
    @Override
    public void updateState(String key, Object value) 
    {
       if (key.equals("ScoutUpdateStatusMessage"))
       {
           String msg = (String)value;

           if (msg.startsWith("ERR"))
           {
               displayErrorMessage(msg);
           }
           else {
               displayMessage(msg);
           }
       }
    }
}


/*******************************************************************************************
 * Revision History:
 * 
 *
 *
 * 04/02/2023 01:12 AM Sebastian Whyte
 * Created a method to check if a string is either null or blank, and added a regex pattern for the phone number field
 * 
 * 04/01/2023 Dominic Laure
 * Dominic made this a subclass of ScoutView.
 * 
 * 03/23/2023 Sebastian Whyte
 * Initial check in. Thinking about making common View methods (createTitle() and createFormContents())
 * abstract methods in the parent class. Not sure yet
*******************************************************************************************/
