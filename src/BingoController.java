package bingo;

import java.util.Observer;
import java.util.Observable;
import java.util.Arrays;
import java.util.ArrayList;

// Controller class
public class BingoController implements Observer{
	private Bingo model;
	private BingoView view;

	public BingoController(Bingo model, BingoView view)
	{
		this.model = model;
		this.view = view;
		view.addObserver(this);
	}

	public int displayMainPage()
	{
		int card = view.displayMainPage();
		return card;
	}

	public void displayCards(int numberOfCards)
	{
		//initialize array with n cards and 25 numbers for each card
		int[][] cards = new int[numberOfCards][25];
		for(int i = 0; i < numberOfCards; ++i)
		{
			//generate one set of randomized numbers of each card
			int[] cardSet = model.generateCardSet();
			cards[i] = cardSet;
		}

		view.displayCards(numberOfCards, cards);
	}

	public String[] getCallSequence()
	{
		return model.getCallSequence();
	}

	public void updateDisplayNumber(String fromServer)
	{
		view.updateDisplayNumber(fromServer);
	}

	//When user clicked Bingo, get the pattern
	@Override
	public void update(Observable o, Object arg)
	{
		int[] pattern = (int[]) arg;
		for(int j = 0; j < 25; ++j)
        {
            System.out.print(pattern[j] + " ");
        }
        System.out.println("");
	}
}