package bingo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

// View class
public class BingoView
{
    //TODO setup global variable for pattern if user clicked BINGO
    //TODO make server listen to client for bingo

    // the array to store values inside the card when bingo is called
    int[][] bingoPattern;
    int numOfCards;
    int[][] bingoCards;

    private JFrame frame;
    private JButton[] bingoButton = new JButton[3];
    private JButton[][] numberButtons;

	public BingoView()
	{
		//Create and set up the window.
        frame = new JFrame("Bingo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

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
                        bingoPattern[j][i] = 1;
                    }  
                }
            }

            button.setBackground(Color.RED);
            button.setForeground(Color.WHITE);
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

    public void displayCards(int numberOfCards, int[][] cards)
    {
        //initialize the global variables
        numOfCards = numberOfCards;
        bingoCards = cards;
        bingoPattern = new int[numberOfCards][25];

        GridLayout mainPage = new GridLayout(numberOfCards, 0);
        JPanel mainPanel = new JPanel();
        
        mainPanel.setLayout(mainPage);

        for(int i = 0; i < numberOfCards; ++i)
        {
            JButton b = new JButton("BINGO!");
            bingoButton[i] = b;
            bingoButton[i].addActionListener(new BingoButtonListener());
        }
        
        numberButtons = new JButton[numberOfCards][25];
        for(int j = 0; j < numberOfCards; ++j)
        {
            GridLayout cardLayout = new GridLayout(6,5);
            JPanel cardPanel = new JPanel();
            cardPanel.setLayout(cardLayout);

            JPanel bingoPanel = new JPanel();
            bingoPanel.setBorder(new EmptyBorder(10, 10, 10, 10) );

            // Initialize grid layout and several buttons for number of cards
            String[] alphabet = {"B", "I", "N", "G", "O"};
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

        frame.getContentPane().add(mainPanel);
    

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

}