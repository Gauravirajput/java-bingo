import bingo.*;
import java.io.*; 
import java.net.*;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class BingoClient{

    public static Bingo model;
    public static BingoView view;
    public static BingoController controller;

    public void handleGUI(final String[] sequence)
    {
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
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

                    controller.updateDisplayNumber(sequence[i]);
                }
            }
        });

        t.start();
    }

    public void handleBingo(final PrintWriter out)
    {
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                while(true)
                {
                    boolean win = controller.getBingoStatus();
                    if(win)
                    {
                        int[] pattern = controller.getPattern();
                        //showPattern("BingoClient", pattern);
                        out.println(Arrays.toString(pattern));
                    }
                }
            }
        });

        t.start();
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
            System.out.print(pattern[i] + " ");
        }
        System.out.println(" ");
    }

	public static void main(String[] args) throws IOException
	{
		if (args.length != 2) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
            Socket clientSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {
            //Initialize bingo instance and prompt users to select number of cards
            model = new Bingo();
            view = new BingoView();
            controller = new BingoController(model, view);

            BingoClient client = new BingoClient();

            String fromServer;
            fromServer = in.readLine();
            System.out.println(fromServer);

            //split here: handle GUI/sequence
            //cards: number of cards selected by user
			int cards = controller.displayMainPage();
			out.println(cards);
            controller.displayCards(cards);

            String sequenceInput = in.readLine();
            String[] sequence = client.convertPattern(sequenceInput);

            //handleGUI and handleBIngo here
            client.handleGUI(sequence);
            client.handleBingo(out);
            

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
	}
}