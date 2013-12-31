import bingo.*;
import java.io.*;
import java.net.*;
import java.util.Arrays;

public class BingoServer{
    private static Bingo model;
    private static BingoController controller;
    private static BingoView view;
    private static ServerSocket serverSocket = null;
    private static Socket clientSocket[] = null;

    private static final int maxClientCount = 2;
    private static final clientThread[] threads = new clientThread[maxClientCount];

	public static void main(String[] args) throws IOException{
        // Initiating server port
		if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }
 
        int portNumber = Integer.parseInt(args[0]);
 
        try {
            serverSocket = new ServerSocket(portNumber);
            System.out.println("Server Started. Waiting for clients...");
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

        // create a thread for each client
        model = new Bingo();
        view = new BingoView();
        controller = new BingoController(model, view);
        
        String[] sequence = controller.getCallSequence();
        
        for(int j = 0; j < maxClientCount; ++j){
            if(threads[j] == null){
                threads[j] = new clientThread(clientSocket[j], threads, sequence);
                threads[j].start();
            }
        }
	}
}

class clientThread extends Thread{

    private String[] sequence;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Socket clientSocket = null;
    private final clientThread[] threads;
    private String playerName = null;

    public clientThread(Socket clientSocket, clientThread[] threads, String[] sequence){
        this.clientSocket = clientSocket;
        this.threads = threads;
        this.sequence = sequence;
        this.playerName = playerName;
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
            String cards;
             
            // Initiate conversation with client
            outputLine = "Welcome to Bingo!";
            out.println(outputLine);

            // Get input from client
            // player's name and number of cards is packed into a string
            // format: number of cards + player's name
            // example: cards = "2yoonwaiyan" 
            cards = in.readLine();
            int numofCards = (int)(cards.charAt(0)) - 48;
            String playerName = cards.substring(1);
            this.playerName = playerName;
            System.out.println("Cards from client: " + numofCards);

            int[][] cardSet = new int[numofCards][];
            //initialize array with n cards and 25 numbers for each card
            for(int i = 0; i < numofCards; ++i)
            {
                //generate one set of randomized numbers of each card
                int[] set = model.generateCardSet();
                cardSet[i] = set;
                out.println(Arrays.toString(set));
            }

            //send the call sequence to clients
            out.println(Arrays.toString(sequence));
            out.flush();

            //loop to receive messages from client
            while(true){
                String patternInput;
                if((patternInput = in.readLine()) != null){
                    // receive "win" message from client
                    if(patternInput.equals("W")){
                        for(int i = 0; i < threads.length; ++i){
                            threads[i].out.println("W " + playerName);
                        }
                    }
                    else if(patternInput.charAt(0) == ':'){
                        //receive chat messages from client
                        String chatMessage = patternInput;
                        for(int i = 0; i < threads.length; ++i){
                            threads[i].out.println("C " + playerName + chatMessage);
                        }
                    }
                }  
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}