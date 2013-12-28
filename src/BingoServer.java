import bingo.*;
import java.io.*;
import java.net.*;
import java.util.Arrays;

public class BingoServer{

    private static ServerSocket serverSocket = null;
    private static Socket clientSocket[] = null;

    private static final int maxClientCount = 2;
    private static final clientThread[] threads = new clientThread[maxClientCount];

	public static void main(String[] args) throws IOException
	{
        // Initiating server port
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

        //handles client connection
        int i = 0;
        clientSocket = new Socket[maxClientCount];
        while(i < maxClientCount){
            try{
                clientSocket[i] = serverSocket.accept();
                i++;

            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        for(int j = 0; j < maxClientCount; ++j){
            if(threads[j] == null){
                threads[j] = new clientThread(clientSocket[j], threads);
                threads[j].start();
            }
        }
 
	}
}

class clientThread extends Thread{

    private PrintWriter out = null;
    private BufferedReader in = null;
    private Socket clientSocket = null;
    private final clientThread[] threads;

    public clientThread(Socket clientSocket, clientThread[] threads){
        this.clientSocket = clientSocket;
        this.threads = threads;
    }

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

    public void run(){
        //Initialize bingo instance and prompt users to select number of cards
        Bingo model = new Bingo();
        BingoView view = new BingoView();
        BingoController controller = new BingoController(model, view);

        try{
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(
            new InputStreamReader(clientSocket.getInputStream()));

            String inputLine, outputLine;
            int cards;
             
            // Initiate conversation with client
            outputLine = "Welcome to Bingo!";
            out.println(outputLine);

            // Get input from client
            cards = Integer.parseInt(in.readLine());
            System.out.println("Cards from client: " + cards);

            int[][] cardSet = new int[cards][];
            //initialize array with n cards and 25 numbers for each card
            for(int i = 0; i < cards; ++i)
            {
                //generate one set of randomized numbers of each card
                int[] set = model.generateCardSet();
                cardSet[i] = set;
                out.println(Arrays.toString(set));
            }

            //get a list of randomized numbers to be called
            String[] sequence = controller.getCallSequence();
            //System.out.println(Arrays.toString(sequence));
            out.println(Arrays.toString(sequence));
            out.flush();

            while(true)
            {
                String patternInput;
                if((patternInput = in.readLine()) != null)
                {
                    //String patternInput = in.readLine();
                    int[] pattern = convertPattern(patternInput);
                    int cardNumber = Integer.parseInt(in.readLine());
                    String sequenceInput = in.readLine();
                    String[] interimSequence = sequenceInput.replaceAll("\\[", "").replaceAll("\\]", "").split(", ");
                    //showPattern("From Server", pattern);
                    boolean win = controller.checkWinningCondition(cardNumber, pattern, interimSequence, cardSet);
                    System.out.println("Win? " + win);
                    if(win)
                    {
                        out.println("Win");
                        break;
                    }
                    else
                    {
                        out.println("Not Win");
                    }
                }  
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}