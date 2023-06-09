package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================

public class TreeTypeTableModel
{
    private final SimpleStringProperty id;
    private final SimpleStringProperty typeDescription;
    private final SimpleStringProperty cost;
    private final SimpleStringProperty barcodePrefix;

    //----------------------------------------------------------------------------
	public TreeTypeTableModel(Vector<String> treeTypeData)
	{
		
		id =  new SimpleStringProperty(treeTypeData.elementAt(0));
        typeDescription =  new SimpleStringProperty(treeTypeData.elementAt(1));
        cost =  new SimpleStringProperty(treeTypeData.elementAt(2));
        barcodePrefix =  new SimpleStringProperty(treeTypeData.elementAt(3));
	}

    //----------------------------------------------------------------------------
    public String getId() {
        return id.get();
    }

    //----------------------------------------------------------------------------
    public void setId(String i) {
        id.set(i);
    }

    public String getTypeDescription() {
        return typeDescription.get();
    }
    
    //----------------------------------------------------------------------------
    public void setTypeDescription(String d) {
        typeDescription.set(d);
    }

    //----------------------------------------------------------------------------
    public String getCost() {
        return cost.get();
    }
        
    //----------------------------------------------------------------------------
    public void setCost(String c) {
        cost.set(c);
    }

    //----------------------------------------------------------------------------
    public String getBarcodePrefix() {
        return barcodePrefix.get();
    }
            
    //----------------------------------------------------------------------------
    public void setBarcodePrefix(String p) {
        barcodePrefix.set(p);
    }

}
