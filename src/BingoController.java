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

	public void displayMainPage()
	{
		view.displayMainPage();
	}
}