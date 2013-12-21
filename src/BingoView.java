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

	class MainPageListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			JButton button = (JButton)e.getSource();
            char label = button.getText().charAt(0);
            numberOfCards = Character.getNumericValue(label);
		}
	}

	public int displayMainPage()
	{
        Object[] cards = {"1", "2", "3"};

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
        GridLayout mainPage = new GridLayout(0, numberOfCards);
        JPanel mainPanel = new JPanel();
        
        mainPanel.setLayout(mainPage);

        for(int j = 0; j < numberOfCards; ++j)
        {
            GridLayout cardLayout = new GridLayout(6,5);
            JPanel cardPanel = new JPanel();
            cardPanel.setLayout(cardLayout);
            cardPanel.setBorder(new EmptyBorder(10, 10, 10, 10) );

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
                cardPanel.add(numberButtons[i]);
            }

            mainPanel.add(cardPanel);
        }

        frame.getContentPane().add(mainPanel);
    
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

}