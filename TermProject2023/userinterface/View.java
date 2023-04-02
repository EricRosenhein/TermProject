// tabs=4
//************************************************************
//	COPYRIGHT 2009/2015 Sandeep Mitra and Michael Steves, The
//    College at Brockport, State University of New York. - 
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot 
// be reproduced, copied, or used in any shape or form without 
// the express written consent of The College at Brockport.
//************************************************************
//
// specify the package
package userinterface;

// system imports
import java.util.Properties;
import java.util.Vector;
import java.util.EventObject;
import javafx.scene.Group;

// project imports
import common.StringList;
import impresario.IView;
import impresario.IModel;
import impresario.IControl;
import impresario.ControlRegistry;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

//==============================================================
public abstract class View extends Group
	implements IView, IControl
{
	// private data
	protected IModel myModel;
	protected ControlRegistry myRegistry;

	
	
	// GUI components
	
		
	// Class constructor
	//----------------------------------------------------------
	public View(IModel model, String classname)
	{
		myModel = model;
		
		myRegistry = new ControlRegistry(classname);

	}
	
	
	//----------------------------------------------------------
	public void setRegistry(ControlRegistry registry)
	{
		myRegistry = registry;
	}
	
	// Allow models to register for state updates
	//----------------------------------------------------------
	public void subscribe(String key,  IModel subscriber)
	{
		myRegistry.subscribe(key, subscriber);
	}
		
		
	// Allow models to unregister for state updates
	//----------------------------------------------------------
	public void unSubscribe(String key, IModel subscriber)
	{
		myRegistry.unSubscribe(key, subscriber);
	}

	//----------------------------------------------------------
	/* Common method used by views to display the title text
	 *
	 * @param title		title to display
	 */
	protected Node createTitle(String title)
	{
		Text titleText = new Text(" " + title + " ");
		titleText.setFont(Font.font("Serif", FontWeight.BOLD, 20));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.BURLYWOOD);

		return titleText;
	}

	//----------------------------------------------------------
	/* Common method used by views to display the title text
	 *
	 * @param title		title to display
	 */
	protected abstract Node createFormContents(String instructions);
}

