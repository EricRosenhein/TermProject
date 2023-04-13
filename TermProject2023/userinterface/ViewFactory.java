package userinterface;


import impresario.IModel;

//==============================================================================
public class ViewFactory 
{
	public static View createView(String viewName, IModel model)
	{
		
		// DEBUG System.out.println("In View : createView(), view name: " + viewName);
		
		if (viewName.equals("TLCView"))
		{
			return new TLCView(model);
		}
		else if (viewName.equals("RegisterScoutView"))
		{
			return new RegisterScoutView(model);
		}
		else if (viewName.equals("AddTreeView"))
		{
			return new AddTreeView(model);
		}
		else if (viewName.equals("AddTreeTypeView"))
		{
			return new AddTreeTypeView(model);
		}
		else if (viewName.equals("SearchScoutView"))
		{
			return new SearchScoutView(model);
		}
		else if (viewName.equals("ScoutCollectionView"))
		{
			return new ScoutCollectionView(model);
		}
		else if (viewName.equals("UpdateScoutView"))
		{
			return new UpdateScoutView(model);
		}
		else if (viewName.equals("ScanTreeBarcodeView"))
		{
			return new ScanTreeBarcodeView(model);
		}
		else if (viewName.equals("UpdateTreeView"))
		{
			return new UpdateTreeView(model);
		}
		else if (viewName.equals("TreeTypeSearchView"))
		{
			return new TreeTypeSearchView(model);
		}
		else if (viewName.equals("UpdateTreeTypeView"))
		{
			return new UpdateTreeTypeView(model);
		}
		else if (viewName.equals("RemoveScoutView"))
		{
			return new RemoveScoutView(model);
		}
		else if (viewName.equals("ConfirmDeleteTreeView"))
		{
			return new ConfirmDeleteTreeView(model);
		}
//		else if (viewName.equals("StartShiftView"))
//		{
//			return new StartShiftView(model);
//		}
//		else if (viewName.equals("EndShiftView"))
//		{
//			return new EndShiftView(model);
//		}
//		else if (viewName.equals("SellTreeView"))
//		{
//			return new SellTreeView(model);
//		}
		else
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
