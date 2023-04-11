// specify package
package userinterface;

// system imports
import java.time.LocalDate;
import java.util.Properties;

// project imports
import impresario.IModel;
import model.Scout;

public class UpdateScoutView extends ScoutView {

    // ------------------------------------------------------------
    public UpdateScoutView(IModel scout)
    {
        super(scout, "Please edit the following information to update the selected Scout.\nSelect \"Submit\" when " +
                "all desired changes have been made.");
    }

    // ----------------------------------------------------------------------
    @Override
    protected String getTitleText()
    {
        return " Boy Scout Troop 209 Tree Sales:\nUpdate Scout ";
    }

    // ----------------------------------------------------------------------
    @Override
    protected boolean getTroopIdEditableFlag()
    {
        return false;
    }

    // ----------------------------------------------------------------------
    @Override
    protected boolean getStatusComboBoxFlag()
    {
        return true;
    }

    // --------------------------------------------------------------------------
    @Override
    protected void getOperation()
    {
        Properties p = gatherUserEnteredData();
        myModel.stateChangeRequest("UpdateScoutData", p);
    }

    public void populateFields(){

        Scout scoutToUpdate = (Scout)myModel.getState("GetScoutToUpdate");

        String fn = (String)scoutToUpdate.getState("FirstName");
        firstName.setText(fn);

        String mn = (String)scoutToUpdate.getState("MiddleName");
        middleName.setText(mn);

        String ln = (String)scoutToUpdate.getState("LastName");
        lastName.setText(ln);


        datePicker.setValue(LocalDate.parse((CharSequence) scoutToUpdate.getState("DateOfBirth")));

        String pn = (String)scoutToUpdate.getState("PhoneNumber");
        String ac = pn.substring(0,3);
        String td = pn.substring(3,6);
        String fd = pn.substring(6,10);
        areaCode.setText(ac);
        threeDigits.setText(td);
        fourDigits.setText(fd);

        String e = (String)scoutToUpdate.getState("Email");
        email.setText(e);

        String ti = (String)scoutToUpdate.getState("TroopID");
        troopId.setText(ti);

        String cb = (String)scoutToUpdate.getState("Status");
        statusComboBox.setValue(cb);
    }
}
