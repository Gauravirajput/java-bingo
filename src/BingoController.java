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
	private int cardNumber = 0;

	public static void showPattern(String message, int[] pattern)
	{
		System.out.println(message);
		for(int i = 0; i < 25; ++i)
		{
			System.out.print(pattern[i] + " ");
		}
		System.out.println(" ");
	}

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

	public void displayCards(int numberOfCards, int[][] cards)
	{

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

	public int getCardNumber()
	{
		return cardNumber;
	}

	public boolean checkWinningCondition(int cardNumber, int[] pattern, String[] sequence, int[][] cardSet)
	{
		boolean validPattern = false;
		boolean win = false;

		int[] realSequence = new int[sequence.length];
		for(int i = 0; i < sequence.length; ++i)
		{
			realSequence[i] = Integer.parseInt(sequence[i].substring(1));
		}

		validPattern = model.checkPattern(pattern);
		//disabled for the moment for fast win
		// if(validPattern)
		// {
		// 	ArrayList<Integer> subPattern = new ArrayList<Integer>();
		// 	for(int i = 0; i < pattern.length; ++i)
		// 	{
		// 		if(pattern[i] == 1)
		// 		{
		// 			subPattern.add(cardSet[cardNumber][i]);
		// 		}
		// 	}
		// 	return model.checkNumber(subPattern, realSequence);
		// }
		// else
		// {
		// 	return false;
		// }

		return validPattern;
	}

	public void annouceWinner()
	{
		view.annouceWinner();
	}

	//When user clicked Bingo, get the pattern
	@Override
	public void update(Observable o, Object arg)
	{
		if(arg instanceof BingoCard)
		{
			BingoCard card = (BingoCard)arg;
			bingoPattern = card.getPattern();
			cardNumber = card.getCardNumber();
			//showPattern("Controller update", bingoPattern);
			bingoStatus = true;
		}
	}
}