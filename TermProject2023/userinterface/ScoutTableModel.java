package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================

public class ScoutTableModel
{
    private final SimpleStringProperty id;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty middleName;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty phoneNumber;
    private final SimpleStringProperty email;
    private final SimpleStringProperty troopID;

    private final SimpleStringProperty status;
    private final SimpleStringProperty dateStatusUpdated;

    //----------------------------------------------------------------------------
	public ScoutTableModel(Vector<String> scoutData)
	{
	    id =  new SimpleStringProperty(scoutData.elementAt(0));
        lastName =  new SimpleStringProperty(scoutData.elementAt(1));
	    firstName =  new SimpleStringProperty(scoutData.elementAt(2));
        middleName =  new SimpleStringProperty(scoutData.elementAt(3));
        dateOfBirth =  new SimpleStringProperty(scoutData.elementAt(4));
        phoneNumber =  new SimpleStringProperty(scoutData.elementAt(5));
        email =  new SimpleStringProperty(scoutData.elementAt(6));
        troopID =  new SimpleStringProperty(scoutData.elementAt(7));
	    status =  new SimpleStringProperty(scoutData.elementAt(8));
	    dateStatusUpdated =  new SimpleStringProperty(scoutData.elementAt(9));
	}

    //----------------------------------------------------------------------------

    public String getId() {
        return id.get();
    }

    //----------------------------------------------------------------------------
    public void setId(String i) {
        id.set(i);
    }
    //----------------------------------------------------------------------------
    public String getFirstName() {
        return firstName.get();
    }

    //----------------------------------------------------------------------------
    public void setFirstName(String fn) {
        firstName.set(fn);
    }

    //----------------------------------------------------------------------------
    public String getLastName() {
        return lastName.get();
    }
    
    //----------------------------------------------------------------------------
    public void setLastName(String ln) {
        lastName.set(ln);
    }

    //----------------------------------------------------------------------------
    public String getMiddleName() {
        return middleName.get();
    }
        
    //----------------------------------------------------------------------------
    public void setMiddleName(String mn) {
        middleName.set(mn);
    }

    //----------------------------------------------------------------------------
    public String getDateOfBirth() {
        return dateOfBirth.get();
    }
            
    //----------------------------------------------------------------------------
    public void setDateOfBirth(String d) {
        dateOfBirth.set(d);
    }

    //----------------------------------------------------------------------------
    public String getPhoneNumber() {
        return phoneNumber.get();
    }
        
    //----------------------------------------------------------------------------
    public void setPhoneNumber(String p) {
        phoneNumber.set(p);
    }

    //----------------------------------------------------------------------------
    public String getEmail() {
        return email.get();
    }
            
    //----------------------------------------------------------------------------
    public void setEmail (String e) {
        email.set(e);
    }

    //----------------------------------------------------------------------------
    public String getTroopID() {
        return troopID.get();
    }
        
    //----------------------------------------------------------------------------
    public void setTroopID(String t) {
        troopID.set(t);
    }
    //----------------------------------------------------------------------------
    public String getStatus() {
        return status.get();
    }

    //----------------------------------------------------------------------------
    public void setStatus(String s) {
        status.set(s);
    }
    //----------------------------------------------------------------------------
    public String getDateStatusUpdated() {
        return dateStatusUpdated.get();
    }

    //----------------------------------------------------------------------------
    public void setDateStatusUpdated(String u) {
        dateStatusUpdated.set(u);
    }
}
