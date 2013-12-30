import bingo.*;
import java.io.*; 
import java.net.*;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

public class BingoClient{

    public static Bingo model;
    public static BingoView view;
    public static BingoController controller;

    public static Socket clientSocket = null;
    public static PrintWriter toServer = null;
    public static BufferedReader in = null;

    public static int cards;
    public static int[][] cardSets;
    public static String sequenceInput;
    public static ArrayList<String> interimSequence;

    public void handleGUI()
    {
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                //split here: handle GUI/sequence
                //cards: number of cards selected by user
                interimSequence = new ArrayList<String>();

                try{
                    String[] sequence = convertPattern(sequenceInput);

                    for(int i = 0; i < 75; ++i)
                    {
                        //System.out.println("Run by " + Thread.currentThread().getName());
                        try
                        {
                            Thread.sleep(5000);
                            controller.updateDisplayNumber(sequence[i]);
                            interimSequence.add(sequence[i]);
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        t.setName("GUI");
        t.start();
    }

    public void handleCards()
    {
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                controller.displayCards(cards, cardSets);

                while(true)
                {
                    toServer.flush();
                    //System.out.println("Run by " + Thread.currentThread().getName());
                    boolean win = controller.getBingoStatus();
                    if(win)
                    {
                        //System.out.println("win");
                        int cardNumber = controller.getCardNumber();
                        int[] pattern = controller.getPattern();
                        try
                        {
                            // toServer.println(Arrays.toString(pattern));
                            // toServer.println(cardNumber);

                             String[] is = new String[interimSequence.size()];
                             is = interimSequence.toArray(is);

                            // toServer.println(Arrays.toString(is));
                            // System.out.println("sent");

                            // String response = in.readLine();

                            boolean w = controller.checkWinningCondition(cardNumber, pattern, is, cardSets);
                            //if(response.equals("Win"))
                            if(w)
                            {
                                //controller.annouceWinner();
                                toServer.println("W");
                            }
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        } 
                    }


                }
            }
        });

        t.setName("BINGO");
        t.start();
    }

    public void handleBingo()
    {
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                while(true){
                    try{
                        String message = in.readLine();
                        if(message != null)
                        {
                            //System.out.println("received message from server " + message);
                            if(message.charAt(0) == 'W'){
                                controller.annouceWinner(message.substring(2));
                            }
                            else if(message.charAt(0) == 'C'){
                                controller.appendChatBox(message.substring(2));
                            }
                        } 
                    }catch(IOException e){
                        e.printStackTrace();
                    } 
                }
            }
        });

        t.setName("Bingo");
        t.start();
    }

    public void handleChatMessages()
    {
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                while(true){
                    toServer.flush();
                    try{
                        String chatBoxMessage = controller.getChatBoxMessage();
                        if(chatBoxMessage != null){
                            //System.out.println("got message from controller");
                            toServer.println(": " + chatBoxMessage);
                            toServer.flush();
                            controller.resetMessage();
                        }  
                    }catch(Exception e){
                        e.printStackTrace();
                    } 
                }
            }
        });

        t.setName("Messages");
        t.start();
    }       

    public int[] convertCards(String array)
    {
        String[] items = array.replaceAll("\\[", "").replaceAll("\\]", "").split(", ");
        int[] convertedArray = new int[items.length];

        for (int i = 0; i < items.length; i++) {
            try {
                convertedArray[i] = Integer.parseInt(items[i]);
            } catch (NumberFormatException nfe) {};
        }

        return convertedArray;
    }

    public String[] convertPattern(String array)
    {
        String[] items = array.replaceAll("\\[", "").replaceAll("\\]", "").split(", ");

        return items;
    }

    public void showPattern(String message, int[] pattern)
    {
        System.out.println(message);
        for(int i = 0; i < 25; ++i)
        {
            System.out.println(pattern[i] + " ");
        }
        System.out.println(" ");
    }

	public static void main(String[] args) throws IOException
	{
        //handle socket connection with server
		if (args.length != 2) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try {
            clientSocket = new Socket(hostName, portNumber);
            toServer = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        }
        catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } 

        //Initialize bingo instance and prompt users to select number of cards
        model = new Bingo();
        view = new BingoView();
        controller = new BingoController(model, view);

        BingoClient client = new BingoClient();

        String fromServer = in.readLine();
        if(fromServer.equals("full")){
            System.out.println("Full already.");
        }
        else{
            System.out.println(fromServer);

            cards = controller.displayMainPage();
            toServer.println(cards);
            toServer.flush();

            cardSets = new int[cards][];
            for(int i = 0; i < cards; ++i)
            {
                try
                {
                    String cardSetInput = in.readLine();
                    //System.out.println(cardSetInput);
                    cardSets[i] = client.convertCards(cardSetInput);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

            //input sequence line
            sequenceInput = in.readLine();

            //handleGUI and handleCards here
            client.handleCards();
            client.handleGUI();
            client.handleBingo();
            client.handleChatMessages();
        }  
	}
}