import bingo.*;
import java.io.*;
import java.net.*;
import java.util.Arrays;

public class BingoServer{

    public static ServerSocket serverSocket = null;
    public static Socket clientSocket = null;

    public static int[] convertPattern(String array)
    {
        String[] items = array.replaceAll("\\[", "").replaceAll("\\]", "").split(", ");
        int[] convertedPattern = new int[items.length];

        //System.out.print("from convertPattern");
        for (int i = 0; i < items.length; i++) {
            try {
                convertedPattern[i] = Integer.parseInt(items[i]);
                //System.out.print(convertedPattern[i] + " ");
            } catch (NumberFormatException nfe) {};
        }

        return convertedPattern;
    }

    public static void showPattern(String message, int[] pattern)
    {
        System.out.println(message);
        for(int i = 0; i < 25; ++i)
        {
            System.out.print(pattern[i] + " ");
        }
        System.out.println(" ");
    }

	public static void main(String[] args) throws IOException
	{
		if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }
 
        int portNumber = Integer.parseInt(args[0]);
 
        try {
            serverSocket = new ServerSocket(portNumber);
        }
        catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }

        //Initialize bingo instance and prompt users to select number of cards
        Bingo model = new Bingo();
        BingoView view = new BingoView();
        BingoController controller = new BingoController(model, view);
     
        clientSocket = serverSocket.accept();
        PrintWriter out =
            new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
            new InputStreamReader(clientSocket.getInputStream()));

        System.out.println("Reader ready: " + in.ready());

        String inputLine, outputLine;
        int cards;
         
        // Initiate conversation with client
       	outputLine = "Welcome to Bingo!";
        out.println(outputLine);

        // Get input from client
        cards = Integer.parseInt(in.readLine());
        System.out.println("Cards from client: " + cards);

        //initialize array with n cards and 25 numbers for each card
        for(int i = 0; i < cards; ++i)
        {
            //generate one set of randomized numbers of each card
            int[] cardSet = model.generateCardSet();
            out.println(Arrays.toString(cardSet));
        }

        //get a list of randomized numbers to be called
        String[] numbers = controller.getCallSequence();
        out.println(Arrays.toString(numbers));
        out.flush();

        System.out.println("Reader ready: " + in.ready());

        while(true)
        {
            String patternInput;
            if((patternInput = in.readLine()) != null)
            {
                //String patternInput = in.readLine();
                int[] pattern = convertPattern(patternInput);
                showPattern("From Server", pattern);
            }  
            else{
                System.out.println("not ready to read");
            }
        }
	}
}