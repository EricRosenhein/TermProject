package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory 
{
	public static View createView(String viewName, IModel model)
	{
		
		// DEBUG
		System.out.println("In ViewFactory : createView(), view name: " + viewName);
		
		if (viewName.equals("TLCView"))
		{
			return new TLCView(model);
		}
		else if (viewName.equals("RegisterScoutView"))
		{
			return new RegisterScoutView(model);
		}
		else if (viewName.equals("UpdateScoutView"))
		{
			return new UpdateScoutView(model);
		}
		else if (viewName.equals("RemoveScoutView"))
		{
			return new RemoveScoutView(model);
		}
		else if (viewName.equals("AddTreeView"))
		{
			return new AddTreeView(model);
		}
		else if (viewName.equals("UpdateTreeView"))
		{
			return new UpdateTreeView(model);
		}
		/* TODO - the following needs to be implemented
		else if (viewName.equals("RemoveTreeView"))
		{
			return new RemoveTreeView(model);
		}
		else if (viewName.equals("AddTreeTypeView"))
		{
			return new AddTreeTypeView(model);
		}
		else if (viewName.equals("UpdateTreeTypeView"))
		{
			return new UpdateTreeTypeView(model);
		}
		*/

		// If we don't meet any of the above conditions, then return null
		return null;
	}


	/*
	public static Vector createVectorView(String viewName, IModel model)
	{
		if(viewName.equals("SOME VIEW NAME") == true)
		{
			//return [A NEW VECTOR VIEW OF THAT NAME TYPE]
		}
		else
			return null;
	}
	*/

}
