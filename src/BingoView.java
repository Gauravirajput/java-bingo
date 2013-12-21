package bingo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

// View class
public class BingoView
{
	private JFrame frame;
	private int numberOfCards = 0;

	public BingoView()
	{
		//Create and set up the window.
        frame = new JFrame("Bingo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	class NumberButtonsListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			JButton button = (JButton)e.getSource();
            button.setBackground(Color.RED);
            button.setForeground(Color.WHITE);
		}
	}

    class BingoButtonListener extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e)
        {
            JButton button = (JButton)e.getSource();
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
        GridLayout mainPage = new GridLayout(numberOfCards, 0);
        JPanel mainPanel = new JPanel();
        
        mainPanel.setLayout(mainPage);

        JButton[] bingoButton = new JButton[3];
        for(int i = 0; i < numberOfCards; ++i)
        {
            JButton b = new JButton("BINGO!");
            bingoButton[i] = b;
            bingoButton[i].addMouseListener(new BingoButtonListener());
        }
        

        for(int j = 0; j < numberOfCards; ++j)
        {
            GridLayout cardLayout = new GridLayout(6,5);
            JPanel cardPanel = new JPanel();
            cardPanel.setLayout(cardLayout);

            GridLayout bingoLayout = new GridLayout(2,0);
            JPanel bingoPanel = new JPanel();
            //bingoPanel.setLayout(bingoLayout);
            bingoPanel.setBorder(new EmptyBorder(10, 10, 10, 10) );

            // Initialize grid layout and several buttons for number of cards
            String[] alphabet = {"B", "I", "N", "G", "O"};
            for(int i = 0; i < 5; ++i)
            {
                JLabel l = new JLabel(alphabet[i], SwingConstants.CENTER);
                cardPanel.add(l);
            }

            JButton[] numberButtons = new JButton[25];
            for(int i = 0; i < 25; ++i)
            { 
                numberButtons[i] = new JButton(Integer.toString(cards[j][i]));
                numberButtons[i].addMouseListener(new NumberButtonsListener());
                cardPanel.add(numberButtons[i]);
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