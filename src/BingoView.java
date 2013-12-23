package bingo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.List;

// View class
public class BingoView
{
    //TODO make server listen to client for bingo

    // the array to store values inside the card when bingo is called
    private int numOfCards;
    private int[][] bingoPattern;
    private int[][] bingoCards;
    private JFrame frame;
    private JButton[] bingoButton = new JButton[3];
    private JButton[][] numberButtons;
    private JLabel[][] completeList = new JLabel[15][5];
    private JLabel numberDisplay = new JLabel("", SwingConstants.CENTER);
    private GridLayout mainPage;
    private JPanel mainPanel = new JPanel();

    private class ListenBingo extends SwingWorker<Void, Void>{
        @Override
        protected Void doInBackground(){
            return null;
        }

        protected Void process(){
            return null;
        }
    }

	public BingoView()
	{
		//Create and set up the window.
        frame = new JFrame("Bingo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        numberDisplay.setFont(new Font("Serif", Font.PLAIN, 60));
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
                    for(int j = 0; j < 25; ++j)
                    {
                        System.out.print(bingoPattern[i][j] + " ");
                    }
                    System.out.println("");
                }
            }

            button.setBackground(Color.GRAY);
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

    public void updateDisplayNumber(String number)
    {
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

        completeList[row][column].setBackground(Color.green);
    }

    public void displayCards(int numberOfCards, int[][] cards)
    {
        mainPage = new GridLayout(numberOfCards + 2, 0);
        mainPanel.setLayout(mainPage);
        mainPanel.add(numberDisplay);

        //initialize the global variables
        numOfCards = numberOfCards;
        bingoCards = cards;
        bingoPattern = new int[numberOfCards][25];
        
        String[] alphabet = {"B", "I", "N", "G", "O"};

        for(int i = 0; i < numberOfCards; ++i)
        {
            JButton b = new JButton("BINGO!");
            bingoButton[i] = b;
            bingoButton[i].addActionListener(new BingoButtonListener());
        }
        
        //initialize each bingo cards
        numberButtons = new JButton[numberOfCards][25];
        for(int j = 0; j < numberOfCards; ++j)
        {
            GridLayout cardLayout = new GridLayout(6,5);
            JPanel cardPanel = new JPanel();
            cardPanel.setLayout(cardLayout);

            JPanel bingoPanel = new JPanel();
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
            bingoPanel.add(bingoButton[j], BorderLayout.EAST);
            mainPanel.add(bingoPanel);
        }

        //initialize the main bingo board
        GridLayout completeLayout = new GridLayout(16,5);
        JPanel completeListPanel = new JPanel();
        completeListPanel.setLayout(completeLayout);
        for(int j = 0; j < 5; ++j)
        {
            JLabel bingoLabel = new JLabel(alphabet[j], SwingConstants.CENTER);
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

        mainPanel.add(completeListPanel);

        frame.getContentPane().add(mainPanel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

}