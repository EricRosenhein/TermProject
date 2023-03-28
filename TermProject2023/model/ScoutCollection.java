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

    public Vector findScoutsOlderThan(String DateOfBirth) {
        String query = "SELECT * FROM " + myTableName + " WHERE (DateOfBirth < " + DateOfBirth + ") ORDER BY name ASC;";
        return doQuery(query);
    }

    public Vector findScoutsYoungerThan(String DateOfBirth) {
        String query = "SELECT * FROM " + myTableName + " WHERE (DateOfBirth > " + DateOfBirth + ") ORDER BY name ASC;";
        return doQuery(query);
    }

    public Vector findAllScouts() {

        String query = "SELECT * FROM " + myTableName + " ORDER BY name ASC;";
        return doQuery(query);
    }

    public Vector findPatronsWithNameLike(String LastName) {
        String query = "SELECT * FROM " + myTableName + " WHERE (author LIKE '%" + LastName + "%')";
        return doQuery(query);
    }

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
        System.out.println("In Bcview: doQuery - ScoutCollection.java");
        System.out.println(scoutList + "prints scoutList");
        return scoutList;
    }

    private void setDependencies() {
        Properties dependencies = new Properties();
        dependencies.setProperty("CancelSearchScout", "SearchScoutView");

        myRegistry.setDependencies(dependencies);
    }

    // ----------------------------------------------------------
    @Override
    public Object getState(String key) {
        if (key.equals("scouts"))
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
        Scene currentScene = myViews.get("ScoutSearchView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("ScoutSearchView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("ScoutSearchView", currentScene);
        }

        swapToView(currentScene);
    }

    public void ShowScoutCollectionView() {
        Scene currentScene = myViews.get("ScoutCollectionView");
        System.out.println("ScoutCollectionView - ScoutCollection.java");
        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("ScoutCollectionView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("ScoutCollectionView", currentScene);
        }

        swapToView(currentScene);
    }
}
