package bingo;

import java.awt.*;
import javax.swing.*;

// View class
public class BingoView
{
	private JFrame frame;

	public BingoView()
	{
		//Create and set up the window.
        frame = new JFrame("Bingo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void displayMainPage()
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
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}
}