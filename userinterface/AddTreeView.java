package userinterface;

import impresario.IModel;
import javafx.event.Event;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

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
        String bcode = barcode.getText();
        String barcodePrefix = bcode.substring(0,2);    // Gets the first two characters of the barcode which will be the barcode prefix
        String notesText = notes.getText();

        // Get current date and convert it into a string
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateScoutStatusUpdated = today.format(formatter);


        // Check if the barcode meets the conditions
        if (bcode.length() != 5)
        {
            displayErrorMessage("ERROR: Invalid barcode");
        }
        else if (!barcodePrefix.equals("20") && !barcodePrefix.equals("21") && !barcodePrefix.equals("30") &&
                !barcodePrefix.equals("31") && !barcodePrefix.equals("40") && !barcodePrefix.equals("41") &&
                !barcodePrefix.equals("50") && !barcodePrefix.equals("51") && !barcodePrefix.equals("60") &&
                !barcodePrefix.equals("61"))
        {
            displayErrorMessage("ERROR: Invalid barcode");
        }
        // Check if the notes field meets the conditions
        else if (notesText == null || notesText.trim().length() == 0)
        {
            displayErrorMessage("Please enter notes");
        }
        else
        {
            clearErrorMessage();
            Properties props = new Properties();
            props.setProperty("Barcode", bcode);
            props.setProperty("Notes", notesText);
            props.setProperty("DateStatusUpdated", dateScoutStatusUpdated);

            // DEBUG
            //System.out.println("Notes: " + notesText);
        }
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
 * 04/03/2023 Dominic Laure & Sebastian Whyte
 * Dominic wrote the initial code, I made some changes and did debugging -SW
 *
 * 04/01/2023 11:35 PM Sebastian Whyte
 * Initial check in.
 ****************************************************************************************/