package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================

public class TreeTableModel
{
        private final SimpleStringProperty barcode;
        private final SimpleStringProperty treeType;
        private final SimpleStringProperty notes;
        private final SimpleStringProperty status;
	    private final SimpleStringProperty dateStatusUpdated;

    //----------------------------------------------------------------------------
	public TreeTableModel(Vector<String> treeData)
	{
		  barcode =  new SimpleStringProperty(treeData.elementAt(0));
          treeType =  new SimpleStringProperty(treeData.elementAt(1));
		  notes =  new SimpleStringProperty(treeData.elementAt(2));
		  status =  new SimpleStringProperty(treeData.elementAt(3));
		  dateStatusUpdated =  new SimpleStringProperty(treeData.elementAt(4));

	}

    //----------------------------------------------------------------------------
    public String getBarcode() {
        return barcode.get();
    }

    //----------------------------------------------------------------------------
    public void setBarcode(String b) {
        barcode.set(b);
    }
    
    //----------------------------------------------------------------------------
    public String getTreeType() {
        return treeType.get();
    }
    
    //----------------------------------------------------------------------------
    public void setTreeType(String tt) {
        treeType.set(tt);
    }
    //----------------------------------------------------------------------------
    public String getNotes() {
        return notes.get();
    }
    
    //----------------------------------------------------------------------------
    public void setNotes(String n) {
        notes.set(n);
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
    public void setDateStatusUpdated(String d) {
        dateStatusUpdated.set(d);
    }
}
