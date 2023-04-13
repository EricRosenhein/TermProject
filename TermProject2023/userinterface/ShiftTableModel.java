package userinterface;

import javafx.beans.property.SimpleStringProperty;

import java.util.Vector;

//==============================================================================

public class ShiftTableModel
{
    private final SimpleStringProperty id;
    private final SimpleStringProperty sessionId;
    private final SimpleStringProperty scoutId;
    private final SimpleStringProperty companionName;
    private final SimpleStringProperty startTime;
    private final SimpleStringProperty endTime;
    private final SimpleStringProperty companionHours;


    //----------------------------------------------------------------------------
    public ShiftTableModel(Vector<String> shiftData)
    {
        id =  new SimpleStringProperty(shiftData.elementAt(0));
        sessionId =  new SimpleStringProperty(shiftData.elementAt(1));
        scoutId =  new SimpleStringProperty(shiftData.elementAt(2));
        companionName =  new SimpleStringProperty(shiftData.elementAt(3));
        startTime =  new SimpleStringProperty(shiftData.elementAt(4));
        endTime =  new SimpleStringProperty(shiftData.elementAt(5));
        companionHours =  new SimpleStringProperty(shiftData.elementAt(6));

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
    public String getSessionId() {return sessionId.get();}

    //----------------------------------------------------------------------------
    public void setSessionId(String ss) {
        sessionId.set(ss);
    }

    //----------------------------------------------------------------------------
    public String getScoutId() {
        return scoutId.get();
    }

    //----------------------------------------------------------------------------
    public void setScoutId(String si) {
        scoutId.set(si);
    }

    //----------------------------------------------------------------------------
    public String getCompanionName() {
        return companionName.get();
    }

    //----------------------------------------------------------------------------
    public void setCompanionName(String cn) {
        companionName.set(cn);
    }

    //----------------------------------------------------------------------------
    public String getStartTime() {
        return startTime.get();
    }

    //----------------------------------------------------------------------------
    public void setStartTime(String st) {
        startTime.set(st);
    }

    //----------------------------------------------------------------------------
    public String getEndTime() {
        return endTime.get();
    }

    //----------------------------------------------------------------------------
    public void setEndTime(String et) {
        endTime.set(et);
    }

    //----------------------------------------------------------------------------
    public String getCompanionHours() {
        return companionHours.get();
    }

    //----------------------------------------------------------------------------
    public void setCompanionHours(String ch) {
        companionHours.set(ch);
    }

}
