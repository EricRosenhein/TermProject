// specify the package
package model;

// system imports
import java.util.Vector;
import javax.swing.JFrame;

// project imports

/** The class containing the TransactionFactory for the ATM application */
//==============================================================
public class TransactionFactory
{

	/**
	 *
	 */
	//----------------------------------------------------------
	public static Transaction createTransaction(String transType)
		throws Exception
	{
		Transaction retValue = null;

		if (transType.equals("InsertNewBook") == true)
		{
			retValue = new InsertBookTransaction();
		}
		else
		if (transType.equals("RegisterNewScout") == true)
		{
			retValue = new RegisterScoutTransaction();
		}
		else
		if (transType.equals("SearchBooks") == true)
		{
			retValue = new SearchBookTransaction();
		}
		else
		if (transType.equals("SearchPatrons") == true)
		{
			retValue = new SearchPatronTransaction();
		} 

		return retValue;
	}
}
