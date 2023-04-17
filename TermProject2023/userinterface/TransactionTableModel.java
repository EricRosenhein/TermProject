package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

public class TransactionTableModel
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
    public TransactionTableModel(Vector<String> transactionData)
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
    public String getID() {
        return id.get();
    }

    //----------------------------------------------------------------------------
    public void setID(String b) {
        id.set(b);
    }

    //----------------------------------------------------------------------------
    public String getSessionID() {
        return sessionId.get();
    }

    //----------------------------------------------------------------------------
    public void setSessionID(String b) {
        sessionId.set(b);
    }

    //----------------------------------------------------------------------------
    public String getTransactionType() {
        return transactionType.get();
    }

    //----------------------------------------------------------------------------
    public void setTransactionType(String b) {
        transactionType.set(b);
    }

    //----------------------------------------------------------------------------
    public String getBarcode() {
        return transactionType.get();
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
    public void setTransactionAmount(String tt) {
        transactionAmount.set(tt);
    }
    //----------------------------------------------------------------------------
    public String getPaymentMethod() {
        return paymentMethod.get();
    }

    //----------------------------------------------------------------------------
    public void setPaymentMethod(String n) {
        paymentMethod.set(n);
    }

    //----------------------------------------------------------------------------
    public String getCustomerName() {
        return customerName.get();
    }

    //----------------------------------------------------------------------------
    public void setCustomerName(String s) {
        customerName.set(s);
    }

    //----------------------------------------------------------------------------
    public String getCustomerPhone() {
        return customerPhone.get();
    }

    //----------------------------------------------------------------------------
    public void setCustomerPhone(String b) {
        customerPhone.set(b);
    }

    //----------------------------------------------------------------------------
    public String getCustomerEmail() {
        return customerEmail.get();
    }

    //----------------------------------------------------------------------------
    public void setCustomerEmail(String b) {
        customerEmail.set(b);
    }

    //----------------------------------------------------------------------------
    public String getTransactionDate() {
        return transactionDate.get();
    }

    //----------------------------------------------------------------------------
    public void setTransactionDate(String b) {
        transactionDate.set(b);
    }

    //----------------------------------------------------------------------------
    public String getTransactionTime() {
        return transactionTime.get();
    }

    //----------------------------------------------------------------------------
    public void setTransactionTime(String b) {
        transactionTime.set(b);
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
