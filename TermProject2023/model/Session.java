// specify package
package model;

// system imports
import exception.InvalidPrimaryKeyException;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;


// project imports


public class Session extends EntityBase
{

    // check for open session everyTime the program boots to update initial gui appropriately
    // empty constructor
    // method to find open session - "SELECT * FROM Session WHERE ((EndTime IS NULL) OR (EndTime = ''))
    public Session(String ID) throws InvalidPrimaryKeyException {

        super(myTableName);
        setDependencies();

        String query = "SELECT * FROM " + myTableName + " WHERE (TroopId = " + troopId + ")";
        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null){
            int size = allDataRetrieved.size();
            if (size != 1){
                throw new InvalidPrimaryKeyException("Multiple Scouts matching troop id : "+ troopId + " found.");
            } else{
                Properties retrievedScoutData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();
                Enumeration allKeys = retrievedScoutData.propertyNames();
                while (allKeys.hasMoreElements() == true){
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedScoutData.getProperty(nextKey);

                    if (nextValue != null){
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }
            }
        }
        // If no scout found for troopId, throw exception
        else{
            throw new InvalidPrimaryKeyException("ERROR: No Scout matching troop id : "+ troopId +" found.");
        }
    }

    /** This is the second constructor - takes in a Properties object to populate the Scout object with */
    //---------------------------------------------------------------------
    public Session(Properties scoutInfo){
        super(myTableName);

        //DEBUG System.out.println("userinterface/Scout: Scout(Properties scoutInfo): Start of method");

        setDependencies();
        persistentState = new Properties();
        Enumeration allKeys = scoutInfo.propertyNames();
        while (allKeys.hasMoreElements() == true){
            String nextKey = (String)allKeys.nextElement();
            String nextValue = scoutInfo.getProperty(nextKey);
            if (nextValue != null){
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    /** Empty (default) constructor */
    //------------------------------------------------------------------------
    public Session() {
        super(myTableName);
        setDependencies();
        persistentState = new Properties();
    }
}
