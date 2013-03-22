package cscie160.hw6;

import java.net.*;
import java.io.*;

/** Client-side proxy class which manages the connection to the
 *  server and forwards the client's requests to the server by writing
 *  the text of requests to the stream on top of the sockect established
 *  at creation time when the constructor is called.
 */
public class ATMProxy implements ATM 
{
    private Socket socket;
    private PrintStream  printStream;
    BufferedReader inputReader;
	
    public ATMProxy(String host, int port) throws UnknownHostException, java.io.IOException 
	{
		socket = new Socket(host, port);
		OutputStream outputStream = socket.getOutputStream();
		printStream = new PrintStream(outputStream);
		InputStream inputStream = socket.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		inputReader = new BufferedReader(inputStreamReader);
    }
	
    public void deposit(float amount) throws ATMException 
	{
		// Commands is an enum in this package
		printStream.println(Commands.DEPOSIT + " " + amount);
    }
	
    public void withdraw(float amount) throws ATMException 
	{
		printStream.println(Commands.WITHDRAW +  " " +  amount);
    }
	
    public Float getBalance() throws ATMException 
	{
		printStream.println(Commands.BALANCE);
		try 
		{
			String response = inputReader.readLine();
			if (response != null)
			{
			return Float.parseFloat(response.trim());
			} 
			else 
			{
				throw new ATMException("ATMProxy: Unexpected end of stream reading commands in getBalance()");
			}
				
		} 
		catch (Exception ex) 
		{
			throw new ATMException(ex.toString());
		}
    }
}
