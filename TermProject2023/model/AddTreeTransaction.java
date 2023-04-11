/**
 *  AddTreeTransaction:
 *
 */
// specify the package
package model;

// system imports
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.Properties;
import java.util.Vector;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;

import userinterface.View;
import userinterface.ViewFactory;

/** The controller class for adding a tree to the database */
//==============================================================
public class AddTreeTransaction extends Transaction
{

    private Tree tree;


    // GUI Components
    private String transactionErrorMessage = "";
    private String treeUpdateStatusMessage = "";

    //--------------------------------------------------------------
    protected AddTreeTransaction() {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("CancelAddTree", "CancelTransaction");
        dependencies.setProperty("InsertTreeData", "TreeUpdateStatusMessage");

        myRegistry.setDependencies(dependencies);
    }

    /** This class facilitates the process of populating a tree object from a properties object gathered
     * from the user on the front end and adding it to the database*/
    //----------------------------------------------------------
    protected void processTreeData(Properties p)
    {
        // Set barcode prefix to the first 2 chars of the Barcode
        String barcode = p.getProperty("Barcode");
        String barPrefix = extract(barcode);

        // DEBUG System.out.println("model/AddTreeTransaction: processTreeData(Properties p): barcode - " + barcode + "\nbarPrefix - " + barPrefix);

        // Retrieve tt object from the database using barcode prefix
        try
        {
            if (barPrefix.length() > 0) {
                TreeType treeType = new TreeType(barPrefix);
                String treeTypeId = (String)treeType.getState("ID");
                p.setProperty("TreeType", treeTypeId);
                try {
                    Tree existingTree = new Tree(barcode);
                    treeUpdateStatusMessage = "ERROR: Tree with barcode: " + barcode + " already exists!";
                }
                catch (InvalidPrimaryKeyException excep)
                {
                    String currentDate = LocalDate.now().toString();
                    p.setProperty("DateStatusUpdated", currentDate);
                    tree = new Tree(p);
                    tree.setOldFlag(false);
                    tree.update();
                    treeUpdateStatusMessage = (String)tree.getState("UpdateStatusMessage");
                }
            }
            else
                treeUpdateStatusMessage = "ERROR: Invalid barcode for tree";
        }
         catch(InvalidPrimaryKeyException ex)
          {
             treeUpdateStatusMessage = "ERROR: No tree type found with barcode prefix " + barPrefix;
          }


            /*
                Calendar rightNow = Calendar.getInstance();
                Date currentDate = rightNow.getTime();
                SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd");
                String currentDate = df.format(currentDate);

             */


    }


    //-----------------------------------------------------------
    // This method
    // @
    private String extract(String bar){

        if ((bar != null) && (bar.length() > 0))
        {
            if (bar.length() > 2) return bar.substring(0,2);
            else return "";
        }
        else
            return "";

    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
        }
        else
        if (key.equals("TreeUpdateStatusMessage") == true)
        {
            return treeUpdateStatusMessage;
        }

        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        // DEBUG System.out.println("DepositTransaction.sCR: key: " + key);

        if (key.equals("DoYourJob") == true)
        {
            doYourJob();
        }
        else
        if (key.equals("InsertTreeData") == true)
        {
            processTreeData((Properties)value);
        }

        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the frame
     */
    //------------------------------------------------------
    protected Scene createView()
    {
        Scene currentScene = myViews.get("AddTreeView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("AddTreeView", this);
            currentScene = new Scene(newView);
            myViews.put("AddTreeView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }
}