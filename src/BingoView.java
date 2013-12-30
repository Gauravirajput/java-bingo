package bingo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;

interface View {
    public void addObserver(Observer o);
}

// View class
public class BingoView extends Observable implements View{
    //TODO make server listen to client for bingo

    // the array to store values inside the card when bingo is called
    private int numOfCards;
    private int[][] bingoPattern;
    private int[][] bingoCards;
    private JFrame frame;
    private JButton[] bingoButton;
    private JButton[][] numberButtons;
    private JLabel[][] completeList = new JLabel[15][5];
    private JLabel numberDisplay = new JLabel("", SwingConstants.CENTER);
    private GridLayout mainPage;
    private JPanel mainPanel = new JPanel();
    private JSplitPane leftSplitPane;
    private JSplitPane rightSplitPane;
    private Color[] colours;
    private JTextField chatbox;
    private JTextArea cb;

	public BingoView()
	{
		//Create and set up the window.
        frame = new JFrame("Bingo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        numberDisplay.setFont(new Font("Serif", Font.PLAIN, 60));

        Color b = new Color(255, 168, 158);
        Color i = new Color(255, 250, 242);
        Color n = new Color(255, 243, 186);
        Color g = new Color(181, 247, 136);
        Color o = new Color(184, 230, 255);
        colours = new Color[]{b, i, n, g, o};
	}

    //Event Listeners
   class NumberButtonsListener implements ActionListener
   {
        public void actionPerformed(ActionEvent e)
        {
            JButton button = (JButton)e.getSource();
            String number = e.getActionCommand();
            outerloop:
            for(int j = 0; j < numOfCards; ++j)
            {
                for(int i = 0; i < 25; ++i)
                {
                    String comp = Integer.toString(bingoCards[j][i]);
                    if(button == numberButtons[j][i])
                    {
                        if(bingoPattern[j][i] == 1)
                        {
                            bingoPattern[j][i] = 0;
                            button.setBackground(null);
                            button.setForeground(Color.BLACK);
                        }
                        else
                        {
                            bingoPattern[j][i] = 1;
                            button.setBackground(Color.RED);
                            button.setForeground(Color.WHITE);
                        }
                    }  
                }
            }   
        }
    }
 
    class BingoButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            JButton button = (JButton)e.getSource();
 
            for(int i = 0; i < numOfCards; ++i)
            {
                if(button == bingoButton[i])
                {
                    //System.out.println("Hello");
                    BingoCard bingoCard= new BingoCard(i, bingoPattern[i]);
                    setChanged();
                    notifyObservers(bingoCard);
                }
            }
 
            button.setBackground(Color.GRAY);
        }
    }

    class ChatBoxButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("fired from action listener");
            String message = chatbox.getText();
            setChanged();
            notifyObservers(message);
        }
    }

	public int displayMainPage()
	{
        Object[] cards = {"1", "2", "3", "4"};

        String numberOfCard = (String)JOptionPane.showInputDialog(
                    frame,
                    "Select number of cards",
                    "select cards",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    cards,
                    "ham");

        return Integer.parseInt(numberOfCard);
	}

    public void updateDisplayNumber(final String number)
    {
        SwingWorker<Void, Void> worker = new SwingWorker<Void,Void>()
        {
            @Override
            protected Void doInBackground() throws Exception{
                Color c = Color.black;

                numberDisplay.setText(number);
                numberDisplay.setForeground(Color.red);

                int n = Integer.parseInt(number.substring(1));
                int column = -1;
                if(n >= 1 && n <= 15)
                {
                    column = 0;
                }
                else if(n >= 16 && n <= 30)
                {
                    column = 1;
                }
                else if(n >= 31 && n <= 45)
                {
                    column = 2;
                }
                else if(n >= 46 && n <= 60)
                {
                    column = 3;
                }
                else if(n >= 61 && n <= 75)
                {
                    column = 4;
                }
                else
                {
                    column = -1;
                }

                int row = n - (column * 15) - 1;

                completeList[row][column].setBackground(colours[column]);
                return null;
            }
        };

        worker.execute();
    }

    public void displayCards(int numberOfCards, int[][] cards)
    {
        //initialize the global variables
        numOfCards = numberOfCards;
        bingoCards = cards;
        bingoPattern = new int[numberOfCards][25];
        bingoButton = new JButton[numberOfCards];

        JPanel numberPanel = new JPanel(new BorderLayout());
        numberPanel.setPreferredSize(new Dimension(300, 100));
        numberPanel.add(numberDisplay, BorderLayout.CENTER);

        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add(numberPanel, c);
        
        String[] alphabet = {"B", "I", "N", "G", "O"}; 

        //initialize the main bingo board that labels down the sequence history
        GridLayout completeLayout = new GridLayout(16,5);
        JPanel completeListPanel = new JPanel();
        completeListPanel.setLayout(completeLayout);
        completeListPanel.setPreferredSize(new Dimension(150,400));
        for(int j = 0; j < 5; ++j)
        {
            JLabel bingoLabel = new JLabel(alphabet[j], SwingConstants.CENTER);
            bingoLabel.setOpaque(true);
            bingoLabel.setBackground(colours[j]);
            completeListPanel.add(bingoLabel);
            for(int i = 0; i < 15; ++i)
            {
                JLabel l = new JLabel(Integer.toString((j*15) + i + 1), SwingConstants.CENTER);
                //j,i because its vertical instead of horizontal
                completeList[i][j] = l;
                completeList[i][j].setOpaque(true);
            }
        }

        //then add to the panel
        for(int i = 0; i < 15; ++i)
        {
            for(int j = 0; j < 5; ++j)
            {
                completeListPanel.add(completeList[i][j]);

            }
        }

        //set each bingo card with BINGO buttons
        for(int i = 0; i < numberOfCards; ++i)
        {
            JButton b = new JButton("BINGO!");
            bingoButton[i] = b;
            bingoButton[i].addActionListener(new BingoButtonListener());
        }

        int row, col;
        switch(numberOfCards){
            case 1: row = 1; col = 1; break;
            case 2: row = 1; col = 2; break;
            case 3: row = 2; col = 2; break;
            case 4: row = 2; col = 2; break;
            default: row = 0; col = 0;
        }

        JPanel mainBingoPanel = new JPanel(new GridLayout(row, col));
        
        numberButtons = new JButton[numberOfCards][25];
        for(int j = 0; j < numberOfCards; ++j)
        {
            GridLayout cardLayout = new GridLayout(6,5);
            JPanel cardPanel = new JPanel();
            cardPanel.setLayout(cardLayout);

            JPanel bingoPanel = new JPanel(new BorderLayout());
            bingoPanel.setBorder(new EmptyBorder(10, 10, 10, 10) );

            // Initialize grid layout and several buttons for number of cards
            for(int i = 0; i < 5; ++i)
            {
                JLabel l = new JLabel(alphabet[i], SwingConstants.CENTER);
                cardPanel.add(l);
            }
            
            for(int i = 0; i < 25; ++i)
            { 
                numberButtons[j][i] = new JButton(Integer.toString(cards[j][i]));
                numberButtons[j][i].addActionListener(new NumberButtonsListener());
                cardPanel.add(numberButtons[j][i]);
            }
            
            bingoPanel.add(cardPanel, BorderLayout.NORTH);
            bingoPanel.add(bingoButton[j], BorderLayout.SOUTH);
            mainBingoPanel.add(bingoPanel);
        }

        c.gridx = 0;
        c.gridy = 1;
        mainPanel.add(mainBingoPanel, c);

        leftSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, completeListPanel, mainPanel);
        leftSplitPane.setDividerLocation(0.4);

        //chat panel
        JPanel chatPanel = new JPanel(new BorderLayout());
        JPanel chatboxPanel = new JPanel(new FlowLayout());
        chatbox = new JTextField();
        chatbox.setColumns(20);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ChatBoxButtonListener());
        chatboxPanel.add(chatbox);
        chatboxPanel.add(sendButton);
        cb = new JTextArea();
        cb.setEditable(false);
        chatPanel.add(cb, BorderLayout.NORTH);
        chatPanel.add(chatboxPanel, BorderLayout.SOUTH);
        rightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftSplitPane, chatPanel);
        rightSplitPane.setDividerLocation(0.8);

        //frame.getContentPane().add(mainPanel);
        frame.getContentPane().add(rightSplitPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public void annouceWinner(String winner)
    {
        JPanel winPanel = new JPanel();
        JLabel win = new JLabel("Player " + winner + " won the game!");
        winPanel.add(win);

        frame.getContentPane().remove(leftSplitPane);
        frame.getContentPane().add(winPanel);
        frame.invalidate();
        frame.validate();
    }

    public void appendChatBox(String message)
    {
        cb.append(message);
    }

}