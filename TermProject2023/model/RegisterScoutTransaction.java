package model;

import java.util.Properties;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

public class RegisterScoutTransaction extends Transaction
{
    private Scout scout;

    // GUI Components
	private String transactionErrorMessage = "";
	private String scoutUpdateStatusMessage = "";

    public RegisterScoutTransaction() throws Exception
    {
        super();

        // DEBUG - program isn't getting to this point 03/26/2023 -SW
        System.out.println("In RegisterScoutTransaction constructor");
    }

    /**
	 * This method encapsulates all the logic of creating the scout,
	 * verifying ownership, crediting, etc. etc.
	 */
	//----------------------------------------------------------
	public void processScoutData(Properties props)
	{
        System.out.println("RegisterScoutTransaction : processScoutData() - Work in Progress");
        /* 
            scout = new Scout(props);
            scout.update();
            scoutUpdateStatusMessage = (String)scout.getState("UpdateStatusMessage");
        */
	}

    //-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("TransactionError"))
		{
			return transactionErrorMessage;
		}
		else
		if (key.equals("ScoutUpdateStatusMessage") == true)
		{
			return scoutUpdateStatusMessage;
		}
		
		return null;
	}

    //-----------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		// DEBUG 
        System.out.println("RegisterScoutTransaction : stateChangeRequest() key is: " + key);

		if (key.equals("DoYourJob"))
		{
			doYourJob();
		}
		else if (key.equals("RegisterScoutData"))
		{
			processScoutData((Properties)value);
		}

		//myRegistry.updateSubscribers(key, this);
	}

    //-----------------------------------------------------------
    protected void setDependencies() 
    {
        dependencies = new Properties();
        dependencies.setProperty("CancelScout", "CancelTransaction");
        dependencies.setProperty("InsertScoutData", "ScoutUpdateStatusMessage");

        myRegistry.setDependencies(dependencies);
    }

    //-----------------------------------------------------------
    protected Scene createView() 
    {
        // DEBUG 
        System.out.println("In RegisterScoutTransaction : createView()");

        Scene currentScene = myViews.get("RegisterScoutView");

        // Create scene if its null
        if (currentScene == null)
        {
            View newView = ViewFactory.createView("RegisterScoutView", this);
            currentScene = new Scene(newView);
            myViews.put("RegisterScoutView", currentScene);
        }

        return currentScene;
    }  
