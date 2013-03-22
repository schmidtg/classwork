package cscie160.hw6;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Executes client requests as a work-order object
 * 1. Parses the request string to determine which operation (deposit, withdraw, balance) is required and the amount for deposit/withdraw.
 * 2. Invokes the appropriate method through the ATM interface.
 * 3. Writes a message back to the client, reporting the result.
 */
public class ATMRunnable implements Runnable {

    private String commandLine;
    private ATMImplementation atmImplementation;
    private PrintStream printStream;

    // To carry out this agenda this object needs the transaction string that the server read from the Socket,
    // a reference to the ATM object, and an output object onto which it can write the result back to the client.
    public ATMRunnable(String commandLine, ATMImplementation atmImpl, PrintStream printStream) {
        this.commandLine = commandLine;
        this.atmImplementation = atmImpl;
        this.printStream = printStream;
    }

    public void run() {
        try
        {
            Float result = executeCommand(commandLine);
            // Only BALANCE command returns non-null
            if (result != null) {
                printStream.println(result);  // 3. Write it back to the client
            }
        }
        catch (ATMException atmex) {
            System.out.println("ERROR: " + atmex);
        }
    }

	/** The logic here is specific to our protocol.  We parse the string
	 *  according to that protocol.
     * @param commandLine String of arguments from command line
     * @throws ATMException error
     * @return Returns the balance, or null if deposit or withdrawal
	 */
	private Float executeCommand(String commandLine) throws ATMException {
		// Break out the command line into String[]
		StringTokenizer tokenizer = new StringTokenizer(commandLine);
		String commandAndParam[] = new String[tokenizer.countTokens()];
		int index = 0;
		while (tokenizer.hasMoreTokens())
		{
			commandAndParam[index++] = tokenizer.nextToken();
		}
        String command = commandAndParam[0];

		// Dispatch BALANCE request without further ado.
		if (command.equalsIgnoreCase(Commands.BALANCE.toString()))
		{
			return atmImplementation.getBalance();
		}
		// Must have 2nd arg for amount when processing DEPOSIT/WITHDRAW commands
		if (commandAndParam.length < 2)
		{
			throw new ATMException("Missing amount for command \"" + command + "\"");
		}
		try
		{
            float amount = Float.parseFloat(commandAndParam[1]);
			if (command.equalsIgnoreCase(Commands.DEPOSIT.toString()))
			{
				atmImplementation.deposit(amount);
			}
			else if (command.equalsIgnoreCase(Commands.WITHDRAW.toString()))
			{
				atmImplementation.withdraw(amount);
			} else
			{
				throw new ATMException("Unrecognized command: " + command);
			}
		}
		catch (NumberFormatException nfe)
		{
			throw new ATMException("Unable to make float from input: " + commandAndParam[1]);
		}

		// BALANCE command returned result above.  Other commands return null;
		return null;
	}
}