package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================

public class ScoutTableModel
{
    private final SimpleStringProperty id;
    private final SimpleStringProperty sessionId;
    private final SimpleStringProperty transactionType;
    private final SimpleStringProperty barcode;
    private final SimpleStringProperty transactionAmount;
    private final SimpleStringProperty paymentMethod;
    private final SimpleStringProperty customerName;
    private final SimpleStringProperty customerPhone;

    private final SimpleStringProperty customerEmail;
    private final SimpleStringProperty transactionDate;
    private final SimpleStringProperty transactionTime;
    private final SimpleStringProperty dateStatusUpdated;

    //----------------------------------------------------------------------------
    public ScoutTableModel(Vector<String> transactionData)
    {
        id =  new SimpleStringProperty(transactionData.elementAt(0));
        sessionId =  new SimpleStringProperty(transactionData.elementAt(1));
        transactionType =  new SimpleStringProperty(transactionData.elementAt(2));
        barcode =  new SimpleStringProperty(transactionData.elementAt(3));
        transactionAmount =  new SimpleStringProperty(transactionData.elementAt(4));
        paymentMethod =  new SimpleStringProperty(transactionData.elementAt(5));
        customerName =  new SimpleStringProperty(transactionData.elementAt(6));
        customerPhone =  new SimpleStringProperty(transactionData.elementAt(7));
        customerEmail =  new SimpleStringProperty(transactionData.elementAt(8));
        transactionDate =  new SimpleStringProperty(transactionData.elementAt(9));
        transactionTime =  new SimpleStringProperty(transactionData.elementAt(10));
        dateStatusUpdated =  new SimpleStringProperty(transactionData.elementAt(11));

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
    public String getSessionId() {
        return sessionId.get();
    }

    //----------------------------------------------------------------------------
    public void setSessionId(String ss) {sessionId.set(ss);}

    //----------------------------------------------------------------------------

    public String getTransactionType() {
        return transactionType.get();
    }

    //----------------------------------------------------------------------------
    public void setTransactionType(String ty) {
        transactionType.set(ty);
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
    public String getTransactionAmount() {
        return transactionAmount.get();
    }

    //----------------------------------------------------------------------------
    public void setTransactionAmount(String ta) {
        transactionAmount.set(ta);
    }

    //----------------------------------------------------------------------------
    public String getPaymentMethod() {
        return paymentMethod.get();
    }

    //----------------------------------------------------------------------------
    public void setPaymentMethod(String p) {
        paymentMethod.set(p);
    }

    //----------------------------------------------------------------------------
    public String getCustomerName() {
        return customerName.get();
    }

    //----------------------------------------------------------------------------
    public void setCustomerName(String n) {
        customerName.set(n);
    }

    //----------------------------------------------------------------------------
    public String getCustomerPhone() {
        return customerPhone.get();
    }

    //----------------------------------------------------------------------------
    public void setCustomerPhone(String ph) {
        customerPhone.set(ph);
    }
    //----------------------------------------------------------------------------
    public String getCustomerEmail() {
        return customerEmail.get();
    }

    //----------------------------------------------------------------------------
    public void setCustomerEmail(String e) {
        customerEmail.set(e);
    }
    //----------------------------------------------------------------------------
    public String getTransactionDate() {
        return transactionDate.get();
    }

    //----------------------------------------------------------------------------
    public void setTransactionDate(String td) {transactionDate.set(td);}
    //----------------------------------------------------------------------------
    public String getTransactionTime() {
        return transactionTime.get();
    }

    //----------------------------------------------------------------------------
    public void setTransactionTime(String tt) {transactionTime.set(tt);}
    //----------------------------------------------------------------------------
    public String getDateStatusUpdated() {
        return dateStatusUpdated.get();
    }

    //----------------------------------------------------------------------------
    public void setDateStatusUpdated(String tt) {dateStatusUpdated.set(tt);}
}
