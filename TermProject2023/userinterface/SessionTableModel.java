package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================

public class SessionTableModel
{
    private final SimpleStringProperty id;
    private final SimpleStringProperty startDate;
    private final SimpleStringProperty startTime;
    private final SimpleStringProperty endTime;
    private final SimpleStringProperty startingCash;
    private final SimpleStringProperty endingCash;
    private final SimpleStringProperty totalCheckTransactions;
    private final SimpleStringProperty notes;


    //----------------------------------------------------------------------------
    public SessionTableModel(Vector<String> sessionData)
    {
        id =  new SimpleStringProperty(sessionData.elementAt(0));
        startDate =  new SimpleStringProperty(sessionData.elementAt(1));
        startTime =  new SimpleStringProperty(sessionData.elementAt(2));
        endTime =  new SimpleStringProperty(sessionData.elementAt(3));
        startingCash =  new SimpleStringProperty(sessionData.elementAt(4));
        endingCash =  new SimpleStringProperty(sessionData.elementAt(5));
        totalCheckTransactions =  new SimpleStringProperty(sessionData.elementAt(6));
        notes =  new SimpleStringProperty(sessionData.elementAt(7));

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
    public String getStartDate() {
        return startDate.get();
    }

    //----------------------------------------------------------------------------
    public void setStartDate(String sd) {
        startDate.set(sd);
    }

    //----------------------------------------------------------------------------
    public String getStartTime() {
        return startTime.get();
    }

    //----------------------------------------------------------------------------
    public void setStartTime(String st) {startTime.set(st);}

    //----------------------------------------------------------------------------
    public String getEndTime() {
        return endTime.get();
    }

    //----------------------------------------------------------------------------
    public void setEndTime(String et) {
        endTime.set(et);
    }

    //----------------------------------------------------------------------------
    public String getStartingCash() {
        return startingCash.get();
    }

    //----------------------------------------------------------------------------
    public void setStartingCash(String sc) {startingCash.set(sc);}

    //----------------------------------------------------------------------------
    public String getEndingCash() {return endingCash.get();}

    //----------------------------------------------------------------------------
    public void setEndingCash(String ec) {
        endingCash.set(ec);
    }

    //----------------------------------------------------------------------------
    public String getTotalCheckTransactions() {
        return totalCheckTransactions.get();
    }

    //----------------------------------------------------------------------------
    public void setTotalCheckTransactions (String ct) {
        totalCheckTransactions.set(ct);
    }

    //----------------------------------------------------------------------------
    public String getNotes() {
        return notes.get();
    }

    //----------------------------------------------------------------------------
    public void setNotes(String n) {
        notes.set(n);
    }

}
