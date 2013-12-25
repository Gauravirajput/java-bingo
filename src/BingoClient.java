import bingo.*;
import java.io.*; 
import java.net.*;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class BingoClient{

    public static String[] convertPattern(String array)
    {
        String[] items = array.replaceAll("\\[", "").replaceAll("\\]", "").split(", ");

        return items;
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
            String fromServer;
            fromServer = in.readLine();
            System.out.println(fromServer);

            //Initialize bingo instance and prompt users to select number of cards
        	Bingo model = new Bingo();
        	BingoView view = new BingoView();

        	BingoController controller = new BingoController(model, view);

			int cards = controller.displayMainPage();
			out.println(cards);

            controller.displayCards(cards);

            String sequenceInput = in.readLine();
            String[] sequence = convertPattern(sequenceInput);

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
                boolean win = controller.getBingoStatus();
                if(win)
                {
                    int[] pattern = controller.getPattern();
                    //showPattern("BingoClient", pattern);
                    out.println(Arrays.toString(pattern));
                }
            }

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