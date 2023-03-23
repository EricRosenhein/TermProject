package model;

import java.util.HashMap;
import java.util.Map;

import event.Event;
import impresario.IModel;
import impresario.IView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

public class TransactionMenu implements IView, IModel
{
    // GUI Components
	//private HashMap<String, Scene> myViews;
    private Map<String, Scene> myViews;
	private Stage stage;

    // ----------------------------------------------------------------
    public TransactionMenu()
    {
        stage = MainStageContainer.getInstance();
        myViews = new HashMap<String, Scene>();

        /* 
        myRegistry = new ModelRegistry("TransactionMenu");

        if(myRegistry == null)
        {
            new Event(Event.getLeafLevelClassName(this), "TransactionMenu", "could not instantiate Registry", Event.ERROR);
        }

        setDependencies();
        */

        createAndShowTransactionMenuView();
    }

    // ----------------------------------------------------------------
    private void createAndShowTransactionMenuView() 
    {
        Scene currentScene = (Scene)myViews.get("TransactionMenu");

        if (currentScene == null)
        {
            View newView = ViewFactory.createView("TransactionMenu", this);
            
            currentScene = new Scene(newView);

            myViews.put("TransactionMenu", currentScene);
        }

        swapToView(currentScene);             
    }

    // ----------------------------------------------------------------
    private void swapToView(Scene newScene) 
    {
        if (newScene == null)
        {
            System.out.println("TransactionMenu : swapToView() - Missing view to display!");
            new Event(Event.getLeafLevelClassName(this), "swaptoView", "Missing view to display", Event.ERROR);
            
            return;
        }

        stage.setScene(newScene);
        stage.sizeToScene();

        WindowPosition.placeCenter(stage);
    }

    // ----------------------------------------------------------------
    @Override
    public Object getState(String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getState'");
    }

    // ----------------------------------------------------------------
    @Override
    public void subscribe(String key, IView subscriber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'subscribe'");
    }

    // ----------------------------------------------------------------
    @Override
    public void unSubscribe(String key, IView subscriber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unSubscribe'");
    }

    // ----------------------------------------------------------------
    @Override
    public void stateChangeRequest(String key, Object value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stateChangeRequest'");
    }

    // ----------------------------------------------------------------
    @Override
    public void updateState(String key, Object value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateState'");
    }
}
