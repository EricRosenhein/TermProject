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
	//----------------------------------------------------------s
	public TransactionFactory() {}
	
	//----------------------------------------------------------
	public static Transaction createTransaction(String transactionType) throws Exception
	{
		// DEBUG
		System.out.println("In TransactionFactory : createTransaction() - Current Transaction Type is: " + transactionType);
		
		Transaction returnValue = null;

		if (transactionType.equals("RegisterScout"))
		{			
			returnValue = new RegisterScoutTransaction();
		}
		/* 
		else
		if (transactionType.equals("InsertNewPatron") == true)
		{
			returnValue = new InsertPatronTransaction();
		}
		else
		if (transactionType.equals("SearchBooks") == true)
		{
			returnValue = new SearchBookTransaction();
		}
		else
		if (transactionType.equals("SearchPatrons") == true)
		{
			returnValue = new SearchPatronTransaction();
		} 
		*/

		// DEBUG
		System.out.println("Return value is: " + returnValue.toString());

		return returnValue;
	}
}
