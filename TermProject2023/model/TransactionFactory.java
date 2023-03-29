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
		else
		if (transactionType.equals("AddTreeType") == true)
		{
			returnValue = new AddTreeTypeTransaction();
		}
		else
		if (transactionType.equals("UpdateScout") == true)
		{
			returnValue = new UpdateScoutTransaction();
		} 
		else
		if (transactionType.equals("UpdateTree") == true)
		{
			returnValue = new UpdateTreeTransaction();
		} 
		else
		if (transactionType.equals("UpdateTreeType") == true)
		{
			returnValue = new UpdateTreeTypeTransaction();
		} 
		else
		if (transactionType.equals("RemoveScout") == true)
		{
			returnValue = new RemoveScoutTransaction();
		} 
		else
		if (transactionType.equals("RemoveTree") == true)
		{
			returnValue = new RemoveTreeTransaction();
		} 

		// DEBUG
		//System.out.println("Return value is: " + returnValue.toString());

		return returnValue;
	}
}
