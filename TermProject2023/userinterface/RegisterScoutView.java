//specify package
package userinterface;

// system imports
import java.util.Properties;

// project imports
import impresario.IModel;



/** This is the Register Scout View class for the Application */
//=============================================================
public class RegisterScoutView extends ScoutView
{
    //------------------------------------------------------------
    public RegisterScoutView(IModel scout)
    {
        super(scout, "Please enter the following information to add a Scout.");
    }

    // ----------------------------------------------------------------------
    public String getTitleText()
    {
        return " Boy Scout Troop 209 Tree Sales: \nRegister Scout ";
    }

    // -------------------------------------------------------------------------
    protected boolean getTroopIdEditableFlag()
    {
        return true;
    }

    // ----------------------------------------------------------------------
    protected boolean getStatusComboBoxFlag()
    {
        return false;
    }

    // -----------------------------------------------------------------------
    protected void getOperation()
    {
        Properties p = gatherUserEnteredData();
        myModel.stateChangeRequest("InsertScoutData", p);
    }

    // ---------------------------------------------------------------------------------------
    public void populateFields() {}
}
