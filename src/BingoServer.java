import bingo.*;
import java.io.*;
import java.net.*;

public class BingoServer{
	public static void main(String[] args) throws IOException
	{
		if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }
 
        int portNumber = Integer.parseInt(args[0]);
 
        try (
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {
         
            String inputLine, outputLine;
            int cards;
             
            // Initiate conversation with client
           	outputLine = "Welcome to Bingo!";
            out.println(outputLine);

            // Get input from client
            cards = Integer.parseInt(in.readLine());
            System.out.println("Cards from client: " + cards);
 
            
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
	}
}