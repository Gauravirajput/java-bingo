import bingo.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

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
            //Initialize bingo instance and prompt users to select number of cards
            Bingo model = new Bingo();
            BingoView view = new BingoView();
            BingoController controller = new BingoController(model, view);
         
            String inputLine, outputLine;
            int cards;
             
            // Initiate conversation with client
           	outputLine = "Welcome to Bingo!";
            out.println(outputLine);

            // Get input from client
            cards = Integer.parseInt(in.readLine());
            System.out.println("Cards from client: " + cards);

            //get a list of randomized numbers to be called
            String[] numbers = controller.getCallSequence();
            for(int i = 0; i < 75; ++i)
            {
                try
                {
                    TimeUnit.SECONDS.sleep(5);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                System.out.println(numbers[i]);
                out.println(numbers[i]);

                // inputLine = in.readLine();
                // System.out.println(inputLine);
            }
 
            
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
	}
}