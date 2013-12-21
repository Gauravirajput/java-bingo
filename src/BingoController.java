package bingo;

// Controller class
public class BingoController{
	private Bingo model;
	private BingoView view;

	public BingoController(Bingo model, BingoView view)
	{
		this.model = model;
		this.view = view;
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
}