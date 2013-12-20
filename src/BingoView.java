package bingo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
 		GridLayout mainPage = new GridLayout(2,2);
        JPanel mainPagePanel = new JPanel();
        mainPagePanel.setLayout(mainPage);

        // Initialize grid layout and several buttons for number of cards
		JButton oneCard = new JButton("1 Card");
		JButton twoCards = new JButton("2 Cards");
		JButton threeCards = new JButton("3 Cards");
		JButton fourCards = new JButton("4 Cards");

        mainPagePanel.add(oneCard);
        mainPagePanel.add(twoCards);
        mainPagePanel.add(threeCards);
        mainPagePanel.add(fourCards);

        // Add mainPage to the frame
        frame.getContentPane().add(mainPagePanel);
        oneCard.addMouseListener(new MainPageListener());
        twoCards.addMouseListener(new MainPageListener());
        threeCards.addMouseListener(new MainPageListener());
        fourCards.addMouseListener(new MainPageListener());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);

        return numberOfCards;
	}
}