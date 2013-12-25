package bingo;

import java.util.Observer;
import java.util.Observable;
import java.util.Arrays;
import java.util.ArrayList;

// Controller class
public class BingoController implements Observer{
	private Bingo model;
	private BingoView view;
	private boolean bingoStatus = false;
	private int[] bingoPattern;

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

	public boolean getBingoStatus()
	{
		boolean status = bingoStatus;
		bingoStatus = false;
		return status;
	}

	public int[] getPattern()
	{
		int[] pattern = bingoPattern;
		//Arrays.fill(pattern, 0);
		//showPattern("Controller getPattern", pattern);
		return pattern;
	}

	//When user clicked Bingo, get the pattern
	@Override
	public void update(Observable o, Object arg)
	{
		bingoPattern = (int[]) arg;
		//showPattern("Controller update", bingoPattern);
		bingoStatus = true;
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
}