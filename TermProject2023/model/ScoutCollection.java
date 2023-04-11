// specify the package
package model;

// system imports

import impresario.IView;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Properties;
import java.util.Vector;

// project imports
//import userinterface.ViewFactory;

/** The class containing the ScoutCollection for the application */
// ==============================================================
public class ScoutCollection extends EntityBase implements IView{
    private static final String myTableName = "Scout";

    private Vector<Scout> scoutList;
    // GUI Components

    // constructor for this class
    // ----------------------------------------------------------
    public ScoutCollection() {
        super(myTableName);
        setDependencies();
        scoutList = new Vector<>(); // new Vector<Scout>();
    }

    // -----------------------------------------------------------------------------------------------
    public Vector findScoutsWithNameLike(String firstName, String lastName)
    {
        String query = "";

        if (((firstName.length() == 0) || (firstName == null)) && ((lastName.length() == 0) || (lastName == null)))
        {
            query = "SELECT * FROM " + myTableName + " ORDER BY LastName, FirstName, TroopID";
        }
        else if ((firstName.length() == 0) || (firstName == null))
        {
            query = "SELECT * FROM " + myTableName + " WHERE (LastName LIKE '%" + lastName + "%') ORDER BY LastName, FirstName, TroopID";

        }
        else if ((lastName.length() == 0) || (lastName == null))
        {
            query = "SELECT * FROM " + myTableName + " WHERE (FirstName LIKE '%" + firstName + "%') ORDER BY LastName, FirstName, TroopID";

        }
        else
        {
            query = "SELECT * FROM " + myTableName + " WHERE (FirstName LIKE '%" + firstName + "%') AND (LastName LIKE '%" + lastName + "%') ORDER BY LastName, FirstName, TroopID";
        }
        return doQuery(query);
    }

    // -----------------------------------------------------------------------------------------------
    public Vector findActiveScoutsWithNameLike(String firstName, String lastName)
    {
        String query = "";

        if (((firstName.length() == 0) || (firstName == null)) && ((lastName.length() == 0) || (lastName == null)))
        {
            query = "SELECT * FROM " + myTableName + " WHERE (Status = 'Active') ORDER BY LastName, FirstName, TroopID";
        }
        else if ((firstName.length() == 0) || (firstName == null))
        {
            query = "SELECT * FROM " + myTableName + " WHERE (LastName LIKE '%" + lastName + "%') " +
                    " AND (Status = 'Active') ORDER BY LastName, FirstName, TroopID";

        }
        else if ((lastName.length() == 0) || (lastName == null))
        {
            query = "SELECT * FROM " + myTableName + " WHERE (FirstName LIKE '%" + firstName + "%') " +
                    " AND (Status = 'Active') ORDER BY LastName, FirstName, TroopID";

        }
        else
        {
            query = "SELECT * FROM " + myTableName + " WHERE (FirstName LIKE '%" + firstName + "%') AND "
                    + " (LastName LIKE '%" + lastName + "%') AND (Status = 'Active') ORDER BY LastName, FirstName, TroopID";
        }
        return doQuery(query);
    }

    // -----------------------------------------------------------------
    private Vector doQuery(String query) {
        try {
            Vector allDataRetrieved = getSelectQueryResult(query);
            if (allDataRetrieved != null) {
                // scoutList = new Vector<Scout>();
                for (int index = 0; index < allDataRetrieved.size(); index++) {
                    Properties data = (Properties) allDataRetrieved.elementAt(index);
                    Scout scout = new Scout(data);
                    scoutList.add(scout);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        // DEBUG System.out.println("model/ScoutCollection: doQuery(String query): Printing scoutList \n" + scoutList);
        return scoutList;
    }


    // --------------------------------------------------------------------------
    private void setDependencies() {
        Properties dependencies = new Properties();
        dependencies.setProperty("CancelSearchScout", "SearchScoutView");

        myRegistry.setDependencies(dependencies);
    }

    // ----------------------------------------------------------
    @Override
    public Object getState(String key) {
        if (key.equals("ScoutCollection"))
            return scoutList;
        return null;
    }

    // ----------------------------------------------------------------
    @Override
    public void stateChangeRequest(String key, Object value) {

        myRegistry.updateSubscribers(key, this);
    }

    /** Called via the IView relationship */
    // ----------------------------------------------------------
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    // -----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }

    public void createAndShowScoutCollectionView() {
        Scene currentScene = myViews.get("SearchScoutView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("SearchScoutView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("SearchScoutView", currentScene);
        }

        swapToView(currentScene);
    }

    public void showScoutCollectionView() {
        Scene currentScene = myViews.get("ScoutCollectionView");
        // DEBUG System.out.println("userinterface/ScoutCollectionView: showScoutCollectionView()");
        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("ScoutCollectionView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("ScoutCollectionView", currentScene);
        }

        swapToView(currentScene);
    }
}